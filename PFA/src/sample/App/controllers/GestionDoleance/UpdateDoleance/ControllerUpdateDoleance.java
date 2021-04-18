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
import sample.App.model.type_Doleance;

import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class ControllerUpdateDoleance implements Initializable {


    @FXML
    private Label lblId;
    @FXML
    private Label lbladr;
    @FXML
    private Label lbltel;
    @FXML
    private Label lblmail;

    @FXML
    private Label lblStatus ;

    @FXML
    private Label lblTypeError;

    @FXML
    private Label lblNomEror;

    @FXML
    private Label lblCinError;

    @FXML
    private Label lbl;//pour le succée s'affiche en vert

    @FXML
    private ChoiceBox<String> TypeEnum;

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

    private String cins ;

    private boolean update;

    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z][a-zA-Z ]*") && name.length()<=30;
    }

    public static boolean isNumeric(String string) {
        return string.matches("[0-9]+");
    }

    private boolean vernom,vercin;

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
    void verifNom(KeyEvent event) {
        vernom=false;
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

    @FXML
    void verifService(ActionEvent  event) {
        if(TypeEnum.getValue()==null){
            lblTypeError.setText("🠔 Selectionner le service");
            lblTypeError.setStyle("-fx-text-fill: red");
            TypeEnum.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
        else{
            lblTypeError.setStyle("-fx-text-fill: #32CD32");
            lblTypeError.setText("✓");
            TypeEnum.setStyle("-fx-background-color:white;");}
    }

    @FXML
    void confirmerButton(ActionEvent event) throws URISyntaxException {
        try {
            String cin = CinTextField.getText();
            String nom = nameTextField.getText();
            String service = TypeEnum.getValue();
            String description = DescriptionFiled.getText();
            if (!vernom || !vercin || service == null|| !verMail || !vertel) {
                if (nom.isEmpty()) {
                    lblNomEror.setText("🠔 Remplir ce champ");
                    lblNomEror.setStyle("-fx-text-fill: red");
                    nameTextField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }

                if(!verMail){
                    lblmail.setText("🠔 le mail est sous la forme abc@ijk.xyz!");
                    verMail = false;
                    mailfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    lblmail.setStyle("-fx-text-fill: red");
                }

                if(!vertel){
                    lbltel.setText("🠔 C'est un nombre composé de 8 chiffres !");
                    vertel = false;
                    telfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    lbltel.setStyle("-fx-text-fill: red");
                }

                if (cin.isEmpty()) {
                    lblCinError.setText("🠔 Remplir ce champ");
                    lblCinError.setStyle("-fx-text-fill: red");
                    CinTextField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
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
                if (service == null) {
                    lblTypeError.setText("🠔 Selectionner le service");
                    lblTypeError.setStyle("-fx-text-fill: red");
                    TypeEnum.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
                } else {
                    lblTypeError.setStyle("-fx-text-fill: #32CD32");
                    lblTypeError.setText("✓");
                    TypeEnum.setStyle("-fx-background-color:white;");
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
                            "Type = "+"\'"+TypeEnum.getValue()+"\'"+", Nom ="+"\'"+nameTextField.getText()+"\'"+", Cin = "+"\'"+CinTextField.getText()+"\'"+",tel = "+"\'"+(telfield.getText())+"\'"+", mail = "+"\'"+mailfield.getText()+"\'"+", adr = "+"\'"+adrfield.getText()+"\'"+", Description = "+"\'"+DescriptionFiled.getText()+"\'"+"where ID = "+Integer.parseInt(lblId.getText())+"";


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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //lblStatus.setText("Initiale");
        list.removeAll();
        list.addAll(type_Doleance.doleance.toString(),type_Doleance.chakwa.toString(),type_Doleance.matlab.toString());
        TypeEnum.getItems().setAll(list);

    }

    public void setTextField(String id, String type, String nom, String cin, String description,String Status,String tel ,String mail ,String adr) {
        this.CinTextField.setText(cin);
        this.lblId.setText(id);
        this.DescriptionFiled.setText(description);
        this.lblStatus.setText(Status);
        this.nameTextField.setText(nom);
        this.TypeEnum.setValue(type);
        this.adrfield.setText(adr);
        this.mailfield.setText(mail);
        this.telfield.setText(tel);
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
}
