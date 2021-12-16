package model;

import java.util.Date;

public class Stock {

    protected int stockId;
    protected int open;
    protected double high;
    protected double low;
    protected String ticker;
    protected String stockName;
    protected double currentPrice;
    protected Date date;

    public Stock(int stockId, String ticker, String stockName, int open, double high, double low, double price, Date date) {
        this.stockId = stockId;
        this.open = open;
        this.high = high;
        this.low = low;
        this.ticker = ticker;
        this.stockName = stockName;
        this.currentPrice = price;
        this.date = date;
    }
    
    //overloading constructor
    public Stock(int stockId, String ticker, String stockName, double price, Date date) {
        this.stockId = stockId;
        this.ticker = ticker;
        this.stockName = stockName;
        this.currentPrice = price;
        this.date = date;
    }
    
    public Stock() {
    	
    }

    public int getStockId() {
        return stockId;
    }
    
    public void setStockId(int stockId) {
    	this.stockId = stockId;
    }
    
    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
    	this.open = open;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getPrice() {
        return currentPrice;
    }

    public void setPrice(double price) {
        this.currentPrice = price;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", ticker='" + ticker + '\'' +
                ", stockName='" + stockName + '\'' +
                ", price=" + currentPrice +
                ", open=" + open +
                ", date=" + date +
                '}';
    }
}
