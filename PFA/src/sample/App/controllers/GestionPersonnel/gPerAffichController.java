package sample.App.controllers.GestionPersonnel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class gPerAffichController implements Initializable {
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Label MatriculeLabel;

    @FXML
    private Label NomLabel;

    @FXML
    private Label PrenomLabel;

    @FXML
    private Label CinLabel;

    @FXML
    private Label TelLabel;

    @FXML
    private Label SexLabel;

    @FXML
    private Label NaissanceLabel;

    @FXML
    private Label ServiceLabel;

    @FXML
    private Label SalaireLabel;

    @FXML
    private TextArea DescriptionFiled;

    @FXML
    void handleClicksAnnuler(ActionEvent event) {
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleClicksPrint(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DescriptionFiled.setText("");
    }
    public void setTextFiel(String matriculey, String nomy, String prenomy, String ciny, int tely, String togsexy, String birthy, String servicey, Float salairey, String descriptiony){
        MatriculeLabel.setText(matriculey);
        ServiceLabel.setText(servicey);
        CinLabel.setText(ciny);
        NomLabel.setText(nomy);
        PrenomLabel.setText(prenomy);
        if(tely!=0)
            TelLabel.setText(String.valueOf(tely));
        else
            TelLabel.setText("-");
        if(salairey!=0)
            SalaireLabel.setText(String.valueOf(salairey)+" DT");
        else
            SalaireLabel.setText("- DT");
        SexLabel.setText(togsexy);
        NaissanceLabel.setText(birthy.toString());
        DescriptionFiled.setText(descriptiony);
        DescriptionFiled.setEditable(false);
        }
}
