package sample.App.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class SettingsController {

    private String gouvernorat [] = {"l'Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kébili", "Gouvernorat du Kef", "Mahdia", "Manouba", "Médenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan"};

    @FXML
    private AnchorPane Anch_settings;

    @FXML
    private Label valid_nom_muni;

    @FXML
    private Label valid_adresse;

    @FXML
    private Label valid_region;

    @FXML
    private Label valid_email;

    @FXML
    private Label valid_Telephone;

    @FXML
    private Label valid_maire_actuel;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private String URL_Logo;

    @FXML
    private TextField tf_nom_muni;

    @FXML
    private TextField tf_adresse;

    @FXML
    private TextField tf_region;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_Telephone;

    @FXML
    private Button btnEnreg;

    @FXML
    private TextField tf_maire_actuel;

    @FXML
    private ImageView logo;

    @FXML
    private Button btnselect_logo;

    @FXML
    private ComboBox<String> cmb_gouvernorat;
    @FXML
    private Label telLabel;



    @FXML
    public void cmdEnreg(ActionEvent event) throws IOException, SQLException {

        String update = "update settings set "+
                "logo=\'"+  URL_Logo+"\',"+
                "nom_muni=\'"+tf_nom_muni.getText()+"\',"+
                "adresse=\'"+tf_adresse.getText()+"\',"+
                "gouvernorat=\'"+cmb_gouvernorat.getSelectionModel().getSelectedItem().toString()+"\',"+
                "region=\'"+tf_region.getText()+"\',"+
                "email=\'"+tf_email.getText()+"\',"+
                "tel=\'"+tf_Telephone.getText()+"\',"+
                "maire_actuel=\'"+tf_maire_actuel.getText()+"\'"+" where id=1";

        System.out.println(update);
        System.out.println(URL_Logo);
        try{
            Connection connection= getOracleConnection();

            Statement statement = connection.createStatement();
            statement.execute(update);
            statement.execute("commit");

            System.out.println("Parametres mis à jour.");

        }
        catch (SQLException e){
            System.out.println("ERREUR!!");
        }
    }
    @FXML
    private TextArea DescriptionFiled;
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }
    public static boolean isNumeric(String string) {
        return string.matches("[0-9]+");
    }
    public static boolean isFloat(String string) {
        try {
            Float.parseFloat(string);
            return true;
        }catch(NumberFormatException e){

        }
        return false;
    }
    private boolean vernom,vertel=true;
    @FXML
    void VerifTel(KeyEvent event) {
        vertel=false;
        boolean b=false;
        String tel = tf_nom_muni.getText();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from Settings ");
            while(rs.next()){
                if(tel.equals(rs.getString("telephone"))){
                    b= true;
                    break;
                }
            }
            connection.close();} catch (SQLException e) {
            e.printStackTrace();
        }
        if (!tel.isEmpty()){

            if ( !isNumeric(tel) || tel.length() != 8||b||String.valueOf(Integer.parseInt(tel)).length()!=8) {
                if(b){
                    telLabel.setText("🠔 Ce numero de tel existe déja!");
                    vertel=false;
                    telLabel.setStyle("-fx-text-fill: red");}
                else if(!isNumeric(tel)||tel.length() != 8||String.valueOf(Integer.parseInt(tel)).length()!=8){
                    telLabel.setText("🠔 C'est un nombre composé de 8 chiffres !");
                    vertel=false;
                    tf_Telephone.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    telLabel.setStyle("-fx-text-fill: red");
                }}
            else {
                tf_Telephone.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                vertel=true;
                telLabel.setText("✓");
                telLabel.setStyle("-fx-text-fill: #32CD32");
            }}
        else{
            vertel=true;
            tf_Telephone.setStyle(null);
            telLabel.setText("");}
    }

    @FXML
    public void cmdSelectLogo(ActionEvent event) throws IOException, SQLException {
        FileChooser fc = new FileChooser();

        File selectedDFile = (File) fc.showOpenDialog(null);

        FileInputStream inputstream = new FileInputStream(selectedDFile.getAbsolutePath());
        Image image = new Image(inputstream);

        //System.out.println(selectedDFile.getAbsolutePath());
        URL_Logo=selectedDFile.getAbsolutePath();
        logo.setImage(image);
    }

    private void initGouv() {
        List<String> list = new ArrayList<String>();

        for(String content:gouvernorat) list.add(content);

        //ObservableList<String> obList = FXCollections.observableArrayList(list);
        cmb_gouvernorat.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    void validAdresse(KeyEvent event) {
        boolean adresseValidation = validation.DataValidation.addressFormat(tf_adresse, valid_adresse, "Format must be a valid address");
    }

    @FXML
    void validEmail(KeyEvent event) {
        boolean emailValidation = validation.DataValidation.emailFormat(tf_email, valid_email, "Format must be name@emailaddress.com");
    }

    @FXML
    void validNomMaire(KeyEvent event) {
        boolean nomMaireValidation = validation.DataValidation.nameFormat(tf_maire_actuel, valid_maire_actuel, "Format must be a name");
    }

    @FXML
    void validNomMun(KeyEvent event) {
        boolean nomMunValidation = validation.DataValidation.textAlphabet(tf_nom_muni, valid_nom_muni, "Format must be text");
    }

    @FXML
    void validPhone(KeyEvent event) {
        boolean phoneValidation = validation.DataValidation.phoneFormat(tf_Telephone, valid_Telephone, "Format must be a phone number");

    }

    @FXML
    void validRegion(KeyEvent event) {
        boolean regionValidation = validation.DataValidation.textAlphabet(tf_region, valid_region, "Format must be a valid region");
    }

    @FXML
    void initialize() {
        assert tf_nom_muni != null : "fx:id=\"tf_nom_muni\" was not injected: check your FXML file 'fiche.fxml'.";
        assert tf_adresse != null : "fx:id=\"tf_adresse\" was not injected: check your FXML file 'fiche.fxml'.";
        assert tf_region != null : "fx:id=\"tf_region\" was not injected: check your FXML file 'fiche.fxml'.";
        assert tf_email != null : "fx:id=\"tf_email\" was not injected: check your FXML file 'fiche.fxml'.";
        assert tf_Telephone != null : "fx:id=\"tf_Telephone\" was not injected: check your FXML file 'fiche.fxml'.";
        assert btnEnreg != null : "fx:id=\"btnEnreg\" was not injected: check your FXML file 'fiche.fxml'.";
        assert tf_maire_actuel != null : "fx:id=\"tf_maire_actuel\" was not injected: check your FXML file 'fiche.fxml'.";
        assert logo != null : "fx:id=\"logo\" was not injected: check your FXML file 'fiche.fxml'.";
        assert btnselect_logo != null : "fx:id=\"btnselect_logo\" was not injected: check your FXML file 'fiche.fxml'.";
        assert cmb_gouvernorat != null : "fx:id=\"cmb_gouvernorat\" was not injected: check your FXML file 'fiche.fxml'.";

        assert Anch_settings != null : "fx:id=\"Anch_settings\" was not injected: check your FXML file 'settings.fxml'.";
        assert valid_nom_muni != null : "fx:id=\"valid_nom_muni\" was not injected: check your FXML file 'settings.fxml'.";
        assert valid_adresse != null : "fx:id=\"valid_adresse\" was not injected: check your FXML file 'settings.fxml'.";
        assert valid_region != null : "fx:id=\"valid_region\" was not injected: check your FXML file 'settings.fxml'.";
        assert valid_email != null : "fx:id=\"valid_email\" was not injected: check your FXML file 'settings.fxml'.";
        assert valid_Telephone != null : "fx:id=\"valid_Telephone\" was not injected: check your FXML file 'settings.fxml'.";
        assert valid_maire_actuel != null : "fx:id=\"valid_maire_actuel\" was not injected: check your FXML file 'settings.fxml'.";

        valid_nom_muni.setText("");
        valid_adresse.setText("");
        valid_region.setText("");
        valid_email.setText("");
        valid_Telephone.setText("");
        valid_maire_actuel.setText("");

        initGouv();

        String query = "Select * from settings";

        Statement statement;

        try{
            Connection connection= getOracleConnection();
            statement = connection.createStatement();

            //get data from db
            ResultSet rs = statement.executeQuery(query);

            //fetch data
            while(rs.next()){
                FileInputStream inputstream = new FileInputStream(rs.getString("logo"));
                Image image = new Image(inputstream);

                logo.setImage(image);


                tf_nom_muni.setText(rs.getString("nom_muni"));
                tf_adresse.setText(rs.getString("adresse"));
                cmb_gouvernorat.getSelectionModel().select(rs.getString("gouvernorat"));
                tf_region.setText(rs.getString("region"));
                tf_email.setText(rs.getString("email"));
                tf_Telephone.setText(rs.getString("tel"));
                tf_maire_actuel.setText(rs.getString("maire_actuel"));

            }
            rs.close();
        }
        catch (SQLException | FileNotFoundException  e){
            System.out.println("Erreur!!");
        }

    }
}
