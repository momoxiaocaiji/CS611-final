package model;

import java.util.Arrays;

public class CustAccounts {

    private int customerId;
    private CheckingAccount checkingAccount;
    private SavingAccount savingAccount;
    private int accountNum;


    public CustAccounts(int customerId, CheckingAccount checkingAccount, SavingAccount savingAccount, int accountNum) {
        this.customerId = customerId;
        this.checkingAccount = checkingAccount;
        this.savingAccount = savingAccount;
        this.accountNum = accountNum;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(CheckingAccount checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    public SavingAccount getSavingAccount() {
        return savingAccount;
    }

    public void setSavingAccount(SavingAccount savingAccount) {
        this.savingAccount = savingAccount;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(int accountNum) {
        this.accountNum = accountNum;
    }

    @Override
    public String toString() {
        return "CustAccounts{" +
                "customerId=" + customerId +
                ", checkingAccount=" + checkingAccount +
                ", savingAccount=" + savingAccount +
                ", accountNum=" + accountNum +
                '}';
    }
}
