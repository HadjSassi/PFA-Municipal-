package sample.App.controllers.GestionDoleance.AfficherDoleance;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class ControllerAfficherDoleance {

    @FXML
    private Label lblId;


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
    private Label namelbl;


    @FXML
    private Label CinTextField;


    @FXML
    private Label lblId1;


    @FXML
    private Label DescriptionFiled;


    @FXML
    private Button buttonFermer1;


    public void setTextField(String id, String type, String nom, String cin, String description , String date ) {
        this.CinTextField.setText(cin);
        this.lblId.setText(id);
        this.DescriptionFiled.setText(description);
        this.lblStatus.setText("Initiale");
        this.namelbl.setText(nom);
        this.lblId1.setText(type);
        this.lbldate.setText(date);
    }

    public void setTextField(String id, String type, String nom, String cin, String description,String Status,String tel,String mail, String adr  , String date) {
        this.CinTextField.setText(cin);
        this.lbldate.setText(date);
        this.lblId.setText(id);
        this.DescriptionFiled.setText(description);
        this.lblStatus.setText(Status);
        this.namelbl.setText(nom);
        this.lblId1.setText(type);
        this.lbladr.setText(adr);
        this.lbltel.setText(tel);
        this.lblmail.setText(mail);
        //Initiale,Approuvé,Refusé,EnCours,Terminé
        switch (Status){
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
