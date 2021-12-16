package model;

public class SecuritiesAccount extends Account{

    private String accountId;
    private String customerId;
    private double investmentAmount;
    private String currency="USD"; //keeping currency in USD by default

    /*
    public SecuritiesAccount(String accountNum, int accountId, int customerId, double investmentAmount, String currency) {
        super(accountNum);
        this.accountId = accountId;
        this.customerId = customerId;
        this.investmentAmount = investmentAmount;
        this.currency = currency;
    }
    */

    public SecuritiesAccount(String accountId, String customerId, double investmentAmount) {
    	super();
        this.accountId = accountId;
        this.customerId = customerId;
        this.investmentAmount = investmentAmount;
    }
    //Overloaded constructor in case of no parameter
    public SecuritiesAccount() {
    	super();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(double investmentAmount) {
        this.investmentAmount = investmentAmount;
    }
    
    @Override
    public String toString() {
        return "customer: "+customerId+"accountID: "+accountId+"amount: "+investmentAmount;
    }
}
