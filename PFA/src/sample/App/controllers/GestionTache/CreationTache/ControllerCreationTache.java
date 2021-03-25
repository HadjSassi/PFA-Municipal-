package sample.App.controllers.GestionTache.CreationTache;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.App.model.Compte;

import java.sql.*;

import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class ControllerCreationTache {

    @FXML
    private Label lbl ;

    @FXML
    private Label lbl2 ;

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

                if (verifier(cin) && notemptyPass(pass) ) {
                    if (find(cin)) {

                        int cins = Integer.parseInt(cin);

                        try {
                            Connection connection = getOracleConnection();
                            String insertion = "insert into COUNTS  values (" + "\'" + cin + "\'" + "," + "\'" + pass + "\'" + ")";

                            Statement statement = connection.createStatement();
                            statement.execute(insertion);
                            statement.execute("commit");

                            System.out.println("parfaitement ajouté");
                            lbl.setText("");
                            lbl2.setText("Ajout avec succée");
                            clean();
                        } catch (SQLException e) {
                            System.out.println("1000000 dawa7");
                        }


                    } else {
                        System.out.println("la carte d'identité déjà existe");
                        lbl2.setText("");
                        lbl.setText("la carte d'identité déjà existe");
                    }
                } else {

                    System.out.println("verifier que le numéro carte d'indentité contient seulement 8 numéros");
                    lbl2.setText("");
                    lbl.setText("Donnée Invalide");
                }
            }
        }
        else{
            {
                String cin = cinTextField.getText();
                String pass = passwordField.getText();

                if (verifier(cin) && notemptyPass(pass)) {
                    if (true) {

                        int cins = Integer.parseInt(cin);

                        try {
                            Connection connection = getOracleConnection();
                            //String insertion = "insert into COUNTS  values (" + "\'" + cin + "\'" + "," + "\'" + pass + "\'" + ")";
                            String updating = "update counts set " +
                                    "cin = "+"\'"+cin+"\'," +
                                    "pass ="+"\'"+pass+"\' where cin = "+"\'"+compteId+"\'";

                            Statement statement = connection.createStatement();
                            statement.execute(updating);
                            statement.execute("commit");

                            System.out.println("parfaitement modifé");
                            lbl.setText("");
                            lbl2.setText("Modification avec succée");
                            clean();
                        } catch (SQLException e) {
                            System.out.println("1000000 dawa7");
                        }


                    } else {
                        System.out.println("la carte d'identité déjà existe");
                        lbl2.setText("");
                        lbl.setText("la carte d'identité déjà existe");
                    }
                } else {

                    System.out.println("verifier que le numéro carte d'indentité contient seulement 8 numéros");
                    lbl2.setText("");
                    lbl.setText("Donnée Invalide");
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

    public void setTextField(String idTache, String compteId, String cin, String pass, Date dateDebut, Date dateFin) {
        this.compteId = compteId ;
        this.passwordField.setText(pass);
        this.cinTextField.setText(cin);
    }

    public void setUpdate(boolean b) {
        this.update = b;

    }

}
