package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class Compte {

    private final SimpleStringProperty cin ;
    private final SimpleStringProperty pass;
    private Button modifier;
    private Button delete;


    public Compte(String cin, String pass){
        this.cin = new SimpleStringProperty(cin);
        this.pass = new SimpleStringProperty(pass);
        this.delete = new Button("Effacer");
        this.modifier = new Button("Modifier");
    }

    public String getCin() {
        return cin.get();
    }

    public SimpleStringProperty cinProperty() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin.set(cin);
    }

    public String getPass() {
        return pass.get();
    }

    public SimpleStringProperty passProperty() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }

    public Button getModifier() {
        return modifier;
    }

    public void setModifier(Button modifier) {
        this.modifier = modifier;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
