package sample.App.controllers.GestionMateriel;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import sample.App.model.Engin;
import sample.App.model.Materiel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class ConsultationMateriel implements Initializable {

    @FXML
    private Label LabelNbInter;

    @FXML
    private Label LabelNbTermine;

    @FXML
    private Label LabelNbAnnule;

    static public boolean  itsokay = false ;
    @FXML
    TableView<Materiel> tableView;
    @FXML
    TableColumn <Materiel,String> typeCol ;
    @FXML
    TableColumn <Materiel,String> qteCol ;
    @FXML
    TableColumn <Materiel,String> modifierCol ;
    @FXML
    TableColumn <Materiel,String> consomablecol ;
    @FXML
    Button addButton ;
    @FXML
    Button deleteButton;
    @FXML
    Button refresh;
    @FXML
    private TextField filterField;
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet rs = null ;
    Materiel materiel = null ;
    ObservableList<Materiel> items;
    ObservableList<Materiel> oblist;
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
            ResultSet rs = connection.createStatement().executeQuery("select count(*) nb from MATERIEL");
            while(rs.next()){
                LabelNbInter.setText(rs.getString("nb"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select sum(qte) nb from MATERIEL ");
            while(rs.next()){
                //LabelNbInitial.setText(rs.getString("nb"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select count(*) nb from MATERIEL where CONSOMABLE = 'Non'");
            while(rs.next()){
                LabelNbAnnule.setText(rs.getString("nb"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select count(*) nb from MATERIEL where CONSOMABLE = 'Oui'");
            while(rs.next()){
                LabelNbTermine.setText(rs.getString("nb"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void filter(){
        FilteredList<Materiel> filteredData = new FilteredList<>(oblist, b -> true);

        filterField.textProperty().addListener((observable ,oldValue, newValue)->{filteredData.setPredicate(materiel -> {

            if(newValue == null || newValue.isEmpty()){
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            if (materiel.getDesignation().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter type
            }else if (materiel.getConsom().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter type
            }else
                return false;//doesn't match
        });});
        SortedList<Materiel> sortedData= new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
    private void initTable(){
        initCols();
    }
    private void initCols(){

        typeCol.setCellValueFactory(
                new PropertyValueFactory<>("designation")
        );
        qteCol.setCellValueFactory(
                new PropertyValueFactory<>("qte")
        );



        //add cell of button edit
        Callback<TableColumn<Materiel, String>, TableCell<Materiel, String>> cellFoctory = (TableColumn<Materiel, String> param) -> {
            // make cell containing buttons
            final TableCell<Materiel, String> cell = new TableCell<Materiel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView viewIcon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
//                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        viewIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:linear-gradient(#0288D1 17%, #e7e5e5 100%);"
                        );
                        /*editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:linear-gradient(#4E342E 17%, #e7e5e5 100%);"
                        );*/
                        viewIcon.setOnMouseClicked((MouseEvent event) -> {

                            materiel = tableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("../../view/Materiel/MaterielAfficher.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ConsultationMateriel.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AfficherMateriel addMaterielController = loader.getController();
                            addMaterielController.setTextField(materiel.getDesignation(),""+materiel.getQte(),materiel.getConsom());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.showAndWait();

                        });
                       /* editIcon.setOnMouseClicked((MouseEvent event) ->{

                            materiel = tableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("../../view/Materiel/MaterielUpdate.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(sample.App.controllers.GestionMateriel.ConsultationMateriel.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            UpdateMateriel addMaterielController = loader.getController();
                            addMaterielController.setTextField(materiel.getId(), materiel.getDesignation(),materiel.getQte());
                            Parent parent = loader.getRoot();

                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.showAndWait();
                            refresh();

                        });*/

                        HBox managebtn = new HBox(viewIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(viewIcon, new Insets(2, 2, 0, 3));
//                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };
            return cell;
        };
        modifierCol.setCellFactory(cellFoctory);

        consomablecol.setCellValueFactory(
                new PropertyValueFactory<>("consom")
        );

    }
    private void loadData(){
        stats();
        oblist = FXCollections.observableArrayList();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from Materiel ");
            while(rs.next()){
                oblist.add(new Materiel(
                        rs.getString("designation"),
                        rs.getInt("qte"),
                        rs.getString("CONSOMABLE")
                ));
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
            root = FXMLLoader.load(getClass().getResource("../../view/materiel/MaterielCreation.fxml"));
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
        //filter();
    }
    @FXML
    public void refresh(){
        loadData();
        //filter();
    }
    @FXML
    void supprimer(ActionEvent event) throws URISyntaxException {
        String s="";
        String s1 = null;
        ObservableList<Materiel>  ob = tableView.getSelectionModel().getSelectedItems();
        if (ob.toArray().length != 0) {
            for (Materiel per : ob){
                s+=per.getDesignation()+"///";
                s1+=per.getDesignation();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText(null);
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.setContentText("Etes-vous sure de supprimer Materiel n°: ///"+s);
            alert.setGraphic(new ImageView(getClass().getResource("../../../images/delete.png").toURI().toString() ));
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    for (Materiel per : ob) {
                        try {
                            Connection connection = getOracleConnection();
                            connection.createStatement().executeQuery("delete from Materiel where " + "\'" + per.getDesignation() + "\'" + "=DESIGNATION");
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
