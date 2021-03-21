package sample.App.model;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.util.Date;

public class Personnel {
    private int id;
    private String nom;
    private String prenom;
    private int tel;
    private String service;
    private Date naissance;
    private String sex;
    private float salaire;
    private CheckBox check;

    Button update;
    Button modify;
    Button delete;



    public Personnel(int id, String nom, String prenom, int tel, String service, Date naissance, float salaire, String sex,String value) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.service = service;
        this.naissance = naissance;
        this.sex = sex;
        this.salaire = salaire;
        this.check = new CheckBox();
    }
    public CheckBox getCheck() {
        return check;
    }

    public void setCheck(CheckBox check) {
        this.check = check;
    }
    public int getId() {
        return id;
    }

    public void setId(int cin) {
        this.id = cin;
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

    public Date getNaissance() {
        return naissance;
    }

    public void setNaissance(Date naissance) {
        this.naissance = naissance;
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

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public Button getModify() {
        return modify;
    }

    public void setModify(Button modify) {
        this.modify = modify;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
