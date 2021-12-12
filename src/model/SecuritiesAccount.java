package model;

public class SecuritiesAccount extends Account{

    private int accountId;
    private int customerId;
    private double investmentAmount;
    private String currency="USD"; //keeping currency in USD by default

    public SecuritiesAccount(String accountNum, int accountId, int customerId, double investmentAmount, String currency) {
        super(accountNum);
        this.accountId = accountId;
        this.customerId = customerId;
        this.investmentAmount = investmentAmount;
        this.currency = currency;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(double investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
