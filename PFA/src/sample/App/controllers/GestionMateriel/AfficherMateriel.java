package sample.App.controllers.GestionMateriel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AfficherMateriel {




    @FXML
    private Label idlbl;


    @FXML
    private Label designationlbl;


    @FXML
    private Label qtelbl;



    @FXML
    private Button buttonFermer1;


    public void setTextField(String id, String designation, String qte) {
        this.idlbl.setText(id);
        this.designationlbl.setText(designation);
        this.qtelbl.setText(qte);
    }

    public void fermerButton (ActionEvent event){
        // get a handle to the stage
        Stage stage = (Stage) buttonFermer1.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void handleClicksPrint(ActionEvent event) {
        //to add later on
    }
}
