package sample.App.model;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.sql.Date;

public class Tache {

    private  String IdTache ;
    private  String NomTache;
    private  String DescriptionTache ;
    private  String DateDebut ;
    private  String DateFin ;
    private CheckBox check;
    Button update;
    Button modify;
    Button delete;

    public Tache(String IdTache, String NomTache, String descriptionTache, String dateDebut, String dateFin, String value){
        this.IdTache = new String(IdTache);
        this.NomTache = new String(NomTache);
        this.DescriptionTache = descriptionTache;
        this.DateDebut = dateDebut;
        this.DateFin = dateFin;
        this.modify = new Button("Modifier");
        this.check = new CheckBox();
    }

    public String getIdTache() {
        return IdTache;
    }

    public String getNomTache() {
        return NomTache;
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

    public String getDescriptionTache() {
        return DescriptionTache;
    }


    public void setIdTache(String idTache) {
        IdTache = idTache;
    }

    public void setNomTache(String nomTache) {
        NomTache = nomTache;
    }

    public void setDescriptionTache(String descriptionTache) {
        DescriptionTache = descriptionTache;
    }

    public void setDateDebut(String dateDebut) {
        DateDebut = dateDebut;
    }

    public void setDateFin(String dateFin) {
        DateFin = dateFin;
    }

    public String getDateDebut() {
        return DateDebut;
    }

    public String getDateFin() {
        return DateFin;
    }
}
