package sample.App.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.App.model.Service;

import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class gPerModifController implements Initializable {
    Stage stage;
    String matricule="";
    String cin="";
    String nom="";
    String Prenom="";
    String tel="";
    String togFemme="";
    String togHomme="";
    String salaire="";
    String birth="";
    String service="";
    String description="";
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Label MatriculeField;

    @FXML
    private TextField CinField;

    @FXML
    private TextField NomFiled;

    @FXML
    private TextField PrenomFiled;

    @FXML
    private TextField TelFiled;

    @FXML
    private RadioButton FemmeTog;

    @FXML
    private ToggleGroup togSex;

    @FXML
    private RadioButton HommeTog;

    @FXML
    private DatePicker BirthField;

    @FXML
    private ChoiceBox<String> ServiceField;

    @FXML
    private TextField SalaireFiled;
    @FXML
    private Label matLabel;

    @FXML
    private Label nomLabel;

    @FXML
    private Label prenomLabel;

    @FXML
    private Label cinLabel;

    @FXML
    private Label telLabel;

    @FXML
    private Label sexLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label serviceLabel;

    @FXML
    private Label salaireLabel;
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
    private boolean vernom=true,verprenom=true,vercin=true,vertel=true,versal=true;
    @FXML
    void VerifTel(KeyEvent event) {
        vertel=false;
        boolean b=false;
        String tel = TelFiled.getText();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from PERSONNEL ");
            while(rs.next()){
                if(!tel.equals(this.tel))
                    if(tel.equals(rs.getString("tel"))){
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
                else if(!isNumeric(tel)||String.valueOf(Integer.parseInt(tel)).length()!=8||tel.length()!=8){
                    telLabel.setText("🠔 C'est un nombre composé de 8 chiffres !");
                    vertel=false;
                TelFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                telLabel.setStyle("-fx-text-fill: red");
            }}
            else {
                TelFiled.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                vertel=true;
                telLabel.setText("✓");
                telLabel.setStyle("-fx-text-fill: #32CD32");
            }}
        else{
            vertel=true;
            TelFiled.setStyle(null);
            telLabel.setText("");}
    }
    @FXML
    void verifCin(KeyEvent event) {
        vercin=false;
        boolean b=false;
        String cin = CinField.getText();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from PERSONNEL ");
            while(rs.next()){
                if(!cin.equals(this.cin))
                    if(cin.equals(rs.getString("cin"))){
                        b= true;
                        break;
                    }
            }
            connection.close();} catch (SQLException e) {
            e.printStackTrace();
        }

        if(!cin.isEmpty()){

            if (!isNumeric(cin) || cin.length() != 8||b) {
                if(b){
                    cinLabel.setText("🠔 Ce numero de cin existe déja!");
                    vercin=false;
                    cinLabel.setStyle("-fx-text-fill: red");}
                else
                if(!isNumeric(cin)||cin.length() != 8){
                    cinLabel.setText("🠔 Le numero de cin est un nombre composé de 8 chiffres !");
                    vercin=false;
                 CinField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    cinLabel.setStyle("-fx-text-fill: red");}}
             else {
                CinField.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                cinLabel.setText("✓");
                vercin=true;
                cinLabel.setStyle("-fx-text-fill: #32CD32");}}
        else{
            CinField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            vercin=false;
            cinLabel.setText("");}
    }

    @FXML
    void verifDate(ActionEvent  event) {
        if(String.valueOf(BirthField.getValue()).length()!=10){
            dateLabel.setText("🠔 Remplir ce champ");
            dateLabel.setStyle("-fx-text-fill: red");
            BirthField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
        else{
            dateLabel.setStyle("-fx-text-fill: #32CD32");
            dateLabel.setText("✓");
            BirthField.setStyle("-fx-background-color:transparent;");}
    }

    @FXML
    void verifNom(KeyEvent event) {
        vernom=false;
        String nom = NomFiled.getText();
        if(!nom.isEmpty()){
            if (!isAlpha(nom)) {
                nomLabel.setText("🠔 Le nom est composé de lettres alphabétiques!");
                NomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                vernom=false;
                nomLabel.setStyle("-fx-text-fill: red");
            } else {
                NomFiled.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                nomLabel.setText("✓");
                vernom=true;
                nomLabel.setStyle("-fx-text-fill: #32CD32");}}
        else{
            NomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            vernom=false;
            nomLabel.setText("");
        }
    }

    @FXML
    void verifPrenom(KeyEvent event) {
        verprenom=false;
        String prenom = PrenomFiled.getText();
        if(!prenom.isEmpty()){
            if (!isAlpha(prenom)) {
                prenomLabel.setText("🠔 Le prenom est composé de lettres alphabétiques!");
                PrenomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                verprenom=false;
                prenomLabel.setStyle("-fx-text-fill: red");
            } else {
                PrenomFiled.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                prenomLabel.setText("✓");
                verprenom=true;
                prenomLabel.setStyle("-fx-text-fill: #32CD32");}}
        else{
            PrenomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            verprenom=false;
            prenomLabel.setText("");
        }
    }

    @FXML
    void verifSalaire(KeyEvent event) {
        versal=false;
        String salaire = SalaireFiled.getText();
        if (!salaire.isEmpty()){
            if (!isFloat(salaire)) {
                salaireLabel.setText("🠔 Le salaire est un nombre réel!");
                SalaireFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                versal=false;
                salaireLabel.setStyle("-fx-text-fill: red");
            } else {
                SalaireFiled.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                salaireLabel.setText("✓");
                versal=true;
                salaireLabel.setStyle("-fx-text-fill: #32CD32");}}
        else{
            versal=true;
            SalaireFiled.setStyle(null);
            salaireLabel.setText("");
        }
    }
    @FXML
    void verifService(ActionEvent  event) {
        if(ServiceField.getValue()==null){
            serviceLabel.setText("🠔 Selectionner le service");
            serviceLabel.setStyle("-fx-text-fill: red");
            ServiceField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
    else{
        serviceLabel.setStyle("-fx-text-fill: #32CD32");
        serviceLabel.setText("✓");
        ServiceField.setStyle("-fx-background-color:white;");}}
    @FXML
    void verifSex(ActionEvent event) {
        if(!FemmeTog.isSelected() && !HommeTog.isSelected()){
            sexLabel.setText("🠔 Selectionner le sex");
            sexLabel.setStyle("-fx-text-fill: red");}
        else{
            sexLabel.setStyle("-fx-text-fill: #32CD32");
            sexLabel.setText("✓");}
    }

    @FXML
    void handleClicksAjout(ActionEvent event) throws SQLException, URISyntaxException {
        matricule = MatriculeField.getText();
        cin = CinField.getText();
        nom = NomFiled.getText();
        Prenom = PrenomFiled.getText();
        tel = TelFiled.getText();
        togFemme = FemmeTog.getText();
        togHomme = HommeTog.getText();
        salaire =  SalaireFiled.getText();
        birth = String.valueOf(BirthField.getValue());
        service = ServiceField.getValue();
        description = DescriptionFiled.getText();
        if(!vernom||!verprenom||birth.length()!=10||(!FemmeTog.isSelected() && !HommeTog.isSelected())||!vertel||!vercin||!versal){
            if(nom.isEmpty()&&!vernom){
                nomLabel.setText("🠔 Remplir ce champ ");
                nomLabel.setStyle("-fx-text-fill: red");
                NomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            }

            if(Prenom.isEmpty()&&!verprenom){
                prenomLabel.setText("🠔 Remplir ce champ ");
                prenomLabel.setStyle("-fx-text-fill: red");
                PrenomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");}

            if(cin.isEmpty()&&!vercin){
                cinLabel.setText("🠔 Remplir ce champ ");
                cinLabel.setStyle("-fx-text-fill: red");
                CinField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");}
            if(birth.length()!=10){
                dateLabel.setText("🠔 Remplir ce champ ");
                dateLabel.setStyle("-fx-text-fill: red");
                BirthField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}

            if(!FemmeTog.isSelected() && !HommeTog.isSelected()){
                sexLabel.setText("🠔 Selectionner le sex");
                sexLabel.setStyle("-fx-text-fill: red");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setHeaderText(null);
            alert.setContentText("Un des champs n'est pas correctement inserer");
            alert.setGraphic(new ImageView(getClass().getResource("../../images/errorinsert.png").toURI().toString() ));
            alert.showAndWait();
        }
        else{
            Connection connection= getOracleConnection();
            PreparedStatement rs = connection.prepareStatement("UPDATE PERSONNEL SET cin=?,nom=?,prenom=?,naissance=?,salaire=?,sex=?,tel=?,service=?,description=? WHERE matricule=?");
            rs.setString(1,cin);
            rs.setString(2,nom);
            rs.setString(3,Prenom);
            rs.setDate(4, Date.valueOf(birth));
            if(isFloat(salaire)){
                rs.setFloat(5, Float.parseFloat(salaire));}
            else
                rs.setInt(5, 0);
            if(FemmeTog.isSelected()){
                rs.setString(6,togFemme);}
            if(HommeTog.isSelected()){
                rs.setString(6,togHomme);}
            if(isNumeric(tel) && String.valueOf(Integer.parseInt(tel)).length()==8 && tel.length()==8 && vertel){
                rs.setInt(7, Integer.parseInt(tel));}
            else
                rs.setInt(7, 0);
            rs.setString(8,service);
            rs.setString(9,description);
            rs.setString(10,matricule);
            rs.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setHeaderText(null);
            alert.setContentText("Modification avec succés");
            alert.setGraphic(new ImageView(getClass().getResource("../../images/approved2.png").toURI().toString() ));
            alert.showAndWait();
            stage = (Stage) anchorpane.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void handleClicksAnnuler(ActionEvent event) {
        stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();
    }
    ObservableList list= FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.removeAll();
        list.addAll(Service.Administration.toString(),Service.Direction.toString(),Service.Terrain.toString(),Service.Ouvrier.toString());
        ServiceField.getItems().setAll(list);

    }
    public void setTextFiel(String matriculey,String nomy,String prenomy,String ciny,int tely,String togsexy,LocalDate birthy,String servicey,Float salairey,String descriptiony){
        MatriculeField.setText(matriculey);
        ServiceField.setValue(servicey);
        CinField.setText(ciny);
        NomFiled.setText(nomy);
        PrenomFiled.setText(prenomy);
        if(tely!=0)
            TelFiled.setText(String.valueOf(tely));
        if(salairey!=0)
            SalaireFiled.setText(String.valueOf(salairey));
        if(togsexy.equals("Femme"))
            FemmeTog.setSelected(true);
        else
            HommeTog.setSelected(true);
        BirthField.setValue(birthy);
        DescriptionFiled.setText(descriptiony);
        matricule = MatriculeField.getText();
        cin = CinField.getText();
        nom = NomFiled.getText();
        Prenom = PrenomFiled.getText();
        tel = TelFiled.getText();
        togFemme = FemmeTog.getText();
        togHomme = HommeTog.getText();
        salaire =  SalaireFiled.getText();
        birth = String.valueOf(BirthField.getValue());
        service = ServiceField.getValue();
        description = DescriptionFiled.getText();
    }
}
