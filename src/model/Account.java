package model;

public class Account {
    private String accountNum;

    public Account(String accountNum) {
        this.accountNum = accountNum;
    }

    public Account() {
        //does nothing
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }


    @Override
    public String toString() {
        return "Account{" +
                "accountNum='" + accountNum + '\'' +
                '}';
    }
}
