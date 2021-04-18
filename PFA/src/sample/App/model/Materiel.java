package sample.App.model;

import javafx.scene.control.CheckBox;

public class Materiel {

    private final String id;
    private final String designation ;
    private final int qte ;
    private final String prix ;
    private final CheckBox cb ;

    public Materiel(String id, String designation, int qte, String price) {
        this.id = id;
        this.designation = designation;
        this.qte = qte;
        this.prix = price;
        this.cb = new CheckBox();
    }

    public String getId() {
        return id;
    }

    /*public static String getID() {
        return id;
    }*/

    public  String getDesignation() {
        return designation;
    }

    public int getQte() {
        return qte;
    }

    public final String getPrix() {
        return prix;
    }

    public CheckBox getCb() {
        return cb;
    }
}
