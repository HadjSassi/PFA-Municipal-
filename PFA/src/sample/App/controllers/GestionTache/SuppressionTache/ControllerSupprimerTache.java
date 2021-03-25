package sample.App.controllers.GestionTache.SuppressionTache;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.App.controllers.GestionTache.ConsultationTache.ControllerConsulterTache;


public class ControllerSupprimerTache {

    @FXML
    private Button ouiButton ;
    @FXML
    private Button nonButton ;
    @FXML
    private Label message ;

    private boolean itsokay ;

    @FXML
    public void oui (ActionEvent event){
        ControllerConsulterTache ac = new ControllerConsulterTache();
        ac.itsokay = true ;
        // get a handle to the stage
        Stage stage = (Stage) nonButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    @FXML
    public void non (ActionEvent event){
        ControllerConsulterTache ac = new ControllerConsulterTache();
        ac.itsokay = false ;
            // get a handle to the stage
            Stage stage = (Stage) nonButton.getScene().getWindow();
            // do what you have to do
            stage.close();


    }

}
