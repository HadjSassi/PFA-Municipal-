package sample.App.model;

import javafx.scene.control.CheckBox;

public class Materiel {

    private final String designation ;
    private final int qte ;
    private final String consom ;

    public Materiel(String designation, int qte, String consom) {
        this.designation = designation;
        this.qte = qte;
        this.consom = consom;
    }
    public  String getDesignation() {
        return designation;
    }

    public int getQte() {
        return qte;
    }

    public String getConsom() {
        return consom;
    }
}
