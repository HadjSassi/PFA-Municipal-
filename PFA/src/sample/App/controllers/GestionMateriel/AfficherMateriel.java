package sample.App.controllers.GestionMateriel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AfficherMateriel {






    @FXML
    private Label designationlbl;

    @FXML
    private Label cosomlbl;


    @FXML
    private Label qtelbl;



    @FXML
    private Button buttonFermer1;


    public void setTextField( String designation, String qte,String consom) {
        this.designationlbl.setText(designation);
        this.qtelbl.setText(qte);
        this.cosomlbl.setText(consom);
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
