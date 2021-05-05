package sample.App.controllers.GestionCompte.ConsultationCompte;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import sample.App.FxmlLoader;
import sample.App.controllers.GestionCompte.AfficherCompte.ControllerAfficherCompte;
import sample.App.controllers.GestionCompte.SuppressionCompte.ControllerSupprimerCompte;
import sample.App.controllers.GestionCompte.UpdateCompte.ControllerUpdateCompte;
import sample.App.model.Compte;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import sample.App.controllers.GestionCompte.CreationCompte.ControllerCreationCompte;
import sample.App.model.Compte;
import sample.App.model.Personnel;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class ControllerConsulterCompte implements Initializable {


    static public boolean  itsokay = false ;

    @FXML
    private Label LabelNbInter;

    @FXML
    private Label LabelNbInitial;

    @FXML
    private Label LabelNbEnCours;

    @FXML
    private Label LabelNbTermine;

    @FXML
    TableView<Compte> tableView;

    @FXML
    TableColumn <Compte,String> matriculeCol ;
    @FXML
    TableColumn <Compte,String> roleCol ;
    @FXML
    TableColumn <Compte,String> passCol ;
    @FXML
    TableColumn <Compte,String> modifierCol ;

    @FXML
    private TableColumn<Compte, CheckBox> col_select;

    @FXML
    Button addButton ;

    @FXML
    Button deleteButton;

    @FXML
    Button refresh;


    @FXML
    private CheckBox check_selAll;

    @FXML
    private TextField filterField;


    private sample.App.controllers.gInterfaceController it ;

    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet rs = null ;
    Compte compte = null ;

    ObservableList<Compte> items;
    ObservableList<Compte> oblist;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        loadData();
        filter();
        stats();
    }
    private void stats(){
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select count(*) nb from COMPTE ");
            while(rs.next()){
                LabelNbInter.setText(rs.getString("nb"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select count(*) nb from compte where role = 'Administration'");
            while(rs.next()){
                LabelNbInitial.setText(rs.getString("nb"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select count(*) nb from compte where role = 'Direction'");
            while(rs.next()){
                LabelNbEnCours.setText(rs.getString("nb"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select count(*) nb from compte where role = 'Financier'");
            while(rs.next()){
                LabelNbTermine.setText(rs.getString("nb"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    private void filter(){
        FilteredList<Compte> filteredData = new FilteredList<>(oblist, b -> true);

        filterField.textProperty().addListener((observable ,oldValue, newValue)->{filteredData.setPredicate(compte -> {

            if(newValue == null || newValue.isEmpty()){
                return true;
            }

            //Comparer le nom et le prenom avec tous les comptes aves filterField
            String lowerCaseFilter = newValue.toLowerCase();

            if (compte.getPer().getMatricule().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter nom
            }else if (compte.getPass().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter prenom
            }else if (compte.getRole().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter role
            }else
                return false;//doesn't match
        });});
        SortedList<Compte> sortedData= new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
    private void checkAll(){
        check_selAll.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                items = tableView.getItems();
                for (Compte item : items){
                    if(check_selAll.isSelected())
                        item.getCheck().setSelected(true);
                    else
                        item.getCheck().setSelected(false);
                    if(!item.getCheck().isSelected()){
                        check_selAll.setSelected(false);
                    }

                }
            }
        });
    }
    private void initTable(){
        initCols();
        checkAll();
        //uncheckAll();
    }
    private void initCols(){

        matriculeCol.setCellValueFactory(
                new PropertyValueFactory<>("matricule")
        );
        passCol.setCellValueFactory(
                new PropertyValueFactory<>("pass")
        );
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        //modifierCol.setCellValueFactory(new PropertyValueFactory<>("modify"));


        //add cell of button edit
        Callback<TableColumn<Compte, String>, TableCell<Compte, String>> cellFoctory = (TableColumn<Compte, String> param) -> {
            // make cell containing buttons
            final TableCell<Compte, String> cell = new TableCell<Compte, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView viewIcon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        viewIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:linear-gradient(#0288D1 17%, #e7e5e5 100%);"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:linear-gradient(#4E342E 17%, #e7e5e5 100%);"
                        );
                        viewIcon.setOnMouseClicked((MouseEvent event) -> {

                            compte = tableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("../../../view/CompteAfficher.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ControllerConsulterCompte.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            ControllerAfficherCompte addCompteController = loader.getController();
                            addCompteController.setTextField(compte.getPer().getMatricule(), compte.getPer().getMatricule(),compte.getPass(),compte.getRole());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.showAndWait();

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) ->{

                            compte = tableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("../../../view/CompteUpdate.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ControllerConsulterCompte.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            ControllerUpdateCompte addCompteController = loader.getController();
                            addCompteController.setUpdate(true);
                            addCompteController.setTextField(compte.getPer().getMatricule(), compte.getPer().getMatricule(),compte.getPass(),compte.getRole());
                            Parent parent = loader.getRoot();

                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.showAndWait();
                            refresh();

                        });

                        HBox managebtn = new HBox(editIcon,viewIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(viewIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        modifierCol.setCellFactory(cellFoctory);


        col_select.setCellValueFactory(new PropertyValueFactory<>("check"));
    }
    private void loadData(){
        stats();
        oblist = FXCollections.observableArrayList();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from COMPTE ");
            while(rs.next()){
                oblist.add(new Compte(rs.getString("matricule"),rs.getString("pass"),"",rs.getString("image"),rs.getString("role")));
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tableView.setItems(oblist);
    }
    @FXML
    public void ajouter (ActionEvent event)  {
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../../../view/CompteCreation.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Municipal");
        assert root != null;
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.showAndWait();
        refresh();
    }
    @FXML
    public void refresh(ActionEvent event){
        loadData();
        filter();
    }
    @FXML
    public void refresh(){
        loadData();
        filter();
    }
    @FXML
    public void supprimer(ActionEvent event) {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../../view/CompteDelete.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsulterCompte.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();


        if (itsokay) {
            ObservableList<Compte> list = tableView.getItems();
            for (Compte item : list) {
                if (item.getCheck().isSelected()) {
                    try {
                        query = "DELETE FROM COMPTE WHERE matricule  =" + item.getPer().getMatricule();
                        Connection connection = getOracleConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.execute();
                        refresh();

                    } catch (SQLException ex) {
                        Logger.getLogger(ControllerConsulterCompte.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }



}
