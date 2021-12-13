package model;

public class BankConstants {
    private final String DBURL = "jdbc:mysql://127.0.0.1:3306/BankMod";
    private final String DBUSERNAME = "root";
    private final String DBPWD = "password";

    private final int SUCCESS_CODE = 200;
    private final int NOT_FOUND = 404;
    private final int SERVER_ERROR = 500;
    private final int ERROR = 400;
    private final int MANAGER_LOGIN = 201;

    public int getSUCCESS_CODE() {
        return SUCCESS_CODE;
    }

    public int getNOT_FOUND() {
        return NOT_FOUND;
    }

    public int getSERVER_ERROR() {
        return SERVER_ERROR;
    }

    public int getERROR() {
        return ERROR;
    }

    public int getMANAGER_LOGIN() {
        return MANAGER_LOGIN;
    }

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
