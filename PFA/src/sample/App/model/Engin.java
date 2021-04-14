package sample.App.model;

import javafx.scene.control.CheckBox;

public class Engin {
    private final String Type ;
    private final String ID ;//Serie d'immatriculation
    private final String Dispo;//oui si diponible et non si non
    private final String Marque;//le nom du marque
    private final float prix ;
    private CheckBox check;

    public Engin(String ID, String type, String dispo, String marque , float prix) {
        Type = type;
        this.ID = ID;
        Dispo = dispo;
        Marque = marque;
        this.prix = prix ;
        this.check = new CheckBox();
    }

    public String getType() {
        return Type;
    }

    public String getID() {
        return ID;
    }

    public String getDispo() {
        return Dispo;
    }

    public String getMarque() {
        return Marque;
    }

    public float getPrix() {
        return prix;
    }

    public CheckBox getCheck() {
        return check;
    }

    public void setCheck(CheckBox check) {
        this.check = check;
    }

}
