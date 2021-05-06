package sample.App.controllers.GestionIntervention;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.App.model.Engin;
import sample.App.model.Personnel;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class AddEngCon implements Initializable {
    @FXML
    private TableView<Engin> table_info;

    @FXML
    private TableColumn<Engin, CheckBox> col_select;

    @FXML
    private TableColumn<Engin, String> idCol;

    @FXML
    private TableColumn<Engin, String> typeCol;

    @FXML
    private TableColumn<Engin, String> markCol;

    @FXML
    private AnchorPane anchorpane;


    @FXML
    void handleClicksAjout(ActionEvent event) {
        oblist2 = FXCollections.observableArrayList();
        for(Engin en:oblist){
            if(en.getCheck().isSelected()){
                oblist2.add(en);
            }
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
    ObservableList<Engin> oblist;
    static ObservableList<Engin> oblist2;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        markCol.setCellValueFactory(new PropertyValueFactory<>("Marque"));
        col_select.setCellValueFactory(new PropertyValueFactory<>("check"));
        oblist = FXCollections.observableArrayList();
        try {
            Connection connection= getOracleConnection();
            ArrayList<String> v=new ArrayList<>();
            PreparedStatement rs1 =connection.prepareStatement("select * from INTERVENTION,INTERENG where INTERENG.IDMAT=INTERVENTION.IDMAT and (dateD BETWEEN ? and ? or dateF BETWEEN ? and ? )");
            rs1.setDate(1,InterventionAddController.dD);
            rs1.setDate(2,InterventionAddController.dF);
            rs1.setDate(3,InterventionAddController.dD);
            rs1.setDate(4,InterventionAddController.dF);
            rs1.execute();
            ResultSet rs11=rs1.executeQuery();
            while(rs11.next()){
                v.add(rs11.getString("id"));
            }
            ResultSet rs = connection.createStatement().executeQuery("select * from Engin ");
            while(rs.next()){
                if(!v.contains(rs.getString("id")))
                    oblist.add(new Engin(rs.getString("id"),rs.getString("type"),rs.getString("dispo"),rs.getString("Marque")));
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table_info.setItems(oblist);
    }
}
