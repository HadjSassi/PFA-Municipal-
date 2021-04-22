package sample.App.controllers.GestionMateriel;

import com.gluonhq.charm.glisten.control.Chip;

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
import sample.App.controllers.GestionPersonnel.gPerAddController;

import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import sample.App.model.Type;

import sample.App.model.Type;
import sample.App.model.type_Doleance;


import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class CreationMateriel implements Initializable {

    @FXML
    private Label lblid;

    @FXML
    private Label lbldesignation ;

    @FXML
    private Label lblqte;

    @FXML
    private TextField qtefield ;

    @FXML
    private TextField designationfield;

    @FXML
    private Button buttonConfirmer ;

    @FXML
    private Button buttonFermer ;


    private boolean verser , verqte  ;

    @FXML
    void verifService(KeyEvent  event) {
        if(designationfield.getText().isEmpty()){
            lbldesignation.setText("🠔 Saisir le type de depense");
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



            if (!verqte || !verser ) {


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


                    String insertion = "INSERT INTO MATERIEL values( null ,"+"\'"+designationfield.getText().toString()+"\'"+","+qtefield.getText()+")";



                    PreparedStatement rs = connection.prepareStatement(insertion);
                    System.out.println(insertion);
                    rs.execute();
                    //lbl.setText("Ajout avec succés");
                    refresh();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.TRANSPARENT);
                    alert.setHeaderText(null);
                    alert.setContentText("Ajout avec succés");
                    alert.setGraphic(new ImageView(getClass().getResource("../../../images/approved.png").toURI().toString()));
                    alert.showAndWait();
                } catch (SQLException | URISyntaxException throwables) {
                    throwables.printStackTrace();
                }

            }
        }
        catch (NullPointerException e){



        }
    }


    private void refresh(){
        designationfield.clear();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select MATERIELSEQ.nextval tt from dual");
            while(rs.next()){
                lblid.setText(rs.getString("tt"));
            }
            rs.close();
        } catch (SQLException throwables) {
            System.out.println("1000000 dawa7");
        }
        qtefield.setText("");

        lbldesignation.setText("");
        lblqte.setText("");

        lbldesignation.setText("");
        lblqte.setText("");
        lbldesignation.setText("");

        designationfield.setStyle("-fx-background-color:white;");
    }
    @FXML
    public void fermerButton (ActionEvent event){
        // get a handle to the stage
        Stage stage = (Stage) buttonFermer.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select MATERIELSEQ.nextval tt from dual");
            while(rs.next()){
                lblid.setText(rs.getString("tt"));
            }
            rs.close();
        } catch (SQLException throwables) {
            System.out.println("1000000 dawa7");
        }


    }



}