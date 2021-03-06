package sample.App.controllers.GestionDoleance;

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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import sample.App.controllers.Authentification;
import sample.App.model.Doleance;
import sample.App.model.Engin;
import sample.App.model.Permission;

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

public class ControllerConsulterDoleance implements Initializable {
    @FXML
    private Label LabelNbInter;

    @FXML
    private Label LabelNbInitial;

    @FXML
    private Label LabelNbEnCours;

    @FXML
    private Label LabelNbTermine;

    @FXML
    private Label LabelNbAnnule;

    static public boolean  itsokay = false ;
    @FXML
    TableView<Doleance> tableView;

    @FXML
    TableColumn <Doleance,String> idCol ;
    @FXML
    TableColumn <Doleance,String> typeCol ;
    @FXML
    TableColumn <Doleance,String> fromCol ;
    @FXML
    TableColumn <Doleance,String> cinCol ;
    @FXML
    TableColumn <Doleance,String> modifierCol ;
    @FXML
    TableColumn <Doleance,String> datecol ;

    @FXML
    TableColumn <Doleance,String> StatusCol ;


    @FXML
    Button addButton ;

    @FXML
    Button deleteButton;

    @FXML
    Button refresh;

    @FXML
    private TextField filterField;


    private sample.App.controllers.gInterfaceController it ;

    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet rs = null ;
    Doleance doleance = null ;

