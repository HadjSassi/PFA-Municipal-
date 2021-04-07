//version 1 with Date type


package sample.App.controllers.GestionTache.CreationTache;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.App.model.Compte;

import java.sql.*;
import java.time.LocalDate;

import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class ControllerCreationTache {

    @FXML
    private Button buttonConfirmer;

    @FXML
    private Button buttonFermer;

    @FXML
    private TextField IdTextField;

    @FXML
    private TextField NomTextField;

    @FXML
    private TextArea descArea;

    @FXML
    private DatePicker dateDebut;

    @FXML
    private DatePicker dateFin;

    @FXML
    private Label lblId;

    @FXML
    private Label lblNom;

    @FXML
    private Label lblDebut;

    @FXML
    private Label lblFin;

    @FXML
    public Label succeeLbl;

    private boolean isNdumber(String ch) {
        boolean test = true;
        for (int i = 0; i < ch.length(); i++)
            if (!(ch.charAt(i) > 0 && ch.charAt(i) > 9))
                test = false;
        return test;
    }

    public boolean isNumber(String name) {
        return name.matches("[0-9]+");
    }

    public boolean isContainSpaces(String ch) {
        return ch.matches("[a-zA-Z][a-zA-Z_ 0-9.]*");
    }

    private boolean verifier(String id, String nom, Date debut, Date fin) {
        try {
            boolean test = true;

            String symbole = "✓";
            //test ID
            if (id.length() == 0) {
                test = false;
                symbole = "🠔 Remplir ce champ";
            }

            if (id.length() != 4) {
                test = false;
                symbole = "🠔 4 chiffres exacte";
                //lblId.setText("🠔 4 chiffres exacte");
            }

            if (!isNumber(id)) {
                test = false;
                symbole = "🠔 Chiffres seulement";
            }

            lblId.setText(symbole);
            //test NOM
            symbole = "✓";

            if (nom.length() == 0) {
                test = false;
                symbole = "🠔 Remplir ce champ";
            }

            //test sur les espace
            if (!isContainSpaces(nom)) {
                test = false;
                symbole = "🠔 valeur incorrecte";
            }

            lblNom.setText(symbole);

            //test dateDebut
            if (String.valueOf(dateDebut.getValue()).length() != 10) {
                lblDebut.setText("🠔 Remplir ce champ");
                lblDebut.setStyle("-fx-text-fill: red");
                lblDebut.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
            } else {
                lblDebut.setStyle("-fx-text-fill: #32CD32");
                lblDebut.setText("✓");
                lblDebut.setStyle("-fx-background-color:transparent;");
            }

            //test dateFin
            if (String.valueOf(dateFin.getValue()).length() != 10) {
                lblFin.setText("🠔 Remplir ce champ");
                lblFin.setStyle("-fx-text-fill: red");
                lblFin.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
            } else {
                lblFin.setStyle("-fx-text-fill: #32CD32");
                lblFin.setText("✓");
                lblFin.setStyle("-fx-background-color:transparent;");
            }


            int diff = dateDebut.getValue().compareTo(dateFin.getValue());//diff : difference entre dateDebut & dateFin
            if (diff > 0) {
                test = false;
                lblFin.setText("durée invalide");
            }
            return test;
        } catch (NullPointerException e) {
            return false;
        }
    }


    public void confirmerButton(ActionEvent event) {
        {
            //lazem test 3lihom 9bal
            String id = IdTextField.getText();
            String nom = NomTextField.getText();
            String desc = descArea.getText();
            String dateDebutBase = String.valueOf(dateDebut.getValue());
            String dateFinBase = String.valueOf(dateFin.getValue());
            //lazem ta3mel if el debut m3ebya

            Date debut = Date.valueOf("2000-01-01"), fin = Date.valueOf("2000-01-01");

            try {
                if (Date.valueOf(dateDebut.getValue()).toString().length() != 0) {
                    debut = Date.valueOf(dateDebut.getValue());
                    //System.out.println(4 + debut.toString());
                }
                if (Date.valueOf(dateFin.getValue()).toString().length() != 0) {
                    fin = Date.valueOf(dateFin.getValue());
                    //System.out.println(5 + fin.toString());
                }
            } catch (NullPointerException e) {
                debut = Date.valueOf("2000-01-01");
                fin = Date.valueOf("2000-01-01");
            }

            if (verifier(id, nom, debut, fin)) {
                if (true) {
                    int ids = Integer.parseInt(id);

                    String dateDebutString = "";
                    String dateFinString = "";


                    try {
                        Connection connection = getOracleConnection();
                        String birth = String.valueOf(dateDebut.getValue());
                        //System.out.println(birdateDebut.);
                        String deb = convertType (Date.valueOf(dateDebut.getValue()) );
                        String fn =convertType(Date.valueOf(dateFin.getValue())) ;

                        System.out.println(deb + "    "+fn);
                        String insertion = "insert into TACHE  values (" + "\'" + id + "\'" + "," + "\'" + nom + "\'" + "," + "\'" + desc + "\'" + "," + "\'" +deb+ "\'" + "," + "\'" + fn + "\'" + ")";
                        //System.out.println(insertion);
                        Statement statement = connection.createStatement();
                        statement.execute(insertion);
                        statement.execute("commit");

                        System.out.println("Ajout avec succée");
                        succeeLbl.setText("Tache creer avec succée");

                        clean();
                    } catch (SQLException e) {
                        System.out.println("Erreur");
                    }

                } else {
                    System.out.println("id déjà existe");

                }
            } else {

                System.out.println("verifier que le numéro carte d'indentité contient seulement 8 numéros");

            }
        }

    }

    public void fermerButton(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) buttonFermer.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void setTextField(String idTache, String idTache1, String nomTache, String descriptionTache, String dateDebut, String dateFin) {
    }

    @FXML
    private void clean() {
        IdTextField.setText(null);
        NomTextField.setText(null);
        descArea.setText(null);
        dateDebut.setValue(null);
        dateFin.setValue(null);
    }

    public void handleClicksAjout(ActionEvent event) {
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


