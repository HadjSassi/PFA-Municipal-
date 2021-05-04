package sample.App.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.App.FxmlLoader;
import sample.App.Main;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class borderController implements Initializable {
    private double xOffSet = 0;
    private double yOffSet = 0;


    Stage stage;
    @FXML
    private AnchorPane all;
    @FXML
    private AnchorPane  border;

    @FXML
    private AnchorPane mainscreen;
    @FXML
    void handleClicksMaximize(ActionEvent event) {
        stage = (Stage) all.getScene().getWindow();
        if(stage.isMaximized())
            stage.setMaximized(false);
        else
            stage.setMaximized(true);
    }
    @FXML
    void handleClicksClose(ActionEvent event) {
        stage = (Stage) all.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleClicksMinimize(ActionEvent event) {
        stage = (Stage) all.getScene().getWindow();
        stage.setIconified(true);
    }


//    private void makeStageDragable() {
//        mainscreen.setOnMousePressed((event) -> {
//            xOffSet = event.getSceneX();
//            yOffSet = event.getSceneY();
//        });
//        mainscreen.setOnMouseDragged((event) -> {
//            Main.stage.setX(event.getScreenX() - xOffSet);
//            Main.stage.setY(event.getScreenY() - yOffSet);
//            Main.stage.setOpacity(0.9f);
//        });
//        mainscreen.setOnDragDone((event) -> {
//            Main.stage.setOpacity(1.0f);
//        });
//        mainscreen.setOnMouseReleased((event) -> {
//            Main.stage.setOpacity(1.0f);
//        });
//        border.setOnMousePressed((event) -> {
//            xOffSet = event.getSceneX();
//            yOffSet = event.getSceneY();
//        });
//        border.setOnMouseDragged((event) -> {
//            Main.stage.setX(event.getScreenX() - xOffSet);
//            Main.stage.setY(event.getScreenY() - yOffSet);
//            Main.stage.setOpacity(0.9f);
//        });
//        border.setOnDragDone((event) -> {
//            Main.stage.setOpacity(1.0f);
//        });
//        border.setOnMouseReleased((event) -> {
//            Main.stage.setOpacity(1.0f);
//        });}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("Interface");
        mainscreen.getChildren().removeAll();
        mainscreen.getChildren().setAll(view);
       // makeStageDragable();
    }

}
