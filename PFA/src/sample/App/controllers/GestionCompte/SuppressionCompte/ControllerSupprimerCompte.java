package sample.App.controllers.GestionCompte.SuppressionCompte;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.App.controllers.GestionCompte.ConsultationCompte.ControllerConsulterCompte;


public class ControllerSupprimerCompte {

    @FXML
    private Button ouiButton ;
    @FXML
    private Button nonButton ;
    @FXML
    public Label message ;

    private boolean itsokay ;

    @FXML
    public void oui (ActionEvent event){
        ControllerConsulterCompte ac = new ControllerConsulterCompte();
        ac.itsokay = true ;
        this.itsokay = true ;
        // get a handle to the stage
        Stage stage = (Stage) nonButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    @FXML
    public void non (ActionEvent event){
        ControllerConsulterCompte ac = new ControllerConsulterCompte();
        ac.itsokay = false ;
        // get a handle to the stage
        Stage stage = (Stage) nonButton.getScene().getWindow();
        // do what you have to do
        stage.close();


    }

    public Button getOuiButton() {
        return ouiButton;
    }

    public void setOuiButton(Button ouiButton) {
        this.ouiButton = ouiButton;
    }

    public Button getNonButton() {
        return nonButton;
    }

    public void setNonButton(Button nonButton) {
        this.nonButton = nonButton;
    }

    public Label getMessage() {
        return message;
    }

    public void setMessage(Label message) {
        this.message = message;
    }

    public boolean isItsokay() {
        return itsokay;
    }

    public void setItsokay(boolean itsokay) {
        this.itsokay = itsokay;
    }
}
