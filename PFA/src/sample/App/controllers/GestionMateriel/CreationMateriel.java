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

import sample.App.model.Materiel;
import sample.App.model.Type;

import sample.App.model.Type;
import sample.App.model.type_Doleance;


import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class CreationMateriel implements Initializable {

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

    @FXML
    private RadioButton toui;

    @FXML
    private RadioButton tnon;

    @FXML
    private ToggleGroup t;


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
            designationfield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
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
                    lbldesignation.setText("🠔 Saisir la designation");
                    lbldesignation.setStyle("-fx-text-fill: red");
                    designationfield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
                } else {
                    lbldesignation.setStyle("-fx-text-fill: #32CD32");
                    designationfield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
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

                    String t1 = "";
                    if (toui.isSelected())
                        t1=toui.getText();
                    else if (tnon.isSelected())
                        t1=tnon.getText();


                    String insertion = "INSERT INTO MATERIEL values("+"\'"+fara8(designationfield.getText().toString())+"\'"+","+qtefield.getText()+","+"\'"+t1+"\'"+")";


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

                    String des = "";

                    try {
                        Connection connection1= getOracleConnection();
                        String req = "select qte from Materiel where designation = "+"upper(\'"+fara8(designationfield.getText())+"\')";

                        PreparedStatement rs1 = connection1.prepareStatement(req);
                        ResultSet rs = rs1.executeQuery(req);
                        System.out.println("debut");
                        while(rs.next()){
                            des = rs.getString("qte");
                        }
                        rs.close();
                        System.out.println("fin");
                    } catch (SQLException e) {
                        System.out.println(e);
                    }



                    try {
                        connection = getOracleConnection();

                        String t1 = "";
                        if (toui.isSelected())
                            t1=toui.getText();
                        else if (tnon.isSelected())
                            t1=tnon.getText();
                        System.out.println(qtefield.getText()+" "+des);
                        int tot = Integer.parseInt(qtefield.getText());
                        tot += Integer.parseInt(des) ;

                        String insertion = "update MATERIEL set DESIGNATION = "+"\'"+fara8(designationfield.getText().toString())+"\'"+", qte = "+tot+",CONSOMABLE ="+"\'"+t1+"\'"+" where DESIGNATION =  "+"\'"+fara8(designationfield.getText().toString())+"\'";



                        PreparedStatement rs = connection.prepareStatement(insertion);
                        System.out.println(insertion);
                        rs.execute();
                        //lbl.setText("Ajout avec succés");
                        refresh();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.TRANSPARENT);
                        alert.setHeaderText(null);
                        alert.setContentText("Mise à jour avec succés");
                        alert.setGraphic(new ImageView(getClass().getResource("../../../images/approved2.png").toURI().toString()));
                        alert.showAndWait();

                    } catch (SQLException e) {
                        lbldesignation.setText("🠔 Designation déjà existe");
                        lbldesignation.setStyle("-fx-text-fill: red");
                        designationfield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");

                        throwables.printStackTrace();
                    }
                }

            }
        }
        catch (NullPointerException e){



        }
    }


    private void refresh(){
        designationfield.clear();
        qtefield.setText("");

        lbldesignation.setText("");
        lblqte.setText("");

        lbldesignation.setText("");
        lblqte.setText("");
        lbldesignation.setText("");

        designationfield.setStyle("-fx-background-color:white;");
        qtefield.setStyle("-fx-background-color:white;");
        toui.fire();
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
        toui.fire();

    }

    private String fara8(String ch){
        ch = ch.replaceAll("\\s+"," ");

        char s = ch.charAt(0), f = ch.charAt(ch.length()-1);

        while (s == ' '){
            String ss="" ;
            for (int i = 1 ; i < ch.length();i++ )
                ss += ch.charAt(i);
            ch = ss ;
            s = ch.charAt(0);
        }
        while (f == ' ') {
            String ss = "";
            for (int i = 0; i < ch.length()-1; i++)
                ss += ch.charAt(i);
            ch = ss;
            f = ch.charAt(ch.length()-1);
        }
        ch = ch.replaceAll("\\s+"," ");
        ch = ch.toUpperCase();
        return ch;
    }


}