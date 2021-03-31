package sample.App.controllers.GestionTache.AfficherTache;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Date;


public class ControllerAfficherTache {

    private String compteId;


    @FXML
    private Button buttonFermer;

    @FXML
    private Label lblCin;

    @FXML
    private Label lblPass;



    public void setTextField(String idTache, String compteId, String cin, String pass, String dateDebut, String dateFin) {
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
