package model;

import java.util.Date;

public class Stock {

    public Stock(){}

    private int stockId;
    private String ticker;
    private String stockName;
    private double price;
    private Date date;

    public Stock(int stockId, String ticker, String stockName, double price, Date date) {
        this.stockId = stockId;
        this.ticker = ticker;
        this.stockName = stockName;
        this.price = price;
        this.date = date;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
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
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
