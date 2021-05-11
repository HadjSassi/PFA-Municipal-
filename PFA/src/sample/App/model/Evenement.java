package sample.App.model;

import javafx.scene.control.CheckBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Evenement {
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



    public Evenement(String idI, String nom,String domaine,String volet, Date dateDeb, Date dateFin, String gouvernerat, String delegation, String localisation, String description, String etat, String cheff, ArrayList<Personnel> equipe, ArrayList<Materiel> materiel, ArrayList<Engin> vehicule) {
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
        this.domaine=domaine;
        this.volet=volet;
    }

    public String getCheff() {
        return cheff;
    }

    public void setCheff(String cheff) {
        this.cheff = cheff;
    }

    public ArrayList<Personnel> getEquipe() {
        return equipe;
    }

    public void setEquipe(ArrayList<Personnel> equipe) {
        this.equipe = equipe;
    }

    public ArrayList<Materiel> getMateriel() {
        return materiel;
    }

    public void setMateriel(ArrayList<Materiel> materiel) {
        this.materiel = materiel;
    }

    public ArrayList<Engin> getVehicule() {
        return vehicule;
    }

    public void setVehicule(ArrayList<Engin> vehicule) {
        this.vehicule = vehicule;
    }

    public String getGouvernerat() {
        return gouvernerat;
    }

    public void setGouvernerat(String gouvernerat) {
        this.gouvernerat = gouvernerat;
    }

    public String getDelegation() {
        return delegation;
    }

    public void setDelegation(String delegation) {
        this.delegation = delegation;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
