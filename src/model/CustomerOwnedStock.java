package model;

import java.util.Date;

public class CustomerOwnedStock extends Stock{

    private int quantity;
    private String customerId;
    private Date purchaseDate;
    private double purchasePrice;
    private double unrealizedProfit;

    public CustomerOwnedStock(int stockId, String ticker, String stockName, double price, Date date, int quantity, String customerId, Date purchaseDate, double purchasePrice) {
        super(stockId, ticker, stockName, price, date);
        this.quantity = quantity;
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
    }
    
    //overload
    public CustomerOwnedStock() {
    	super();
    }

    public void setUnrealizedProfit(Stock stock){
    	//make sure the price of stock is updated
    	this.unrealizedProfit = (this.purchasePrice-stock.getPrice())*this.quantity;
    }

    public double getUnrealizedProfit() {
        return this.unrealizedProfit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String string) {
        this.customerId = string;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    
    @Override
    public String toString() {
        return "CustomerOwnedStock{" +
                "stockId=" + stockId +
                ", quantity=" + quantity +
                ", customerId='" + customerId + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", purchasePrice=" + purchasePrice +
                '}';
    }
}
