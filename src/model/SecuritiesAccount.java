package model;

public class SecuritiesAccount extends Account{

    private String accountId;
    private String customerId;
    private double investmentAmount;
    private double realizedProfit=0;
    private double unrealizedProfit=0;
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
    
    public void buyStock(double price, int amount) {
    	//change inverstment amount
    	this.investmentAmount-=price*amount;
    }
    

    public void sellStock(double price, double profit, int amount) {
    	//change inverstment amount
    	this.investmentAmount+=price*amount;
    	//change realizedProfit
    	this.realizedProfit+=profit*amount;
    }
    
    public void setUnrealizedProfit(String customerId){
    	//TODO make sure the price of stock is updated
    	//get customerStock array list, for each of them, setUnrealized, getUnrealized
    	//double total += cStock.getUnrealized()
    	//this.unrealizedProfit = total;
    }

    public double getUnrealizedProfit() {
        return this.unrealizedProfit;
    }
    
    public double getRealizedProfit() {
        return this.unrealizedProfit;
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
