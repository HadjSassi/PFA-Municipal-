package sample.App.controllers.GestionMateriel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.App.model.Type;

import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class UpdateMateriel implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private String ids;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private TextField designationfield;

    @FXML
    private Button buttonConfirmer;

    @FXML
    private Button buttonFermer;

    @FXML
    private Label lbldesignation;

    @FXML
    private Label lblqte;

    @FXML
    private TextField qtefield;



    @FXML
    private Label idlbl;

    private String id;


    private boolean verser , verqte  ;


    @FXML
    void verifService(KeyEvent  event) {
        if(designationfield.getText().isEmpty()){
            lbldesignation.setText("🠔 Saisir la designation");
            verser = false;
            lbldesignation.setStyle("-fx-text-fill: red");
            designationfield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
        else{
            lbldesignation.setStyle("-fx-text-fill: #32CD32");
            lbldesignation.setText("✓");
            verser = true;
            designationfield.setStyle("-fx-background-color:white;");}
    }

    private static boolean isNumeric(String string) {
        return string.matches("[0-9]+");
    }


    @FXML
    void verifQte(KeyEvent event){
        verqte=false;
        String salaire = qtefield.getText();
        if (!salaire.isEmpty()){
            if (!isNumeric(salaire)) {
                lblqte.setText("🠔 La quantité est un nombre entier!");
                qtefield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblqte.setStyle("-fx-text-fill: red");
                verqte=false;
            } else {
                qtefield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblqte.setText("✓");
                verqte=true;
                lblqte.setStyle("-fx-text-fill: #32CD32");}}
        else{
            verqte=false;
            lblqte.setText("🠔 Remplir ce champs");
            lblqte.setStyle("-fx-text-fill: red");
            qtefield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}


    }

    public static boolean isFloat(String string) {
        try {
            Float.parseFloat(string);
            if (string.length() >=3){

                try {
                    String[] p = string.split("\\.");
                    if (p[0].length() <= 10 && p[1].length()<=10)
                        return true;
                    else {
                        return false;
                    }
                } catch (IndexOutOfBoundsException e) {
                    return true;
                }
            }
            else
                return true;
        }catch(NumberFormatException e){
        }
        return false;
    }



    @FXML
    void confirmerButton(ActionEvent event) throws URISyntaxException {
        try {
            String qte = qtefield.getText();
            String type = designationfield.getText();



            if (!verqte || !verser) {


                if (!verqte) {
                    //lblqte.setText("🠔 Remplir ce champ");
                    lblqte.setStyle("-fx-text-fill: red");
                    qtefield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }




                if (!verser) {
                    lbldesignation.setText("🠔 Selectionner la designation");
                    lbldesignation.setStyle("-fx-text-fill: red");
                    designationfield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
                } else {
                    lbldesignation.setStyle("-fx-text-fill: #32CD32");
                    lbldesignation.setText("✓");
                    designationfield.setStyle("-fx-background-color:white;");
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.TRANSPARENT);
                alert.setHeaderText(null);
                alert.setContentText("Un des champs n'est pas correctement inserer");
                alert.setGraphic(new ImageView(getClass().getResource("../../images/errorinsert.png").toURI().toString()));
                alert.showAndWait();
            } else {

                Connection connection = null;
                try {
                    connection = getOracleConnection();


                    String insertion = "Update Materiel set " +
                            "DESIGNATION = "+"\'"+designationfield.getText()+"\'"+",qte = "+"\'"+qtefield.getText()+"\'"+"where ID  = "+"\'"+ids+"\'"+"";



                    PreparedStatement rs = connection.prepareStatement(insertion);
                    System.out.println(insertion);
                    rs.execute();
                    //lbl.setText("Ajout avec succés");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.TRANSPARENT);
                    alert.setHeaderText(null);
                    alert.setContentText("Modification avec succés");
                    alert.setGraphic(new ImageView(getClass().getResource("../../../images/approved2.png").toURI().toString()));
                    alert.showAndWait();
                    Stage stage = (Stage) buttonConfirmer.getScene().getWindow();
                    // do what you have to do
                    stage.close();

                } catch (SQLException | URISyntaxException throwables) {
                    throwables.printStackTrace();
                }

            }
        }
        catch (NullPointerException e){



        }
    }





    @FXML
    public void fermerButton (ActionEvent event){
        // get a handle to the stage
        Stage stage = (Stage) buttonFermer.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private String desi ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setTextField(String id, String designation, int qte)  {
        ids = id ;
        desi = designation;
        this.idlbl.setText(id);
        this.designationfield.setText(designation);
        this.qtefield.setText(""+qte);
    }


}
