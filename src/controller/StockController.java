package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Stock;
import model.Customer;
import model.CustomerOwnedStock;
import model.SecuritiesAccount;
import service.AccountService;
import service.LoginService;
import service.StockService;

public class StockController {

	StockService stockService = new StockService();
	AccountService accountService = new AccountService();
	LoginService loginService = new LoginService();
	
	public ArrayList<Stock> getStockArrayList() throws SQLException, Exception {
    	ArrayList<Stock> stockArrayList = stockService.getStockArrayList();
		return stockArrayList;
	}

	public ArrayList<CustomerOwnedStock> getCustomerStockArrayList(String customerId) throws SQLException, Exception {
    	ArrayList<CustomerOwnedStock> customerStockArrayList = stockService.getCustomerStockArrayList(customerId);
		return customerStockArrayList;
	}
	
	public int createStock(String ticker, String stockName, int open, double high, double low, double price, Date date) throws SQLException, Exception {
		return stockService.createStock(ticker, stockName, open, high, low, price, date);
	}
	
	public void removeStock(String ticker) throws SQLException, Exception {
		stockService.removeStock(ticker);
	}
	
	//TODO
	public void buyStock(String ticker, int quantity, String customerId) throws SQLException, Exception {
		Customer customer = loginService.getCustomerDetails(customerId);
    	java.util.Date javaDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
		stockService.buyStock(stockService.getStock(ticker), quantity, accountService.getSecuritiesInfo(customer), sqlDate);
	}
	
	//TODO
	public void sellStock(Stock s, int quantity, SecuritiesAccount securitiesAccount) throws SQLException, Exception {
		CustomerOwnedStock cStock = getCustomerStock(s.getStockName(), securitiesAccount.getCustomerId());
		stockService.sellStock(cStock, quantity, securitiesAccount);
	}

	public CustomerOwnedStock getCustomerStock(String ticker, String customerId) throws Exception, SQLException {
		return stockService.getCustomerStock(ticker, customerId);
	}

}
