package sample.App.model;

import javafx.scene.control.CheckBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Intervention {
    private String idI;
    private String nom;
    private String dateDeb;
    private String dateFin;
    private String etat;
    private String cheff;
    private ArrayList<Personnel> equipe;
    private ArrayList<Materiel> materiel;
    private ArrayList<Engin> vehicule;
    private String gouvernerat;
    private String delegation;
    private String localisation;
    private String description;
    private String domaine;
    private String volet;
    private CheckBox check;



    public Intervention(String idI, String nom,String domaine,String volet, Date dateDeb, Date dateFin, String gouvernerat, String delegation, String localisation, String description, String etat, String cheff, ArrayList<Personnel> equipe, ArrayList<Materiel> materiel, ArrayList<Engin> vehicule) {
        this.idI = idI;
        this.nom = nom;
        this.dateDeb = formatD.format(dateDeb);
        this.dateFin = formatD.format(dateFin);
        this.etat = etat;
        this.cheff = cheff;
        this.equipe = equipe;
        this.materiel = materiel;
        this.vehicule = vehicule;
        this.gouvernerat = gouvernerat;
        this.delegation = delegation;
        this.localisation = localisation;
        this.description = description;
        this.check = new CheckBox();
        this.domaine=domaine;
        this.volet=volet;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getVolet() {
        return volet;
    }

    public void setVolet(String volet) {
        this.volet = volet;
    }

    private DateFormat formatD= DateFormat.getDateInstance(DateFormat.DEFAULT,
            new Locale("fr","FR"));
    public CheckBox getCheck() {
        return check;
    }

    public void setCheck(CheckBox check) {
        this.check = check;
    }
    public String getIdI() {
        return idI;
    }

    public void setIdI(String idI) {
        this.idI = idI;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(String dateDeb) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        this.dateDeb = formatter.format(dateDeb);
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        this.dateFin = formatter.format(dateFin);
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
