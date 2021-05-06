package sample.App.controllers.GestionIntervention;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.App.model.Engin;
import sample.App.model.Intervention;
import sample.App.model.Materiel;
import sample.App.model.Personnel;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class InterventionAffController implements Initializable {

    @FXML
    private TableColumn<Personnel, String> col_select;
    @FXML
    private ComboBox<String> EtatCombo;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Label InterventionLabel;

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
    private TableColumn<Personnel, String> col_cheff;

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
        if(EtatCombo.getValue()!=null){
            Connection connection=getOracleConnection();
            PreparedStatement rs1;
            rs1 = connection.prepareStatement("UPDATE intervention set etat=? where IDMAT=?");
            rs1.setString(1, EtatCombo.getValue());
            rs1.setString(2, InterventionLabel.getText());
            rs1.execute();
            rs1 = connection.prepareStatement("DELETE from interMAT WHERE IDMAT=?");
            rs1.setString(1,InterventionLabel.getText());
            rs1.execute();
            rs1 = connection.prepareStatement("DELETE from interPER WHERE IDMAT=?");
            rs1.setString(1,InterventionLabel.getText());
            rs1.execute();
            rs1 = connection.prepareStatement("DELETE from interENG WHERE IDMAT=?");
            rs1.setString(1,InterventionLabel.getText());
            rs1.execute();
        }
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DescriptionFiled.setText("");
    }
    public void setTextFiel(Intervention intervention){
        InterventionLabel.setText(intervention.getIdI());
        NomLabel.setText(intervention.getNom());
        DateDLabel.setText(intervention.getDateDeb().toString());
        DateFLabel.setText(intervention.getDateDeb().toString());
        EtatCombo.setPromptText(intervention.getEtat());
        if(intervention.getEtat().equals("Annulé"))
            EtatCombo.setDisable(true);
        else
            EtatCombo.getItems().addAll(intervention.getEtat(),"Annulé");
        //liste materiel
        ObservableList<Materiel> oblist1;
        consCol1.setCellValueFactory(new PropertyValueFactory<>("consom"));
        typeCol1.setCellValueFactory(new PropertyValueFactory<>("designation"));
        qteCol1.setCellValueFactory(new PropertyValueFactory<>("qte"));
        oblist1 = FXCollections.observableArrayList();
        for(int i=0;i<intervention.getMateriel().size();i++){
            oblist1.add(intervention.getMateriel().get(i));
            System.out.println(intervention.getMateriel().get(i).getDesignation());
        }
        table_info_Mat.setItems(oblist1);
        //liste equipe
        ObservableList<Personnel> oblist2;
        col_id.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        Callback<TableColumn<Personnel, String>, TableCell<Personnel, String>> cellFoctory = (TableColumn<Personnel, String> param) -> {
            final TableCell<Personnel, String> cell = new TableCell<Personnel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        for(Personnel p:table_info_Per.getItems()) {
                            p.setCheff(new RadioButton());
                            if(p.getMatricule().equals(intervention.getCheff()))
                                p.getCheff().setSelected(true);
                            p.getCheff().setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };
        col_cheff.setCellValueFactory(new PropertyValueFactory<>("cheff"));
        col_select.setCellFactory(cellFoctory);
        oblist2 = FXCollections.observableArrayList();
        for(int i=0;i<intervention.getEquipe().size();i++){
            oblist2.add(intervention.getEquipe().get(i));}
        table_info_Per.setItems(oblist2);
        //liste vehicule
        ObservableList<Engin> oblist3;
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        markCol.setCellValueFactory(new PropertyValueFactory<>("Marque"));
        oblist3 = FXCollections.observableArrayList();
        for(int i=0;i<intervention.getVehicule().size();i++){
            oblist3.add(intervention.getVehicule().get(i));}
        table_info_Eng.setItems(oblist3);
        GouverneratLabel.setText(intervention.getGouvernerat());
        DelegationLabel.setText(intervention.getDelegation());
        LocalisationLabel.setText(intervention.getLocalisation());
        DomaineLabel.setText(intervention.getDomaine());
        VoletLabel.setText(intervention.getVolet());
        DescriptionFiled.setText(intervention.getDescription());
        DescriptionFiled.setEditable(false);
    }
}
