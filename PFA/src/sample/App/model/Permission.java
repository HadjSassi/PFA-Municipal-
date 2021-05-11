package sample.App.model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Permission {

    private final String id ;
    private final String type ;
    private final String cin ;
    private final String nom ;
    private final String prenom ;
    private final String tel ;
    private final String mail ;
    private final String adr ;
    private final String status;
    private final String dates;
    private final String description ;



    private DateFormat formatD= DateFormat.getDateInstance(DateFormat.DEFAULT,
            new Locale("fr","FR"));


    public Permission(String id, String type, String cin, String nom, String prenom, String description , String tel ,String mail , String adr, Date dates,String status) {
        this.id = id;
        this.type = type;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.description = description;
        this.tel = tel ;
        this.mail = mail;
        this.status= status;
        this.adr = adr ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.dates = formatD.format(dates);
    }

    public String getId() {
        return id;
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

    public String getType() {
        return type;
    }

    public String getCin() {
        return cin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getDates() {
        return dates;
    }
}
