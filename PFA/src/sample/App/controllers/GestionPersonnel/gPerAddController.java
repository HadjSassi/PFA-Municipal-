package sample.App.controllers.GestionPersonnel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.App.model.Personnel;
import sample.App.model.Service;

import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;


import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class gPerAddController implements Initializable {
    Stage stage;
    @FXML
    private AnchorPane anchorpane;

    @FXML
    public TextField MatriculeField;

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
    public TextField getMatriculeField() {
        return MatriculeField;
    }
    @FXML
    private Label salaireLabel;
    @FXML
    private TextArea DescriptionFiled;
    private boolean isAlpha(String name) {
        return name.matches("[a-zA-Z][a-zA-Z ]*") && name.length()<=30;
    }
    private static boolean isNumeric(String string) {
        return string.matches("[0-9]+");
    }
    private static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }}
    public static boolean isFloat(String string) {
        try {
            Float.parseFloat(string);
            if (string.length() >=3){

                try {
                    String[] p = string.split("\\.");
                    if (p[0].length() <= 4 && p[1].length()<=3)
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
    private boolean vermat,vernom,verprenom,vercin,verdate,vertel=true,versal=true;
    @FXML
    void VerifTel(KeyEvent event) {
        vertel=false;
        boolean b=false;
        String tel = TelFiled.getText();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from PERSONNEL ");
            while(rs.next()){
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
                else if(!isNumeric(tel)||tel.length() != 8||String.valueOf(Integer.parseInt(tel)).length()!=8){
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
        verdate=false;
        if(String.valueOf(BirthField.getValue()).length()!=10){
            dateLabel.setText("🠔 Remplir ce champ");
            verdate=false;
            dateLabel.setStyle("-fx-text-fill: red");
            BirthField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
        else{
            if(calculateAge(BirthField.getValue(),LocalDate.now())<=18){
                dateLabel.setText("🠔 Date de naissance invalide");
                verdate=false;
                dateLabel.setStyle("-fx-text-fill: red");
                BirthField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
        else{
            dateLabel.setStyle("-fx-text-fill: #32CD32");
            verdate=true;
            dateLabel.setText("✓");
            BirthField.setStyle("-fx-background-color:transparent;");}
    }}

    @FXML
    void verifMat(KeyEvent event) {
        vermat=false;
        String matricule = MatriculeField.getText();
        boolean b=false;
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from PERSONNEL ");
            while(rs.next()){
                if(matricule.equals(rs.getString("matricule"))){
                    b= true;
                    break;
                }
            }
            connection.close();} catch (SQLException e) {
            e.printStackTrace();
        }
        if(!matricule.isEmpty()){
        if ( !isNumeric(matricule) || matricule.length() != 8||b) {
            if(b){
                matLabel.setText("🠔 Ce numéro de matricule existe déja!");
                vermat=false;
                matLabel.setStyle("-fx-text-fill: red");}
            else
            if(!isNumeric(matricule)||matricule.length() != 8){
                vermat=false;
                matLabel.setText("🠔 La matricule est un nombre composé de 8 chiffres !");
            MatriculeField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            matLabel.setStyle("-fx-text-fill: red");}
        } else {
            MatriculeField.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            vermat=true;
            matLabel.setText("✓");
            matLabel.setStyle("-fx-text-fill: #32CD32");}}
        else{
            MatriculeField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            matLabel.setText("");
            vermat=false;
        }
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
                    prenomLabel.setStyle("-fx-text-fill: red");
                    verprenom=false;
                } else {
                    PrenomFiled.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                    prenomLabel.setText("✓");
                    verprenom=true;
                    prenomLabel.setStyle("-fx-text-fill: #32CD32");}}
                else{
            PrenomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            prenomLabel.setText("");
            verprenom=false;
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
                salaireLabel.setStyle("-fx-text-fill: red");
                versal=false;
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
            ServiceField.setStyle("-fx-background-color:white;");}
    }
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
        String matricule = MatriculeField.getText();
        String cin = CinField.getText();
        String nom = NomFiled.getText();
        String Prenom = PrenomFiled.getText();
        String tel = TelFiled.getText();
        String togFemme = FemmeTog.getText();
        String togHomme = HommeTog.getText();
        String salaire =  SalaireFiled.getText();
        String birth = String.valueOf(BirthField.getValue());
        String service = ServiceField.getValue();
        String description = DescriptionFiled.getText();
        if(matricule.isEmpty())
        {
            matLabel.setText("🠔 Remplir ce champ");
            matLabel.setStyle("-fx-text-fill: red");
            MatriculeField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");}
        if(nom.isEmpty()){
            nomLabel.setText("🠔 Remplir ce champ");
            nomLabel.setStyle("-fx-text-fill: red");
            NomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");}
        if(Prenom.isEmpty()){
            prenomLabel.setText("🠔 Remplir ce champ");
            prenomLabel.setStyle("-fx-text-fill: red");
            PrenomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");}
        if(cin.isEmpty()){
            cinLabel.setText("🠔 Remplir ce champ");
            cinLabel.setStyle("-fx-text-fill: red");
            CinField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");}
        if(!vermat||!vernom||!verprenom||!vercin||service==null||birth.length()!=10||(!FemmeTog.isSelected() && !HommeTog.isSelected())||!vertel||!versal||!verdate){
        if(!vermat  &&  matricule.isEmpty())
        {
            matLabel.setText("🠔 Remplir ce champ");
            matLabel.setStyle("-fx-text-fill: red");
            MatriculeField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");}
        if(!vernom  &&  nom.isEmpty()){
            nomLabel.setText("🠔 Remplir ce champ");
            nomLabel.setStyle("-fx-text-fill: red");
            NomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");}
        if(!verprenom   &&  Prenom.isEmpty()){
            prenomLabel.setText("🠔 Remplir ce champ");
            prenomLabel.setStyle("-fx-text-fill: red");
            PrenomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");}
        if(!vercin && cin.isEmpty()){
            cinLabel.setText("🠔 Remplir ce champ");
            cinLabel.setStyle("-fx-text-fill: red");
            CinField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");}
        if(service==null){
            serviceLabel.setText("🠔 Selectionner le service");
            serviceLabel.setStyle("-fx-text-fill: red");
            ServiceField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
        else{
            serviceLabel.setStyle("-fx-text-fill: #32CD32");
            serviceLabel.setText("✓");
            ServiceField.setStyle("-fx-background-color:white;");}
        if(birth.length()!=10){
            dateLabel.setText("🠔 Remplir ce champ");
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
            alert.setGraphic(new ImageView(getClass().getResource("../../../images/errorinsert.png").toURI().toString() ));
            alert.showAndWait();}
        else{
            Connection connection= getOracleConnection();
            PreparedStatement rs = connection.prepareStatement("INSERT INTO PERSONNEL values(?,?,?,?,?,?,?,?,?,?)");
            rs.setString(1,matricule);
            rs.setString(2,cin);
            rs.setString(3,nom);
            rs.setString(4,Prenom);
            rs.setDate(5, Date.valueOf(birth));
            if(isFloat(salaire))
                rs.setFloat(6, Float.parseFloat(salaire));
            else
                rs.setInt(6, 0);
            if(FemmeTog.isSelected())
                rs.setString(7,togFemme);
            if(HommeTog.isSelected())
                rs.setString(7,togHomme);
            if(isNumeric(tel)&& String.valueOf(Integer.parseInt(tel)).length()==8 && tel.length()==8 && vertel)
                rs.setInt(8, Integer.parseInt(tel));
            else
                rs.setInt(8, 0);
            rs.setString(9,service);
            rs.setString(10,description);
            rs.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setHeaderText(null);
            alert.setContentText("Ajout avec succés");
            alert.setGraphic(new ImageView(getClass().getResource("../../../images/approved.png").toURI().toString() ));
            alert.showAndWait();
            refresh();


        }
    }
    private void refresh(){
        ServiceField.setValue(null);
        MatriculeField.setText("");
        CinField.setText("");
        NomFiled.setText("");
        PrenomFiled.setText("");
        TelFiled.setText("");
        SalaireFiled.setText("");
        FemmeTog.setSelected(false);
        HommeTog.setSelected(false);
        BirthField.setValue(null);
        DescriptionFiled.setText("");
        MatriculeField.setStyle(null);
        CinField.setStyle(null);
        NomFiled.setStyle(null);
        PrenomFiled.setStyle(null);
        TelFiled.setStyle(null);
        SalaireFiled.setStyle(null);
        matLabel.setText(null);
        nomLabel.setText(null);
        prenomLabel.setText(null);
        cinLabel.setText(null);
        telLabel.setText(null);
        serviceLabel.setText(null);
        dateLabel.setText(null);
        salaireLabel.setText(null);
        sexLabel.setText(null);
        ServiceField.setStyle("-fx-background-color:white;");
        BirthField.setStyle("-fx-background-color:transparent;");
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


}
