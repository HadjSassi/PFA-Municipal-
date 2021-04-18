package sample.App.model;

import javafx.scene.control.CheckBox;

public class Materiel {

    private final String id;
    private final String designation ;
    private final int qte ;
    private final CheckBox cb ;

    public Materiel(String id, String designation, int qte) {
        this.id = id;
        this.designation = designation;
        this.qte = qte;
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

    public CheckBox getCb() {
        return cb;
    }
}
