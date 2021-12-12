package model;

import java.util.Date;

public class Transaction {

    private int transactionId;
    private int customerId;
    private String transactionType;
    private double amount;
    private String currency;
    private Date date;
    private String sourceAccountId;
    private String destinationAccountId;

    public Transaction(int transactionId, int customerId, String transactionType, double amount, String currency, Date date, String sourceAccountId, String destinationAccountId) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(String destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }
}
