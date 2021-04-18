package sample.App.controllers.GestionRevenu;

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
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import sample.App.model.Type;
import sample.App.model.type_Doleance;


import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class CreationRevenu implements Initializable {


    @FXML
    private Label matriculelbl;

    @FXML
    private Label lblType ;
    @FXML
    private TextArea descfield ;
    @FXML
    private Label lblprix;

    @FXML
    private Label lbldate;

    @FXML
    private TextField prixfield ;

    @FXML
    private ChoiceBox<String> Typefield;

    @FXML
    private DatePicker datefield;

    @FXML
    private Button buttonConfirmer ;

    @FXML
    private Button buttonFermer ;

    private boolean matricule = false;
    private boolean marque = false ;


    private boolean verservice,verprix,verdate ;

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
    void verifService(ActionEvent  event) {
        if(Typefield.getValue()==null){
            lblType.setText("🠔 Selectionner le type d'engin");
            verservice = false;
            lblType.setStyle("-fx-text-fill: red");
            Typefield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
        else{
            lblType.setStyle("-fx-text-fill: #32CD32");
            lblType.setText("✓");
            verservice = true;
            Typefield.setStyle("-fx-background-color:white;");}
    }

    @FXML
    void verifPrix (KeyEvent event){
        String salaire = prixfield.getText();
        if (!salaire.isEmpty()){
            if (!isFloat(salaire)) {
                lblprix.setText("🠔 Le prix est un nombre réel! Max Valeur = 999999.999");
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
            lblprix.setText("🠔 Remplir ce champ");
            prixfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            lblprix.setStyle("-fx-text-fill: red");
        }
    }


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
    void confirmerButton(ActionEvent event) throws URISyntaxException {
        try {
            String type = Typefield.getValue();
            LocalDate dispo = datefield.getValue();
            String prix = prixfield.getText();
            if (!verdate || !verprix) {
                if(!verdate) {
                    lbldate.setText("🠔 Remplir ce champ");
                    verdate = false;
                    lbldate.setStyle("-fx-text-fill: red");
                    datefield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
                }
                if(!verprix){
                    verprix=false;
                    lblprix.setText("🠔 Remplir ce champ");
                    prixfield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    lblprix.setStyle("-fx-text-fill: red");
                }
            }
            else
            {
                Connection connection = null;
                try {
                    connection = getOracleConnection();
                    try {

                        String insertion = "insert into revenu values (null,"   +"\'"+Typefield.getValue()+"\'"+","+"\'"+prixfield.getText()+"\'"+","+"\'"+convertDate(String.valueOf(datefield.getValue()))+"\'"+","+"\'"+descfield.getText() +"\'"+" )";
                        PreparedStatement rs = connection.prepareStatement(insertion);
                        //System.out.println(insertion);
                        rs.execute();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.TRANSPARENT);
                        alert.setHeaderText(null);
                        alert.setContentText("Ajout avec succés");
                        alert.setGraphic(new ImageView(getClass().getResource("../../../images/approved.png").toURI().toString()));
                        alert.showAndWait();
                        refresh();
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
        lbldate.setText("");
        lblprix.setText("");
        lblType.setText("");
        datefield.setValue(null);
        prixfield.setText("");
        descfield.clear();
        Typefield.setStyle("-fx-background-color:white;");
        datefield.setStyle("-fx-background-color:white;");
        prixfield.setStyle("-fx-background-color:white;");
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select SEQREVENU.nextval from dual");
            while(rs.next()){
                // oblist.add(new Compte(rs.getString("cin"),rs.getString("pass"),""));
                matriculelbl.setText(rs.getString("nextval"));
            }
            rs.close();
            lbldate.setText("");
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
            ResultSet rs = connection.createStatement().executeQuery("select SEQREVENU.nextval from dual");
            while(rs.next()){
                // oblist.add(new Compte(rs.getString("cin"),rs.getString("pass"),""));
                matriculelbl.setText(rs.getString("nextval"));
            }
            rs.close();
        } catch (SQLException throwables) {
            System.out.println("1000000 dawa7");
        }


        ObservableList list= FXCollections.observableArrayList();
        list.removeAll();
        list.addAll(Type.recette_fiscale.toString(),Type.recette_non_fiscale.toString(),Type.Autre.toString());
        Typefield.getItems().setAll(list);

    }



}