package sample.App.controllers.GestionPermission;

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
import sample.App.model.type_Doleance;


import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class CreationPermission implements Initializable {


    @FXML
    private Label lblType ;

    @FXML
    private Label lblcin;

    @FXML
    private Label lblnom ;

    @FXML
    private Label lblprenom ;

    @FXML
    private Label lblid ;

    @FXML
    private TextField idfield ;

    @FXML
    private TextField cinfield ;

    @FXML
    private TextField nomfield ;

    @FXML
    private TextField prenomfield ;

    @FXML
    private TextArea descriptionfiled ;

    @FXML
    private ChoiceBox<String> Typefield;

    @FXML
    private Button buttonConfirmer ;

    @FXML
    private Button buttonFermer ;


    private boolean vernom,verprenom,vercin,verifid;

    private boolean isAlpha(String name) {
        return name.matches("[a-zA-Z][a-zA-Z ]*") && name.length()<=30;
    }
    private static boolean isNumeric(String string) {
        return string.matches("[0-9]+");
    }

    @FXML
    void verifId (KeyEvent event){
        verifid=false;
        String cin = idfield.getText();
        if(!cin.isEmpty()){

            if (!isNumeric(cin) || cin.length() != 4) {
                lblid.setText("🠔 Le numero de cin est un nombre composé de 8 chiffres !");
                verifid=false;
                idfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblid.setStyle("-fx-text-fill: red");}
            else {
                idfield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblid.setText("✓");
                verifid=true;
                lblid.setStyle("-fx-text-fill: #32CD32");}}
        else{
            idfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            verifid=false;
            lblid.setText("");}
    }

    @FXML
    void verifCin(KeyEvent event) {
        vercin=false;
        String cin = cinfield.getText();
        if(!cin.isEmpty()){

            if (!isNumeric(cin) || cin.length() != 8) {
                    lblcin.setText("🠔 Le numero de cin est un nombre composé de 8 chiffres !");
                    vercin=false;
                    cinfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    lblcin.setStyle("-fx-text-fill: red");}
            else {
                cinfield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblcin.setText("✓");
                vercin=true;
                lblcin.setStyle("-fx-text-fill: #32CD32");}}
        else{
            cinfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            vercin=false;
            lblcin.setText("");}
    }

    @FXML
    void verifNom(KeyEvent event) {
        vernom=false;
        String nom = nomfield.getText();
        if(!nom.isEmpty()){
            if (!isAlpha(nom)) {
                lblnom.setText("🠔 Le nom est composé de lettres alphabétiques!");
                nomfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                vernom=false;
                lblnom.setStyle("-fx-text-fill: red");
            } else {
                nomfield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblnom.setText("✓");
                vernom=true;
                lblnom.setStyle("-fx-text-fill: #32CD32");}}
        else{
            nomfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            vernom=false;
            lblnom.setText("");
        }
    }

    @FXML
    void verifPrenom(KeyEvent event) {
        verprenom=false;
        String prenom = prenomfield.getText();
        if(!prenom.isEmpty()){
            if (!isAlpha(prenom)) {
                lblprenom.setText("🠔 Le prenom est composé de lettres alphabétiques!");
                prenomfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblprenom.setStyle("-fx-text-fill: red");
                verprenom=false;
            } else {
                prenomfield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblprenom.setText("✓");
                verprenom=true;
                lblprenom.setStyle("-fx-text-fill: #32CD32");}}
        else{
            prenomfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            lblprenom.setText("");
            verprenom=false;
        }
    }

    @FXML
    void verifService(ActionEvent  event) {
        if(Typefield.getValue()==null){
            lblType.setText("🠔 Selectionner le service");
            lblType.setStyle("-fx-text-fill: red");
            Typefield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
        else{
            lblType.setStyle("-fx-text-fill: #32CD32");
            lblType.setText("✓");
            Typefield.setStyle("-fx-background-color:white;");}
    }


    @FXML
    void confirmerButton(ActionEvent event) throws URISyntaxException {
        try {
            String id = idfield.getText();
            String cin = cinfield.getText();
            String type = Typefield.getValue();
            String nom = nomfield.getText();
            String prenom = prenomfield.getText();
            String desc = descriptionfiled.getText();


            if ( !verifid || !vercin|| !vernom || !verprenom   ||type == null || id.isEmpty() || cin.isEmpty() || nom.isEmpty() || prenom.isEmpty()) {
                if (id.isEmpty() && verifid) {
                    lblid.setText("🠔 Remplir ce champ");
                    lblid.setStyle("-fx-text-fill: red");
                    idfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }

                if (cin.isEmpty() && vercin) {
                    lblcin.setText("🠔 Remplir ce champ");
                    lblcin.setStyle("-fx-text-fill: red");
                    cinfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }
                if (nom.isEmpty() && !vernom) {
                    lblnom.setText("🠔 Remplir ce champ");
                    lblnom.setStyle("-fx-text-fill: red");
                    nomfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }


                if (prenom.isEmpty() && !verprenom) {
                    lblprenom.setText("🠔 Remplir ce champ");
                    lblprenom.setStyle("-fx-text-fill: red");
                    prenomfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }



                if (type == null) {
                    lblType.setText("🠔 Selectionner le type de permission");
                    lblType.setStyle("-fx-text-fill: red");
                    Typefield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
                } else {
                    lblType.setStyle("-fx-text-fill: #32CD32");
                    lblType.setText("✓");
                    Typefield.setStyle("-fx-background-color:white;");
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.TRANSPARENT);
                alert.setHeaderText(null);
                alert.setContentText("Un des champs n'est pas correctement inserer");
                alert.setGraphic(new ImageView(getClass().getResource("../../images/errorinsert.png").toURI().toString()));
                alert.showAndWait();
            }
            else {
                Connection connection = null;
                try {
                    connection = getOracleConnection();
                    try {
                        String insertion = "INSERT INTO permission values(" + "\'" + id + "\'" + "," + "\'" + type + "\'" + "," + "\'" + cin + "\'" + "," + "\'" + nom + "\'" + ","+"\'" + prenom + "\'" + "," +"\'" +desc+"\'" + ")";
                        PreparedStatement rs = connection.prepareStatement(insertion);
                        //System.out.println(insertion);
                            rs.execute();


                        //lbl.setText("Ajout avec succés");
                        refresh();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.TRANSPARENT);
                        alert.setHeaderText(null);
                        alert.setContentText("Ajout avec succés");
                        alert.setGraphic(new ImageView(getClass().getResource("../../../images/approved.png").toURI().toString()));
                        alert.showAndWait();
                    }
                    catch (NumberFormatException e){
                        System.out.println("");
                    }
                } catch (SQLException | URISyntaxException throwables) {
                    throwables.printStackTrace();
                }

            }
        }
        catch (NullPointerException e){
            //System.out.println("you have an error go check please mr Mahdi");
            lblid.setText("🠔 Cette matricule existe déjà");
            lblid.setStyle("-fx-text-fill: red");
            idfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            if(idfield.getText().isEmpty())
            {
                lblid.setText("🠔 Remplir ce champ");
                lblid.setStyle("-fx-text-fill: red");
                idfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            }
            if(cinfield.getText().isEmpty())
            {
                lblcin.setText("🠔 Remplir ce champ");
                lblcin.setStyle("-fx-text-fill: red");
                cinfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            }

            if(Typefield.getValue().isEmpty())
            {
                lblType.setText("🠔 Remplir ce champ");
                lblType.setStyle("-fx-text-fill: red");
                Typefield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            }

            if(nomfield.getText().isEmpty())
            {
                lblnom.setText("🠔 Remplir ce champ");
                lblnom.setStyle("-fx-text-fill: red");
                nomfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            }

            if(prenomfield.getText().isEmpty())
            {
                lblprenom.setText("🠔 Remplir ce champ");
                lblprenom.setStyle("-fx-text-fill: red");
                prenomfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            }

        }
    }


    private void refresh(){
        Typefield.setValue(null);
        idfield.clear();
        cinfield.setText("");
        nomfield.setText("");
        prenomfield.setText("");
        descriptionfiled.setText("");
        lblcin.setText("");
        lblType.setText("");
        lblnom.setText("");
        lblid.setText("");
        lblprenom.setText("");
        Typefield.setStyle("-fx-background-color:white;");
        prenomfield.setStyle("-fx-background-color:white;");
        nomfield.setStyle("-fx-background-color:white;");
        cinfield.setStyle("-fx-background-color:white;");
        idfield.setStyle("-fx-background-color:white;");
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

        ObservableList list= FXCollections.observableArrayList();
        list.removeAll();
        list.addAll(Type.permission_de_sortie.toString(),Type.permission_de_voirie.toString(),Type.permission_de_sortir_confinnement.toString());
        Typefield.getItems().setAll(list);

    }



}