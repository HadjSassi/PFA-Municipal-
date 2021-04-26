package sample.App.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.App.FxmlLoader;
import sample.App.Main;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class InterfaceController implements Initializable {

    @FXML
    private AnchorPane anchorpane1;

    @FXML
    private BorderPane mainPane;

    @FXML
    private AnchorPane anchorpane3;

    @FXML
    private AnchorPane anchorpane2;

    @FXML
    public Label name;

    @FXML
    public ImageView img ;

    Stage stage;
    @FXML
    void handleClicksAccueil(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Parent view = object.getPane("accueil");
        //view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);

    }

    private double x, y;
    Stage window;
    @FXML
    void handleClicksDeconnecter(ActionEvent event) {
        stage = (Stage) anchorpane1.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/Authentification.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        window=primaryStage;
        this.stage=primaryStage;
        window.setScene(new Scene(root));
        //set stage borderless
        window.initStyle(StageStyle.UNDECORATED);
        try {
            primaryStage.getIcons().add((new Image( getClass().getResource("../../images/municipalite-tunis.png").toURI().toString())));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        primaryStage.show();
    }

    @FXML
    void handleClicksInfo(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("Statistics");
        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);
    }

    @FXML
    void handleClicksGestions(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Parent view = object.getPane("gInterface");
        anchorpane1.getChildren().removeAll();
        anchorpane1.getChildren().setAll(view);
    }

    @FXML
    void handleClicksParametre(ActionEvent event) {
             /*FxmlLoader object = new FxmlLoader();
        Parent view = object.getPane("settings");
        anchorpane1.getChildren().removeAll();
        anchorpane1.getChildren().setAll(view);*/

        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("settings");
        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FxmlLoader object = new FxmlLoader();
        Parent view = object.getPane("accueil");
        //view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);
        name.setText(Authentification.name);
        img.setImage(Authentification.img);
        if (Authentification.role.equals("Administration")){
            g4.setManaged(false);
            i4.setManaged(false);
        }
        else if (Authentification.role.equals("Financier")){
            g4.setManaged(false);
            i4.setManaged(false);
        }
    }


    @FXML
    private javafx.scene.control.Button g1 ;
    @FXML
    private javafx.scene.control.Button g2 ;
    @FXML
    private javafx.scene.control.Button g3 ;
    @FXML
    private javafx.scene.control.Button g4 ;
    @FXML
    private javafx.scene.control.Button g5 ;
    @FXML
    private ImageView i1;
    @FXML
    private ImageView i2;
    @FXML
    private ImageView i3;
    @FXML
    private ImageView i4;
    @FXML
    private ImageView i5;

}
