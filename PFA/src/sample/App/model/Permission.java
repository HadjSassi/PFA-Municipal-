package sample.App.model;

import javafx.scene.control.CheckBox;

public class Permission {

    private final String id ;
    private final String type ;
    private final String cin ;
    private final String nom ;
    private final String prenom ;
    private final String tel ;
    private final String mail ;
    private final String adr ;
    private final String description ;
    private final CheckBox check;

    public Permission(String id, String type, String cin, String nom, String prenom, String description , String tel ,String mail , String adr) {
        this.id = id;
        this.type = type;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.description = description;
        this.check = new CheckBox();
        this.tel = tel ;
        this.mail = mail;
        this.adr = adr ;
    }

    public String getId() {
        return id;
    }

    public String getTel() {
        return tel;
    }


    public String getMail() {
        return mail;
    }


    public String getAdr() {
        return adr;
    }

    public String getType() {
        return type;
    }

    public String getCin() {
        return cin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDescription() {
        return description;
    }

    public CheckBox getCheck() {
        return check;
    }
}
