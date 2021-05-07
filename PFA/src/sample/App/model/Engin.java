package sample.App.model;


public class Engin {
    private final String Type ;
    private final String ID ;//Serie d'immatriculation
    private final String Dispo;//oui si diponible et non si non
    private final String Marque;//le nom du marque

    public Engin(String ID, String type, String dispo, String marque) {
        Type = type;
        this.ID = ID;
        Dispo = dispo;
        Marque = marque;
    }

    public String getType() {
        return Type;
    }

    public String getID() {
        return ID;
    }

    public String getDispo() {
        return Dispo;
    }

    public String getMarque() {
        return Marque;
    }


}
