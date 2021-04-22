package sample.App.model;

import javafx.scene.control.CheckBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Doleance {


    private final String id ;
    private final String type;
    private final String nom ;
    private final String cin ;
    private final String tel ;
    private final String mail ;
    private final String adr ;
    private final String status;
    private final String dates;
    private final String description;
    private CheckBox check;


    private DateFormat formatD= DateFormat.getDateInstance(DateFormat.DEFAULT,
            new Locale("fr","FR"));

    public Doleance(String id, String type, String nom, String cin,String status,String description , String tel ,String mail , String adr, Date dates) {
        this.id = id;
        this.type = type;
        this.nom = nom;
        this.cin = cin;
        this.status= status;
        this.description = description;
        this.check = new CheckBox();
        this.tel = tel ;
        this.mail = mail;
        this.adr = adr ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.dates = formatD.format(dates);
    }

    public String getDescription() {
        return description;
    }


    public String getTel() {
        return tel;
    }


    public String getMail() {
        return mail;
    }


    public String getAdr() {
        return adr;
    }


    public  String getId() {
        return id;
    }


    public  String getType() {
        return type;
    }


    public  String getNom() {
        return nom;
    }


    public  String getCin() {
        return cin;
    }


    public String getStatus() {
        return status;
    }


    public CheckBox getCheck() {
        return check;
    }


    public void setCheck(CheckBox check) {
        this.check = check;
    }


    public String getDates() {
        return dates;
    }


}
