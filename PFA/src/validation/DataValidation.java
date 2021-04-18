package validation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DataValidation {

    public static boolean textDataLength(TextField inputTextField, Label inputLabel, String validationText, String requiredLength) {
        boolean rightDataLength = true;
        String validationString = null;

        //\b\w{8} & \ 'escapes' it.
        if (!inputTextField.getText().matches("\\b\\w" + "{" + requiredLength + "}")) {
            rightDataLength = false;
            validationString = validationText;

            inputLabel.setText(validationString);

        }

        return rightDataLength;


    }

    public static boolean dataLength(TextField inputTextField, Label inputLabel, String validationText, String requiredLength) {
        boolean isDataLength = true;
        String validationString = null;

        if (!inputTextField.getText().matches("\\b\\w" + "{" + requiredLength + "}")) {
            isDataLength = false;
            validationString = validationText;

        }
        inputLabel.setText(validationString);
        return isDataLength;

    }
    public static boolean textAlphabet(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isAlphabet = true;
        String validationString = null;

        if (!inputTextField.getText().matches("[a-z A-Z]+")) {
            isAlphabet = false;
            validationString = validationText;

        }
        inputLabel.setText(validationString);

        System.out.println(inputTextField.getText().matches("[a-z A-Z]"));
        return isAlphabet;

    }

    public static boolean textNumeric(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isNumeric = true;
        String validationString = null;

        if (!inputTextField.getText().matches("[0-9]+")) {
            isNumeric = false;
            validationString = validationText;

        }
        inputLabel.setText(validationString);
        return isNumeric;

    }

    public static boolean emailFormat(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isEmail = true;
        String validationString = null;

        if (!inputTextField.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            isEmail = false;
            validationString = validationText;

        }
        inputLabel.setText(validationString);
        return isEmail;

    }

    public static boolean addressFormat(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isPhone = true;
        String validationString = null;
        if (!inputTextField.getText().matches("^\\s*\\S+(?:\\s+\\S+)*")) {
            isPhone = false;
            validationString = validationText;

        }
        inputLabel.setText(validationString);
        return isPhone;

    }


    public static boolean phoneFormat(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isPhone = true;
        String validationString = null;
        String formatShort="^[0-9]{8}$";
        String formatFull="(^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$)";
        if (!inputTextField.getText().matches(formatShort)) {
            isPhone = false;
            validationString = validationText;

        }
        inputLabel.setText(validationString);
        return isPhone;

    }

    public static boolean nameFormat(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isPhone = true;
        String validationString = null;
        String shortFormat ="^[a-z ,.'-]+$";
        String longFormat="(?<FirstName>[A-Z]\\.?\\w*\\-?[A-Z]?\\w*)\\s?(?<MiddleName>[A-Z]\\w+|[A-Z]?\\.?)\\s(?<LastName>(?:[A-Z]\\w{1,3}|St\\.\\s)?[A-Z]\\w+\\-?[A-Z]?\\w*)(?:,\\s|)(?<Suffix>Jr\\.|Sr\\.|IV|III|II|)";

        if (!inputTextField.getText().matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")) {
            isPhone = false;
            validationString = validationText;

        }
        inputLabel.setText(validationString);
        return isPhone;

    }

    public static boolean zID(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isZID = true;
        String validationString = null;

        if (!inputTextField.getText().matches("\\z[0-9]{7}")) {
            isZID = false;
            validationString = validationText;

        }
        inputLabel.setText(validationString);

        return isZID;

    }

    //Regular Expressions: zMail: \z[0-9]{7}
    public static boolean textFieldIsNull(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isNull = false;
        String validationString = null;

        System.out.println("*******************************************************");

        //point out difference between null and isEmpty() *FIND OUT WHEN TO USE NULL
        if (inputTextField.getText().isEmpty()) {
            isNull = true;
            validationString = validationText;

        }
        String isEmpty = Boolean.toString(inputTextField.getText().isEmpty());
        String nil = Boolean.toString(inputTextField.getText() == null);

        inputLabel.setText(validationString);

        System.out.println("Label Should be Set to: " + validationString);
        System.out.println("Input TextField: " + inputTextField.getText());
        System.out.println("Null: " + nil + " isEmpty: " + isEmpty);

        return isNull;

    }

}