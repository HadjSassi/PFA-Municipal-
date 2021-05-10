package sample.App.controllers.GestionEvenement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import sample.App.model.Engin;
import sample.App.model.Evenement;
import sample.App.model.Materiel;
import sample.App.model.Personnel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class EvenementController implements Initializable {
    @FXML
    private Label LabelNbInter;

    @FXML
    private Label LabelNbInitial;

    @FXML
    private Label LabelNbEnCours;

    @FXML
    private Label LabelNbTermine;

    @FXML
    private Label LabelNbAnnule;
    @FXML
    private TableView<Evenement> table_info;

    @FXML
    private TableColumn<Evenement, String> col_id;

    @FXML
    private TableColumn<Evenement, String> col_nom;

    @FXML
    private TableColumn<Evenement, Date> col_dDebut;

    @FXML
    private TableColumn<Evenement, Date> col_dFin;

    @FXML
    private TableColumn<Evenement, String> col_etat;

    @FXML
    private TableColumn<Evenement, String> col_edit;

    @FXML
    private TextField filterField;

    private double x, y;
    private boolean s=true;
    private Evenement evenement =null;
    @FXML
    void handleAjoutEvenement(ActionEvent event) throws IOException {
        if (s){Parent root = FXMLLoader.load(getClass().getResource("../../view/evenement/evenAdd.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.focusedProperty().addListener((ov, onHidden, onShown) -> {
                if (!stage.isShowing()){
                    loadData();
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
//        FxmlLoader object = new FxmlLoader();
//        AnchorPane view = object.getPane("interIAdd");
//        view.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
//        gInterfaceController.getAnchorpane3().getChildren().removeAll();
//        gInterfaceController.getAnchorpane3().getChildren().setAll(view);
    }

    @FXML
    void handleClicksDeleteSelected(ActionEvent event) throws URISyntaxException {
        String s="";
        String s1 = null;
        ObservableList<Evenement>  ob = table_info.getSelectionModel().getSelectedItems();
        if (ob.toArray().length != 0) {
            for (Evenement per : ob){
                s+=per.getIdI()+"///";
                s1+=per.getIdI();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText(null);
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.setContentText("Etes-vous sure de supprimer Evenement n°: ///"+s);
            alert.setGraphic(new ImageView(getClass().getResource("../../../images/delete.png").toURI().toString() ));
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    for (Evenement inter : ob) {
                        try {
                            Connection connection= getOracleConnection();
                            PreparedStatement rs1 = connection.prepareStatement("DELETE from EVENMAT WHERE IDMAT=?");
                            rs1.setString(1,inter.getIdI());
                            rs1.execute();
                            rs1 = connection.prepareStatement("DELETE from EVENPER WHERE IDMAT=?");
                            rs1.setString(1,inter.getIdI());
                            rs1.execute();
                            rs1 = connection.prepareStatement("DELETE from EVENENG WHERE IDMAT=?");
                            rs1.setString(1,inter.getIdI());
                            rs1.execute();
                            rs1 = connection.prepareStatement("delete from Evenement where idMat=?");
                            rs1.setString(1,inter.getIdI());
                            rs1.execute();
                            connection.close();

                        }catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }


                    }

                }
            });
        }
        loadData();
    }

    @FXML
    void handleClicksRefresh(ActionEvent event) {
        loadData();
        filter();
        filterField.setText(null);
    }
    ObservableList<Evenement> oblist;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table_info.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        initTable();
        loadData();
        EtatColorTable();
    }
    private void filter(){
        FilteredList<Evenement> filteredData = new FilteredList<>(oblist, b -> true);

        filterField.textProperty().addListener((observable ,oldValue, newValue)->{filteredData.setPredicate(Evenement -> {

            if(newValue == null || newValue.isEmpty()){
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            if (Evenement.getIdI().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;
            }else if (Evenement.getNom().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;
            }else if(Evenement.getDateDeb().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;
            }else if(Evenement.getDateFin().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
                return true;
            }else if(Evenement.getEtat().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
                return true;
            }else
                return false;//doesn't match
        });});
        SortedList<Evenement> sortedData= new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table_info.comparatorProperty());
        table_info.setItems(sortedData);
    }
    private void initTable(){
        initCols();
    }
    private void initCols(){

        col_id.setCellValueFactory(new PropertyValueFactory<>("idI"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_dDebut.setCellValueFactory(new PropertyValueFactory<>("dateDeb"));
        col_dFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        col_edit.setCellValueFactory(new PropertyValueFactory<Evenement, String>("update"));

        //add cell of button edit
        Callback<TableColumn<Evenement, String>, TableCell<Evenement, String>> cellFoctory = (TableColumn<Evenement, String> param) -> {
            // make cell containing buttons
            final TableCell<Evenement, String> cell = new TableCell<Evenement, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        //FontAwesomeIconView eye = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                        Image im= new Image("sample/images/info.png");
                        ImageView eye=new ImageView();
                        eye.setImage(im);
                        eye.setFitHeight(28);
                        eye.setFitWidth(28);
                        eye.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                        );
                        eye.setOnMouseClicked((MouseEvent event) -> {
                            evenement =table_info.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("../../view/evenement/evenAffich.fxml"));
                            try {
                                loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            EvenementAffController addController= loader.getController();
                            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.FRANCE);
                            //LocalDate dateTime = LocalDate.parse(personnel.getNaissance(), formatter);
                            addController.setTextFiel(evenement);
                            Parent parent=loader.getRoot();
                            Stage stage=new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.focusedProperty().addListener((ov, onHidden, onShown) -> {
                                if (!stage.isShowing()){
                                    loadData();
                                }
                            });
                            //drag it here
                            parent.setOnMousePressed(event1 -> {
                                x = event1.getSceneX();
                                y = event1.getSceneY();
                            });
                            parent.setOnMouseDragged(event1 -> {

                                stage.setX(event1.getScreenX() - x);
                                stage.setY(event1.getScreenY() - y);

                            });
                            stage.show();
                        });

                        HBox managebtn = new HBox(eye);
                        managebtn.setStyle("-fx-alignment:center");
                        setGraphic(managebtn);
                        setText(null);

                    }
                }

            };

            return cell;
        };
        col_edit.setCellFactory(cellFoctory);
