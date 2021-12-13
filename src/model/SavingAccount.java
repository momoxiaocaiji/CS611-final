package model;

import java.util.HashMap;
import java.util.Map;

public class SavingAccount extends Account{

    private String accountId;
    private int pin;
    private double amount;
    private String currency;
    private String customerId;
    private Map<String,Double> money;

    public SavingAccount(String accountNum, String accountId, int pin, double amount, String currency, String customerId) {
        super(accountNum);
        this.accountId = accountId;
        this.pin = pin;
        this.amount = amount;
        this.currency = currency;
        this.customerId = customerId;
        money = new HashMap<>();
        money.put(currency,amount);
    }

    public SavingAccount() {
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Map<String, Double> getMoney() {
        return money;
    }

    public void setMoney(Map<String, Double> money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "SavingAccount{" +
                "accountId='" + accountId + '\'' +
                ", pin=" + pin +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", customerId='" + customerId + '\'' +
                ", money=" + money +
                '}';
    }
}

