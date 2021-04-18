package sample.App.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
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
    void handleClicksTache(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("TacheConsultation");
        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getPane("CompteConsultation");
        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        anchorpane3.getChildren().removeAll();
        anchorpane3.getChildren().setAll(view);

    }
}
