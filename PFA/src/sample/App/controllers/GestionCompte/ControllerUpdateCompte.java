package sample.App.controllers.GestionCompte;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.App.model.Compte;
import sample.App.model.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class ControllerUpdateCompte implements Initializable {



    private String URL_Logo;


    @FXML
    private Label lbl ;

    @FXML
    private Label lbl2 ;

    @FXML
    private Label lbl1 ;

    @FXML
    private Label loulou ;


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


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Compte compte = null;
    private boolean update;
    String  compteId;


    public void confirmerButton (ActionEvent event) {
        {
            {
                String matricule = matriculeTextField.getText();
                String pass = passwordField.getText();



                if (notemptyPass(pass)) {

                    try {
                        Connection connection = getOracleConnection();
                        //String insertion = "insert into COMPTE  values (" + "\'" + matricule + "\'" + "," + "\'" + pass + "\'" + ")";
                        String updating = "update COMPTE set " +
                                "pass =" + "\'" + pass + "\' , image = "+"\'"+URL_Logo+"\'"+", role ="+"\'"+selRole.getValue()+"\'"+" where matricule = " + "\'" + compteId + "\'";

                        Statement statement = connection.createStatement();
                        statement.execute(updating);
                        statement.execute("commit");
                        System.out.println("parfaitement modifé");
                        lbl1.setText("");
                        lbl2.setText("");
                        lbl.setText("Modification avec succée");
                        Stage stage = (Stage) buttonConfirmer.getScene().getWindow();
                        // do what you have to do
                        stage.close();
                    } catch (SQLException e) {
                        System.out.println(e);
                        System.out.println("la carte d'identité déjà existe");
                        lbl2.setText("");
                        lbl1.setText("la carte d'identité déjà existe");
                    }

                }
                else{
                    System.out.println("verifier que le numéro carte d'indentité contient seulement 8 numéros");
                    lbl1.setText("");
                    lbl2.setText("Mot de Passe invalide");
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
            System.out.println(e);
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
            System.out.println(e);
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
    private void clean() {
        matriculeTextField.setText(null);
        passwordField.setText(null);
    }

    public void setTextField(String compteId, String matricule, String pass ,String role) {
        this.compteId = compteId ;
        this.passwordField.setText(pass);
        this.loulou.setText(matricule);
        selRole.setValue(role);
        try{
            Connection connection= getOracleConnection();
            Statement statement = connection.createStatement();
            String query = "select * from compte where matricule = "+"\'"+compteId+"\'";
            //System.out.println(query);
            //get data from db
            ResultSet rs = statement.executeQuery(query);
            //fetch data
            while(rs.next()){
                FileInputStream inputstream = new FileInputStream(rs.getString("image"));
                Image image = new Image(inputstream);

                logo.setImage(image);
            }
            rs.close();
        }
        catch (SQLException | FileNotFoundException e){
            //System.out.println("Erreur!!");
        }
    }

    public void setUpdate(boolean b) {
        this.update = b;

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
    }
}
