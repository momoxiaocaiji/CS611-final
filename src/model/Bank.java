package model;

public class Bank {
    private int bankId;
    private String bankName;
    private String branchName;
    private int managerId;
    private String currency;
    private double balance;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Bank(int bankId, String bankName, String branchName, int managerId) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.branchName = branchName;
        this.managerId = managerId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "bankId=" + bankId +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", managerId=" + managerId +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                '}';
    }
}
