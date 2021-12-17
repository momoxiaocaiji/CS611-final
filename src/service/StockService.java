package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;
import model.BankConstants;
import model.Customer;
import model.CustomerOwnedStock;
import model.SecuritiesAccount;
import model.Stock;
import controller.DbController;

public class StockService {

    DbController dbController = new DbController();
    BankConstants bankConstants = new BankConstants();
    
    /**
     * getStockArrayList
     * use while(resultSet.next())
     * 
     * @return ArrayList<Stock>
     */
    public ArrayList<Stock> getStockArrayList() throws Exception, SQLException{
    	ArrayList<Stock> stockArrayList = new ArrayList<Stock>(); 
    	Connection connection = dbController.connectToDb();
    	Statement statement = connection.createStatement();
    	String query = "SELECT ticker from stock";
    	ResultSet resultSet = statement.executeQuery(query);
    	while(resultSet.next()) {
    		stockArrayList.add(getStock(resultSet.getString("ticker")));
    	}
    	return stockArrayList;
    }
    
    
    /**
     * getCustomerStockArrayList
     * 
     * @return ArrayList<CustomerOwnedStock>
     */
    public ArrayList<CustomerOwnedStock> getCustomerStockArrayList(String customerId) throws Exception, SQLException{
    	ArrayList<CustomerOwnedStock> customerOwnedStock = new ArrayList<CustomerOwnedStock>(); 
    	Connection connection = dbController.connectToDb();
    	Statement statement = connection.createStatement();
    	String query = "SELECT stockId, customerId, purchasePrice FROM customer_owned_stock"+
        		" WHERE customerId='"+customerId+"'; ";
    	ResultSet resultSet = statement.executeQuery(query);
    	while(resultSet.next()) {
    		customerOwnedStock.add(getCustomerStock( getStock(resultSet.getInt("stockId")).getTicker(), resultSet.getString("customerId")));
    	}
    	return customerOwnedStock;
    }
    
    
	/**
	 * createStock, INSERT a stock in the stock database
	 * 
	 * @param String ticker, String stockName, int open, double high, double low, double price, Date date
	 * @return int statusCode
	 */
	public int createStock(String ticker, String stockName, int open, double high, double low, double price, Date date) throws Exception, SQLException {
		int responseStatus = 0;
		Connection connection = dbController.connectToDb();
		//check whether this stock already exist
    	boolean stockExists = checkIfStockExist(connection, ticker);
        if(!stockExists){
            //insert into stock
            String query1 = "INSERT into stock(ticker, stockName, open, high, low, price, date)"+" VALUES(?,?,?,?,?,?,?);";
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            //stockId is a auto increment
            preparedStatement1.setString(1, ticker);
            preparedStatement1.setString(2, stockName);
            preparedStatement1.setInt(3, open);
            preparedStatement1.setDouble(4, high);
            preparedStatement1.setDouble(5, low);
            preparedStatement1.setDouble(6, price);
        	java.util.Date javaDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
            preparedStatement1.setDate(7, sqlDate);
            int rowCount = preparedStatement1.executeUpdate();
            if(rowCount!=0) {
                //successfully created stock
                responseStatus = bankConstants.getSUCCESS_CODE();
            }else {
                responseStatus = bankConstants.getERROR();
            }
        }else {
        	// TODO stock already exists, alert message
            responseStatus = bankConstants.getSERVER_ERROR();
        }
    	return responseStatus;
    }
		
	
	
	/**
	 * removeStock, DELETE that stock in the stock database
	 * 
	 * @param String ticker
	 * @return void
	 */
	public void removeStock(String ticker) throws Exception {
		Connection connection = dbController.connectToDb();
		//check whether this stock already exist
    	boolean stockExists = checkIfStockExist(connection, ticker);
    	if(stockExists) {
    		String query = "DELECT * from stock where ticker='"+ticker+"';";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
    	}
    	
	}
	
	
	/**
	 * overloading getStock, read a stock from DB with stockId
	 * 
	 * @param int stockId
	 * @return Stock
	 */
	public Stock getStock(int stockId) throws Exception, SQLException {
    	//connection with DB
    	Connection connection = dbController.connectToDb();
		Stock tempStock = new Stock();
        String query = "SELECT * from stock where stockId="+stockId+";";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
    	//set values to 'tempStock' 
		tempStock.setStockId(resultSet.getInt("stockId"));
		tempStock.setOpen(resultSet.getInt("open"));
		tempStock.setHigh(resultSet.getDouble("high"));
		tempStock.setLow(resultSet.getDouble("low"));
		tempStock.setTicker(resultSet.getString("ticker"));
		tempStock.setStockName(resultSet.getString("stockName"));
		tempStock.setPrice(resultSet.getDouble("price"));
		tempStock.setDate(resultSet.getDate("date"));
		return tempStock;
	}

