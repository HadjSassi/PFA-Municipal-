package sample.App.controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.App.model.Personnel;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

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
    private TableColumn<Personnel, Float> col_salaire;

    @FXML
    private TableColumn<Personnel, Date> col_naissance;

   @FXML
   private TableColumn<Personnel, String> col_sex;

   @FXML
   private TableColumn<Personnel, String> col_tel;

   @FXML
   private TableColumn<Personnel, Button> col_edit;

   @FXML
   private TableColumn<Personnel, CheckBox> col_select;

   @FXML
   private CheckBox check_selAll;

   @FXML
   private TextField filterField;

    ObservableList<Personnel> items;
    ObservableList<Personnel> oblist;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        loadData();
        filter();
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
            }else if(String.valueOf(personnel.getSalaire()).indexOf(lowerCaseFilter)!= -1) {
                return true;//filter tel
            }else if(String.valueOf(personnel.getId()).indexOf(lowerCaseFilter)!= -1) {
                return true;//filter id
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
                items = table_info.getItems();
                for (Personnel item : items){
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
    private void uncheckAll(){
        for (Personnel item : items){
            item.getCheck().selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!item.getCheck().isSelected())
                    check_selAll.setSelected(false);
            }
        });
    }}

    private void initTable(){
        initCols();
        checkAll();
        //uncheckAll();
    }
    private void initCols(){

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_service.setCellValueFactory(new PropertyValueFactory<>("service"));
        col_salaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        col_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        col_tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        col_naissance.setCellValueFactory(new PropertyValueFactory<>("naissance"));
        col_edit.setCellValueFactory(new PropertyValueFactory<>("update"));
        col_select.setCellValueFactory(new PropertyValueFactory<>("check"));
    }
    private void loadData(){
        oblist = FXCollections.observableArrayList();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from PERSONNEL");
            while(rs.next()){
                oblist.add(new Personnel(rs.getInt("cin"),rs.getString("nom"),rs.getString("prenom"),0,rs.getString("service"),rs.getDate("naissance"),rs.getFloat("salaire"),rs.getString("sex"),""));
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table_info.setItems(oblist);
    }
}
