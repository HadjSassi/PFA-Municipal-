package sample.App.controllers;


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
import sample.App.model.Personnel;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class gPersonnelController implements Initializable {

    @FXML
    private TableView<Personnel> table_info;

    @FXML
    private TableColumn<Personnel, Integer> col_id;

    @FXML
    private TableColumn<Personnel, String> col_nom;

    @FXML
    private TableColumn<Personnel, String> col_prenom;

    @FXML
    private TableColumn<Personnel, String> col_service;

    @FXML
    private TableColumn<Personnel, Integer> col_salaire;

    @FXML
    private TableColumn<Personnel, Date> col_naissance;

    @FXML
    private TableColumn<Personnel, String> col_sex;

    @FXML
    private TableColumn<Personnel, String> col_cin;

    @FXML
    private TableColumn<Personnel, String> col_edit;

    @FXML
    private TableColumn<Personnel, CheckBox> col_select;

    @FXML
    private CheckBox check_selAll;

    @FXML
    private TextField  filterField;
    private double x, y;
    private boolean s=true;
    private Personnel personnel=null;
    @FXML
    void handleAjoutPersonnel(ActionEvent event) throws IOException {
        if (s){Parent root = FXMLLoader.load(getClass().getResource("../view/gPerAdd.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.focusedProperty().addListener((ov, onHidden, onShown) -> {
                if (!stage.isShowing()){
                    loadData();
                }


            });
            //drag it here
            root.setOnMousePressed(event1 -> {
                x = event1.getSceneX();
                y = event1.getSceneY();
            });
            root.setOnMouseDragged(event1 -> {

                stage.setX(event1.getScreenX() - x);
                stage.setY(event1.getScreenY() - y);

            });
            stage.show();
        }}
    @FXML
    void handleClicksRefresh(ActionEvent event) {
        loadData();
        filter();
        check_selAll.setSelected(false);

    }
    @FXML
    void handleClicksDeleteSelected(ActionEvent event) throws URISyntaxException {
        String s="";
        String s1 = null;
        for(Personnel per:oblist){
            if(per.getCheck().isSelected()){
                s+=per.getMatricule()+"///";
                s1=per.getMatricule();
            }}int so=0;
        AtomicBoolean del = new AtomicBoolean(true);
        for(Personnel per:oblist){

            if(per.getCheck().isSelected() && so==0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setHeaderText(null);
                ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(okButton, noButton);
                alert.setContentText("Etes-vous sure de supprimer le personnel  de n°: ///"+s);
                alert.setGraphic(new ImageView(getClass().getResource("../../images/delete.png").toURI().toString() ));
                alert.showAndWait().ifPresent(type -> {
                    if (type == okButton) {
                        del.set(true);
                    } else if (type == noButton) {
                        del.set(false);
                    }
                });
                so++;
            }
            if(per.getCheck().isSelected() && del.get()){
                try {
                    Connection connection= getOracleConnection();
                    ResultSet rs = connection.createStatement().executeQuery("delete from PERSONNEL where "+per.getMatricule()+"=matricule");
                    connection.close();
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        loadData();
        check_selAll.setSelected(false);
    }

    ObservableList<Personnel> oblist;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        loadData();
        checkAll();
    }
    private void filter(){
        FilteredList<Personnel> filteredData = new FilteredList<>(oblist,b -> true);

        filterField.textProperty().addListener((observable ,oldValue, newValue)->{filteredData.setPredicate(personnel -> {

            if(newValue == null || newValue.isEmpty()){
                return true;
            }

            //Comparer le nom et le prenom avec tous les personnels aves filterField
            String lowerCaseFilter = newValue.toLowerCase();

            if (personnel.getNom().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter nom
            }else if (personnel.getPrenom().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter prenom
            }else if(personnel.getService().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter service
            }else if(personnel.getSex().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
                return true;//filter sex
            }else if(String.valueOf(personnel.getMatricule()).indexOf(lowerCaseFilter)!= -1) {
                return true;//filter matricule
            }else if(String.valueOf(personnel.getCin()).indexOf(lowerCaseFilter)!= -1) {
                return true;//filter cin
            }else if(String.valueOf(personnel.getNaissance()).indexOf(lowerCaseFilter)!= -1) {
                return true;//filter naissance
            }else
                return false;//doesn't match
        });});
        SortedList<Personnel> sortedData= new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table_info.comparatorProperty());
        table_info.setItems(sortedData);
    }
    private void checkAll(){
        check_selAll.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                oblist = table_info.getItems();
                for (Personnel item : oblist){
                    if(check_selAll.isSelected())
                        item.getCheck().setSelected(true);
                    else
                        item.getCheck().setSelected(false);
                }}});
    }

    private void initTable(){
        initCols();
    }
    private void initCols(){

        col_id.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_service.setCellValueFactory(new PropertyValueFactory<>("service"));
        col_salaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        col_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        col_cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        col_naissance.setCellValueFactory(new PropertyValueFactory<>("naissance"));
        col_edit.setCellValueFactory(new PropertyValueFactory<Personnel, String>("update"));
        col_select.setCellValueFactory(new PropertyValueFactory<>("check"));

        //add cell of button edit
        Callback<TableColumn<Personnel, String>, TableCell<Personnel, String>> cellFoctory = (TableColumn<Personnel, String> param) -> {
            // make cell containing buttons
            final TableCell<Personnel, String> cell = new TableCell<Personnel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView eye = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT);

                        eye.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:linear-gradient(#0288D1 17%, #e7e5e5 100%);"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:linear-gradient(#4E342E 17%, #e7e5e5 100%);"
                        );

                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            personnel=table_info.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("../view/gPerModif.fxml"));
                            try {
                                loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            gPerModifController addController= loader.getController();
                            DateFormat formatD= DateFormat.getDateInstance(DateFormat.DEFAULT);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.FRANCE);
                            LocalDate dateTime = LocalDate.parse(personnel.getNaissance(), formatter);
                            addController.setTextFiel(personnel.getMatricule(),personnel.getNom(),personnel.getPrenom(),personnel.getCin(),personnel.getTel(),personnel.getSex(),dateTime,personnel.getService(),personnel.getSalaire(),personnel.getDescription());
                            Parent parent=loader.getRoot();
                            Stage stage=new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.focusedProperty().addListener((ov, onHidden, onShown) -> {
                                if (!stage.isShowing()){
                                    loadData();
                                }
                            });
                            //drag it here
                            parent.setOnMousePressed(event1 -> {
                                x = event1.getSceneX();
                                y = event1.getSceneY();
                            });
                            parent.setOnMouseDragged(event1 -> {

                                stage.setX(event1.getScreenX() - x);
                                stage.setY(event1.getScreenY() - y);

                            });
                            stage.show();
                        });

                        HBox managebtn = new HBox(editIcon, eye);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(eye, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        col_edit.setCellFactory(cellFoctory);
    }
    private void loadData(){
        oblist = FXCollections.observableArrayList();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from PERSONNEL");
            while(rs.next()){
                oblist.add(new Personnel(rs.getString("matricule"),rs.getString("cin"),rs.getString("nom"),rs.getString("prenom"),rs.getDate("naissance"),rs.getFloat("salaire"),rs.getString("sex"),rs.getInt("tel"),rs.getString("service"),rs.getString("description"),""));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table_info.setItems(oblist);
        filter();
    }
}
