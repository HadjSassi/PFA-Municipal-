package sample.App.model;

import javafx.scene.control.CheckBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Depense {

    private final String id ;
    private final String type;
    private final String prix;
    private final String dates;
    private final String description;
    private final CheckBox cb ;

    private DateFormat formatD= DateFormat.getDateInstance(DateFormat.DEFAULT,
            new Locale("fr","FR"));

    public Depense(String id, String type, String prix, Date dates,String desc) {
        this.id = id;
        this.type = type;
        this.prix = prix;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.dates = formatD.format(dates);
        this.cb = new CheckBox();
        this.description = desc;
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

    public String getDescription() {
        return description;
    }
}
