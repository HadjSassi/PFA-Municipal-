package sample.App.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import sample.App.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class accueilController implements Initializable {
    @FXML
    private Pagination p;

    private List<String> list = new ArrayList<String>();
    //ImageView imageView;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            list.add("sample/images/municipalité-tunisie-640x411.jpg");
            list.add("sample/images/Municipalite_de_Tunis-Kassus.jpg");
            list.add("sample/images/mairie-tunis_0805.jpg");
        list.add("sample/images/municipalite_nabeul_1556619001.jpg");
        list.add("sample/images/municipalite-tunis-afrique.jpg");



            //imageview.setImage(new Image("sample/images/municipalité-tunisie-640x411.jpg"));
            Image images[] = new Image[list.size()];
            for (int i = 0; i <list.size(); i++) {
                images[i] = new Image(list.get(i));
            }
        p.setPageFactory((Integer pageIndex) -> {
            ImageView im = new ImageView(images[pageIndex]);
            im.setFitHeight(515);
            im.setFitWidth(793);
            return im;
        });

        //imageview.setCursor(Cursor.CLOSED_HAND);
            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
                if(p.getCurrentPageIndex()<list.size()-1){
                    int pos = (p.getCurrentPageIndex()+1) % p.getPageCount();
                    p.setCurrentPageIndex(pos); }
                else
                    p.setCurrentPageIndex(0);
            }));
            fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            fiveSecondsWonder.play();
    }
}