    ObservableList<Doleance> items;
    ObservableList<Doleance> oblist;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        initTable();
        loadData();
        filter();
        stats();
        EtatColorTable();
    }
    private void stats(){
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select count(*) nb from doleance");
            while(rs.next()){
                LabelNbInter.setText(rs.getString("nb"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select count(*) nb from doleance where Status = 'Initiale'");
            while(rs.next()){
                LabelNbInitial.setText(rs.getString("nb"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select count(*) nb from doleance where Status = 'EnCours'");
            while(rs.next()){
                LabelNbEnCours.setText(rs.getString("nb"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select count(*) nb from doleance where status = 'Approuv??' or status = 'Termin??'");
            while(rs.next()){
                LabelNbTermine.setText(rs.getString("nb"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select count(*) nb from doleance where status = 'Refus??'");
            while(rs.next()){
                LabelNbAnnule.setText(rs.getString("nb"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    private void filter(){
        FilteredList<Doleance> filteredData = new FilteredList<>(oblist, b -> true);

        filterField.textProperty().addListener((observable ,oldValue, newValue)->{filteredData.setPredicate(dol -> {

            if(newValue == null || newValue.isEmpty()){
                return true;
            }

            //Comparer le nom et le prenom avec tous les Doleances aves filterField
            String lowerCaseFilter = newValue.toLowerCase();

            if (dol.getId().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter id
            }
            else if (dol.getType().toString().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter type
            }
            else if (dol.getNom().toString().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter type
            }
            else if (dol.getCin().toString().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter type
            }
            else if (dol.getStatus().toString().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter type
            }
            else if (dol.getDates().toString().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter type
            }
            else
                return false;//doesn't match
        });});
        SortedList<Doleance> sortedData= new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
    private void initTable(){ initCols();
    }
    private void initCols(){

        typeCol.setCellValueFactory(
                new PropertyValueFactory<>("type")
        );
        fromCol.setCellValueFactory(
                new PropertyValueFactory<>("nom")
        );
        StatusCol.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );
        datecol.setCellValueFactory(
                new PropertyValueFactory<>("dates")
        );
        //add cell of button edit
        Callback<TableColumn<Doleance, String>, TableCell<Doleance, String>> cellFoctory = (TableColumn<Doleance, String> param) -> {
            // make cell containing buttons
            final TableCell<Doleance, String> cell = new TableCell<Doleance, String>() {
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

                            doleance = tableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("../../view/doleance/DoleanceAfficher.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ControllerConsulterDoleance.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            ControllerAfficherDoleance addDoleanceController = loader.getController();
                            addDoleanceController.setTextField(doleance.getId(), doleance.getType(),doleance.getNom(),doleance.getCin(),doleance.getDescription(),doleance.getStatus(),doleance.getTel(),doleance.getMail(),doleance.getAdr(),doleance.getDates());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.showAndWait();

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) ->{
                            if (Authentification.role.equals("Direction")) {
                            doleance = tableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("../../view/doleance/DoleanceUpdateSuper.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ControllerConsulterDoleance.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                ControllerUpdateDoleanceSuper addDoleanceController = loader.getController();
                                addDoleanceController.setTextField(doleance.getId(), doleance.getType(), doleance.getNom(), doleance.getCin(), doleance.getDescription(), doleance.getStatus(), doleance.getTel(), doleance.getMail(), doleance.getAdr(),doleance.getDates());
                                Parent parent = loader.getRoot();

                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.showAndWait();
                                refresh();
                            }
                            else{
                                doleance = tableView.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader ();
                                loader.setLocation(getClass().getResource("../../view/doleance/DoleanceUpdate.fxml"));
                                try {
                                    loader.load();
                                } catch (IOException ex) {
                                    Logger.getLogger(ControllerConsulterDoleance.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ControllerUpdateDoleance addDoleanceController = loader.getController();
                                addDoleanceController.setTextField(doleance.getId(), doleance.getType(), doleance.getNom(), doleance.getCin(), doleance.getDescription(), doleance.getStatus(), doleance.getTel(), doleance.getMail(), doleance.getAdr(),doleance.getDates());
                                Parent parent = loader.getRoot();

                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.showAndWait();
                                refresh();
                            }

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

        cinCol.setCellValueFactory(
                new PropertyValueFactory<>("cin")
        );

        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
    }
    private void loadData(){
        stats();
        oblist = FXCollections.observableArrayList();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from Doleance ");
            while(rs.next()){
                oblist.add(new Doleance(rs.getString("id"),rs.getString("type"),rs.getString("nom"),rs.getString("cin"),rs.getString("status"),rs.getString("description"),rs.getString("tel"),rs.getString("mail"),rs.getString("adr"),rs.getDate("dates")));
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
            root = FXMLLoader.load(getClass().getResource("../../view/doleance/DoleanceCreation.fxml"));
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
        ObservableList<Doleance>  ob = tableView.getSelectionModel().getSelectedItems();
        if (ob.toArray().length != 0) {
            for (Doleance per : ob){
                s+=per.getId()+"///";
                s1+=per.getId();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText(null);
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.setContentText("Etes-vous sure de supprimer doleance  de n??: ///"+s);
            alert.setGraphic(new ImageView(getClass().getResource("../../../images/delete.png").toURI().toString() ));
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    for (Doleance per : ob) {
                        try {
                            Connection connection = getOracleConnection();
                            connection.createStatement().executeQuery("delete from doleance where " + "\'" + per.getId() + "\'" + "=ID");
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
    private void EtatColorTable() {
        loadData();
        StatusCol.setCellFactory(new Callback<TableColumn<Doleance, String>,
                TableCell<Doleance, String>>()
        {
            @Override
            public TableCell<Doleance, String> call(
                    TableColumn<Doleance, String> param) {
                return new TableCell<Doleance, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (!empty) {
                            int currentIndex = indexProperty()
                                    .getValue() < 0 ? 0
                                    : indexProperty().getValue();
                            String clmStatus = param
                                    .getTableView().getItems()
                                    .get(currentIndex).getStatus();
//                            if (clmStatus != null) {
                            if (clmStatus.equals("Termin??")) {
                                setStyle("-fx-text-fill : #4CAF50");
                                setFont(Font.font("Impact", 20));
                                setText(clmStatus);
                            } else if (clmStatus.equals("EnCours")) {
                                setStyle("-fx-text-fill : #FFC107");
                                setFont(Font.font("Impact", 20));
                                setText(clmStatus);
                            } else if (clmStatus.equals("Initial")) {
                                setStyle("-fx-text-fill : #26bfbc");
                                setFont(Font.font("Impact", 20));
                                setText(clmStatus);
                            } else if (clmStatus.equals("Annul??")) {
                                setStyle("-fx-text-fill : #fb3232");
                                setFont(Font.font("Impact", 20));
                                setText(clmStatus);
                            } else if (clmStatus.equals("Initiale")) {
                                setStyle("-fx-text-fill : #48c7c7");
                                setFont(Font.font("Impact", 20));
                                setText(clmStatus);
                            }
                            else if (clmStatus.equals("Approuv??")) {
                                setStyle("-fx-text-fill : #2a73ff");
                                setFont(Font.font("Impact", 20));
                                setText(clmStatus);
                            }
                        } else {
                            setText(null);
                            setStyle(null);
                        }

                    }
                };
            }
        }); }



}
