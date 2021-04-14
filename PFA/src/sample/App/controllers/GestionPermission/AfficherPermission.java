package sample.App.controllers.GestionPermission;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AfficherPermission {




    @FXML
    private Label lblid;


    @FXML
    private Label lblType;


    @FXML
    private Label lblcin;


    @FXML
    private Label lblnom;

    @FXML
    private Label lblprenom;


    @FXML
    private Label lbldesc;


    @FXML
    private Button buttonFermer1;


    public void setTextField(String id, String type, String nom, String prenom , String cin , String desc) {
        this.lblid.setText(id);
        this.lblType.setText(type);
        this.lblnom.setText(nom);
        this.lblprenom.setText(prenom);
        this.lblcin.setText(cin);
        this.lbldesc.setText(desc);
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
