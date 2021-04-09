package sample.App.controllers.GestionDoleance.CreationDoleance;

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

import sample.App.model.type_Doleance;


import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class ControllerCreationDoleance implements Initializable {

    @FXML
    private Label lblId;

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
                alert.setGraphic(new ImageView(getClass().getResource("../../images/errorinsert.png").toURI().toString()));
                alert.showAndWait();
            } else {
                Connection connection = null;
                try {
                    connection = getOracleConnection();

                    String insertion = "INSERT INTO doleance values(Integer.parseInt(lblId.getText()),TypeEnum.getValue(),nameTextField.getText(),CinTextField.getText(),DescriptionFiled.getText())";

                    PreparedStatement rs = connection.prepareStatement(insertion);
                    //System.out.println(insertion);
                    rs.execute();
                    //lbl.setText("Ajout avec succés");
                    refresh();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.TRANSPARENT);
                    alert.setHeaderText(null);
                    alert.setContentText("Ajout avec succés");
                    alert.setGraphic(new ImageView(getClass().getResource("../../../../images/approved.png").toURI().toString()));
                    alert.showAndWait();
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
        TypeEnum.setValue(null);
        CinTextField.setText("");
        nameTextField.setText("");
        DescriptionFiled.setText("");
        lblStatus.setText("");
        lblTypeError.setText("");
        lblCinError.setText("");
        lblNomEror.setText("");
        TypeEnum.setStyle("-fx-background-color:white;");
        CinTextField.setStyle("-fx-background-color:white;");
        nameTextField.setStyle("-fx-background-color:white;");
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select myseq.nextval from dual");
            while(rs.next()){
                // oblist.add(new Compte(rs.getString("cin"),rs.getString("pass"),""));
                lblId.setText(rs.getString("nextval"));
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

    ObservableList list= FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select myseq.nextval from dual");
            while(rs.next()){
                // oblist.add(new Compte(rs.getString("cin"),rs.getString("pass"),""));
                lblId.setText(rs.getString("nextval"));
               }
            rs.close();
        } catch (SQLException throwables) {
            System.out.println("1000000 dawa7");
        }
        lblStatus.setText("Initial");
        list.removeAll();
        list.addAll(type_Doleance.doleance.toString(),type_Doleance.chakwa.toString(),type_Doleance.matlab.toString());
        TypeEnum.getItems().setAll(list);

    }



}