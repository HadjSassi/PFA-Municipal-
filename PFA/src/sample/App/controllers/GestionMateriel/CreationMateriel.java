package sample.App.controllers.GestionMateriel;

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


public class CreationMateriel implements Initializable {

    @FXML
    private Label lblid;

    @FXML
    private Label lblType ;

    @FXML
    private Label lblqte;

    @FXML
    private TextField idField ;

    @FXML
    private TextField qtefield ;

    @FXML
    private TextArea DescriptionFiled;

    @FXML
    private ChoiceBox<String> Typefield;

    @FXML
    private Button buttonConfirmer ;

    @FXML
    private Button buttonFermer ;


    @FXML
    void verifService(ActionEvent  event) {
        if(Typefield.getValue()==null){
            lblType.setText("🠔 Selectionner la designation du materiel");
            lblType.setStyle("-fx-text-fill: red");
            Typefield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");}
        else{
            lblType.setStyle("-fx-text-fill: #32CD32");
            lblType.setText("✓");
            Typefield.setStyle("-fx-background-color:white;");}
    }


    @FXML
    void confirmerButton(ActionEvent event) throws URISyntaxException {
        try {
            String id = idField.getText();
            String qte = qtefield.getText();
            String type = Typefield.getValue();
            String desc = DescriptionFiled.getText();
            if (type == null || id == null ||qte == null) {
                if (id.isEmpty()) {
                    lblid.setText("🠔 Remplir ce champ");
                    lblid.setStyle("-fx-text-fill: red");
                    idField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }

                if (qte.isEmpty()) {
                    lblqte.setText("🠔 Remplir ce champ");
                    lblqte.setStyle("-fx-text-fill: red");
                    qtefield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }


                if ( id.isEmpty()) {
                    lblid.setText("🠔 Remplir ce champ");
                    lblid.setStyle("-fx-text-fill: red");
                    idField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }
                if ( qte.isEmpty()) {
                    lblqte.setText("🠔 Remplir ce champ");
                    lblqte.setStyle("-fx-text-fill: red");
                    qtefield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
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

                    String insertion = "INSERT INTO MATERIEL values("+"\'"+idField.getText()+"\'"+","+"\'"+Typefield.getValue().toString()+"\'"+","+qtefield.getText()+","+"\'"+DescriptionFiled.getText()+"\'"+")";

                    PreparedStatement rs = connection.prepareStatement(insertion);
                    System.out.println(insertion);
                    rs.execute();
                    //lbl.setText("Ajout avec succés");
                    refresh();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.TRANSPARENT);
                    alert.setHeaderText(null);
                    alert.setContentText("Ajout avec succés");
                    alert.setGraphic(new ImageView(getClass().getResource("../../../images/approved.png").toURI().toString()));
                    alert.showAndWait();
                } catch (SQLException | URISyntaxException throwables) {
                    throwables.printStackTrace();
                }

            }
        }
        catch (NullPointerException e){
            //System.out.println("you have an error go check please mr Mahdi");
            lblid.setText("🠔 Cette matricule existe déjà");
            lblid.setStyle("-fx-text-fill: red");
            idField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");

        }
    }


    private void refresh(){
        Typefield.setValue(null);
        idField.setText("");
        qtefield.setText("");
        DescriptionFiled.setText("");
        lblid.setText("");
        lblType.setText("");
        lblqte.setText("");
        lblType.setText("");
        Typefield.setStyle("-fx-background-color:white;");
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
        list.removeAll();
        list.addAll(Type.Jardinage.toString(),Type.Poubelle.toString(),Type.barriere_de_securité.toString(),Type.barwita.toString(),Type.drapeaux.toString());
        Typefield.getItems().setAll(list);

    }



}