package sample.App.controllers.GestionCompte.ConsultationCompte;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.App.FxmlLoader;
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

    @FXML
    TableView<Compte> tableView;

    @FXML
    TableColumn <Compte,String> cinCol ;
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
    }


    
    private void filter(){
        FilteredList<Compte> filteredData = new FilteredList<>(oblist, b -> true);

        filterField.textProperty().addListener((observable ,oldValue, newValue)->{filteredData.setPredicate(compte -> {

            if(newValue == null || newValue.isEmpty()){
                return true;
            }

            //Comparer le nom et le prenom avec tous les comptes aves filterField
            String lowerCaseFilter = newValue.toLowerCase();

            if (compte.getCin().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter nom
            }else if (compte.getPass().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter prenom
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


    private void uncheckAll(){
        for (Compte item : items){
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

        cinCol.setCellValueFactory(
                new PropertyValueFactory<>("cin")
        );
        passCol.setCellValueFactory(
                new PropertyValueFactory<>("pass")
        );

        //modifierCol.setCellValueFactory(new PropertyValueFactory<>("update"));
        col_select.setCellValueFactory(new PropertyValueFactory<>("check"));
    }


    private void loadData(){
        oblist = FXCollections.observableArrayList();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from COUNTS ");
            while(rs.next()){
                oblist.add(new Compte(rs.getString("cin"),rs.getString("pass"),""));
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
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../../../view/CreationCompte.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Municipal");
        assert root != null;
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    public void refresh(ActionEvent event){

        loadData();

    }




}
