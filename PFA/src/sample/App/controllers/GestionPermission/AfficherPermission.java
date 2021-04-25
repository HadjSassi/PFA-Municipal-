package sample.App.controllers.GestionPermission;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Date;


public class AfficherPermission {




    @FXML
    private Label lblid;


    @FXML
    private Label lblStatus;


    @FXML
    private Label lbldate;


    @FXML
    private Label lblmail;



    @FXML
    private Label lbladr;



    @FXML
    private Label lbltel;


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


    public void setTextField(String id, String type, String nom, String prenom , String cin , String desc, String tel, String mail , String adr , String  dates, String status) {
        this.lblid.setText(id);
        this.lblType.setText(type);
        this.lblnom.setText(nom);
        this.lblprenom.setText(prenom);
        this.lblcin.setText(cin);
        this.lbldesc.setText(desc);
        this.lbladr.setText(adr);
        this.lbltel.setText(tel);
        this.lblmail.setText(mail);
        this.lbldate.setText(dates);
        this.lblStatus.setText(status);
        switch (status){
            case "Initiale" :
                lblStatus.setStyle("-fx-text-fill: lightblue,linear-gradient(to bottom, derive(deepskyblue,60%) 5%,derive(lightskyblue,90%) 40%);");
                break;
            case "Approuvé" :
                lblStatus.setStyle("-fx-text-fill: green,linear-gradient(to bottom, derive(green,60%) 5%,derive(darkgreen,90%) 40%);");
                break;
            case "Refusé" :
                lblStatus.setStyle("-fx-text-fill: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
                break;
            case "EnCours" :
                lblStatus.setStyle("-fx-text-fill: yellow,linear-gradient(to bottom, derive(yellow,60%) 5%,derive(yellow,90%) 40%);");
                break ;
            case "Terminé" :
                lblStatus.setStyle("-fx-text-fill: green,linear-gradient(to bottom, derive(forestgreen,60%) 5%,derive(greenyellow,90%) 40%);");
                break;
        }

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
