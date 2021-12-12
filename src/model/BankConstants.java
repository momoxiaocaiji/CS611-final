package model;

public class BankConstants {
    private final String DBURL = "jdbc:mysql://127.0.0.1:3306/Bank";
    private final String DBUSERNAME = "root";
    private final String DBPWD = "password";

    public BankConstants() {
    }

    public String getDBURL() {
        return DBURL;
    }

    public String getDBUSERNAME() {
        return DBUSERNAME;
    }

    public String getDBPWD() {
        return DBPWD;
    }
}
