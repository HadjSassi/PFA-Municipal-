package sample.GestionCompte.SuppressionCompte;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static OracleConnection.OracleConnection.getOracleConnection;

public class ControllerSuppressionCompte {

    @FXML
    private Button ouiButton ;

    @FXML
    Button nonButton ;

    @FXML
    Label lbl ;

    int cins ;


    public void oui (ActionEvent event){

        try {
            Connection connection = getOracleConnection();
            Statement statement = connection.createStatement();
            statement.execute("delete from COUNT where CIN = \'"+cins+"\'");
            statement.execute("commit");

            System.out.println("parfaitement supprimé");

        } catch (SQLException e) {
            System.out.println("1000000 dawa7");
        }


        // get a handle to the stage
        Stage stage = (Stage) ouiButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void non (ActionEvent event){
        // get a handle to the stage
        Stage stage = (Stage) nonButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }





}
