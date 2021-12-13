package model;

import java.util.Date;

public class CustomerOwnedStock extends Stock{

    private int quantity;
    private int customerId;
    private Date purchaseDate;
    private double purchasePrice;

    public CustomerOwnedStock(int stockId, String ticker, String stockName, double price, Date date, int quantity, int customerId, Date purchaseDate, double purchasePrice) {
        super(stockId, ticker, stockName, price, date);
        this.quantity = quantity;
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
                "quantity=" + quantity +
                ", customerId=" + customerId +
                ", purchaseDate=" + purchaseDate +
                ", purchasePrice=" + purchasePrice +
                '}';
    }
}
