package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Stock;
import model.CustomerOwnedStock;
import model.SecuritiesAccount;
import service.AccountService;
import service.StockService;

public class StockController {

	StockService stockService = new StockService();
	AccountService accountService = new AccountService();
	
	public ArrayList<Stock> getStockArrayList() throws SQLException, Exception {
    	ArrayList<Stock> stockArrayList = stockService.getStockArrayList();
		return stockArrayList;
	}

	public ArrayList<CustomerOwnedStock> getCustomerStockArrayList() throws SQLException, Exception {
    	ArrayList<CustomerOwnedStock> customerStockArrayList = stockService.getCustomerStockArrayList();
		return customerStockArrayList;
	}
	
	public int createStock(String ticker, String stockName, int open, double high, double low, double price, Date date) throws SQLException, Exception {
		return stockService.createStock(ticker, stockName, open, high, low, price, date);
	}
	
	public void removeStock(String ticker) throws SQLException, Exception {
		stockService.removeStock(ticker);
	}
	
	//TODO
	public void buyStock(String ticker, int quantity, int securitiesAccountId) throws SQLException, Exception {
		stockService.buyStock(stockService.getStock(ticker), quantity, accountService.getSecuritiesInfo(null), null);
	}
	
	//TODO
	public void sellStock() throws SQLException, Exception {
		stockService.sellStock(null, 0, null);
	}

}
