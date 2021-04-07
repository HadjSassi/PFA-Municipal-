package sample.App.controllers.GestionTache.UpdateTache;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.App.model.Compte;

import java.sql.*;
import java.time.LocalDate;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class ControllerUpdateTache {

    @FXML
    private Label idLbl;

    @FXML
    private Label idLabel;

    @FXML
    private Label nomLbl;

    @FXML
    private Label dateDebutLbl;

    @FXML
    private Label dateFinLbl;

    @FXML
    private Button buttonConfirmer ;

    @FXML
    private Button buttonFermer;

    @FXML
    private TextField nomField;

    @FXML
    private DatePicker dateDebutField;

    @FXML
    private DatePicker dateFinField;

    @FXML
    private TextArea descArea;


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Compte compte = null;
    private boolean update;
    String  compteId;

    //Statement statement;
    //ResultSet rs = statement.executeQuery("select matricule from PERSONNEL");




    public void confirmerButton (ActionEvent event) {
        {
            {
                String id = idLbl.getText();
                String nom = nomField.getText();
                String dateDebut = dateDebutField.toString();
                String dateFin = dateFinField.toString();
                String desc = descArea.getText();

                String query = "update tache set NOMTACHE = "+"\'"+nom+"\'"+", DESCRIPTION = "+"\'"+desc+"\'"+", DEBUT =  "+"\'"+convertType(Date.valueOf(dateDebutField.getValue()))+"\'"+", FIN = "+"\'"+convertType(Date.valueOf(dateFinField .getValue()))+"\'"+" where IDTACHE = "+"\'"+id+"\'";

                //System.out.println(query);
                if (notEmpty(nom, dateDebut, dateFin)) {
                    try {
                        Connection connection = getOracleConnection();

                        Statement statement = connection.createStatement();

                        statement.executeUpdate(query);

                        statement.execute("commit");
                        System.out.println("parfaitement modifé");
                        idLabel.setText("Modification avec succée");

                        Stage stage = (Stage) buttonConfirmer.getScene().getWindow();
                        // do what you have to do
                        stage.close();
                    } catch (SQLException e) {

                        System.out.println("erreur");
                        System.out.println("ID déjà existe");
                        nomLbl.setText("");
                    }
                } else {
                    Boolean test = true;

                    if (nom.isEmpty())
                        nomLbl.setText("🠔 Remplir ce champ");

                    if (nom.isEmpty())
                        dateDebutLbl.setText("🠔 Remplir ce champ");

                    if (nom.isEmpty())
                        dateFinLbl.setText("🠔 Remplir ce champ");
                }
            }
        }
    }

//    private boolean verifier(String matricule){
//
//
//        boolean b = false ;
//
//        boolean isNumeric =  matricule.matches("[+-]?\\d*(\\.\\d+)?");
//
//
//        if (matricule.length() == 8) {
//            b = true ;
//            for (int i = 0; i < matricule.length(); i++) {
//
//                if (!isNumeric)
//                    b = false ;
//
//            }
//
//        }
//        return b;
//    }

    private boolean notEmpty(String nom, String dateDebut, String dateFin){
        return (!(nom.isEmpty() && dateDebut.isEmpty() && dateFin.isEmpty()));
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
            System.out.println("1000000 dawa7");
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
            System.out.println("Erreur");
        }
        return notfound ;

    }

    public void fermerButton (ActionEvent event){
        // get a handle to the stage
        Stage stage = (Stage) buttonFermer.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public Button getButtonFermer() {
        return buttonFermer;
    }

    public void setButtonFermer(Button buttonFermer) {
        this.buttonFermer = buttonFermer;
    }


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }


    @FXML
    private void clean() {
        idLbl.setText(null);
    }


    public void setTextField(String idTache,String nom,String desc,String debut,String fin) {
        this.idLbl.setText(idTache);
        this.nomField.setText(nom);
        this.descArea.setText(desc);

        String a = ""+debut.charAt(6)+debut.charAt(7)+debut.charAt(8)+debut.charAt(9);
        String m = ""+debut.charAt(3)+debut.charAt(4);
        String j = ""+debut.charAt(0)+debut.charAt(1);
        debut = a+"-"+j+"-"+m;
        LocalDate dateTime = LocalDate.parse(debut);
        this.dateDebutField.setValue(dateTime);

         a = ""+fin.charAt(6)+fin.charAt(7)+fin.charAt(8)+fin.charAt(9);
         m = ""+fin.charAt(3)+fin.charAt(4);
         j = ""+fin.charAt(0)+fin.charAt(1);
        fin = a+"-"+j+"-"+m;
        dateTime = LocalDate.parse(fin);
        this.dateFinField.setValue(dateTime);

    }


    private String convertType(Date d) {
        String jour = "";
        String mois = "";
        String anne = "";
        String date = "";

        date = d.toString();//"aaaa-mm-jj
        anne = ""+date.charAt(0)+date.charAt(1)+date.charAt(2)+date.charAt(3);
        mois =""+ date.charAt(5)+date.charAt(6);
        jour =""+ date.charAt(8)+date.charAt(9);

        date = mois + "/"+jour+"/"+anne ;
        return date;
    }
}
