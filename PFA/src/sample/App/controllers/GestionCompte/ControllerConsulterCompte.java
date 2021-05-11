package sample.App.controllers.GestionCompte;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
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

import java.io.IOException;
import java.net.URISyntaxException;
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
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
    private void initTable(){
        initCols();
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
                            loader.setLocation(getClass().getResource("../../view/compte/CompteAfficher.fxml"));
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
                            loader.setLocation(getClass().getResource("../../view/compte/CompteUpdate.fxml"));
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
            root = FXMLLoader.load(getClass().getResource("../../view/compte/CompteCreation.fxml"));
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
    void supprimer(ActionEvent event) throws URISyntaxException {
        String s="";
        String s1 = null;
        ObservableList<Compte>  ob = tableView.getSelectionModel().getSelectedItems();
        if (ob.toArray().length != 0) {
            for (Compte per : ob){
                s+=per.getMatricule()+"///";
                s1+=per.getMatricule();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText(null);
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.setContentText("Etes-vous sure de supprimer les comptes  de n°: ///"+s);
            alert.setGraphic(new ImageView(getClass().getResource("../../../images/delete.png").toURI().toString() ));
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    for (Compte per : ob) {
                        try {
                            Connection connection = getOracleConnection();
                            connection.createStatement().executeQuery("delete from Compte where " + "\'" + per.getMatricule() + "\'" + "=Matricule");
                            connection.close();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }

                }
            });
        }
        loadData();
    }




}
