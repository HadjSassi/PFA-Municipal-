package sample.App.controllers.GestionDoleance.UpdateDoleance;

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
import sample.App.model.Etat;
import sample.App.model.type_Doleance;

import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class ControllerUpdateDoleanceSuper implements Initializable {


    @FXML
    private Label lblId;

    @FXML
    private ChoiceBox<String> EtatEnum;

    @FXML
    private Label lblTypeError;


    @FXML
    private Label lbladr;
    @FXML
    private Label lbltel;
    @FXML
    private Label lblmail;

    @FXML
    private Label lblNomEror;

    @FXML
    private Label lblCinError;

    @FXML
    private Label lbl;//pour le succée s'affiche en vert

    @FXML
    private TextField TypeEnum;

    @FXML
    private TextField nameTextField ;

    @FXML
    private TextField CinTextField ;


    @FXML
    private TextField telfield ;

    @FXML
    private TextField mailfield ;

    @FXML
    private TextField adrfield ;

    @FXML
    private TextArea DescriptionFiled ;

    @FXML
    private Button buttonConfirmer ;

    @FXML
    private Button buttonFermer ;


    @FXML
    private DatePicker datefield;

    @FXML
    private Label lbldate;

    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z][a-zA-Z ]*") && name.length()<=30;
    }

    public static boolean isNumeric(String string) {
        return string.matches("[0-9]+");
    }

    private boolean vernom = true ,vercin = true,verdate = true ;

    @FXML
    void verifCin(KeyEvent event) {
        vercin=false;
        String cin = CinTextField.getText();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from doleance ");
            connection.close();} catch (SQLException e) {
            e.printStackTrace();
        }

        if(!cin.isEmpty()){

            if (!isNumeric(cin) || cin.length() != 8) {
                if(!isNumeric(cin)||cin.length() != 8){
                    lblCinError.setText("🠔 Le numero de cin est un nombre composé de 8 chiffres !");
                    vercin=false;
                    CinTextField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    lblCinError.setStyle("-fx-text-fill: red");}}
            else {
                CinTextField.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblCinError.setText("✓");
                vercin=true;
                lblCinError.setStyle("-fx-text-fill: #32CD32");}}
        else{
            CinTextField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            vercin=false;
            lblCinError.setText("");}
    }

    private boolean vertel = true ;


    @FXML
    void verifDate(ActionEvent  event) {
        if(String.valueOf(datefield.getValue()).length()!=10){
            lbldate.setText("🠔 Remplir ce champ");
            verdate = false;
            lbldate.setStyle("-fx-text-fill: red");
            datefield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
        else{
            if(Period.between(datefield.getValue(), LocalDate.now()).getDays()<0){
                lbldate.setText("🠔 Date invalide");
                verdate = false;
                lbldate.setStyle("-fx-text-fill: red");
                datefield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
            else{
                lbldate.setStyle("-fx-text-fill: #32CD32");
                lbldate.setText("✓");
                verdate = true;
                datefield.setStyle("-fx-background-color:#32CD32;");}
        }}

    private String convertDate (String d){
        String year = ""+d.charAt(0)+d.charAt(1)+d.charAt(2)+d.charAt(3);
        String month= ""+d.charAt(5)+d.charAt(6);
        String day=""+d.charAt(8)+d.charAt(9) ;
        return ""+day+"/"+month+"/"+year;
    }

    @FXML
    void VerifTel(KeyEvent event) {
        try {
            vertel = false;
            boolean b = false;
            String tel = telfield.getText();
            if (!tel.isEmpty()) {

                if (!isNumeric(tel) || tel.length() != 8 || b || String.valueOf(Integer.parseInt(tel)).length() != 8) {
                    if (b) {
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
    void verifNom(KeyEvent event) {
        String nom = nameTextField.getText();
        if(!nom.isEmpty()){
            if (!isAlpha(nom)) {
                lblNomEror.setText("🠔 Le nom est composé de lettres alphabétiques!");
                nameTextField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                vernom=false;
                lblNomEror.setStyle("-fx-text-fill: red");
            } else {
                nameTextField.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblNomEror.setText("✓");
                vernom=true;
                lblNomEror.setStyle("-fx-text-fill: #32CD32");}}
        else{
            nameTextField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            vernom=false;
            lblNomEror.setText("");
        }
    }

    private boolean verser ;
    @FXML
    void verifService(KeyEvent  event) {
        if(TypeEnum.getText().isEmpty()){
            verser = false;
            lblTypeError.setText("🠔 Saisir le type doleance");
            lblTypeError.setStyle("-fx-text-fill: red");
            TypeEnum.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
        else{
            verser =true;
            lblTypeError.setStyle("-fx-text-fill: #32CD32");
            lblTypeError.setText("✓");
            TypeEnum.setStyle("-fx-background-color:white;");}
    }

    @FXML
    void confirmerButton(ActionEvent event) throws URISyntaxException {
        try {
            String cin = CinTextField.getText();
            String nom = nameTextField.getText();
            String service = TypeEnum.getText();
            String description = DescriptionFiled.getText();
            if (!verdate ||!vernom || !vercin || service == null|| !verMail || !vertel || !verser) {
                if (nom.isEmpty()) {
                    lblNomEror.setText("🠔 Remplir ce champ");
                    lblNomEror.setStyle("-fx-text-fill: red");
                    nameTextField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }

                if(!verser){
                    if(TypeEnum.getText().isEmpty()){
                        verser = false;
                        lblTypeError.setText("🠔 Saisir le type doleance");
                        lblTypeError.setStyle("-fx-text-fill: red");
                        TypeEnum.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
                    else{
                        verser =true;
                        lblTypeError.setStyle("-fx-text-fill: #32CD32");
                        lblTypeError.setText("✓");
                        TypeEnum.setStyle("-fx-background-color:white;");}
                }

                if(!verdate) {
                    lbldate.setText("🠔 Remplir ce champ");
                    verdate = false;
                    lbldate.setStyle("-fx-text-fill: red");
                    datefield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
                }
                if (cin.isEmpty()) {
                    lblCinError.setText("🠔 Remplir ce champ");
                    lblCinError.setStyle("-fx-text-fill: red");
                    CinTextField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }


                if(!verMail){
                    lblmail.setText("🠔 le mail est sous la forme abc@ijk.xyz!");
                    mailfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    lblmail.setStyle("-fx-text-fill: red");
                }

                if(!vertel){
                    lbltel.setText("🠔 C'est un nombre composé de 8 chiffres !");
                    telfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    lbltel.setStyle("-fx-text-fill: red");
                }


                if (!vernom && nom.isEmpty()) {
                    lblNomEror.setText("🠔 Remplir ce champ");
                    lblNomEror.setStyle("-fx-text-fill: red");
                    nameTextField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }
                if (!vercin && cin.isEmpty()) {
                    lblCinError.setText("🠔 Remplir ce champ");
                    lblCinError.setStyle("-fx-text-fill: red");
                    CinTextField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.TRANSPARENT);
                alert.setHeaderText(null);
                alert.setContentText("Un des champs n'est pas correctement inserer");
                alert.setGraphic(new ImageView(getClass().getResource("../../../../images/errorinsert.png").toURI().toString()));
                alert.showAndWait();
            } else {
                Connection connection = null;
                try {
                    connection = getOracleConnection();

                    //String insertion = "UPDATE doleance values("+Integer.parseInt(lblId.getText())+","+"\'"+TypeEnum.getValue()+"\'"+","+"\'"+nameTextField.getText()+"\'"+","+"\'"+CinTextField.getText()+"\'"+",'initiale',"+"\'"+DescriptionFiled.getText()+"\'"+")";

                    String insertion = "Update doleance set " +
                            "Type = "+"\'"+TypeEnum.getText()+"\'"+", Nom ="+"\'"+nameTextField.getText()+"\'"+",dates = "+"\'"+convertDate(String.valueOf(datefield.getValue()))+"\'"+", Cin = "+"\'"+CinTextField.getText()+"\'"+",tel = "+"\'"+(telfield.getText())+"\'"+", mail = "+"\'"+mailfield.getText()+"\'"+", adr = "+"\'"+adrfield.getText()+"\'"+", Description = "+"\'"+DescriptionFiled.getText()+"\'"+", STATUS = "+"\'"+EtatEnum.getValue()+"\'"+"where ID = "+Integer.parseInt(lblId.getText())+"";


                    PreparedStatement rs = connection.prepareStatement(insertion);
                    //System.out.println(insertion);
                    rs.execute();
                    //lbl.setText("Ajout avec succés");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.TRANSPARENT);
                    alert.setHeaderText(null);
                    alert.setContentText("Modification avec succés");
                    alert.setGraphic(new ImageView(getClass().getResource("../../../../images/approved2.png").toURI().toString()));
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
            //System.out.println("you have an error go check please mr Mahdi");
        }
    }


    @FXML
    void fermerButton(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) buttonFermer.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    ObservableList list= FXCollections.observableArrayList();
    ObservableList list2= FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list2.removeAll();
        list2.addAll(Etat.Initiale.toString(),Etat.Approuvé.toString(),Etat.Refusé.toString(),Etat.EnCours.toString(),Etat.Terminé.toString());
        EtatEnum.getItems().setAll(list2);
        EtatEnum.setValue(Etat.Initiale.toString());
    }
    public void setTextField(String id, String type, String nom, String cin, String description,String Status,String tel ,String mail ,String adr, String date) {
        this.CinTextField.setText(cin);
        this.lblId.setText(id);
        this.DescriptionFiled.setText(description);
        this.EtatEnum.setValue(Status);
        this.nameTextField.setText(nom);
        this.TypeEnum.setText(type);
        this.adrfield.setText(adr);
        this.mailfield.setText(mail);
        this.telfield.setText(tel);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.FRANCE);
        LocalDate dateTime = LocalDate.parse(date, formatter);
        this.datefield.setValue(dateTime);
        verdate= true;

    }


}
