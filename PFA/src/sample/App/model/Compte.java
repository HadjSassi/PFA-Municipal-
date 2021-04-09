package sample.App.model;

import javafx.scene.control.CheckBox;

public class Compte {

    private final String matricule ;
    private final String pass;
    private final String image ;
    private CheckBox check;

    public Compte(String matricule, String pass,String value , String image){
        this.matricule = new String(matricule);
        this.pass = new String(pass);
        this.check = new CheckBox();
        this.image = image ;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getPass() {
        return pass;
    }

    public CheckBox getCheck() {
        return check;
    }

    public void setCheck(CheckBox check) {
        this.check = check;
    }

    public String getImage() {
        return image;
    }
}
