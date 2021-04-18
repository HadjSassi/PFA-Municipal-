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
    private ChoiceBox<String> designationfield;

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
    private TextField prixfield;

    @FXML
    private Label lblprix;

    @FXML
    private Label idlbl;

    private String id;


    private boolean verser , verqte , verprix ;


    @FXML
    void verifService(ActionEvent  event) {
        if(designationfield.getValue()==null){
            verser = false ;
            lbldesignation.setText("🠔 Selectionner la designation du materiel");
            lbldesignation.setStyle("-fx-text-fill: red");
            designationfield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
        else{
            verser = true ;
            lbldesignation.setStyle("-fx-text-fill: #32CD32");
            lbldesignation.setText("✓");
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
                lblqte.setText("🠔 Le salaire est un nombre entier!");
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
    void verifPrix (KeyEvent event){
        verprix=false;
        String salaire = prixfield.getText();
        if (!salaire.isEmpty()){
            if (!isFloat(salaire)) {
                lblprix.setText("🠔 Le salaire est un nombre réel!");
                prixfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblprix.setStyle("-fx-text-fill: red");
                verprix=false;
            } else {
                prixfield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblprix.setText("✓");
                verprix=true;
                lblprix.setStyle("-fx-text-fill: #32CD32");}}
        else{
            verprix=false;
            lblprix.setText("🠔 Remplir ce champs");
            lblprix.setStyle("-fx-text-fill: red");
            prixfield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}

    }


    @FXML
    void confirmerButton(ActionEvent event) throws URISyntaxException {
        try {
            String qte = qtefield.getText();
            String type = designationfield.getValue();

            String prix = prixfield.getText();


            if (!verqte || !verser || !verprix) {


                if (!verqte) {
                    //lblqte.setText("🠔 Remplir ce champ");
                    lblqte.setStyle("-fx-text-fill: red");
                    qtefield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }



                if ( !verprix) {
                    //lblprix.setText("🠔 Remplir ce champ");
                    lblprix.setStyle("-fx-text-fill: red");
                    prixfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }
                if (!verser) {
                    lbldesignation.setText("🠔 Selectionner le service");
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
                            "DESIGNATION = "+"\'"+designationfield.getValue()+"\'"+",qte = "+"\'"+qtefield.getText()+"\'"+",prix = "+"\'"+prixfield.getText()+"\'" +" where ID = "+"\'"+ids+"\'"+"";



                    PreparedStatement rs = connection.prepareStatement(insertion);
                    System.out.println(insertion);
                    rs.execute();
                    //lbl.setText("Ajout avec succés");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.TRANSPARENT);
                    alert.setHeaderText(null);
                    alert.setContentText("Ajout avec succés");
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
        ObservableList list= FXCollections.observableArrayList();
        list.removeAll();
        list.addAll(Type.Jardinage.toString(),Type.Poubelle.toString(),Type.barriere_de_securite.toString(),Type.barwita.toString(),Type.drapeaux.toString());
        designationfield.getItems().setAll(list);
    }

    public void setTextField(String id, String designation, int qte, String prix)  {
        ids = id ;
        desi = designation;
        this.idlbl.setText(id);
        this.designationfield.setValue(designation);
        this.qtefield.setText(""+qte);
        this.prixfield.setText(prix);
    }


}
