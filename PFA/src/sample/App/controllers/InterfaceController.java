package sample.App.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.App.FxmlLoader;
import sample.App.Main;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class InterfaceController implements Initializable {

    @FXML
    private AnchorPane anchorpane1;

    @FXML
    private BorderPane mainPane;

    @FXML
    private AnchorPane anchorpane3;

    @FXML
    private AnchorPane anchorpane2;
    Stage stage;
    @FXML
    void handleClicksAccueil(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Parent view = object.getPane("accueil");
        //view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);

        }
    @FXML
    void handleClicksDeconnecter(ActionEvent event) {
        stage = (Stage) anchorpane1.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleClicksInfo(ActionEvent event) {

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
    }
}
