package sample.App.model;

import javafx.scene.control.CheckBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Revenu {

    private final String id ;
    private final String type;
    private final String prix;
    private final String dates;
    private final CheckBox cb ;

    private DateFormat formatD= DateFormat.getDateInstance(DateFormat.DEFAULT,
            new Locale("fr","FR"));

    public Revenu(String id, String type, String prix, Date dates) {
        this.id = id;
        this.type = type;
        this.prix = prix;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.dates = formatD.format(dates);
        this.cb = new CheckBox();
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getPrix() {
        return prix;
    }

    public String getDates() {
        return dates;
    }

    public CheckBox getCb() {
        return cb;
    }
}
