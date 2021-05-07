package sample.App.controllers.GestionIntervention;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.App.model.Engin;
import sample.App.model.Materiel;
import sample.App.model.Personnel;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class AddMatCon implements Initializable {
    @FXML
    private TableView<Materiel> table_info;

    @FXML
    private TableColumn<Materiel, String> consCol;

    @FXML
    private TableColumn<Materiel, String> typeCol;

    @FXML
    private AnchorPane anchorpane;


    @FXML
    void handleClicksAjout(ActionEvent event) throws SQLException {
        ObservableList<Materiel> ob = table_info.getSelectionModel().getSelectedItems();
        oblist2 = FXCollections.observableArrayList();
        for (Materiel ma : ob) {
            oblist2.add(ma);

        }
        stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();
        Connection connection = null;
        connection = getOracleConnection();
        PreparedStatement rs1 = connection.prepareStatement("DELETE from interMAT WHERE IDMAT=?");
        rs1.setString(1, matricule);
        rs1.execute();
    }

    Stage stage;

    @FXML
    void handleClicksAnnuler(ActionEvent event) {
        stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();
    }

    ObservableList<Materiel> oblist;
    static ObservableList<Materiel> oblist2;
    static String matricule;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table_info.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        consCol.setCellValueFactory(new PropertyValueFactory<>("consom"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("designation"));
        oblist = FXCollections.observableArrayList();
        try {
            Connection connection = getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from Materiel ");
            while (rs.next()) {
                oblist.add(new Materiel(rs.getString("designation"), rs.getInt("qte"), rs.getString("CONSOMABLE")));
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table_info.setItems(oblist);
    }
}
