package sample.App.controllers.GestionEngin;

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


public class UpdateEngin implements Initializable {

    @FXML
    private Label matriculelbl;

    @FXML
    private Label lblType ;

    @FXML
    private Label lblprix;

    @FXML
    private Label lblmarque;

    @FXML
    private Label lbldispo ;

    @FXML
    private TextField marquefield ;

    @FXML
    private TextField prixfield ;

    @FXML
    private ChoiceBox<String> Typefield;

    @FXML
    private ChoiceBox<String> dispofield;

    @FXML
    private Button buttonConfirmer ;

    @FXML
    private Button buttonFermer ;

    private String id ;
    private boolean marque = false ;


    @FXML
    void verifMarque (KeyEvent event){
        String mat = marquefield.getText();
        if(!mat.isEmpty()){
            marquefield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            lblmarque.setText("✓");
            marque = true ;
            lblmarque.setStyle("-fx-text-fill: #32CD32");}
        else{
            marquefield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            lblmarque.setStyle("-fx-text-fill: red");
            lblmarque.setText("🠔 Remplir ce champ");
            marque = false ;
        }
    }

    @FXML
    void verifService(ActionEvent  event) {
        if(Typefield.getValue()==null){
            lblType.setText("🠔 Selectionner le type d'engin");
            lblType.setStyle("-fx-text-fill: red");
            Typefield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
        else{
            lblType.setStyle("-fx-text-fill: #32CD32");
            lblType.setText("✓");
            Typefield.setStyle("-fx-background-color:white;");}

        lbldispo.setStyle("-fx-text-fill: #32CD32");
        lbldispo.setText("✓");
        dispofield.setStyle("-fx-background-color:white;");
    }


    private boolean versal = true ;

    public static boolean isFloat(String string) {
        try {
            Float.parseFloat(string);
            if (string.length() >=3){

                try {
                    String[] p = string.split("\\.");
                    if (p[0].length() <= 6 && p[1].length()<=3)
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
        versal=false;
        String salaire = prixfield.getText();
        if (!salaire.isEmpty()){
            if (!isFloat(salaire)) {
                lblprix.setText("🠔 Le prix est un nombre réel! Max Valeur = 999999.999");
                prixfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblprix.setStyle("-fx-text-fill: red");
                versal=false;
            } else {
                prixfield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                lblprix.setText("✓");
                versal=true;
                lblprix.setStyle("-fx-text-fill: #32CD32");}}
        else{
            versal=true;
            prixfield.setStyle(null);
            lblprix.setText("");
        }
    }

    @FXML
    void confirmerButton(ActionEvent event) throws URISyntaxException {
        try {
            String mar = marquefield.getText();
            String type = Typefield.getValue();
            String dispo = dispofield.getValue();
            String prix = prixfield.getText();
            if (!versal || !marque || type == null || dispo == null) {

                System.out.println("test");

                if (mar.isEmpty()) {
                    lblmarque.setText("🠔 Remplir ce champ");
                    lblmarque.setStyle("-fx-text-fill: red");
                    marquefield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }
                if (!versal && prix.isEmpty()) {
                    lblmarque.setText("🠔 Remplir ce champ");
                    lblmarque.setStyle("-fx-text-fill: red");
                    marquefield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }

                if (!marque && mar.isEmpty()) {
                    lblmarque.setText("🠔 Remplir ce champ");
                    lblmarque.setStyle("-fx-text-fill: red");
                    marquefield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }



                if (type == null) {
                    lblType.setText("🠔 Selectionner le service");
                    lblType.setStyle("-fx-text-fill: red");
                    Typefield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
                } else {
                    lblType.setStyle("-fx-text-fill: #32CD32");
                    lblType.setText("✓");
                    Typefield.setStyle("-fx-background-color:white;");
                }
                if (dispo == null) {
                    lbldispo.setText("🠔 Selectionner le service");
                    lbldispo.setStyle("-fx-text-fill: red");
                    dispofield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
                } else {
                    lbldispo.setStyle("-fx-text-fill: #32CD32");
                    lbldispo.setText("✓");
                    dispofield.setStyle("-fx-background-color:white;");
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.TRANSPARENT);
                alert.setHeaderText(null);
                alert.setContentText("Un des champs n'est pas correctement inserer");
                alert.setGraphic(new ImageView(getClass().getResource("../../images/errorinsert.png").toURI().toString()));
                alert.showAndWait();
            }
            else
                {
                Connection connection = null;
                try {
                    connection = getOracleConnection();
                    try {
                        String insertion = "Update engin set " +
                                "Type = "+"\'"+Typefield.getValue()+"\'"+", marque = "+"\'"+marquefield.getText()+"\'"+",prix = "+Float.parseFloat(prixfield.getText())+", dispo = "+"\'"+dispofield.getValue()+"\'"+"where ID = "+"\'"+id+"\'"+"";
                        PreparedStatement rs = connection.prepareStatement(insertion);
                        //System.out.println(insertion);
                        if (isFloat(prixfield.getText()))
                            rs.execute();


                        //lbl.setText("Ajout avec succés");
                        refresh();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.TRANSPARENT);
                        alert.setHeaderText(null);
                        alert.setContentText("Modification avec succés");
                        alert.setGraphic(new ImageView(getClass().getResource("../../../images/approved2.png").toURI().toString()));
                        alert.showAndWait();
                        Stage stage = (Stage) buttonConfirmer.getScene().getWindow();
                        // do what you have to do
                        stage.close();
                    }
                    catch (NumberFormatException e){
                        lblprix.setText("🠔 Remplir ce champ");
                        lblprix.setStyle("-fx-text-fill: red");
                        prixfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");

                    }
                } catch (SQLException | URISyntaxException throwables) {
                    throwables.printStackTrace();
                }

            }
        }
        catch (NullPointerException e){
            //System.out.println("you have an error go check please mr Mahdi");

        }
    }

    private void refresh(){
        Typefield.setValue(null);
        dispofield.setValue(null);
        prixfield.clear();
        lblprix.setText("");
        marquefield.setText("");
        matriculelbl.setText("");
        lbldispo.setText("");
        lblmarque.setText("");
        lblType.setText("");
        Typefield.setStyle("-fx-background-color:white;");
        dispofield.setStyle("-fx-background-color:white;");
        marquefield.setStyle("-fx-background-color:white;");

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
        ObservableList list1= FXCollections.observableArrayList();
        list.removeAll();
        list.addAll(Type.Autobus.toString(),Type.Camion.toString(),Type.Camionnette.toString(),Type.Tractor.toString(),Type.Trax.toString(),Type.Voiture.toString());
        Typefield.getItems().setAll(list);
        Typefield.setValue(Type.Voiture.toString());
        list1.removeAll();
        list1.addAll(Type.Oui.toString(),Type.Non.toString());
        dispofield.getItems().setAll(list1);
        dispofield.setValue(Type.Oui.toString());

    }

    public void setTextField(String id, String type, String dispo, String mar ,Float prix)  {
        this.id = id ;
        this.matriculelbl.setText(id);
        this.marquefield.setText(mar);
        this.Typefield.setValue(type);
        this.dispofield.setValue(dispo);
        this.prixfield.setText(String.valueOf(prix));
    }


}