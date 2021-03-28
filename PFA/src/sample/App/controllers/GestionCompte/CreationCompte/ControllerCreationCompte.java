package sample.App.controllers.GestionCompte.CreationCompte;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.App.model.Compte;

import java.sql.*;
import java.time.LocalDate;

import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class ControllerCreationCompte {

    @FXML
    private Label lbl ;

    @FXML
    private Label lbl2 ;

    @FXML
    private Label lbl1 ;



    @FXML
    private Button buttonConfirmer ;

    @FXML
    private Button buttonFermer;

    @FXML
    private TextField cinTextField;

    @FXML
    private PasswordField passwordField;


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Compte compte = null;
    private boolean update;
    String  compteId;


    public void confirmerButton (ActionEvent event) {
        if (update == false) {
            {
                String cin = cinTextField.getText();
                String pass = passwordField.getText();

                if (verifier(cin) ) {
                    if (notemptyPass(pass)) {
                        if (find(cin)) {


                            try {
                                Connection connection = getOracleConnection();
                                String insertion = "insert into COUNTS  values (" + "\'" + cin + "\'" + "," + "\'" + pass + "\'" + ")";

                                Statement statement = connection.createStatement();
                                statement.execute(insertion);
                                statement.execute("commit");

                                System.out.println("parfaitement ajouté");
                                lbl2.setText("");
                                lbl1.setText("");
                                lbl.setText("Ajout avec succée");
                                clean();
                            } catch (SQLException e) {
                                System.out.println("1000000 dawa7");
                            }


                        } else {
                            System.out.println("la matricule déjà existe");
                            lbl2.setText("");
                            lbl1.setText("la matricule déjà existe");
                        }
                    }
                    else {
                        System.out.println("passe invalide");
                        lbl1.setText("");
                        lbl2.setText("Mot de Passe invalide");
                    }
                }
                else {

                    System.out.println("verifier que le numéro carte d'indentité contient seulement 8 numéros");
                    lbl2.setText("");
                    lbl1.setText("Matricule doit être 8 numéro");
                }
            }
        }
        else{
            {
                String cin = cinTextField.getText();
                String pass = passwordField.getText();
                String cns = cin ;

                if (verifier(cin)) {
                    if (notemptyPass(pass)) {
                        if (true) {

                            try {
                                Connection connection = getOracleConnection();
                                //String insertion = "insert into COUNTS  values (" + "\'" + cin + "\'" + "," + "\'" + pass + "\'" + ")";
                                String updating = "update counts set " +
                                        "cin = " + "\'" + cin + "\'," +
                                        "pass =" + "\'" + pass + "\' where cin = " + "\'" + compteId + "\'";

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
                                System.out.println("1000000 dawa7");
                                System.out.println("la carte d'identité déjà existe");
                                lbl2.setText("");
                                lbl1.setText("la carte d'identité déjà existe");
                            }

                        }
                        else{
                            System.out.println("la carte d'identité déjà existe");
                            lbl2.setText("");
                            lbl1.setText("la carte d'identité déjà existe");
                        }
                    } else {

                        System.out.println("verifier que le numéro carte d'indentité contient seulement 8 numéros");
                        lbl1.setText("");
                        lbl2.setText("Mot de Passe invalide");
                    }
                }
                else{
                    System.out.println("verifier que le numéro carte d'indentité contient seulement 8 numéros");
                    lbl2.setText("");
                    lbl1.setText("Matricule doit être 8 numéro");
                }
            }

        }
    }

    private boolean verifier(String cin){


        boolean b = false ;

        boolean isNumeric =  cin.matches("[+-]?\\d*(\\.\\d+)?");


        if (cin.length() == 8) {
            b = true ;
            for (int i = 0; i < cin.length(); i++) {

                if (!isNumeric)
                    b = false ;

            }

        }
        return b;
    }

    private boolean notemptyPass (String pass){
        return (!pass.isEmpty());
    }

    private boolean find (String cin){

        boolean notfound = true ;

        try{
            Connection connection= getOracleConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select CIN from COUNTS");

            while(rs.next() && notfound){
                String cins = rs.getString("CIN");
                if (cin.equals(cins)){
                    notfound = false ;
                }
            }

        }
        catch (SQLException e){
            notfound = false ;
            System.out.println("1000000 dawa7");
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
        cinTextField.setText(null);
        passwordField.setText(null);
    }

    public void setTextField(String compteId, String cin, String pass) {
        this.compteId = compteId ;
        this.passwordField.setText(pass);
        this.cinTextField.setText(cin);
    }

    public void setUpdate(boolean b) {
        this.update = b;

    }

}
