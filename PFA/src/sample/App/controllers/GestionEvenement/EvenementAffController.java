package sample.App.controllers.GestionEvenement;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.App.model.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class EvenementAffController implements Initializable {

    @FXML
    private TableColumn<Personnel, String> col_select;
    @FXML
    private ComboBox<String> EtatCombo;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Label EvenementLabel;

    @FXML
    private Label NomLabel;

    @FXML
    private Label DomaineLabel;

    @FXML
    private Label VoletLabel;

    @FXML
    private Label DateDLabel;

    @FXML
    private Label DateFLabel;

    @FXML
    private Label GouverneratLabel;

    @FXML
    private Label DelegationLabel;

    @FXML
    private Label LocalisationLabel;

    @FXML
    private TableView<Personnel> table_info_Per;

    @FXML
    private TableColumn<?, ?> col_id;

    @FXML
    private TableColumn<?, ?> col_nom;

    @FXML
    private TableColumn<?, ?> col_prenom;

    @FXML
    private TableView<Engin> table_info_Eng;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private TableColumn<?, ?> markCol;

    @FXML
    private TableView<Materiel> table_info_Mat;

    @FXML
    private TableColumn<?, ?> typeCol1;

    @FXML
    private TableColumn<?, ?> consCol1;

    @FXML
    private TableColumn<?, ?> qteCol1;

    @FXML
    private TextArea DescriptionFiled;

    @FXML
    void handleClicksAnnuler(ActionEvent event) throws SQLException {
        if (EtatCombo.getValue()!=null&&EtatCombo.getValue().equals("Annul??")) {
            Connection connection = getOracleConnection();
            PreparedStatement rs1;
            rs1 = connection.prepareStatement("UPDATE EVENEMENT set etat=? where IDMAT=?");
            rs1.setString(1, EtatCombo.getValue());
            rs1.setString(2, EvenementLabel.getText());
            rs1.execute();
            rs1 = connection.prepareStatement("select EVENMAT.ID,QTEUSED from EVENMAT,MATERIEL where ID=DESIGNATION and IDMAT=? and CONSOMABLE='Oui'");
            rs1.setString(1,EvenementLabel.getText());
            ResultSet rs=rs1.executeQuery();
            while(rs.next()){
                rs1 = connection.prepareStatement("UPDATE MATERIEL set QTE=QTE + ? where DESIGNATION='" + rs.getString(1) + "'");
                rs1.setString(1,rs.getString(2));
                rs1.execute();
            }
            rs1 = connection.prepareStatement("DELETE from EVENMAT WHERE IDMAT=?");
            rs1.setString(1, EvenementLabel.getText());
            rs1.execute();
            rs1 = connection.prepareStatement("DELETE from EVENPER WHERE IDMAT=?");
            rs1.setString(1, EvenementLabel.getText());
            rs1.execute();
            rs1 = connection.prepareStatement("DELETE from EVENENG WHERE IDMAT=?");
            rs1.setString(1, EvenementLabel.getText());
            rs1.execute();
        }
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DescriptionFiled.setText("");
    }

    public void setTextFiel(Evenement Evenement) {
        EvenementLabel.setText(Evenement.getIdI());
        NomLabel.setText(Evenement.getNom());
        DateDLabel.setText(Evenement.getDateDeb().toString());
        DateFLabel.setText(Evenement.getDateDeb().toString());
        EtatCombo.setPromptText(Evenement.getEtat());
        if (Evenement.getEtat().equals("Annul??"))
            EtatCombo.setDisable(true);
        else
            EtatCombo.getItems().addAll(Evenement.getEtat(), "Annul??");
        //liste materiel
        ObservableList<Materiel> oblist1;
        consCol1.setCellValueFactory(new PropertyValueFactory<>("consom"));
        typeCol1.setCellValueFactory(new PropertyValueFactory<>("designation"));
        qteCol1.setCellValueFactory(new PropertyValueFactory<>("qte"));
        oblist1 = FXCollections.observableArrayList();
        for (int i = 0; i < Evenement.getMateriel().size(); i++) {
            oblist1.add(Evenement.getMateriel().get(i));
            System.out.println(Evenement.getMateriel().get(i).getDesignation());
        }
        table_info_Mat.setItems(oblist1);
        //liste equipe
        ObservableList<Personnel> oblist2;
        col_id.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        oblist2 = FXCollections.observableArrayList();
        Personnel p=null;
        try {
            Connection connection = getOracleConnection();
            PreparedStatement rs = connection.prepareStatement("select cheff from EVENEMENT where IDMAT=?");
            rs.setString(1, EvenementLabel.getText());
            ResultSet rc = rs.executeQuery();
            while (rc.next()) {
                for (int i = 0; i < Evenement.getEquipe().size(); i++) {
                    if (Evenement.getEquipe().get(i).getMatricule().equals(rc.getString("CHEFF"))) {
                        oblist2.add(Evenement.getEquipe().get(i));
                        p=Evenement.getEquipe().get(i);
                    } else {
                        oblist2.add(Evenement.getEquipe().get(i));
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table_info_Per.setItems(oblist2);
        table_info_Per.getSelectionModel().select(p);
        Personnel finalP = p;
        table_info_Per.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable, oldvalue, newValue) -> {

                    Platform.runLater(() -> {
                        table_info_Per.getSelectionModel().select(finalP);
                    });

                });
        //liste vehicule
        ObservableList<Engin> oblist3;
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        markCol.setCellValueFactory(new PropertyValueFactory<>("Marque"));
        oblist3 = FXCollections.observableArrayList();
        for (int i = 0; i < Evenement.getVehicule().size(); i++) {
            oblist3.add(Evenement.getVehicule().get(i));
        }
        table_info_Eng.setItems(oblist3);
        GouverneratLabel.setText(Evenement.getGouvernerat());
        DelegationLabel.setText(Evenement.getDelegation());
        LocalisationLabel.setText(Evenement.getLocalisation());
        DomaineLabel.setText(Evenement.getDomaine());
        VoletLabel.setText(Evenement.getVolet());
        DescriptionFiled.setText(Evenement.getDescription());
        DescriptionFiled.setEditable(false);
        table_info_Eng.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable, oldvalue, newValue) -> {

                    Platform.runLater(() -> {
                        table_info_Eng.getSelectionModel().clearSelection();
                    });

                });
        table_info_Mat.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable, oldvalue, newValue) -> {

                    Platform.runLater(() -> {
                        table_info_Mat.getSelectionModel().clearSelection();
                    });

                });
    }
}
