package sample.App.controllers.GestionEngin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AfficherEngin {




    @FXML
    private Label lblId;


    @FXML
    private Label typelbl;


    @FXML
    private Label marquelbl;

    @FXML
    private Label dispolbl;


    @FXML
    private Button buttonFermer1;


    public void setTextField(String id, String type, String marque, String dispo) {
        this.lblId.setText(id);
        this.typelbl.setText(type);
        this.marquelbl.setText(marque);
        this.dispolbl.setText(dispo);
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
