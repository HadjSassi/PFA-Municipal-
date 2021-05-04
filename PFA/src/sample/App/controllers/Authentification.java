package sample.App.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;
import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class Authentification {

    @FXML
    private TextField matriculeTextField ;

    @FXML
    private PasswordField passwordField ;

    @FXML
    private Label Msg ;

    @FXML
    private Button fermerBtn ;

    public static Image img ;
    public static String name ;
    public static String role ;
    private double x, y;
    @FXML
    private Button loginBtn ;

    @FXML
    public void fermer (ActionEvent event){
         Stage stage = (Stage) fermerBtn.getScene().getWindow();
         stage.close();
     }

    @FXML
    public void confirmer (ActionEvent event) throws InterruptedException {
        String login = matriculeTextField.getText();
        String pass = passwordField.getText();
        String nom = "Foulen" ;
        String prenom = "Foulen Ben";
        boolean ok = false;
        try {
            Connection connection = getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from COMPTE ");
            while (rs.next() && !ok) {

                if (rs.getString("MATRICULE").equals(login) && rs.getString("PASS").equals(pass)) {
                    ok = true;
                    role = rs.getString("role");
                    FileInputStream inputstream = new FileInputStream(rs.getString("image"));
                    img = new Image(inputstream);
                }
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!ok) {
            Msg.setText("Compte non trouvé");
            Msg.setStyle("-fx-text-fill: red");
            matriculeTextField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            passwordField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
        } else {
            Msg.setText("Bonjour");
            Msg.setStyle("-fx-text-fill: #32CD32");
            matriculeTextField.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            passwordField.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                try {
                    Connection connection = getOracleConnection();
                    String req = "select * from PERSONNEL where matricule = " + "\'" + login + "\'";
                    ResultSet rs = connection.createStatement().executeQuery(req);
                    while (rs.next()) {
                        nom = rs.getString("NOM");
                        prenom = rs.getString("PRENOM");
                    }
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                name = prenom + " " + nom;

                Stage primaryStage = new Stage();
                primaryStage.initModality(Modality.APPLICATION_MODAL);
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../view/Boarder.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.setTitle("Municipal");
                assert root != null;
                primaryStage.setScene(new Scene(root));
                primaryStage.initStyle(StageStyle.UNDECORATED);
                root.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    }
                });
                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        primaryStage.setX(event.getScreenX() - x);
                        primaryStage.setY(event.getScreenY() - y);
                    }
                });
                Stage stage = (Stage) loginBtn.getScene().getWindow();
                stage.close();
                try {
                    primaryStage.getIcons().add((new Image(getClass().getResource("../../images/municipalite-tunis.png").toURI().toString())));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                primaryStage.show();

        }
    }
}
