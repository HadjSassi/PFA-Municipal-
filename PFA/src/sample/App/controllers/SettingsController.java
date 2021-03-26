package sample.App.controllers;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.scene.input.MouseEvent;

//import static OracleConnection.OracleConnection.getOracleConnection;
import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class SettingsController {

    private String gouvernorat [] = {"l'Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kébili", "Gouvernorat du Kef", "Mahdia", "Manouba", "Médenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan"};



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
        catch (SQLException | FileNotFoundException e){
            System.out.println("Erreur!!");
        }

    }
}
