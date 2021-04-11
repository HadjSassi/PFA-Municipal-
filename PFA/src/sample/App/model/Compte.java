package sample.App.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class Compte {

    private Personnel per ;
    private final String pass;
    private final String image ;
    private final String role;
    private CheckBox check;

    public Compte(String matricule, String pass,String value , String image ,String role){
        this.pass = new String(pass);
        this.role = role;
        this.check = new CheckBox();
        this.image = image ;
        this.per = new Personnel(matricule);
    }

    public Personnel getPer() {
        return per;
    }

    public String getRole() {
        return role;
    }

    public String getMatricule() {
        return getPer().getMatricule();// tout simplement pour qu'on peut affecter la column du consultation compte du matricule à cette valeur
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
