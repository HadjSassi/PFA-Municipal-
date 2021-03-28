package sample.App.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class Compte {

    private final String matricule ;
    private final String pass;
    private CheckBox check;
    Button update;
    Button modify;
    Button delete;

    public Compte(String matricule, String pass,String value){
        this.matricule = new String(matricule);
        this.pass = new String(pass);
        this.modify = new Button("Modifier");
        this.check = new CheckBox();
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
