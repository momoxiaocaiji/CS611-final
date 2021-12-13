package service;

import controller.DbController;
import model.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankManagerService {

    BankConstants bankConstants = new BankConstants();
    DbController dbController =  new DbController();
    LoginService loginService = new LoginService();
    AccountService accountService = new AccountService();

    public List<Stock> getAvailableStocks() throws Exception {
        String query = "select * from stock";
        Connection connection = dbController.connectToDb();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<Stock> stocks = new ArrayList<>();

        while (resultSet.next()) {
            Stock stock = new Stock();
            stock.setStockId(resultSet.getInt("stockId"));
            stock.setStockName(resultSet.getString("stockName"));
            stock.setTicker(resultSet.getString("ticker"));
            stock.setDate(resultSet.getDate("date"));
            stock.setPrice(resultSet.getDouble("price"));
            stocks.add(stock);
        }

        return stocks;
    }

    //function to get stock id based on ticker
    public int getStockId(String ticker) throws Exception {
        List<Stock> stocks = getAvailableStocks();
        for(Stock stock : stocks) {
            if(stock.getTicker().equalsIgnoreCase(ticker))
                return stock.getStockId();
        }
        return -1;
    }

    //function to change stock price
    public int changeStockPrice(int stockId, double price) throws Exception {
        Date date = new Date(System.currentTimeMillis());
        String query = "UPDATE stock set price="+price+" ,date='"+date+"' where stockId="+stockId+";";
        Connection connection = dbController.connectToDb();
        Statement statement= connection.createStatement();
        int count = statement.executeUpdate(query);
        if(count>0) {
            System.out.println("Stock id:"+stockId+" updated");
            return bankConstants.getSUCCESS_CODE();
        }else{
            System.out.println("Update failed");
            return bankConstants.getERROR();
        }
    }


    //function to get customer data
    public Map<String,Object> getCustomerData(String customerId) throws Exception {
        Map<String,Object> map = new HashMap<>();

        //get personal info - name,personid, customerid, email, dob
        Customer customer = getCustomerPersonalInfo(customerId);
        map.put("Profile",customer);
        map.put("Account Number",customer.getPersonId());

        //get account details
        List<Object> accounts = accountService.getAccountInfoForCustomer(customer);
        CheckingAccount checkingAccount = new CheckingAccount();
        SavingAccount savingAccount = new SavingAccount();

        for(Object obj : accounts) {
            if(obj instanceof CheckingAccount) {
                checkingAccount = (CheckingAccount) obj;
                map.put("Checking",checkingAccount);
            }else if(obj instanceof SavingAccount) {
                savingAccount = (SavingAccount) obj;
                map.put("Saving",savingAccount);
            }
        }

        return map;

    }

    //private methods to get customer data
    private Customer getCustomerPersonalInfo(String customerId) throws Exception {
        return loginService.getCustomerDetails(customerId);
    }
}
