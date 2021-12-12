package model;

public class CheckingAccount extends Account{

    private String accountId;
    private int pin;
    private double amount;
    private String currency;
    private int customerId;

    public CheckingAccount(String accountNum, String accountId, int pin, double amount, String currency, int customerId) {
        super(accountNum);
        this.accountId = accountId;
        this.pin = pin;
        this.amount = amount;
        this.currency = currency;
        this.customerId = customerId;
    }

    public CheckingAccount() {
        super();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    @Override
    public String toString() {
        return "CheckingAccount{" +
                "accountId='" + accountId + '\'' +
                "accountNum='"+getAccountNum()+'\''+
                ", pin=" + pin +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}
