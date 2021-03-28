package sample.App.controllers.GestionTache.ConsultationTache;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import sample.App.model.Compte;
import sample.App.controllers.GestionTache.AfficherTache.ControllerAfficherTache;
import sample.App.controllers.GestionTache.CreationTache.ControllerCreationTache;
import sample.App.model.Tache;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class ControllerConsulterTache implements Initializable {


    static public boolean  itsokay = false ;
    @FXML
    TableView<Tache> tableView;

    @FXML
    TableColumn <Tache,String> IdCol ;
    @FXML
    TableColumn <Tache,String> nomCol ;
    @FXML
    TableColumn <Tache,Date> debutCol ;
    @FXML
    TableColumn <Tache,Date> finCol ;
    @FXML
    TableColumn <Tache,String> modifierCol ;

    @FXML
    TableColumn<Tache, CheckBox> col_select;

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
    Tache tache = null ;

    ObservableList<Tache> items;
    ObservableList<Tache> oblist;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        loadData();
        filter();
    }

    
    private void filter(){
        FilteredList<Tache> filteredData = new FilteredList<>(oblist, b -> true);

        filterField.textProperty().addListener((observable ,oldValue, newValue)->{filteredData.setPredicate(tache -> {

            if(newValue == null || newValue.isEmpty()){
                return true;
            }

            //Comparer le nom et le prenom avec tous les taches aves filterField
            String lowerCaseFilter = newValue.toLowerCase();

            if (tache.getIdTache().toLowerCase().indexOf(lowerCaseFilter)!= -1) {return true; }
            else if (tache.getNomTache().toLowerCase().indexOf(lowerCaseFilter)!= -1) { return true; }
            //else if (tache.getDescriptionTache().toLowerCase().indexOf(lowerCaseFilter)!= -1) { return true; }
            //else if (tache.getDateDebut().toLowerCase().indexOf(lowerCaseFilter)!= -1) { return true; }
           // else if (tache.getDateFin().toLowerCase().indexOf(lowerCaseFilter)!= -1) { return true; }
            else
                return false;//doesn't match
        });});
        SortedList<Tache> sortedData= new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }


    private void checkAll(){
        check_selAll.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                items = tableView.getItems();
                for (Tache item : items){
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

        IdCol.setCellValueFactory(
                new PropertyValueFactory<>("IdTache")
        );
        nomCol.setCellValueFactory(
                new PropertyValueFactory<>("NomTache")
        );
        debutCol.setCellValueFactory(
                new PropertyValueFactory<>("DateDebut")
        );

        finCol.setCellValueFactory(
                new PropertyValueFactory<>("DateFin")
        );


        //add cell of button edit
        Callback<TableColumn<Tache, String>, TableCell<Tache, String>> cellFoctory = (TableColumn<Tache, String> param) -> {
            // make cell containing buttons
            final TableCell<Tache, String> cell = new TableCell<Tache, String>() {
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

                            tache = tableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("../../../view/TacheAfficher.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ControllerConsulterTache.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            ControllerAfficherTache addTacheController = loader.getController();
                            addTacheController.setTextField(tache.getIdTache(), tache.getIdTache(),tache.getNomTache(),tache.getDescriptionTache(),tache.getDateDebut(),tache.getDateFin());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.showAndWait();

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) ->{

                            tache = tableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("../../../view/TacheCreation.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ControllerConsulterTache.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            ControllerCreationTache addTacheController = loader.getController();
                            addTacheController.setUpdate(true);
                            addTacheController.setTextField(tache.getIdTache(), tache.getIdTache(),tache.getNomTache(),tache.getDescriptionTache(),tache.getDateDebut(),tache.getDateFin());
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

        oblist = FXCollections.observableArrayList();
        try {

            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from TACHE ");


            while(rs.next()){

                oblist.add(new Tache(rs.getString("IdTache"),rs.getString("NomTache"),rs.getString("Description"),rs.getDate("Debut"),rs.getDate("Fin"),""));

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
            root = FXMLLoader.load(getClass().getResource("../../../view/TacheCreation.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Municipal");
        assert root != null;
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.showAndWait();
        refresh();
        filter();
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
        loader.setLocation(getClass().getResource("../../../view/TacheDelete.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsulterTache.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();


        if (itsokay) {
            ObservableList<Tache> list = tableView.getItems();
            for (Tache item : list) {
                if (item.getCheck().isSelected()) {
                    try {
                        query = "DELETE FROM counts WHERE cin  =" + item.getIdTache();
                        Connection connection = getOracleConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.execute();
                        refresh();

                    } catch (SQLException ex) {
                        Logger.getLogger(ControllerConsulterTache.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }

}
