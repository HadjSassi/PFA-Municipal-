package sample.GestionCompte.ConsulterComptes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConsulterComptesMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ConsulterComptes.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/mycss.css");
        primaryStage.setTitle("Municipal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
