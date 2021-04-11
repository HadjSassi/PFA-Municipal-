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
    private TextArea DescriptionFiled ;

    @FXML
    private Button buttonConfirmer ;

    @FXML
    private Button buttonFermer ;

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
            if (!vernom || !vercin || service == null) {
                if (nom.isEmpty()) {
                    lblNomEror.setText("🠔 Remplir ce champ");
                    lblNomEror.setStyle("-fx-text-fill: red");
                    nameTextField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
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
                            "Type = "+"\'"+TypeEnum.getValue()+"\'"+", Nom ="+"\'"+nameTextField.getText()+"\'"+", Cin = "+"\'"+CinTextField.getText()+"\'"+", Description = "+"\'"+DescriptionFiled.getText()+"\'"+", STATUS = "+"\'"+EtatEnum.getValue()+"\'"+"where ID = "+Integer.parseInt(lblId.getText())+"";


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
        list.removeAll();
        list.addAll(type_Doleance.doleance.toString(),type_Doleance.chakwa.toString(),type_Doleance.matlab.toString());
        TypeEnum.getItems().setAll(list);
        list2.removeAll();
        list2.addAll(Etat.Initiale.toString(),Etat.Approuvé.toString(),Etat.Refusé.toString(),Etat.EnCours.toString(),Etat.Terminé.toString());
        EtatEnum.getItems().setAll(list2);

    }
    public void setTextField(String Id, String Type , String Nom ,String Cin,String Description){
        lblId.setText(Id);
        nameTextField.setText(Nom);
        CinTextField.setText(Cin);
        DescriptionFiled.setText(Description);
        TypeEnum.setValue(Type);
    }
    public void setTextField(String id, String type, String nom, String cin, String description,String Status) {
        this.CinTextField.setText(cin);
        this.lblId.setText(id);
        this.DescriptionFiled.setText(description);
        this.EtatEnum.setValue(Status);
        this.nameTextField.setText(nom);
        this.TypeEnum.setValue(type);
    }


}