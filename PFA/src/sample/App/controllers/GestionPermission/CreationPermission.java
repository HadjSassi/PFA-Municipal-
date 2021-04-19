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
    private Label lbladr;
    @FXML
    private Label lbltel;
    @FXML
    private Label lblmail;


    @FXML
    private TextField telfield ;

    @FXML
    private TextField mailfield ;

    @FXML
    private TextField adrfield ;


    @FXML
    private Label lblnom ;

    @FXML
    private Label lblprenom ;

    @FXML
    private Label lblid1 ;

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

    private boolean vertel = true ;

    @FXML
    void VerifTel(KeyEvent event) {
        try {
            vertel = false;
            String tel = telfield.getText();
            if (!tel.isEmpty()) {

                if (!isNumeric(tel) || tel.length() != 8  || String.valueOf(Integer.parseInt(tel)).length() != 8) {
                    if (false) {
                        lbltel.setText("🠔 Ce numero de tel existe déja!");
                        vertel = false;
                        lbltel.setStyle("-fx-text-fill: red");
                    } else if (!isNumeric(tel) || tel.length() != 8 || String.valueOf(Integer.parseInt(tel)).length() != 8) {
                        lbltel.setText("🠔 C'est un nombre composé de 8 chiffres !");
                        vertel = false;
                        telfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                        lbltel.setStyle("-fx-text-fill: red");
                    }
                } else {
                    telfield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    vertel = true;
                    lbltel.setText("✓");
                    lbltel.setStyle("-fx-text-fill: #32CD32");
                }
            } else {
                vertel = true;
                telfield.setStyle(null);
                lbltel.setText("");
            }
        }
        catch (NullPointerException e){

        }
    }

    private boolean verMail = true ;

    @FXML
    void VerifMail(KeyEvent event){
        try {
            verMail = false;
            boolean b = false;
            String ml = mailfield.getText();
            if (!ml.isEmpty()) {
                if (ml.matches("[a-z0-9]*@[a-z0-9]*[.][a-z0-9]*")) {
                    verMail = true;
                    mailfield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    lblmail.setText("✓");
                    lblmail.setStyle("-fx-text-fill: #32CD32");
                } else {
                    lblmail.setText("🠔 le mail est sous la forme abc@ijk.xyz!");
                    verMail = false;
                    mailfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    lblmail.setStyle("-fx-text-fill: red");
                }
            }
            else{
                verMail = true;
                mailfield.setStyle("-fx-text-box-border: white;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblmail.setText("");
                lblmail.setStyle("-fx-text-fill: #32CD32");
            }
        }
        catch (NullPointerException e){

        }
    }

    @FXML
    void verifAdr (KeyEvent event){
        try {
            String ml = adrfield.getText();
            if(!ml.isEmpty()){
                adrfield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lbladr.setText("✓");
                lbladr.setStyle("-fx-text-fill: #32CD32");
            }
            else{
                adrfield.setStyle("-fx-text-box-border: white;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lbladr.setText("");
                lbladr.setStyle("-fx-text-fill: #32CD32");
            }
        }
        catch (NullPointerException e){

        }
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
            String cin = cinfield.getText();
            String type = Typefield.getValue();
            String nom = nomfield.getText();
            String prenom = prenomfield.getText();
            String desc = descriptionfiled.getText();


            if ( !vercin|| !vernom || !verprenom   ||type == null  || cin.isEmpty() || nom.isEmpty() || prenom.isEmpty() || !verMail || !vertel) {

                System.out.println("test");
                if(!verMail){
                    lblmail.setText("🠔 le mail est sous la forme abc@ijk.xyz!");
                    vertel = false;
                    mailfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    lblmail.setStyle("-fx-text-fill: red");
                }

                if(!vertel){
                    lbltel.setText("🠔 C'est un nombre composé de 8 chiffres !");
                    verMail = false;
                    telfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    lbltel.setStyle("-fx-text-fill: red");
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
                        String insertion = "INSERT INTO PERMISSION values(" + "null "+ "," + "\'" + type + "\'" + "," + "\'" + cin + "\'" + "," + "\'" + nom + "\'" + ","+"\'" + prenom + "\'" + "," +"\'" +desc+"\'"+","+"\'"+(telfield.getText())+"\'"+","+"\'"+mailfield.getText()+"\'"+","+"\'"+adrfield.getText()+"\'"+")";
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
                    }
                    catch (NumberFormatException e){
                        System.out.println(e.getMessage());
                    }
                } catch (SQLException | URISyntaxException throwables) {
                    throwables.printStackTrace();
                }

            }
        }
        catch (NullPointerException e){
            //System.out.println("you have an error go check please mr Mahdi");

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
        cinfield.setText("");
        nomfield.setText("");
        prenomfield.setText("");
        descriptionfiled.setText("");
        lblcin.setText("");
        lblType.setText("");
        lblnom.setText("");
        lblprenom.setText("");
        lblmail.setText("");
        lbltel.setText("");
        lbladr.setText("");
        mailfield.clear();
        telfield.clear();
        adrfield.clear();
        adrfield.setStyle("-fx-background-color:white;");
        mailfield.setStyle("-fx-background-color:white;");
        telfield.setStyle("-fx-background-color:white;");
        Typefield.setStyle("-fx-background-color:white;");
        prenomfield.setStyle("-fx-background-color:white;");
        nomfield.setStyle("-fx-background-color:white;");
        cinfield.setStyle("-fx-background-color:white;");
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select PERMISSIONSEQ.nextval from dual");
            while(rs.next()){
                // oblist.add(new Compte(rs.getString("cin"),rs.getString("pass"),""));
                lblid1.setText(rs.getString("nextval"));
            }
            rs.close();
        } catch (SQLException throwables) {
            System.out.println("1000000 dawa7");
        }
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
            ResultSet rs = connection.createStatement().executeQuery("select PERMISSIONSEQ.nextval from dual");
            while(rs.next()){
                // oblist.add(new Compte(rs.getString("cin"),rs.getString("pass"),""));
                lblid1.setText(rs.getString("nextval"));
            }
            rs.close();
        } catch (SQLException throwables) {
            System.out.println("1000000 dawa7");
        }

        ObservableList list= FXCollections.observableArrayList();
        list.removeAll();
        list.addAll(Type.permission_de_sortie.toString(),Type.permission_de_voirie.toString(),Type.permission_de_sortir_confinnement.toString());
        Typefield.getItems().setAll(list);

    }



}