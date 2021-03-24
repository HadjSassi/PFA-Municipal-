package sample.App.controllers.GestionCompte.AfficherCompte;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.App.model.Compte;

import java.sql.*;

import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class ControllerAfficherCompte {

    private String compteId;


    @FXML
    private Button buttonFermer;

    @FXML
    private Label lblCin;

    @FXML
    private Label lblPass;



    public void setTextField(String compteId, String cin, String pass) {
        this.compteId = compteId ;
        this.lblPass.setText(pass);
        this.lblCin.setText(cin);
    }

    public void fermerButton (ActionEvent event){
        // get a handle to the stage
        Stage stage = (Stage) buttonFermer.getScene().getWindow();
        // do what you have to do
        stage.close();
    }


}
