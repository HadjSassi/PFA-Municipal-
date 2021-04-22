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
    private Label lblType;

    @FXML
    private Label lblmarque;

    @FXML
    private Label lbldispo;

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

    private String id;
    private boolean marque = true , verservice = true;


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
    void confirmerButton(ActionEvent event) throws URISyntaxException {
        try {
            String mar = marquefield.getText();
            String type = Typefield.getText();
            String dispo = dispofield.getValue();
            if (!marque || type == null || dispo == null || !verservice) {

                System.out.println("test");

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
            } else {
                Connection connection = null;
                try {
                    connection = getOracleConnection();
                    try {
                        String insertion = "Update engin set " +
                                "Type = " + "\'" + Typefield.getText() + "\'" + ", marque = " + "\'" + marquefield.getText() + "\'" + ", dispo = " + "\'" + dispofield.getValue() + "\'" + "where ID = " + "\'" + id + "\'" + "";
                        PreparedStatement rs = connection.prepareStatement(insertion);
                        //System.out.println(insertion);
                        rs.execute();


                        //lbl.setText("Ajout avec succés");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.TRANSPARENT);
                        alert.setHeaderText(null);
                        alert.setContentText("Modification avec succés");
                        alert.setGraphic(new ImageView(getClass().getResource("../../../images/approved2.png").toURI().toString()));
                        alert.showAndWait();
                        Stage stage = (Stage) buttonConfirmer.getScene().getWindow();
                        // do what you have to do
                        stage.close();
                    } catch (NumberFormatException e) {

                    }
                } catch (SQLException | URISyntaxException throwables) {
                    throwables.printStackTrace();
                }

            }
        } catch (NullPointerException e) {
            //System.out.println("you have an error go check please mr Mahdi");

        }
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

    public void setTextField(String id, String type, String dispo, String mar) {
        this.id = id;
        this.matriculelbl.setText(id);
        this.marquefield.setText(mar);
        this.Typefield.setText(type);
        this.dispofield.setValue(dispo);
    }

    @FXML
    public void verifDispo(ActionEvent event) {

    }
}