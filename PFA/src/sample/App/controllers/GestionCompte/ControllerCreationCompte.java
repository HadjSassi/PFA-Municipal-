package sample.App.controllers.GestionCompte;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.App.controllers.GestionCompte.ControllerConsulterCompte;
import sample.App.controllers.GestionPersonnel.gPerAddController;
import sample.App.model.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class ControllerCreationCompte implements Initializable {


    private String URL_Logo = "D:\\git\\PFA-Municipal-\\PFA\\src\\sample\\images\\icon.png";

    @FXML
    private Label lbl ;

    @FXML
    private Label lbl2 ;

    @FXML
    private Label lbl1 ;

    @FXML
    private ImageView logo;

    @FXML
    private Button btnselect_logo;

    @FXML
    private Button buttonConfirmer ;

    @FXML
    private Button buttonFermer;

    @FXML
    private TextField matriculeTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> selRole;


    String  compteId;


    public void confirmerButton (ActionEvent event) throws URISyntaxException, FileNotFoundException {
        {
            {
                String matricule = matriculeTextField.getText();
                String pass = passwordField.getText();

                if (verifier(matricule) ) {
                    if (notemptyPass(pass)) {
                        if (!NonReserve(matricule)) {
                            if (find(matricule)) {
                                try {
                                    Connection connection = getOracleConnection();
                                    String insertion = "insert into COMPTE  values (" + "\'" + matricule + "\'" + "," + "\'" + pass + "\'" + ","+"\'"+URL_Logo+"\'"+","+"\'"+selRole.getValue()+"\'"+")";
                                    System.out.println(insertion);
                                    Statement statement = connection.createStatement();
                                    statement.execute(insertion);
                                    statement.execute("commit");

                                    System.out.println("parfaitement ajout??");
                                    lbl2.setText("");
                                    lbl1.setText("");
                                    lbl.setText("Ajout avec succ??e");
                                    clean();
                                } catch (SQLException e) {
                                    System.out.println(e);
                                }
                            }else{
                                System.out.println("la matricule d??j?? existe");
                                lbl2.setText("");
                                lbl1.setText("la matricule d??j?? existe");
                            }

                        } else {
                            System.out.println("la matricule n'existe pas dans la table personnel");
                            lbl2.setText("");
                            lbl1.setText("Aucun personnel avec cette matricule");

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.initStyle(StageStyle.UNDECORATED);
                            alert.setHeaderText(null);
                            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                            alert.getButtonTypes().setAll(okButton, noButton);
                            alert.setContentText("Aucun personnel avec cette matricule\nVoulez vous ajouter un personnel de ce compte");
                            alert.setGraphic(new ImageView(getClass().getResource("../../../images/info4.png").toURI().toString() ));
                            alert.showAndWait().ifPresent(type -> {
                                if (type == okButton) {
                                    FXMLLoader loader2 = new FXMLLoader ();
                                    loader2.setLocation(getClass().getResource("../../view/gPerAdd.fxml"));
                                    try {
                                        loader2.load();
                                    } catch (IOException ex) {
                                        Logger.getLogger(ControllerConsulterCompte.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    gPerAddController addper = loader2.getController();
                                    addper.getMatriculeField().setText(matriculeTextField.getText());

                                    Parent parent2 = loader2.getRoot();

                                    Stage stage2 = new Stage();
                                    stage2.initModality(Modality.APPLICATION_MODAL);
                                    stage2.setScene(new Scene(parent2));
                                    stage2.initStyle(StageStyle.UNDECORATED);
                                    stage2.showAndWait();

                                }
                            });
                        }
                    }
                    else {
                        System.out.println("passe invalide");
                        lbl1.setText("");
                        lbl2.setText("Mot de Passe invalide");
                    }
                }
                else {

                    System.out.println("verifier que le num??ro carte d'indentit?? contient seulement 8 num??ros");
                    lbl2.setText("");
                    lbl1.setText("Matricule doit ??tre 8 num??ro");
                }
            }
        }

    }

    private boolean verifier(String matricule){


        boolean b = false ;

        boolean isNumeric =  matricule.matches("[+-]?\\d*(\\.\\d+)?");


        if (matricule.length() == 8) {
            b = true ;
            for (int i = 0; i < matricule.length(); i++) {

                if (!isNumeric)
                    b = false ;

            }

        }
        return b;
    }

    private boolean notemptyPass (String pass){
        return (!pass.isEmpty());
    }

    private boolean find (String matricule){

        boolean notfound = true ;

        try{
            Connection connection= getOracleConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select matricule from COMPTE");

            while(rs.next() && notfound){
                String matricules = rs.getString("matricule");
                if (matricule.equals(matricules)){
                    notfound = false ;
                }
            }

        }
        catch (SQLException e){
            notfound = false ;
            System.out.println("erreur");
        }
        return notfound ;

    }

    private boolean NonReserve (String matricule){

        boolean notfound = true ;

        try{
            Connection connection= getOracleConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select matricule from PERSONNEL");

            while(rs.next() && notfound){
                String matricules = rs.getString("MATRICULE");
                if (matricule.equals(matricules)){
                    notfound = false ;
                }
            }

        }
        catch (SQLException e){
            notfound = false ;
            System.out.println("erreur");
        }
        return notfound ;

    }

    public void fermerButton (ActionEvent event){
        // get a handle to the stage
        Stage stage = (Stage) buttonFermer.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void clean() throws FileNotFoundException {
        matriculeTextField.setText(null);
        passwordField.setText(null);
        try{
            Connection connection= getOracleConnection();
            Statement statement = connection.createStatement();
            String query = "select * from settings where id = 1";
            //get data from db
            ResultSet rs = statement.executeQuery(query);
            //fetch data
            while(rs.next()){
                FileInputStream inputstream = new FileInputStream(rs.getString("logo"));
                Image image = new Image(inputstream);

                logo.setImage(image);
            }
            rs.close();
        }
        catch (SQLException | FileNotFoundException e){
            //System.out.println("Erreur!!");
        }
    }

    @FXML
    public void cmdSelectLogo(ActionEvent event) throws IOException, SQLException {
        FileChooser fc = new FileChooser();

        File selectedDFile = (File) fc.showOpenDialog(null);

        FileInputStream inputstream = new FileInputStream(selectedDFile.getAbsolutePath());
        Image image = new Image(inputstream);

        //System.out.println(selectedDFile.getAbsolutePath());
        URL_Logo=selectedDFile.getAbsolutePath();
        logo.setImage(image);
    }

    ObservableList list= FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.removeAll();
        list.addAll(Service.Administration.toString(),Service.Direction.toString(),Service.Financier.toString());
        selRole.getItems().setAll(list);
        selRole.setValue(Service.Administration.toString());
    }
}