	/**
	 * getStock, read a stock from DB with ticker
	 * 
	 * @param String ticker (varchar(4))
	 * @return Stock
	 */
	public Stock getStock(String ticker) throws Exception, SQLException {
    	//connection with DB
    	Connection connection = dbController.connectToDb();
		Stock tempStock = new Stock();
    	if(checkIfStockExist(connection, ticker) == true) {
            String query = "SELECT * from stock where ticker='"+ticker+"';";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
        	//set values to 'tempStock' 
    		tempStock.setStockId(resultSet.getInt("stockId"));
    		tempStock.setOpen(resultSet.getInt("open"));
    		tempStock.setHigh(resultSet.getDouble("high"));
    		tempStock.setLow(resultSet.getDouble("low"));
    		tempStock.setTicker(resultSet.getString("ticker"));
    		tempStock.setStockName(resultSet.getString("stockName"));
    		tempStock.setPrice(resultSet.getDouble("price"));
    		tempStock.setDate(resultSet.getDate("date"));
    	}
		return tempStock;
	}

	
	/**
	 * checkIfStockExist
	 * 
	 * @param int stockId, Connection connection
	 * @return boolean
	 */
	public boolean checkIfStockExist(Connection connection, String ticker) throws SQLException {
        String query = "SELECT * from stock WHERE ticker='"+ticker+"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet==null || !resultSet.next()) {
            return false;
        }
        else
        	return true;
	}
	
	
	/**
	 * buyStock, INSERT a stock in the customer_owned_stock database
	 * (optional)call generateTradeRecord
	 * (optional error code return, return inr)
	 * 
	 * @param Stock stock, int quantity, SecuritiesAccount securitiesAccount, Date purchaseDate
	 * @return void
	 */
	public void buyStock(Stock stock, int quantity, SecuritiesAccount securitiesAccount, Date purchaseDate) throws Exception, SQLException {
		//check stock exist
    	Connection connection = dbController.connectToDb();
    	if(checkIfStockExist(connection, stock.getTicker()) == true) {
    		//have enough money in securities
    		if(securitiesAccount.getInvestmentAmount()>=(quantity*stock.getPrice())) {
        		//check exist in customer Stock, which mean price = purchasePrice; if so, just ++quantity
        		if(checkIfCustomerStockExist(connection, stock.getStockId(), securitiesAccount.getCustomerId())==true) {
        			//UPDATE customer_owned_stock(`quantity`)
        			String query0 = "UPDATE customer_owned_stock SET quantity=quantity+"+quantity+
    		        		" WHERE customerId='"+securitiesAccount.getCustomerId()+"' AND stockId="+stock.getStockId()+" AND purchasePrice="+stock.getPrice()
    		        				+ "; ";
        			Statement stmt = connection.createStatement();
        			stmt.executeUpdate(query0);
                    String query1 =  "UPDATE securities_account SET investmentAmount = investmentAmount-"+(quantity*stock.getPrice())
                    			+ " WHERE customerId='"+securitiesAccount.getCustomerId()+"';";
        			stmt.executeUpdate(query1);
        		}
        		else {
        			//INSERT new row, 5 values. does the order matter?
        			String query1 = "INSERT into customer_owned_stock(`customerId`, `stockId`, `quantity`, `purchaseDate`, `purchasePrice`)"+" VALUES(?,?,?,?,?);";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                    //stockId is a auto increment
                    preparedStatement1.setString(1, securitiesAccount.getCustomerId());
                    preparedStatement1.setInt(2, stock.getStockId());
                    preparedStatement1.setInt(3, quantity);
                	java.util.Date javaDate = new java.util.Date();
                    java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
                    preparedStatement1.setDate(4, sqlDate);
                    preparedStatement1.setDouble(5, stock.getPrice());
                    int rowCount = preparedStatement1.executeUpdate();
                    String query = "UPDATE securities_Account SET investmentAmount = investmentAmount-"+quantity*stock.getPrice()
								+ "WHERE customerId='"+securitiesAccount.getCustomerId()+"';";
        			Statement stmt = connection.createStatement();
        			stmt.executeUpdate(query);
                    
                    if(rowCount!=0) {
                        //successfully created stock
                    }else {
                    	
                    }
        		}
    			
    		}
    	}
		
	}
	

	/**
	 * sellStock, UPDATE
	 * if sellAmount==CustomerOwnedStock.quantity, call removeCustomerStock, compare price&stockId&customerId
	 * (optional)call generateTradeRecord
	 * 
	 * @parma Stock stock, int quantity, SecuritiesAccount securitiesAccount
	 * @return void
	 */
	public void sellStock(CustomerOwnedStock cStock, int sellQuantity, SecuritiesAccount securitiesAccount) throws SQLException, Exception {
		Connection connection = dbController.connectToDb();
		Statement statement = connection.createStatement();
		//check stock exist
		boolean existInStock = checkIfStockExist(connection, cStock.getTicker());
		boolean existInCustomerStock = checkIfCustomerStockExist(connection, cStock.getStockId(), securitiesAccount.getCustomerId());
		if(existInStock && existInCustomerStock) {
			//TODO update securitiesAccount.realizedProfit
			if(cStock.getQuantity()>sellQuantity && cStock.getQuantity()>0) {
				//sell some
				//cStock.quantity --;securitiesAccount.amount++
		        String query = "UPDATE customer_owned_stock SET quantity = quantity-"+sellQuantity+
		        		" WHERE customerId='"+cStock.getCustomerId()+"' AND stockId="+cStock.getStockId()+" AND purchasePrice="+cStock.getPurchasePrice()+";"
		        						+ "UPDATE securities_Account SET investmentAmount = investmentAmount+"+sellQuantity*cStock.getPrice()
		        								+ "WHERE customerId='"+securitiesAccount.getCustomerId()+"';";
				statement.executeUpdate(query);
			}
			else if(cStock.getQuantity()==sellQuantity) {
				//sell all
				//removeCustomerStock; securitiesAccount.amout++
				removeCustomerStock(cStock, securitiesAccount.getCustomerId());
				String query = "UPDATE securities_Account SET investmentAmount = investmentAmount+"+sellQuantity*cStock.getPrice()
								+ "WHERE customerId='"+securitiesAccount.getCustomerId()+"';";
				statement.executeUpdate(query);
			}
			else {
				//illeagal value of sellQuantity
			}
		}
	}
	
	
	/**
	 * removeCustomerStock, DELETE that stock in the customer_owned_stock database
	 */
	public void removeCustomerStock(CustomerOwnedStock cStock, String customerId) throws Exception {
		Connection connection = dbController.connectToDb();
		//check whether this stock already exist
    	boolean stockExists = checkIfStockExist(connection, cStock.getTicker());
    	if(stockExists) {
    		String query = "DELETE from customer_owned_stock where stockId='"+cStock.getStockId()+"' and customerId='"+customerId+"';";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
    	}
	}

	/**
	 * getCustomerStock, read a CustomerOwnedStock from the customer_owned_stock database
	 * 
	 * @param String ticker, String customerId, double purchasedPrice
	 * @return CustomerOwnedStock 
	 */
	public CustomerOwnedStock getCustomerStock(String ticker, String customerId) throws Exception, SQLException {
    	//connection with DB
    	Connection connection = dbController.connectToDb();
    	CustomerOwnedStock tempStock = new CustomerOwnedStock();
		//SQL query to get customer's security account by customerID
    	if(checkIfStockExist(connection, ticker)==true) {
    		if(checkIfCustomerStockExist(connection, getStock(ticker).getStockId(), customerId)==true) {
	            String query = "SELECT * from customer_owned_stock "
	            		+ "where stockId="+getStock(ticker).getStockId()+" and customerId='"+customerId+"';";
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery(query);
	            resultSet.next();
	        	//set values to 'tempStock' 
	    		tempStock.setStockId(resultSet.getInt("stockId"));
	    		tempStock.setTicker(ticker);
	    		tempStock.setCustomerId(resultSet.getString("customerId"));
	    		tempStock.setQuantity(resultSet.getInt("quantity"));
	    		tempStock.setPurchasePrice(resultSet.getDouble("purchasePrice"));
	    		tempStock.setPurchaseDate(resultSet.getDate("purchaseDate"));
	    		tempStock.setUnrealizedProfit(getStock(ticker));
	            String query1 = "SELECT * from stock "
	            		+ "where stockId="+getStock(ticker).getStockId()+";";
	            ResultSet resultSet1 = statement.executeQuery(query1);
	            resultSet1.next();
	    		tempStock.setOpen(resultSet1.getInt("open"));
	    		tempStock.setHigh(resultSet1.getDouble("high"));
	    		tempStock.setLow(resultSet1.getDouble("low"));
	    		tempStock.setStockName(resultSet1.getString("stockName"));
	    		tempStock.setPrice(resultSet1.getDouble("price"));
	    		tempStock.setDate(resultSet1.getDate("date"));
    		}
    		else{
    			//not exist in customer_owned_stock
    		}
    		
    	}
		return tempStock;
	}

	/**
	 * checkIfCustomerStockExist
	 * @param Connection connection, int stockId, String customerId, double purchasedPrice
	 * @return boolean
	 * 
	 */
	public boolean checkIfCustomerStockExist(Connection connection, int stockId, String customerId) throws SQLException{
        String query = "SELECT * from customer_owned_stock WHERE stockId="+stockId+" and customerId='"+customerId+"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet==null || !resultSet.next()) {
            return false;
        }
        else
        	return true;
	}
	
	/*
	 * generateTradeRecord
	 * optional function, for generating trade report
	 */
	
}
