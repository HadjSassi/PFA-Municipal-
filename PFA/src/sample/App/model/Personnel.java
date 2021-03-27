package sample.App.model;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Personnel {
    private String matricule;
    private String cin;
    private String nom;
    private String prenom;
    private int tel;
    private String service;
    private String naissance;
    private String sex;
    private Float salaire;
    private CheckBox check;
    private String description;

    private DateFormat formatD= DateFormat.getDateInstance(DateFormat.DEFAULT,
            new Locale("fr","FR"));

    public Personnel(String matricule,String cin, String nom, String prenom,Date naissance, Float salaire, String sex, int tel, String service,String description ,String value) {
        this.matricule=matricule;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;this.tel = tel;
        this.service = service;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.naissance= formatD.format(naissance);
        this.sex = sex;
        this.salaire = salaire;
        this.tel = tel;
        this.service = service;
        this.description=description;
        this.check = new CheckBox();
    }
    public CheckBox getCheck() {
        return check;
    }

    public void setCheck(CheckBox check) {
        this.check = check;
    }
    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }



    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getNaissance() {
        return naissance;
    }

    public void setNaissance(Date naissance) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        this.naissance= formatter.format(naissance);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public Float getSalaire() {
        return salaire;
    }

    public void setSalaire(Float salaire) {
        this.salaire = salaire;
    }

}