package sample.App.controllers.GestionEvenement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import sample.App.model.Engin;
import sample.App.model.Materiel;
import sample.App.model.Personnel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class EvenementAddController implements Initializable {
    @FXML
    private TableColumn<Personnel, String> col_id;
    @FXML
    private TableColumn<Personnel, String> col_nom;
    @FXML
    private TableColumn<Personnel, String> col_prenom;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private AnchorPane anchorpane1;
    @FXML
    private AnchorPane anchorpane2;
    @FXML
    private AnchorPane anchorpane3;
    @FXML
    private AnchorPane anchorpane4;
    @FXML
    private AnchorPane anchorpane5;

    @FXML
    private Button btnPage2;

    @FXML
    private ComboBox<String> GouverneratField;

    @FXML
    private ComboBox<String> DelegationField;

    @FXML
    private TextArea DescriptionField;


    @FXML
    private Button btnPage3;

    @FXML
    private Button btnPage4;

    @FXML
    private Button btnPage1;

    @FXML
    private Label IdEvenementField;

    @FXML
    private TextField NomFiled;

    @FXML
    private ChoiceBox<String> DomainField;

    @FXML
    private DatePicker dateDebutField;

    @FXML
    private ChoiceBox<String> VoletField;

    @FXML
    private DatePicker dateFinField;

    @FXML
    private Label NomLabel;

    @FXML
    private Label DomainLabel;

    @FXML
    private Label VoletLabel;

    @FXML
    private Label dateDebutLabel;

    @FXML
    private Label dateFinLabel;

    @FXML
    private Label DomainLabel1;

    @FXML
    private Label VoletLabel1;

    @FXML
    private Label dateDebutLabel1;

    @FXML
    private Label dateFinLabel1;

    @FXML
    private Label GouverneratLabel;

    @FXML
    private Label DelegationLabel;
    @FXML
    private Label LocationLabel;
    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> typeCol;
    @FXML
    private TableColumn<Engin, String> markCol;

    @FXML
    private TableColumn<?, ?> consCol1;

    @FXML
    private TableColumn<?, ?> typeCol1;

    @FXML
    private TableColumn<Materiel, String> qteCol1;
    @FXML
    private TableView<Materiel> table_info_Mat;
    @FXML
    private TableView<Personnel> table_info_Per;
    @FXML
    private TableView<Engin> table_info_Eng;
    @FXML
    private TextField LocalisationField;
    @FXML
    private VBox vbox_personnel;
    @FXML
    private VBox vbox_vehicule;
    @FXML
    private VBox vbox_outils;
    private boolean vernom = false, verdateDebut = false, verdateFin = false, verDomaine = false, verVolet = false, verGouvernerat = false, verDelegation = false, verLocation = false;
    private boolean vercheff = false;
    private double x, y;
    private Personnel personnel = null;
    Stage stage;

    private boolean isAlphaNum(String name) {
        return name.matches("[a-zA-Z0-9 ]+") && name.length() <= 30;
    }

    private boolean isAlpha(String name) {
        return name.matches("[a-zA-Z][a-zA-Z ]*") && name.length() <= 30;
    }

    private static boolean isNumeric(String string) {
        return string.matches("[0-9]+");
    }

    private static int validDate(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getDays();
        } else {
            return 0;
        }
    }

    @FXML
    void handleClicksAnnuler(ActionEvent event) throws SQLException {
        stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();
        Connection connection = null;
        connection = getOracleConnection();
        PreparedStatement rs1 = connection.prepareStatement("DELETE from EVENMAT WHERE IDMAT=?");
        rs1.setString(1, table_info_Mat.toString());
        rs1.execute();
    }

    public static Date dD;
    public static Date dF;

    @FXML
    void handleClicksPage2(ActionEvent event) throws URISyntaxException {
        String nom = NomFiled.getText();
        String domain = DomainField.getValue();
        String volet = VoletField.getValue();
        String dateDeb = String.valueOf(dateDebutField.getValue());
        String dateFin = String.valueOf(dateFinField.getValue());
        if (!vernom || !verDomaine || !verVolet || !verdateDebut || !verdateFin) {
            if (nom.isEmpty()) {
                NomLabel.setText("🠔 Remplir ce champ");
                NomLabel.setStyle("-fx-text-fill: red");
                NomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            }
            if (domain == null) {
                DomainLabel.setText("🠔 Remplir ce champ");
                DomainLabel1.setText("");
                DomainLabel.setStyle("-fx-text-fill: red");
                DomainField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
            }
            if (volet == null) {
                VoletLabel.setText("🠔 Remplir ce champ");
                VoletLabel1.setText("");
                VoletLabel.setStyle("-fx-text-fill: red");
                VoletField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
            }
            if (dateDeb.length() != 10) {
                dateDebutLabel.setText("🠔 Remplir ce champ");
                dateDebutLabel1.setText("");
                dateDebutLabel.setStyle("-fx-text-fill: red");
                dateDebutField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
            }
            if (dateFin.length() != 10) {
                dateFinLabel.setText("🠔 Remplir ce champ");
                dateFinLabel1.setText("");
                dateFinLabel.setStyle("-fx-text-fill: red");
                dateFinField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setHeaderText(null);
            alert.setContentText("Un des champs n'est pas correctement inserer");
            alert.setGraphic(new ImageView(getClass().getResource("../../../images/errorinsert.png").toURI().toString()));
            alert.showAndWait();
        } else {
            anchorpane2.toFront();
            dD = Date.valueOf(dateDebutField.getValue());
            dF = Date.valueOf(dateFinField.getValue());
        }
    }

    @FXML
    void handleClicksPage3(ActionEvent event) throws URISyntaxException {
        String gouvernerat = GouverneratField.getValue();
        String delegation = DelegationField.getValue();
        String localisation = LocalisationField.getText();
        if (!verGouvernerat || !verDelegation || !verLocation) {
            if (gouvernerat == null) {
                GouverneratLabel.setText("🠔 Remplir ce champ");
                GouverneratLabel.setStyle("-fx-text-fill: red");
                GouverneratField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
            }
            if (delegation == null) {
                DelegationLabel.setText("🠔 Remplir ce champ");
                DelegationLabel.setStyle("-fx-text-fill: red");
                DelegationField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
            }
            if (localisation.isEmpty()) {
                LocationLabel.setText("🠔 Remplir ce champ");
                LocationLabel.setStyle("-fx-text-fill: red");
                LocalisationField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setHeaderText(null);
            alert.setContentText("Un des champs n'est pas correctement inserer");
            alert.setGraphic(new ImageView(getClass().getResource("../../../images/errorinsert.png").toURI().toString()));
            alert.showAndWait();
        } else
            anchorpane3.toFront();
    }

    @FXML
//here is the personnel probléme okay!!!!!
    void handleClicksPage4(ActionEvent event) throws URISyntaxException {
        Label label = new Label();
        Personnel pr = table_info_Per.getSelectionModel().getSelectedItem();
        try {
            if (pr.getNom() != "") {
                percheff = pr;
                vercheff = true;
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        if (vbox_personnel.getChildren().size() == 2)
            vbox_personnel.getChildren().remove(1);
        if (table_info_Per.getItems() == null || table_info_Per.getItems().isEmpty() || !vercheff) {
            if (table_info_Per.getItems() == null || table_info_Per.getItems().isEmpty())
                label.setText("* Selectionner les personnels qui vont participer à cette evenement");
            else if (!vercheff)
                label.setText("* Selectionner le cheff de l'equipe");
            vbox_personnel.getChildren().add(label);
            label.setStyle("-fx-text-fill:red");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setHeaderText(null);
            alert.setContentText("Un des champs n'est pas correctement inserer");
            alert.setGraphic(new ImageView(getClass().getResource("../../../images/errorinsert.png").toURI().toString()));
            alert.showAndWait();
        } else
            anchorpane4.toFront();
    }

    @FXML
    void handleClicksPage5(ActionEvent event) throws URISyntaxException {
        Label label = new Label();
        if (vbox_vehicule.getChildren().size() == 2)
            vbox_vehicule.getChildren().remove(1);
        if (table_info_Eng.getItems() == null || table_info_Eng.getItems().isEmpty()) {
            label.setText("* Selectionner les vehicule qui vont être utilisées");
            vbox_vehicule.getChildren().add(label);
            label.setStyle("-fx-text-fill:red");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setHeaderText(null);
            alert.setContentText("Un des champs n'est pas correctement inserer");
            alert.setGraphic(new ImageView(getClass().getResource("../../../images/errorinsert.png").toURI().toString()));
            alert.showAndWait();
        } else
            anchorpane5.toFront();
    }

    @FXML
    void handleClicksInsert(ActionEvent event) throws URISyntaxException, SQLException {
        boolean ver = true;
        Label label = new Label();
        if (table_info_Mat.getItems() != null && !table_info_Mat.getItems().isEmpty())
            for (int i = 1; i < vbox_outils.getChildren().size(); i++) {
                System.out.println(((Label) vbox_outils.getChildren().get(i)).getText());
                if (!((Label) vbox_outils.getChildren().get(i)).getText().equals("✓")) {
                    ver = false;
                    break;
                }
            }
        if (table_info_Mat.getItems() == null || table_info_Mat.getItems().isEmpty() || !ver) {
            if (table_info_Mat.getItems() == null || table_info_Mat.getItems().isEmpty()) {
                label.setStyle("-fx-text-fill:red");
                label.setText("* Selectionner les outils nécessaire pour cette evenement");
                vbox_outils.getChildren().add(label);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setHeaderText(null);
            alert.setContentText("Un des champs n'est pas correctement inserer");
            alert.setGraphic(new ImageView(getClass().getResource("../../../images/errorinsert.png").toURI().toString()));
            alert.showAndWait();
        } else {
            ///////////
            ///insertion sql !!!!!!!!!!!
            ////////////
            Connection connection = getOracleConnection();
            PreparedStatement rs = connection.prepareStatement("INSERT INTO EVENEMENT values(?,?,?,?,?,?,?,?,?,?,?,?)");
            rs.setString(1, IdEvenementField.getText());
            rs.setString(2, NomFiled.getText());
            rs.setString(3, DomainField.getValue());
            rs.setString(4, VoletField.getValue());
            rs.setDate(5, Date.valueOf(String.valueOf(dateDebutField.getValue())));
            rs.setDate(6, Date.valueOf(String.valueOf(dateFinField.getValue())));
            rs.setString(7, GouverneratField.getValue());
            rs.setString(8, DelegationField.getValue());
            rs.setString(9, LocalisationField.getText());
            rs.setString(10, DescriptionField.getText());
            rs.setString(11, percheff.getMatricule());
            if(dateDebutField.getValue().equals(LocalDate.now()))
                rs.setString(12, "EnCours");
            else
                rs.setString(12, "Initial");
            rs.execute();
            for (Personnel i : table_info_Per.getItems()) {
                rs = connection.prepareStatement("INSERT INTO EVENPER values(?,?)");
                rs.setString(1, i.getMatricule());
                rs.setString(2, IdEvenementField.getText());
                rs.execute();
            }
            System.out.println(T);
            for (int i = 0; i < V.size(); i++) {
                System.out.println(V.get(i)[0] + " " + V.get(i)[1]);
                rs = connection.prepareStatement("UPDATE MATERIEL set QTE=QTE - ? where DESIGNATION=?");
                rs.setString(1, V.get(i)[1]);
                rs.setString(2, V.get(i)[0]);
                rs.execute();
            }
//            for(Materiel i:table_info_Mat.getItems()){
//                rs = connection.prepareStatement("INSERT INTO interMAT values(?,?,?)");
//                rs.setString(1,i.getId());
//                rs.setString(2,IdEvenementField.getText());
//                rs.setInt(3,i.getQte());
//                rs.execute();
//            }
            for (Engin i : table_info_Eng.getItems()) {
                rs = connection.prepareStatement("INSERT INTO EVENENG values(?,?)");
                rs.setString(1, i.getID());
                rs.setString(2, IdEvenementField.getText());
                rs.execute();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setHeaderText(null);
            alert.setContentText("Ajout avec succés");
            alert.setGraphic(new ImageView(getClass().getResource("../../../images/icons8-receipt-approved-64.png").toURI().toString()));
            alert.showAndWait();
            stage = (Stage) anchorpane.getScene().getWindow();
            stage.close();
        }
    }

    ArrayList<String[]> T;
    private Personnel percheff;
    ArrayList<String[]> V;

    @FXML
    void handleClicksTabOutils(ActionEvent event) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("../../view/evenement/evenAddMateriel.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        consCol1.setCellValueFactory(new PropertyValueFactory<>("consom"));
        typeCol1.setCellValueFactory(new PropertyValueFactory<>("designation"));
        ArrayList<Integer> qtelist = new ArrayList<>();
        V = new ArrayList<>();
        stage.focusedProperty().addListener((ov, onHidden, onShown) -> {
            if (!stage.isShowing()) {

                table_info_Mat.setItems(AddMatCon.oblist2);
                vbox_outils.getChildren().clear();
                ImageView img = new ImageView();
                img.fitHeightProperty().set(76);
                vbox_outils.getChildren().add(img);
                if (table_info_Mat.getItems() != null) {
                    for (int i = 0; i < table_info_Mat.getItems().size(); i++) {
                        Label l = new Label();
                        l.setStyle("-fx-text-fill:red");
                        l.setText("* Remplir la quantite de " + table_info_Mat.getItems().get(i).getDesignation());
                        vbox_outils.getChildren().add(l);
                    }
                    qtelist.clear();
                    V.clear();
                    for (int i = 0; i < table_info_Mat.getItems().size(); i++) {

                        try {
                            int s = 0;
                            Connection connection = getOracleConnection();
                            PreparedStatement rs1 = connection.prepareStatement("select QTEUSED from EVENMAT,EVENEMENT WHERE EVENMAT.ID=? and EVENEMENT.IDMAT=EVENMAT.IDMAT and (dateD BETWEEN ? and ? or dateF BETWEEN ? and ? )");
                            rs1.setString(1, table_info_Mat.getItems().get(i).getDesignation());
                            rs1.setDate(2, EvenementAddController.dD);
                            rs1.setDate(3, EvenementAddController.dF);
                            rs1.setDate(4, EvenementAddController.dD);
                            rs1.setDate(5, EvenementAddController.dF);
                            ResultSet rc = rs1.executeQuery();
                            while (rc.next()) {
                                s += rc.getInt("QTEUSED");
                            }
                            if (table_info_Mat.getItems().get(i).getConsom().equals("Oui")) {
                                String[] c = new String[2];
                                c[0] = table_info_Mat.getItems().get(i).getDesignation();
                                c[1] = "0";
                                V.add(c);
                            }
                            qtelist.add(table_info_Mat.getItems().get(i).getQte() - s);
                            System.out.println(qtelist.get(i) + " non utilisé");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                }
            }
        });
        AddMatCon.matricule = IdEvenementField.getText();
        //drag it here
        root.setOnMousePressed(event1 -> {
            x = event1.getSceneX();
            y = event1.getSceneY();
        });
        root.setOnMouseDragged(event1 -> {

            stage.setX(event1.getScreenX() - x);
            stage.setY(event1.getScreenY() - y);

        });


        //add cell of button edit
        Callback<TableColumn<Materiel, String>, TableCell<Materiel, String>> cellFoctory = (TableColumn<Materiel, String> param) -> {
            // make cell containing buttons
            final TableCell<Materiel, String> cell = new TableCell<Materiel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (!empty) {
                        TextField text1 = new TextField();
                        text1.setStyle("-fx-text-fill : #000000");
                        TextField text2 = new TextField();
                        text2.setStyle("-fx-text-fill : #000000");
                        text1.setOnKeyReleased((KeyEvent event) -> {
                            Label l = new Label();
                            l.setStyle("-fx-text-fill:red");
                            if (text1.getText() != null && isNumeric(text1.getText()) && Integer.valueOf(text1.getText()) > 0) {
                                if (Integer.valueOf(text1.getText()) > qtelist.get(this.getTableRow().getIndex())) {
                                    l.setText("* La quantité de " + table_info_Mat.getItems().get(this.getTableRow().getIndex()).getDesignation() + " est superieur à la quantite disponible");
                                    vbox_outils.getChildren().set(this.getTableRow().getIndex() + 1, l);
                                } else {
                                    l.setText("✓");
                                    l.setStyle("-fx-text-fill: #32CD32");
                                    vbox_outils.getChildren().set(this.getTableRow().getIndex() + 1, l);
                                    Connection connection = null;
                                    try {
                                        connection = getOracleConnection();
                                        String place1 = IdEvenementField.getText();
                                        String place2 = table_info_Mat.getItems().get(this.getTableRow().getIndex()).getDesignation();
                                        PreparedStatement rs1 = connection.prepareStatement("select * from EVENMAT WHERE IDMAT=? AND ID=?");
                                        rs1.setString(1, place1);
                                        rs1.setString(2, place2);
                                        ResultSet rs = rs1.executeQuery();
                                        if (rs.next() == false) {
                                            rs1 = connection.prepareStatement("INSERT INTO EVENMAT values(?,?,?)");
                                            rs1.setString(1, table_info_Mat.getItems().get(this.getTableRow().getIndex()).getDesignation());
                                            rs1.setString(2, IdEvenementField.getText());
                                            rs1.setInt(3, Integer.parseInt(text1.getText()));
                                            rs1.execute();
                                        } else {
                                            rs1 = connection.prepareStatement("UPDATE EVENMAT set qteUsed=? where IDMAT='" + IdEvenementField.getText() + "' AND ID='" + table_info_Mat.getItems().get(this.getTableRow().getIndex()).getDesignation() + "'");
                                            rs1.setInt(1, Integer.parseInt(text1.getText()));
                                            rs1.execute();
                                        }
                                        connection.close();
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    //table_info_Mat.getItems().get(this.getTableRow().getIndex()).setQte(Integer.parseInt(text.getText()));
                                    //System.out.println(table_info_Mat.getItems().get(this.getTableRow().getIndex()).getQte());
                                }
                            } else {
                                l.setText("* Remplir la quantite de " + table_info_Mat.getItems().get(this.getTableRow().getIndex()).getDesignation());
                                vbox_outils.getChildren().set(this.getTableRow().getIndex() + 1, l);
                            }
                        });
                        text2.setOnKeyReleased((KeyEvent event) -> {
                            Label l = new Label();
                            l.setStyle("-fx-text-fill:red");
                            if (text2.getText() != null && isNumeric(text2.getText()) && Integer.valueOf(text2.getText()) > 0) {
                                if (Integer.valueOf(text2.getText()) > qtelist.get(this.getTableRow().getIndex())) {
                                    l.setText("* La quantité de " + table_info_Mat.getItems().get(this.getTableRow().getIndex()).getDesignation() + " est superieur à la quantite disponible");
                                    vbox_outils.getChildren().set(this.getTableRow().getIndex() + 1, l);
                                } else {
                                    l.setText("✓");
                                    l.setStyle("-fx-text-fill: #32CD32");
                                    vbox_outils.getChildren().set(this.getTableRow().getIndex() + 1, l);
                                    Connection connection = null;
                                    try {
                                        connection = getOracleConnection();
                                        String place1 = IdEvenementField.getText();
                                        String place2 = table_info_Mat.getItems().get(this.getTableRow().getIndex()).getDesignation();
                                        PreparedStatement rs1 = connection.prepareStatement("select * from EVENMAT WHERE IDMAT=? AND ID=?");
                                        rs1.setString(1, place1);
                                        rs1.setString(2, place2);
                                        ResultSet rs = rs1.executeQuery();
                                        if (rs.next() == false) {
                                            rs1 = connection.prepareStatement("INSERT INTO EVENMAT values(?,?,?)");
                                            rs1.setString(1, table_info_Mat.getItems().get(this.getTableRow().getIndex()).getDesignation());
                                            rs1.setString(2, IdEvenementField.getText());
                                            rs1.setInt(3, Integer.parseInt(text2.getText()));
                                            rs1.execute();
                                        } else {
                                            rs1 = connection.prepareStatement("UPDATE EVENMAT set qteUsed=? where IDMAT='" + IdEvenementField.getText() + "' AND ID='" + table_info_Mat.getItems().get(this.getTableRow().getIndex()).getDesignation() + "'");
                                            rs1.setInt(1, Integer.parseInt(text2.getText()));
                                            rs1.execute();
                                        }
                                        connection.close();
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    for (int i = 0; i < V.size(); i++) {
                                        if (V.get(i)[0].equals(table_info_Mat.getItems().get(this.getTableRow().getIndex()).getDesignation()))
                                            V.get(i)[1] = text2.getText();
                                    }
                                }
                            } else {
                                l.setText("* Remplir la quantite de " + table_info_Mat.getItems().get(this.getTableRow().getIndex()).getDesignation());
                                vbox_outils.getChildren().set(this.getTableRow().getIndex() + 1, l);
                            }
                            for (int i = 0; i < V.size(); i++)
                                System.out.println(V.get(i)[0] + " " + V.get(i)[1]);
                        });

                        if (table_info_Mat.getItems().get(this.getTableRow().getIndex()).getConsom().equals("Non")) {
                            setGraphic(text1);

                        } else {
                            setGraphic(text2);
                        }
                        setText(null);

                    } else
                        setGraphic(null);
                }

            };

            return cell;
        };
        qteCol1.setCellFactory(cellFoctory);
        stage.show();
    }

    @FXML
//here too you should look
    void handleClicksTabPersonnel(ActionEvent event) throws IOException {
        vercheff = false;
        Parent root = FXMLLoader.load(getClass().getResource("../../view/evenement/evenAddPersonnel.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        col_id.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));


/*
        Callback<TableColumn<Personnel, String>, TableCell<Personnel, String>> cellFoctory = (TableColumn<Personnel, String> param) -> {
            final TableCell<Personnel, String> cell = new TableCell<Personnel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        for(Personnel p:table_info_Per.getItems()) {
                            p.getCheff().setOnMouseClicked((MouseEvent event) -> {
                                vercheff=false;
                                for(Personnel p1:table_info_Per.getItems()) {
                                    if(p1!=p){
                                        p1.getCheff().setSelected(false);
                                    }else if(p1.getCheff().isSelected()){
                                        percheff=p1;
                                        vercheff=true;
                                    }
                                }
                            });
                        }
                    }
                }
            };
            return cell;
        };


        col_select.setCellFactory(cellFoctory);
        col_cheff.setCellValueFactory(new PropertyValueFactory<>("cheff"));

 */
        AddPerCon.oblist2 = null;
        stage.focusedProperty().addListener((ov, onHidden, onShown) -> {
            if (!stage.isShowing()) {
                table_info_Per.setItems(AddPerCon.oblist2);
            }
        });
        //drag it here
        root.setOnMousePressed(event1 -> {
            x = event1.getSceneX();
            y = event1.getSceneY();
        });
        root.setOnMouseDragged(event1 -> {

            stage.setX(event1.getScreenX() - x);
            stage.setY(event1.getScreenY() - y);

        });
        stage.show();

    }

    @FXML
    void handleClicksTabVehicule(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../view/evenement/evenAddEngin.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        markCol.setCellValueFactory(new PropertyValueFactory<>("Marque"));
        AddEngCon.oblist2 = null;
        stage.focusedProperty().addListener((ov, onHidden, onShown) -> {
            if (!stage.isShowing()) {
                table_info_Eng.setItems(AddEngCon.oblist2);
            }


        });
        //drag it here
        root.setOnMousePressed(event1 -> {
            x = event1.getSceneX();
            y = event1.getSceneY();
        });
        root.setOnMouseDragged(event1 -> {

            stage.setX(event1.getScreenX() - x);
            stage.setY(event1.getScreenY() - y);

        });
        stage.show();
    }


    @FXML
    void verifDomain(ActionEvent event) {
        verDomaine = false;
        voletController();
        if (DomainField.getValue() == null) {
            DomainLabel.setText("🠔 Selectionner Domaine");
            DomainLabel1.setText("");
            verDomaine = false;
            VoletField.setDisable(true);
            DomainLabel.setStyle("-fx-text-fill: red");
            DomainField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
        } else {
            DomainLabel1.setStyle("-fx-text-fill: #32CD32");
            DomainLabel.setText("");
            VoletField.setDisable(false);
            verDomaine = true;
            DomainLabel1.setText("✓");
            DomainField.setStyle("-fx-background-color:white;");
        }
    }

    private void voletController() {
        list2.clear();
        VoletField.getItems().removeAll(VoletField.getItems());
        if ("Social".equals(DomainField.getValue())) {
            list2.addAll("Loisirs", "Sports", "Santé", "Sécurité", "Culture", "Education", "Gouvernace", "Vie Communautaire");
        }
        if ("Territorial".equals(DomainField.getValue())) {
            list2.addAll("Environnement physique", "Transport", "Habitation", "Aménagement");
        }
        if ("Economique".equals(DomainField.getValue())) {
            list2.addAll("Vitalité économique", "Emploi");
        }
        VoletField.getItems().setAll(list2);
    }

    @FXML
    void verifVolet(ActionEvent event) {
        verVolet = false;
        if (VoletField.getValue() == null) {
            VoletLabel1.setText("");
            VoletLabel.setText("");
            verVolet = false;
//            VoletLabel.setText("🠔 Selectionner Volet");
//            VoletLabel1.setText("");
//            VoletLabel.setStyle("-fx-text-fill: red");
//            VoletField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
        } else {
            VoletLabel1.setStyle("-fx-text-fill: #32CD32");
            VoletLabel1.setText("✓");
            verVolet = true;
            VoletLabel.setText("");
            VoletField.setStyle("-fx-background-color:white;");
        }
    }

    @FXML
    void verifGouvernerat(ActionEvent event) {
        verGouvernerat = false;
        verDelegation = false;
        delegationController();
        if (GouverneratField.getValue() == null) {
            GouverneratLabel.setText("🠔 Selectionner Gouvernerat");
            verGouvernerat = false;
            DelegationField.setDisable(true);
            GouverneratLabel.setStyle("-fx-text-fill: red");
            GouverneratField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
        } else {
            GouverneratLabel.setStyle("-fx-text-fill: #32CD32");
            DelegationField.setDisable(false);
            verGouvernerat = true;
            GouverneratLabel.setText("✓");
            GouverneratField.setStyle("-fx-background-color:white;");
        }
    }

    private void delegationController() {
        list4.clear();
        DelegationField.getItems().removeAll(DelegationField.getItems());
        if ("Ariana".equals(GouverneratField.getValue())) {
            list4.addAll("Ariana Ville", "Ettadhamen", "Kalaat Landlous", "La Soukra", "Mnihla", "Raoued", "Sidi Thabet");
        }
        if ("Beja".equals(GouverneratField.getValue())) {
            list4.addAll("Amdoun", "Beja Nord", "Beja Sud", "Goubellat", "Mejez El Bab", "Nefza", "Teboursouk", "Testour", "Thibar");
        }
        if ("Ben arous".equals(GouverneratField.getValue())) {
            list4.addAll("Ben Arous", "Bou Mhel El Bassatine", "El Mourouj", "Ezzahra", "Fouchana", "Hammam Chatt", "Hammam Lif", "Megrine", "Mohamadia", "Mornag", "Nouvelle Medina", "Rades");
        }
        if ("Bizerte".equals(GouverneratField.getValue())) {
            list4.addAll("Bizerte Nord", "Bizerte Sud", "El Alia", "Ghar El Melh", "Ghezala", "Jarzouna", "Joumine", "Mateur", "Menzel Bourguiba", "Menzel Jemil", "Ras Jebel", "Sejnane", "Tinja", "Utique");
        }
        if ("Gabes".equals(GouverneratField.getValue())) {
            list4.addAll("El Hamma", "El Metouia", "Gabes Medina", "Gabes Ouest", "Gabes Sud", "Ghannouche", "Mareth", "Matmata", "Menzel Habib", "Nouvelle Matmata");
        }
        if ("Gafsa".equals(GouverneratField.getValue())) {
            list4.addAll("Belkhir", "El Guettar", "El Ksar", "El Mdhilla", "Gafsa Nord", "Gafsa Sud", "Metlaoui", "Moulares", "Redeyef", "Sidi Aich", "Sned");
        }
        if ("Jendouba".equals(GouverneratField.getValue())) {
            list4.addAll("Ain Draham", "Balta Bou Aouene", "Bou Salem", "Fernana", "Ghardimaou", "Jendouba", "Jendouba Nord", "Oued Mliz", "Tabarka");
        }
        if ("Kairouan".equals(GouverneratField.getValue())) {
            list4.addAll("Bou Hajla", "Chebika", "Cherarda", "El Ala", "Haffouz", "Hajeb El Ayoun", "Kairouan Nord", "Kairouan Sud", "Nasrallah", "Oueslatia", "Sbikha");
        }
        if ("Kasserine".equals(GouverneratField.getValue())) {
            list4.addAll("El Ayoun", "Ezzouhour (Kasserine)", "Feriana", "Foussana", "Haidra", "Hassi El Frid", "Jediliane", "Kasserine Nord", "Kasserine Sud", "Mejel Bel Abbes", "Sbeitla", "Sbiba", "Thala");
        }
        if ("Kebili".equals(GouverneratField.getValue())) {
            list4.addAll("Douz", "El Faouar", "Kebili Nord", "Kebili Sud", "Souk El Ahad");
        }
        if ("Le Kef".equals(GouverneratField.getValue())) {
            list4.addAll("Dahmani", "El Ksour", "Jerissa", "Kalaa El Khasba", "Kalaat Sinane", "Le Kef Est", "Le Kef Ouest", "Le Sers", "Nebeur", "Sakiet Sidi Youssef", "Tajerouine", "Touiref");
        }
        if ("Mahdia".equals(GouverneratField.getValue())) {
            list4.addAll("Bou Merdes", "Chorbane", "El Jem", "Hbira", "Ksour Essaf", "La Chebba", "Mahdia", "Melloulech", "Ouled Chamakh", "Sidi Alouene", "Souassi");
        }
        if ("Mannouba".equals(GouverneratField.getValue())) {
            list4.addAll("Borj El Amri", "Douar Hicher", "El Battan", "Jedaida", "Mannouba", "Mornaguia", "Oued Ellil", "Tebourba");
        }
        if ("Medenine".equals(GouverneratField.getValue())) {
            list4.addAll("Ajim", "Ben Guerdane", "Beni Khedache", "Djerba - Houmet Essouk", "Djerba - Midoun", "Medenine Nord", "Medenine Sud", "Sidi Makhlouf", "Zarzis");
        }
        if ("Monastir".equals(GouverneratField.getValue())) {
            list4.addAll("Bekalta", "Bembla", "Beni Hassen", "Jemmal", "Ksar Helal", "Ksibet El Mediouni", "Moknine", "Monastir", "Ouerdanine", "Sahline", "Sayada Lamta Bou Hajar", "Teboulba", "Zeramdine");
        }
        if ("Nabeul".equals(GouverneratField.getValue())) {
            list4.addAll("Beni Khalled", "Beni Khiar", "Bou Argoub", "Dar Chaabane Elfehri", "El Haouaria", "El Mida", "Grombalia", "Hammam El Ghezaz", "Hammamet", "Kelibia", "Korba", "Menzel Bouzelfa", "Menzel Temime", "Nabeul", "Soliman", "Takelsa");
        }
        if ("Sfax".equals(GouverneratField.getValue())) {
            list4.addAll("Kerkennah", "Agareb", "Bir Ali Ben Khelifa", "El Amra", "El Hencha", "Esskhira", "Ghraiba", "Jebeniana", "Mahras", "Menzel Chaker", "Sakiet Eddaier", "Sakiet Ezzit", "Sfax Est", "Sfax Sud", "Sfax Ville");
        }
        if ("Sidi bouzid".equals(GouverneratField.getValue())) {
            list4.addAll("Ben Oun", "Bir El Haffey", "Cebbala", "Jilma", "Maknassy", "Menzel Bouzaiene", "Mezzouna", "Ouled Haffouz", "Regueb", "Sidi Bouzid Est", "Sidi Bouzid Ouest", "Souk Jedid");
        }
        if ("Siliana".equals(GouverneratField.getValue())) {
            list4.addAll("Bargou", "Bou Arada", "El Aroussa", "Gaafour", "Kesra", "Le Krib", "Makthar", "Rohia", "Sidi Bou Rouis", "Siliana Nord", "Siliana Sud");
        }
        if ("Sousse".equals(GouverneratField.getValue())) {
            list4.addAll("Akouda", "Bou Ficha", "Enfidha", "Hammam Sousse", "Hergla", "Kalaa El Kebira", "Kalaa Essghira", "Kondar", "Msaken", "Sidi Bou Ali", "Sidi El Heni", "Sousse Jaouhara", "Sousse Riadh", "Sousse Ville");
        }
        if ("Tataouine".equals(GouverneratField.getValue())) {
            list4.addAll("Bir Lahmar", "Dhehiba", "Ghomrassen", "Remada", "Smar", "Tataouine Nord", "Tataouine Sud");
        }
        if ("Tozeur".equals(GouverneratField.getValue())) {
            list4.addAll("Degueche", "Hezoua", "Nefta", "Tameghza", "Tozeur");
        }
        if ("Tunis".equals(GouverneratField.getValue())) {
            list4.addAll("Ain Zaghouan", "Bab Bhar", "Bab Souika", "Carthage", "Cite El Khadra", "El Hrairia", "El Kabbaria", "El Kram", "El Menzah", "El Omrane", "El Omrane Superieur", "El Ouerdia", "Essijoumi", "Ettahrir", "Ezzouhour (Tunis)", "Jebel Jelloud", "La Goulette", "La Marsa", "La Medina", "Le Bardo", "Sidi El Bechir", "Sidi Hassine");
        }
        if ("Zaghouan".equals(GouverneratField.getValue())) {
            list4.addAll("Bir Mcherga", "El Fahs", "Ennadhour", "Hammam Zriba", "Saouef", "Zaghouan");
        }
        DelegationField.getItems().setAll(list4);
    }

    @FXML
    void verifDelegation(ActionEvent event) {
        verDelegation = false;
        if (DelegationField.getValue() == null) {
            DelegationLabel.setText("");
            verDelegation = false;
//            DelegationLabel.setText("🠔 Selectionner Delegation");
//            DelegationLabel.setStyle("-fx-text-fill: red");
//            DelegationField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
        } else {
            DelegationLabel.setStyle("-fx-text-fill: #32CD32");
            DelegationLabel.setText("✓");
            verDelegation = true;
            DelegationField.setStyle("-fx-background-color:white;");
        }
    }

    @FXML
    void verifdateDebut(ActionEvent event) throws SQLException {
        table_info_Mat.setItems(null);
        table_info_Per.setItems(null);
        table_info_Eng.setItems(null);
        Connection connection = null;
        connection = getOracleConnection();
        PreparedStatement rs1 = connection.prepareStatement("DELETE from EVENMAT WHERE IDMAT=?");
        rs1.setString(1, IdEvenementField.getText());
        rs1.execute();
        verdateDebut = false;
        verdateFin = false;
        if (!dateFinField.isDisable()) {
            dateFinLabel1.setText("");
            dateDebutLabel.setText("");
        }
        if (validDate(dateDebutField.getValue(), LocalDate.now()) > 0) {
            dateDebutLabel.setText("🠔 Date de Debut invalide");
            verdateDebut = false;
            dateDebutLabel1.setText("");
            dateDebutLabel.setStyle("-fx-text-fill: red");
            dateFinField.setDisable(true);
            dateDebutField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
        } else if (validDate(dateDebutField.getValue(), LocalDate.now()) <= 0 && dateDebutField.getValue() != null) {
            dateDebutLabel1.setStyle("-fx-text-fill: #32CD32");
            verdateDebut = true;
            dateDebutLabel.setText("");
            dateFinField.setDisable(false);
            dateDebutLabel1.setText("✓");
            dateDebutField.setStyle("-fx-background-color:transparent;");
            if (dateFinField.getValue() != null)
                verifdateFin(event);
        } else {
            dateDebutLabel.setText("🠔 Date de Debut invalide");
            verdateDebut = false;
            dateDebutLabel1.setText("");
            dateFinField.setDisable(true);
            dateDebutLabel.setStyle("-fx-text-fill: red");
            dateDebutField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
        }
    }

    @FXML
    void verifdateFin(ActionEvent event) throws SQLException {
        table_info_Mat.setItems(null);
        table_info_Per.setItems(null);
        table_info_Eng.setItems(null);
        Connection connection = null;
        connection = getOracleConnection();
        PreparedStatement rs1 = connection.prepareStatement("DELETE from EVENMAT WHERE IDMAT=?");
        rs1.setString(1, IdEvenementField.getText());
        rs1.execute();
        if (validDate(dateFinField.getValue(), dateDebutField.getValue()) > 0) {
            dateFinLabel.setText("🠔 Date de Fin invalide");
            verdateFin = false;
            dateFinLabel1.setText("");
            dateFinLabel.setStyle("-fx-text-fill: red");
            dateFinField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
        } else if (validDate(dateFinField.getValue(), LocalDate.now()) <= 0 && dateFinField.getValue() != null) {
            dateFinLabel1.setStyle("-fx-text-fill: #32CD32");
            verdateFin = true;
            dateFinLabel.setText("");
            dateFinLabel1.setText("✓");
            dateFinField.setStyle("-fx-background-color:transparent;");
        } else {
            dateFinLabel.setText("🠔 Date de Fin invalide");
            verdateFin = false;
            dateFinLabel1.setText("");
            dateFinLabel.setStyle("-fx-text-fill: red");
            dateFinField.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
        }
    }

    @FXML
    void verifLoc(KeyEvent event) {
        verLocation = false;
        String Loc = LocalisationField.getText();
        if (!Loc.isEmpty()) {
            if (!isAlphaNum(Loc)) {
                verLocation = false;
                LocationLabel.setText("🠔 La localisation est incorrect !");
                LocalisationField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                LocationLabel.setStyle("-fx-text-fill: red");
            } else {
                LocalisationField.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                verLocation = true;
                LocationLabel.setText("✓");
                LocationLabel.setStyle("-fx-text-fill: #32CD32");
            }
        } else {
            LocalisationField.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            LocationLabel.setText("");
            verLocation = false;
        }
    }

    @FXML
    void verifNom(KeyEvent event) {
        vernom = false;
        String nom = NomFiled.getText();
        if (!nom.isEmpty()) {
            if (!isAlpha(nom)) {
                NomLabel.setText("🠔 Le nom est composé de lettres alphabétiques!");
                NomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                vernom = false;
                NomLabel.setStyle("-fx-text-fill: red");
            } else {
                NomFiled.setStyle("-fx-text-box-border: #32CD32;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
                NomLabel.setText("✓");
                vernom = true;
                NomLabel.setStyle("-fx-text-fill: #32CD32");
            }
        } else {
            NomFiled.setStyle("-fx-text-box-border: red;  -fx-border-width: 2px  ;-fx-background-insets: 0, 0 0 3 0 ; -fx-background-radius: 0.7em ;");
            vernom = false;
            NomLabel.setText("");
        }
    }

    ObservableList list1 = FXCollections.observableArrayList();
    ObservableList list2 = FXCollections.observableArrayList();
    ObservableList list3 = FXCollections.observableArrayList();
    ObservableList list4 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateFinField.setDisable(true);
        VoletField.setDisable(true);
        DelegationField.setDisable(true);
        list1.addAll("Social", "Territorial", "Economique");
        list3.addAll("Ariana", "Beja", "Ben arous", "Bizerte", "Gabes", "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kebili", "Le Kef", "Mahdia", "Mannouba", "Medenine", "Monastir", "Nabeul", "Sfax", "Sidi bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan");
        GouverneratField.getItems().setAll(list3);
        DomainField.getItems().setAll(list1);
        random8(IdEvenementField);
    }

    public static char randomCharacter() {
        int rand = (int) (Math.random() * 36);
        if (rand <= 9) {
            int number = rand + 48;
            return (char) (number);
        } else {
            int uppercase = rand + 55;
            return (char) (uppercase);
        }
    }

    public void random8(Label label) {
        String randomPassword;
        boolean b;
        do {
            b = false;
            randomPassword = "";
            for (int j = 0; j < 8; j++) {
                randomPassword += randomCharacter();
            }
            try {
                Connection connection = getOracleConnection();
                ResultSet rs = connection.createStatement().executeQuery("select * from EVENEMENT");
                while (rs.next()) {
                    if (randomPassword.equals(rs.getString("idMat"))) {
                        b = true;
                        break;
                    }
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(randomPassword);
        } while (b);
        label.setText(randomPassword);
    }

    @FXML
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnPage1) {
            anchorpane1.toFront();
        }
        if (actionEvent.getSource() == btnPage2) {
            anchorpane2.toFront();
        }
        if (actionEvent.getSource() == btnPage3) {
            anchorpane3.toFront();
        }
        if (actionEvent.getSource() == btnPage4) {
            anchorpane4.toFront();
        }
    }

}
