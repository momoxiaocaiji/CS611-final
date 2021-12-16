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

    private final double MINIMUM_BALANCE_FOR_INTEREST = 1000.0;
    private final int RATE_OF_INTEREST= 1;

    private final int IS_LOAN_APPROVED=1;

    private final int REJECT_LOAN_CODE = -1;

    private final int APPROVE_LOAN_CODE = 1;

    private final double TRANSACTION_CHARGE_RATE = 1;

    public double getTRANSACTION_CHARGE_RATE() {
        return TRANSACTION_CHARGE_RATE;
    }

    public int getREJECT_LOAN_CODE() {
        return REJECT_LOAN_CODE;
    }

    public int getAPPROVE_LOAN_CODE() {
        return APPROVE_LOAN_CODE;
    }

    public int getIS_LOAN_APPROVED() {
        return IS_LOAN_APPROVED;
    }

    public double getMINIMUM_BALANCE_FOR_INTEREST() {
        return MINIMUM_BALANCE_FOR_INTEREST;
    }

    public int getRATE_OF_INTEREST() {
        return RATE_OF_INTEREST;
    }

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
