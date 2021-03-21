package sample.App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    public static Stage stage= null;
    private double x, y;
    Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Boarder.fxml"));
        window=primaryStage;
        this.stage=primaryStage;
        window.setScene(new Scene(root));
        //set stage borderless
        window.initStyle(StageStyle.UNDECORATED);

        //drag it here
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {

            window.setX(event.getScreenX() - x);
            window.setY(event.getScreenY() - y);

        });
        primaryStage.show();
    }

}
