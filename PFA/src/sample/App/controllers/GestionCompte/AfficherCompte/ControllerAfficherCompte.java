package sample.App.controllers.GestionCompte.AfficherCompte;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class ControllerAfficherCompte {

    private String compteId;

    @FXML
    private ImageView logo;

    @FXML
    private Button buttonFermer;

    @FXML
    private Label lblMatricule;

    @FXML
    private Label lblPass;



    public void setTextField(String compteId, String Matricule, String pass) {
        this.compteId = compteId ;
        this.lblPass.setText(pass);
        this.lblMatricule.setText(Matricule);
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

    public void fermerButton (ActionEvent event){
        // get a handle to the stage
        Stage stage = (Stage) buttonFermer.getScene().getWindow();
        // do what you have to do
        stage.close();
    }


}
