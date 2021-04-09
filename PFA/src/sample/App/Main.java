package sample.App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Scanner;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    public static Stage stage= null;
    private double x, y;
    Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Boarder.fxml"));
        window=primaryStage;
        this.stage=primaryStage;
        window.setScene(new Scene(root));
        //set stage borderless
       window.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add((new Image( getClass().getResource("../images/municipalite-tunis.png").toURI().toString())));
        primaryStage.show();
    }

}
