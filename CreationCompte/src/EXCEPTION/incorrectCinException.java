package EXCEPTION;

public class incorrectCinException extends Exception{
    public incorrectCinException(){
        super("Verifier que le numéro carte d'identité contient seulement 8 numéros");
    }
}
