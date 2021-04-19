package sample.App.controllers.GestionDepense;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import sample.App.model.Depense;
import sample.App.model.Depense;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class ConsultatinoDepense implements Initializable {




    static public boolean  itsokay = false ;
    @FXML
    TableView<Depense> tableView;

    @FXML
    TableColumn<Depense,String> idCol ;
    @FXML
    TableColumn <Depense,String> typeCol ;
    @FXML
    TableColumn <Depense,String> datecol ;
    @FXML
    TableColumn <Depense,String> prixcol ;
    @FXML
    TableColumn <Depense,String> modifierCol ;

    @FXML
    private TableColumn<Depense, CheckBox> col_select;

    @FXML
    Button addButton ;

    @FXML
    Button deleteButton;

    @FXML
    Button refresh;


    @FXML
    private CheckBox check_selAll;

    @FXML
    private TextField filterField;

    @FXML
    private Label tot ;


    private sample.App.controllers.gInterfaceController it ;

    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet rs = null ;
    Depense depense = null ;

    ObservableList<Depense> items;
    ObservableList<Depense> oblist;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        loadData();
        filter();
    }


    private void filter(){
        FilteredList<Depense> filteredData = new FilteredList<>(oblist, b -> true);

        filterField.textProperty().addListener((observable ,oldValue, newValue)->{filteredData.setPredicate(Depense -> {

            if(newValue == null || newValue.isEmpty()){
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            if (Depense.getId().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter id
            }else if (Depense.getType().toString().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter type
            }else if (Depense.getDates().toString().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                return true;//filter type
            }else
                return false;//doesn't match
        });});
        SortedList<Depense> sortedData= new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }


    private void checkAll(){
        check_selAll.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                items = tableView.getItems();
                for (Depense item : items){
                    if(check_selAll.isSelected())
                        item.getCb().setSelected(true);
                    else
                        item.getCb().setSelected(false);
                    if(!item.getCb().isSelected()){
                        check_selAll.setSelected(false);
                    }

                }
            }
        });
    }



    private void initTable(){
        initCols();
        checkAll();
    }


    private void initCols(){

        typeCol.setCellValueFactory(
                new PropertyValueFactory<>("type")
        );
        datecol.setCellValueFactory(
                new PropertyValueFactory<>("dates")
        );


        //add cell of button edit
        Callback<TableColumn<Depense, String>, TableCell<Depense, String>> cellFoctory = (TableColumn<Depense, String> param) -> {
            // make cell containing buttons
            final TableCell<Depense, String> cell = new TableCell<Depense, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView viewIcon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        viewIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:linear-gradient(#0288D1 17%, #e7e5e5 100%);"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:linear-gradient(#4E342E 17%, #e7e5e5 100%);"
                        );
                        viewIcon.setOnMouseClicked((MouseEvent event) -> {

                            depense = tableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("../../view/depense/DepenseAfficher.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ConsultatinoDepense.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AfficherDepense addDepenseController = loader.getController();
                            addDepenseController.setTextField(depense.getId(), depense.getType(),depense.getPrix(),depense.getDates(),depense.getDescription());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.showAndWait();

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) ->{

                            depense = tableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("../../view/depense/DepenseUpdate.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ConsultatinoDepense.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            UpdateDepense addDepenseController = loader.getController();
                            addDepenseController.setTextField(depense.getId(), depense.getType(),depense.getPrix(),depense.getDates(),depense.getDescription());
                            Parent parent = loader.getRoot();

                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.showAndWait();
                            refresh();

                        });

                        HBox managebtn = new HBox(editIcon,viewIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(viewIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };
            return cell;
        };
        modifierCol.setCellFactory(cellFoctory);

        prixcol.setCellValueFactory(new PropertyValueFactory<>("prix"));

        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        col_select.setCellValueFactory(new PropertyValueFactory<>("cb"));
    }


    private void loadData(){
        double tt = 0;
        String t ;
        oblist = FXCollections.observableArrayList();
        try {
            Connection connection= getOracleConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from DEPENSE ");
            while(rs.next()){
                oblist.add(new Depense(rs.getString("id"),rs.getString("type"),rs.getString("prix"),rs.getDate("dates"),rs.getString("description")));
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tableView.setItems(oblist);

        try {
            connection= getOracleConnection();
            rs = connection.createStatement().executeQuery("select prix from depense ");
            while(rs.next()){
                t = rs.getString("prix");
                tt += Double.parseDouble(t);
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tot.setText(String.valueOf(tt));
    }

    @FXML
    public void ajouter (ActionEvent event)  {
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../../view/depense/DepenseCreation.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Municipal");
        assert root != null;
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.showAndWait();
        refresh();
    }

    @FXML
    public void refresh(ActionEvent event){
        loadData();
        filter();
    }

    @FXML
    public void refresh(){
        loadData();
        filter();
    }

    @FXML
    void supprimer(ActionEvent event) throws URISyntaxException {
        String s="";
        String s1 = null;
        for(Depense per:oblist){
            if(per.getCb().isSelected()){
                s+=per.getId()+"///";
                s1=per.getId();
            }}int so=0;
        AtomicBoolean del = new AtomicBoolean(true);
        for(Depense per:oblist){

            if(per.getCb().isSelected() && so==0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setHeaderText(null);
                ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(okButton, noButton);
                alert.setContentText("Etes-vous sure de supprimer cette depense  de n°: ///"+s);
                alert.setGraphic(new ImageView(getClass().getResource("../../../images/delete.png").toURI().toString() ));
                alert.showAndWait().ifPresent(type -> {
                    if (type == okButton) {
                        del.set(true);
                    } else if (type == noButton) {
                        del.set(false);
                    }
                });
                so++;
            }
            if(per.getCb().isSelected() && del.get()){
                try {
                    Connection connection= getOracleConnection();
                    connection.createStatement().executeQuery("delete from depense where "+"\'"+per.getId()+"\'"+"=ID");
                    connection.close();
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        loadData();
        check_selAll.setSelected(false);
    }




}