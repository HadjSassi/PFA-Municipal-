package sample.GestionCompte.ConsulterComptes;

import Model.Compte;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static OracleConnection.OracleConnection.getOracleConnection;

public class ControllerConsulterComptes implements Initializable {

    @FXML
    TableView tableView;

    @FXML
    Button addButton ;

    @FXML
    Button refresh;

    //ici vous venez de faire l'appel a la requete sql
    private ObservableList<Compte> data = FXCollections.observableArrayList();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        {
            TableColumn cinCol = new TableColumn("Numéro Carte d'identité");
            TableColumn passCol = new TableColumn("Mot de passe");
            TableColumn modifierCol = new TableColumn("Modifier");
            TableColumn deleteCol = new TableColumn("Effacer");

            tableView.getColumns().addAll(cinCol, passCol, modifierCol, deleteCol);



            String selectTableSQL = "SELECT cin , pass FROM counts";

            Statement statement = null;

            try {
                Connection connection = getOracleConnection();

                statement = connection.createStatement();

                //get data from db

                ResultSet rs = statement.executeQuery(selectTableSQL);

                //fetch data

                while (rs.next()) {
                    String cins = rs.getString("CIN");
                    String passs = rs.getString("PASS");
                    data.add(new Compte(cins, passs));

                }
                rs.close();
            } catch (SQLException e) {
                System.out.println("1000000 dawa7");
            }


            cinCol.setCellValueFactory(
                    new PropertyValueFactory<Compte, String>("cin")
            );
            passCol.setCellValueFactory(
                    new PropertyValueFactory<Compte, String>("pass")
            );
            modifierCol.setCellValueFactory(
                    new PropertyValueFactory<Compte, String>("modifier")
            );

/*
            Callback<TableColumn<Compte,String>, TableCell<Compte,String>> cellFactory = (param) ->{
                final TableCell<Compte,String> cell = new TableCell<Compte,String>(){



                };
                return cell;
            };
*/



            deleteCol.setCellValueFactory(
                    new PropertyValueFactory<Compte, String>("delete")
            );

            tableView.setItems(data);
        }

    }



    public void ajouter (ActionEvent event)  {
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("..\\CreationCompte\\CreationCompte.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Municipal");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        refresh();

    }



    public void refresh(){
        data.clear();
        String selectTableSQL = "SELECT cin , pass FROM counts";

        Statement statement = null;

        try {
            Connection connection = getOracleConnection();

            statement = connection.createStatement();

            //get data from db

            ResultSet rs = statement.executeQuery(selectTableSQL);

            //fetch data

            while (rs.next()) {
                String cins = rs.getString("CIN");
                String passs = rs.getString("PASS");
                data.add(new Compte(cins, passs));

            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("1000000 dawa7");
        }
    }

    public void refresh(ActionEvent event){
        data.clear();
        String selectTableSQL = "SELECT cin , pass FROM counts";

        Statement statement = null;

        try {
            Connection connection = getOracleConnection();

            statement = connection.createStatement();

            //get data from db

            ResultSet rs = statement.executeQuery(selectTableSQL);

            //fetch data

            while (rs.next()) {
                String cins = rs.getString("CIN");
                String passs = rs.getString("PASS");
                data.add(new Compte(cins, passs));

            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("1000000 dawa7");
        }
    }


}