//        //add cell of button edit
//        Callback<TableColumn<Evenement, String>, TableCell<Evenement, String>> cellfact = (TableColumn<Evenement, String> param) -> {
//            // make cell containing buttons
//            final TableCell<Evenement, String> cell = new TableCell<Evenement, String>() {
//                @Override
//                public void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    //that cell created only on non-empty rows
//                    if (empty) {
//                        setGraphic(null);
//                        setText(null);
//
//                    } else {
//                        CheckBox check= new CheckBox();
//                        setGraphic(check);
//                    }
//                }
//            };
//            return cell;
//        };
//        col_select.setCellFactory(cellfact);
    }
    private void loadData(){
        oblist = FXCollections.observableArrayList();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from Evenement");
            int NbInter=0;
            int NbTermine=0;
            int NbEnCours=0;
            int NbInitial=0;
            int NbAnnule=0;
            while(rs.next()){
                ArrayList<Personnel> p=new ArrayList<>();
                try {
                    PreparedStatement rs1 = connection.prepareStatement("select PERSONNEL.MATRICULE,CIN,NOM,PRENOM,NAISSANCE,SALAIRE,SEX,TEL,SERVICE,DESCRIPTION from PERSONNEL,EVENPER WHERE IDMAT=? and PERSONNEL.MATRICULE=EVENPER.MATRICULE");
                    rs1.setString(1,rs.getString("idMat"));
                    ResultSet rc=rs1.executeQuery();
                    while(rc.next()){
                        p.add(new Personnel(rc.getString("matricule"),rc.getString("cin"),rc.getString("nom"),rc.getString("prenom"),rc.getDate("naissance"),rc.getFloat("salaire"),rc.getString("sex"),rc.getInt("tel"),rc.getString("service"),rc.getString("description")));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ArrayList<Materiel> m=new ArrayList<>();
                try {
                    PreparedStatement rs1 = connection.prepareStatement("select ID,qteUsed,CONSOMABLE from MATERIEL,EVENMAT WHERE IDMAT=? and ID=DESIGNATION");
                    rs1.setString(1,rs.getString("idMat"));
                    ResultSet rc=rs1.executeQuery();
                    while(rc.next()){
                        m.add(new Materiel(rc.getString("ID"),rc.getInt("qteUsed"),rc.getString("CONSOMABLE")));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ArrayList<Engin> e=new ArrayList<>();
                try {
                    PreparedStatement rs1 = connection.prepareStatement("select ENGIN.id,type,dispo,marque from ENGIN,EVENENG WHERE IDMAT=? and ENGIN.ID=EVENENG.ID");
                    rs1.setString(1,rs.getString("idMat"));
                    ResultSet rc=rs1.executeQuery();
                    while(rc.next()){
                        e.add(new Engin(rc.getString("id"),rc.getString("type"),rc.getString("dispo"),rc.getString("marque")));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                oblist.add(new Evenement(rs.getString("idMat"),rs.getString("nom"),rs.getString("domaine"),rs.getString("volet"),rs.getDate("dateD"),rs.getDate("dateF"),rs.getString("gouvernerat"),rs.getString("delegation"),rs.getString("localisation"),rs.getString("description"),rs.getString("etat"),rs.getString("cheff"),p,m,e));
                NbInter++;
                if(rs.getString("etat")!=null){
                    if (rs.getString("etat").equals("Terminé")) {
                        NbTermine++;
                    } else if (rs.getString("etat").equals("EnCours")){
                        NbEnCours++;
                    } else if (rs.getString("etat").equals("Initial")){
                        NbInitial++;
                    } else if (rs.getString("etat").equals("Annulé")){
                        NbAnnule++;
                    }}
            }
            LabelNbInter.setText(String.valueOf(NbInter));
            LabelNbAnnule.setText(String.valueOf(NbAnnule));
            LabelNbEnCours.setText(String.valueOf(NbEnCours));
            LabelNbTermine.setText(String.valueOf(NbTermine));
            LabelNbInitial.setText(String.valueOf(NbInitial));
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        table_info.setItems(oblist);
        filter();
    }
    private void EtatColorTable() {
        loadData();
        col_etat.setCellFactory(new Callback<TableColumn<Evenement, String>,
                TableCell<Evenement, String>>()
        {
            @Override
            public TableCell<Evenement, String> call(
                    TableColumn<Evenement, String> param) {
                return new TableCell<Evenement, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (!empty) {
                            int currentIndex = indexProperty()
                                    .getValue() < 0 ? 0
                                    : indexProperty().getValue();
                            String clmStatus = param
                                    .getTableView().getItems()
                                    .get(currentIndex).getEtat();
//                            if (clmStatus != null) {
                            if (clmStatus.equals("Terminé")) {
                                setStyle("-fx-text-fill : #4CAF50");
                                setFont(Font.font("Impact", 20));
//                                setTextFill(Color.WHITE);
//                                setStyle("-fx-font-weight: bold");
//                                setStyle("-fx-background-color: #4CAF50");
                                setText(clmStatus);
                            } else if (clmStatus.equals("EnCours")) {
                                setStyle("-fx-text-fill : #FFC107");
                                setFont(Font.font("Impact", 20));
//                                setTextFill(Color.BLACK);
//                                setStyle("-fx-font-weight: bold");
//                                setStyle("-fx-background-color: #FFC107");
                                setText(clmStatus);
                            } else if (clmStatus.equals("Initial")) {
                                setStyle("-fx-text-fill : #26bfbc");
                                setFont(Font.font("Impact", 20));
//                                setStyle("-fx-font-weight: bold");
//                                setStyle("-fx-background-color: #48c7c7");
                                setText(clmStatus);
                            } else if (clmStatus.equals("Approuvé")) {
                                setStyle("-fx-text-fill : #03A9F4");
                                setFont(Font.font("Impact", 20));
//                                setTextFill(Color.BLACK);
//                                setStyle("-fx-font-weight: bold");
//                                setStyle("-fx-background-color: #03A9F4");
                                setText(clmStatus);
                            } else if (clmStatus.equals("Annulé")) {
                                setStyle("-fx-text-fill : #fb3232");
                                setFont(Font.font("Impact", 20));
//                                setTextFill(Color.WHITE);
//                                setStyle("-fx-font-weight: bold");
//                                setStyle("-fx-background-color: #fb3232");
                                setText(clmStatus);
                            }
                        } else {
                            setText(null);
                            setStyle(null);
                        }

                    }
                };
            }
        }); }
}
