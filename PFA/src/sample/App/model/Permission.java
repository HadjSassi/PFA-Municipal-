package sample.App.model;

import javafx.scene.control.CheckBox;

public class Permission {

    private final String id ;
    private final String type ;
    private final String cin ;
    private final String nom ;
    private final String prenom ;
    private final String description ;
    private final CheckBox check;

    public Permission(String id, String type, String cin, String nom, String prenom, String description) {
        this.id = id;
        this.type = type;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.description = description;
        this.check = new CheckBox();
    }

    public String getId() {
        return id;
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
