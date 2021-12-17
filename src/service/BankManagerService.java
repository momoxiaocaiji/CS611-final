package service;

import controller.DbController;
import model.*;

import java.sql.*;
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

    //function to pay interest to accounts
    public int payInterestToAccounts() throws Exception {
        //assuming interest is only paid to savings account having balance >=$1000
        Connection connection = dbController.connectToDb();
        List<SavingAccount> eligibleAccounts = getInterestEligibleAccounts(connection);
        if(eligibleAccounts.size()>0) {
            int statusOfPayment = payInterest(connection,eligibleAccounts);
            //add to transaction table
            return statusOfPayment;
        }

        return bankConstants.getNOT_FOUND();

    }

    //function to update values in table
    public int payInterest(Connection connection,List<SavingAccount>savingAccounts) throws SQLException {
        String accountId = ""; String customerId = ""; double amount = 0.0;

        for(SavingAccount savingAccount : savingAccounts) {
            accountId = savingAccount.getAccountId();
            customerId = savingAccount.getCustomerId();
            double principal = savingAccount.getAmount();
            double interest = (bankConstants.getRATE_OF_INTEREST()*principal)/100;
            amount = principal+interest;
            String query = "UPDATE saving_account set amount="+amount+" where customerId='"+customerId+"' and accountId='"+accountId+"';";
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }

        return bankConstants.getSUCCESS_CODE();

    }

    //function to get all savings account having balance >1000
    private List<SavingAccount> getInterestEligibleAccounts(Connection connection) throws Exception {

        List<SavingAccount> savingAccounts = new ArrayList<>();
        Statement statement = connection.createStatement();
        String query = "select * from saving_account where amount >="+bankConstants.getMINIMUM_BALANCE_FOR_INTEREST()+";";

        ResultSet rs = statement.executeQuery(query);
        while (rs.next()){
            SavingAccount savingAccount = new SavingAccount();
            savingAccount.setAccountId(rs.getString("accountId"));
            savingAccount.setAccountNum(String.valueOf(rs.getInt("accountNum")));
            savingAccount.setCurrency(rs.getString("currency"));
            savingAccount.setAmount(rs.getDouble("amount"));
            savingAccount.setCustomerId(rs.getString("customerId"));

            savingAccounts.add(savingAccount);
        }

        return savingAccounts;

    }

    public int withdrawProfits(double amount) throws Exception{
        Connection connection = dbController.connectToDb();
        String query = "select * from bank limit 1;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        double bankbalance = 0;
        if(resultSet.next()) {
            bankbalance = resultSet.getDouble("bankBalance");
        }

        String update = "update bank set bankBalance="+(bankbalance-amount)+" where bankId="+bankConstants.getDEFAULT_BANKID();
        int count = statement.executeUpdate(update);
        return (count>0)?bankConstants.getSUCCESS_CODE(): bankConstants.getERROR();
    }

}
