package sample.GestionCompte.CreationCompte;

import EXCEPTION.incorrectCinException;
import EXCEPTION.isFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static OracleConnection.OracleConnection.getOracleConnection;

public class ControllerCreationCompte {


    public Button ouiButton;
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


    public void confirmerButton (ActionEvent event) {


        String cin = cinTextField.getText();
        String pass = passwordField.getText();

        if (verifier(cin)) {
            if (find(cin)) {

                int cins = Integer.parseInt(cin);

                try {
                    Connection connection = getOracleConnection();
                    String insertion = "insert into counts  values (" + cins + "," + "\'" + pass + "\'" + ")";

                    Statement statement = connection.createStatement();
                    statement.execute(insertion);
                    statement.execute("commit");

                    System.out.println("parfaitement ajouté");
                    lbl.setText("");
                    lbl2.setText("Ajout avec succée");
                } catch (SQLException e) {
                    System.out.println("1000000 dawa7");
                }


            }

            else {
                System.out.println("la carte d'identité déjà existe");
                lbl2.setText("");
                lbl.setText("la carte d'identité déjà existe");
            }
        }
        else{

            System.out.println("verifier que le numéro carte d'indentité contient seulement 8 numéros");
            lbl2.setText("");
            lbl.setText("Numéro carte d'indentité Invalide");
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


}
