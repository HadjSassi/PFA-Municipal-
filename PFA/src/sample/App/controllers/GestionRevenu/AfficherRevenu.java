package sample.App.controllers.GestionRevenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AfficherRevenu {




    @FXML
    private Label lblId;


    @FXML
    private Label desclbl;

    @FXML
    private Label typelbl;


    @FXML
    private Label lbldate;

    @FXML
    private Label lblprix;


    @FXML
    private Button buttonFermer1;


    public void setTextField(String id, String type, String prix, String date , String desc) {
        this.lblId.setText(id);
        this.typelbl.setText(type);
        this.lblprix.setText(prix);
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRANCE);
        LocalDate dateTime = LocalDate.parse(date, formatter);
        this.lbldate.setText(String.valueOf(dateTime));*/
        this.lbldate.setText(date);
        this.desclbl.setText(desc);
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
