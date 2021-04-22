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


public class CreationEngin implements Initializable {

    @FXML
    private Label matriculelbl;

    @FXML
    private Label lblType;

    @FXML
    private Label lblmarque;

    @FXML
    private Label lbldispo;

    @FXML
    private TextField matrifield;

    @FXML
    private TextField marquefield;

    @FXML
    private TextField Typefield;

    @FXML
    private ChoiceBox<String> dispofield;

    @FXML
    private Button buttonConfirmer;

    @FXML
    private Button buttonFermer;


    private boolean matricule = false;
    private boolean marque = false;


    @FXML
    void verifMatricule(KeyEvent event) {
        String mat = matrifield.getText();
        if (!mat.isEmpty()) {
            matrifield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            matriculelbl.setText("✓");
            matricule = true;
            matriculelbl.setStyle("-fx-text-fill: #32CD32");
        } else {
            matrifield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            matriculelbl.setStyle("-fx-text-fill: red");
            matriculelbl.setText("🠔 Remplir ce champ");
            matricule = false;
        }
    }


    public static boolean isFloat(String string) {
        try {
            Float.parseFloat(string);
            if (string.length() >= 3) {

                try {
                    String[] p = string.split("\\.");
                    if (p[0].length() <= 6 && p[1].length() <= 3)
                        return true;
                    else {
                        return false;
                    }
                } catch (IndexOutOfBoundsException e) {
                    return true;
                }
            } else
                return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }


    @FXML
    void verifMarque(KeyEvent event) {
        String mat = marquefield.getText();
        if (!mat.isEmpty()) {
            marquefield.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            lblmarque.setText("✓");
            marque = true;
            lblmarque.setStyle("-fx-text-fill: #32CD32");
        } else {
            marquefield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            lblmarque.setStyle("-fx-text-fill: red");
            lblmarque.setText("🠔 Remplir ce champ");
            marque = false;
        }
    }

    private boolean verservice;

    @FXML
    void verifService(KeyEvent event) {
        if (Typefield.getText().isEmpty()) {
            lblType.setText("🠔 Saisir le type d'engin");
            verservice = false;
            lblType.setStyle("-fx-text-fill: red");
            Typefield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
        } else {
            lblType.setStyle("-fx-text-fill: #32CD32");
            lblType.setText("✓");
            verservice = true;
            Typefield.setStyle("-fx-background-color:white;");
        }
    }


    @FXML
    void confirmerButton(ActionEvent event) throws URISyntaxException {
        try {
            String mat = matrifield.getText();
            String mar = marquefield.getText();
            String type = Typefield.getText();
            String dispo = dispofield.getValue();
            if (!matricule || !marque || type == null || dispo == null || !verservice) {
                if (mat.isEmpty()) {
                    matriculelbl.setText("🠔 Remplir ce champ");
                    matriculelbl.setStyle("-fx-text-fill: red");
                    matrifield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }


                if (!verservice) {
                    lblType.setText("🠔 Saisir le type de depense");
                    verservice = false;
                    lblType.setStyle("-fx-text-fill: red");
                    Typefield.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
                }


                if (mar.isEmpty()) {
                    lblmarque.setText("🠔 Remplir ce champ");
                    lblmarque.setStyle("-fx-text-fill: red");
                    marquefield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }


                if (!matricule && mat.isEmpty()) {
                    matriculelbl.setText("🠔 Remplir ce champ");
                    matriculelbl.setStyle("-fx-text-fill: red");
                    matrifield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                }
                if (!marque && mar.isEmpty()) {
                    lblmarque.setText("🠔 Remplir ce champ");
                    lblmarque.setStyle("-fx-text-fill: red");
                    marquefield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
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
            } else {
                Connection connection = null;
                try {
                    connection = getOracleConnection();
                    try {
                        String insertion = "INSERT INTO engin values(" + "\'" + matrifield.getText() + "\'" + "," + "\'" + Typefield.getText() + "\'" + "," + "\'" + dispofield.getValue().toString() + "\'" + "," + "\'" + marquefield.getText() + "\'" + ")";
                        PreparedStatement rs = connection.prepareStatement(insertion);
                        //System.out.println(insertion);

                        rs.execute();


                        //lbl.setText("Ajout avec succés");
                        refresh();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.TRANSPARENT);
                        alert.setHeaderText(null);
                        alert.setContentText("Ajout avec succés");
                        alert.setGraphic(new ImageView(getClass().getResource("../../../images/approved.png").toURI().toString()));
                        alert.showAndWait();
                    } catch (NumberFormatException e) {

                    }
                } catch (SQLException | URISyntaxException throwables) {
                    throwables.printStackTrace();
                }

            }
        } catch (NullPointerException e) {
            //System.out.println("you have an error go check please mr Mahdi");
            if (matriculelbl.getText().isEmpty()) {
                matriculelbl.setText("🠔 Remplir ce champ");
                matriculelbl.setStyle("-fx-text-fill: red");
                matrifield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");

            } else {
                matriculelbl.setText("🠔 Cette matricule existe déjà");
                matriculelbl.setStyle("-fx-text-fill: red");
                matrifield.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            }
        }
    }


    private void refresh() {
        Typefield.clear();
        dispofield.setValue(null);
        matrifield.setText("");
        marquefield.setText("");
        matriculelbl.setText("");
        lbldispo.setText("");
        lblmarque.setText("");
        lblType.setText("");
        Typefield.setStyle("-fx-background-color:white;");
        dispofield.setStyle("-fx-background-color:white;");
        matrifield.setStyle("-fx-background-color:white;");
        marquefield.setStyle("-fx-background-color:white;");
        lbldispo.setStyle("-fx-text-fill: #32CD32");
        lbldispo.setText("✓");
        dispofield.setStyle("-fx-background-color:white;");
        dispofield.setValue(Type.Oui.toString());
    }

    @FXML
    public void fermerButton(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) buttonFermer.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList list1 = FXCollections.observableArrayList();
        list1.removeAll();
        list1.addAll(Type.Oui.toString(), Type.Non.toString());
        dispofield.getItems().setAll(list1);
        dispofield.setValue(Type.Oui.toString());
        lbldispo.setStyle("-fx-text-fill: #32CD32");
        lbldispo.setText("✓");
    }


    @FXML
    public void verifDispo(ActionEvent event) {

    }
}