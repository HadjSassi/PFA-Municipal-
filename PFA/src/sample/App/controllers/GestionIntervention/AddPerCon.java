package sample.App.controllers.GestionIntervention;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.App.model.Engin;
import sample.App.model.Intervention;
import sample.App.model.Personnel;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class AddPerCon  implements Initializable {
    @FXML
    private TableView<Personnel> table_info;

    @FXML
    private TableColumn<Personnel, String> col_id;

    @FXML
    private TableColumn<Personnel, String> col_nom;

    @FXML
    private TableColumn<Personnel, String> col_prenom;

    @FXML
    private TableColumn<Personnel, String> col_service;
    @FXML
    private AnchorPane anchorpane;


    @FXML
    void handleClicksAjout(ActionEvent event) {
        ObservableList<Personnel>  ob = table_info.getSelectionModel().getSelectedItems();
        oblist2 = FXCollections.observableArrayList();
        for(Personnel per:ob){
                oblist2.add(per);
        }
        stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();
    }

    Stage stage;
    @FXML
    void handleClicksAnnuler(ActionEvent event) {
        stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();
    }
    ObservableList<Personnel> oblist;
    static ObservableList<Personnel> oblist2;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table_info.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        col_id.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_service.setCellValueFactory(new PropertyValueFactory<>("service"));
        oblist = FXCollections.observableArrayList();
        try {
            Connection connection= getOracleConnection();
            ArrayList<String> v=new ArrayList<>();
            PreparedStatement rs1 =connection.prepareStatement("select * from INTERVENTION,INTERPER where INTERPER.IDMAT=INTERVENTION.IDMAT and (dateD BETWEEN ? and ? or dateF BETWEEN ? and ? )");
            rs1.setDate(1,InterventionAddController.dD);
            rs1.setDate(2,InterventionAddController.dF);
            rs1.setDate(3,InterventionAddController.dD);
            rs1.setDate(4,InterventionAddController.dF);
            rs1.execute();
            ResultSet rs11=rs1.executeQuery();
            while(rs11.next()){
                v.add(rs11.getString("MATRICULE"));
            }
            System.out.println(v);
            ResultSet rs = connection.createStatement().executeQuery("select * from PERSONNEL");
            while(rs.next()){
                if(!v.contains(rs.getString("matricule")))
                    oblist.add(new Personnel(rs.getString("matricule"),rs.getString("cin"),rs.getString("nom"),rs.getString("prenom"),rs.getDate("naissance"),rs.getFloat("salaire"),rs.getString("sex"),rs.getInt("tel"),rs.getString("service"),rs.getString("description")));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table_info.setItems(oblist);
    }
}
