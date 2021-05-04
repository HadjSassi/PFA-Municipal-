package sample.App.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.App.FxmlLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class gInterfaceController implements Initializable {
    @FXML
    private AnchorPane anchorpane1;


    @FXML
    private AnchorPane anchorpane3;

    @FXML
    private AnchorPane anchorpane2;

    @FXML
    public Label name;

    @FXML
    public ImageView img ;

    @FXML
    void handleClicksDepenses(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("depense/DepenseConsultation");
       // view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);
    }

    @FXML
    void handleClicksEquipe(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("engin/EnginConsultation");
        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);
    }
    @FXML
    void handleClicksPermission(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("permission/PermissionConsultation");
        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);
    }

    @FXML
    void handleClicksMateriels(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("materiel/MaterielConsultatoin");
        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);

    }
    @FXML
    void handleClicksPersonnels(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("gPersonnel");
        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);
    }
    @FXML
    void handleClicksRetour(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("Interface");
        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane1.getChildren().removeAll();
        anchorpane1.getChildren().setAll(view);
    }

    @FXML
    void handleClicksRevenus(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("revenu/RevenuConsultation");
        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);

    }
    @FXML
    void handleClicksComptes(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("CompteConsultation");
        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);

    }

    @FXML
    void handleClicksDoleance(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("doleance/DoleanceConsultation");
        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);
    }

    @FXML
    void handleClicksInteventions(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("interI");
        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FxmlLoader object = new FxmlLoader();
        if (Authentification.role.equals("Administration")) {
            AnchorPane view = object.getPane("materiel/MaterielConsultatoin");
            view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            anchorpane3.getChildren().removeAll();
            anchorpane3.getChildren().setAll(view);}

        else if (Authentification.role.equals("Financier")){
            AnchorPane view = object.getPane("revenu/RevenuConsultation");
            view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            anchorpane3.getChildren().removeAll();
            anchorpane3.getChildren().setAll(view);}

        else {
            AnchorPane view = object.getPane("gPersonnel");
            view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            anchorpane3.getChildren().removeAll();
            anchorpane3.getChildren().setAll(view);
        }

        name.setText(Authentification.name);
        img.setImage(Authentification.img);
        if (Authentification.role.equals("Administration")){
            g7.setManaged(false);
            i7.setManaged(false);
            g2.setManaged(false);
            i2.setManaged(false);
            g8.setManaged(false);
            i8.setManaged(false);
        }
        else if (Authentification.role.equals("Financier")){
            g1.setManaged(false);
            i1.setManaged(false);
            g2.setManaged(false);
            g3.setManaged(false);
            g4.setManaged(false);
            g5.setManaged(false);
            g6.setManaged(false);
            i2.setManaged(false);
            i3.setManaged(false);
            i4.setManaged(false);
            i5.setManaged(false);
            i6.setManaged(false);
        }
    }

    @FXML
    private VBox v ;

    @FXML
    private Button g1 ;
    @FXML
    private Button g2 ;
    @FXML
    private Button g3 ;
    @FXML
    private Button g4 ;
    @FXML
    private Button g5 ;
    @FXML
    private Button g6 ;
    @FXML
    private Button g7 ;
    @FXML
    private Button g8 ;
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
    @FXML
    private ImageView i6;
    @FXML
    private ImageView i7;
    @FXML
    private ImageView i8;


}
