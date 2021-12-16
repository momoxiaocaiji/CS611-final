package service;

import controller.DbController;
import model.BankConstants;
import model.CheckingAccount;
import model.Customer;
import model.SavingAccount;
import model.SecuritiesAccount;

import java.sql.*;
import java.util.*;

public class AccountService {

    DbController dbController = new DbController();

    BankConstants bankConstants = new BankConstants();

    //create new account on sign up for new user
    public int createNewAccountForCustomer(Customer customer) throws Exception {
        Connection connection = dbController.connectToDb();

        boolean accountExists = doesAccountExist(connection,customer);
        if(!accountExists) {
            //insert new record into account table
            int status = insertIntoAccount(connection,customer);
            if(status>0)
                return bankConstants.getSUCCESS_CODE();
            else
                return bankConstants.getERROR();
        }else{
            return bankConstants.getSERVER_ERROR();
        }

    }

    //create a new checking account for customer
    public int createNewCheckingOrSavingAccount(Customer customer,String accountType,int pin) throws Exception {

        Connection connection = dbController.connectToDb();
        int responseStatus = 0;
        //insert into checking/saving account
        switch(accountType) {
            case "SAVING" :
                //1.check if saving account exists
                boolean accountExists = doesSavingAccountExist(connection,customer);
                if(!accountExists){
                    //insert into account
                    int count = insertIntoCheckingOrSaving(connection,customer,"SAVING",pin);
                    if(count!=0) {
                        //successfully created saving account
                        responseStatus = bankConstants.getSUCCESS_CODE();
                    }else {
                        responseStatus = bankConstants.getERROR();
                    }
                }else {
                    responseStatus = bankConstants.getSERVER_ERROR();
                }
                break;
            case "CHECKING" :
                //1.check if saving account exists
                boolean accountExists1 = doesCheckingAccountExist(connection,customer);
                if(!accountExists1){
                    //insert into account
                    int count = insertIntoCheckingOrSaving(connection,customer,"CHECKING",pin);
                    if(count!=0) {
                        //successfully created saving account
                        responseStatus = bankConstants.getSUCCESS_CODE();
                    }else {
                        responseStatus = bankConstants.getERROR();
                    }
                }else {
                    responseStatus = bankConstants.getSERVER_ERROR();
                }
                break;
            default:
                responseStatus = bankConstants.getSERVER_ERROR();
        }

        return responseStatus;
    }

    public boolean doesCheckingAccountExist(Connection connection,Customer customer) throws SQLException {
        int accountId = Objects.hash("CHECK",customer.getPersonId());
        String query = "select * from checking_account where accountNum="+customer.getPersonId()+" and accountId="+accountId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if(resultSet==null || !resultSet.next())
            return false; //account doesnt exist

        return true;
    }


    public boolean doesSavingAccountExist(Connection connection,Customer customer) throws SQLException {
        int accountId = Objects.hash("SAVE",customer.getPersonId());
        String query = "select * from saving_account where accountNum="+customer.getPersonId()+" and accountId="+accountId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if(resultSet==null || !resultSet.next())
            return false; //account doesnt exist

        return true;
    }

    public int insertIntoCheckingOrSaving(Connection connection,Customer customer,String accountType,int pin) throws SQLException {
        int status = 0;
        switch (accountType) {
            case "SAVING" :
                String query = "INSERT into saving_account(accountId,accountNum,customerId,pin,currency,amount)"+" VALUES(?,?,?,?,?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,Objects.hash("SAVE",customer.getPersonId()));
                preparedStatement.setInt(2,customer.getPersonId());
                preparedStatement.setString(3,customer.getCustomerId());
                preparedStatement.setInt(4,pin);
                preparedStatement.setString(5,"USD");
                preparedStatement.setDouble(6,100.0);
                status= preparedStatement.executeUpdate();
                break;
            case "CHECKING" :
                String query1 = "INSERT into checking_account(accountId,accountNum,customerId,pin,currency,amount)"+" VALUES(?,?,?,?,?,?);";
                PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                preparedStatement1.setInt(1,Objects.hash("CHECK",customer.getPersonId()));
                preparedStatement1.setInt(2,customer.getPersonId());
                preparedStatement1.setString(3,customer.getCustomerId());
                preparedStatement1.setInt(4,pin);
                preparedStatement1.setString(5,"USD");
                preparedStatement1.setDouble(6,100.0);
                status=preparedStatement1.executeUpdate();
                break;

        }

        return status;
    }


    public int insertIntoAccount(Connection connection,Customer customer) throws SQLException {
        String query = "INSERT into account(accountNum,customerId)"+" VALUES(?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,customer.getPersonId());
        preparedStatement.setString(2,customer.getCustomerId());

        return preparedStatement.executeUpdate();
    }

    //get account for customer
    public boolean doesAccountExist(Connection connection,Customer customer) throws Exception {
        String query = "select * from account where accountNum="+customer.getPersonId()+" and customerId='"+customer.getCustomerId()+"';";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if(resultSet==null || !resultSet.next())
            return false; //account doesnt exist

        return true;
    }


    //get checking and saving account belongin to customer
    public List<Object> getAccountInfoForCustomer(Customer customer) throws Exception {
        Connection connection = dbController.connectToDb();
        List<Object> accounts = new ArrayList<>();
//        CheckingAccount checkingAccount = new CheckingAccount();
//        SavingAccount savingAccount = new SavingAccount();
        //1.get checking account
        boolean checkingExists = doesCheckingAccountExist(connection,customer);
        if(checkingExists) {
            CheckingAccount checkingAccount = new CheckingAccount();
            String query = "SELECT * from checking_account where customerId='"+customer.getCustomerId()+"' and accountNum="+customer.getPersonId()+";";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet!=null && resultSet.next()) {
                checkingAccount.setAccountId(resultSet.getString("accountId"));
                checkingAccount.setAccountNum(String.valueOf(resultSet.getInt("accountNum")));
                checkingAccount.setCustomerId(customer.getCustomerId());
                checkingAccount.setAmount(resultSet.getDouble("amount"));
                checkingAccount.setCurrency(resultSet.getString("currency"));
                checkingAccount.setPin(resultSet.getInt("pin"));
                Map<String,Double> map = new HashMap<>();
                map.put(checkingAccount.getCurrency(),checkingAccount.getAmount());
                checkingAccount.setMoney(map);

            }
            accounts.add(checkingAccount);
        }

        //2.get savings account
        boolean savingExists = doesSavingAccountExist(connection,customer);
        if(savingExists) {
            SavingAccount savingAccount = new SavingAccount();
            String query = "SELECT * from saving_account where customerId='"+customer.getCustomerId()+"' and accountNum="+customer.getPersonId()+";";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet!=null && resultSet.next()) {
                savingAccount.setAccountId(resultSet.getString("accountId"));
                savingAccount.setAccountNum(String.valueOf(resultSet.getInt("accountNum")));
                savingAccount.setCustomerId(customer.getCustomerId());
                savingAccount.setAmount(resultSet.getDouble("amount"));
                savingAccount.setCurrency(resultSet.getString("currency"));
                savingAccount.setPin(resultSet.getInt("pin"));
                Map<String,Double> map = new HashMap<>();
                map.put(savingAccount.getCurrency(),savingAccount.getAmount());
                savingAccount.setMoney(map);
            }
            accounts.add(savingAccount);
        }




        return accounts;

    }
    
    //get
    public SavingAccount getSavingAccountInfoForCustomer(Customer customer) throws Exception {
    	Connection connection = dbController.connectToDb();
    	boolean savingExists = doesSavingAccountExist(connection,customer);
        SavingAccount savingAccount = new SavingAccount();
        if(savingExists) {
            String query = "SELECT * from saving_account where customerId='"+customer.getCustomerId()+"' and accountNum="+customer.getPersonId()+";";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet!=null && resultSet.next()) {
                savingAccount.setAccountId(resultSet.getString("accountId"));
                savingAccount.setAccountNum(String.valueOf(resultSet.getInt("accountNum")));
                savingAccount.setCustomerId(customer.getCustomerId());
                savingAccount.setAmount(resultSet.getDouble("amount"));
                savingAccount.setCurrency(resultSet.getString("currency"));
                savingAccount.setPin(resultSet.getInt("pin"));
                Map<String,Double> map = new HashMap<>();
                map.put(savingAccount.getCurrency(),savingAccount.getAmount());
                savingAccount.setMoney(map);
            }
        }
    	return savingAccount;
    }

    //create Securities account
    public int createNewSecuritiesAccount (Customer customer, double depositAmount) throws Exception, SQLException{
    	int responseStatus = 0;
    	Connection connection = dbController.connectToDb();
    	//check whether the account already exist
    	boolean accountExists = doesSecuritiesAccountExist(connection,customer);
    	//check prerequisites of creating a SecuritiesAccount
    	//disable condition: savingAmount>=5000 && savingAmount-depositAmount>=2500; TODO && depositAmount>=1000
        if(!accountExists && doesSavingAccountExist(connection, customer) ){
            SavingAccount savingAccount = getSavingAccountInfoForCustomer(customer);
        	savingAccount.setAmount(savingAccount.getAmount()-depositAmount);
            //insert into account
        	Statement statement = connection.createStatement();
        	//generate the unique hash code
        	int accountId = Objects.hash("SECURITIES",customer.getPersonId());
            int rowCount = statement.executeUpdate("INSERT INTO securities_account (`accountId`, `customerId`, `investmentAmount`) "
            		+ "VALUES ("+accountId+", '"+customer.getCustomerId()+"',"+depositAmount+");");
            if(rowCount!=0) {
                //successfully created saving account
                responseStatus = bankConstants.getSUCCESS_CODE();
            }else {
                responseStatus = bankConstants.getERROR();
            }
        }else {
        	// accountExists, no saving account, or deposit amount illegal
            responseStatus = bankConstants.getSERVER_ERROR();
        }
    	return responseStatus;
    }
    
    //return the SecuritiesAccount of this customer
    public SecuritiesAccount getSecuritiesInfo(Customer customer) throws Exception {
    	//connection with DB
    	Connection connection = dbController.connectToDb();
    	//temp securitiesAccount
    	SecuritiesAccount securitiesAccount = new SecuritiesAccount();
    	//check whether the SecuritiesAccount of this customer exist or not
        boolean securitiesExists = doesSecuritiesAccountExist(connection,customer);
        if(securitiesExists) {
        	//SQL query to get customer's security account by customerID
            String query = "SELECT * from securities_account where customerId='"+customer.getCustomerId()+"' and accountId="+customer.getPersonId()+";";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
        	//set values to 'securitiesAccount'
            securitiesAccount.setAccountId(resultSet.getString("accountId"));
        	securitiesAccount.setCustomerId(resultSet.getString("customerId"));
        	securitiesAccount.setInvestmentAmount(resultSet.getDouble("investmentAmount"));
        }
        else {
        	//account does not exist!
        	//TODO alert message
        }
    	//return
    	return securitiesAccount;
    }

    //check doesSecuritiesAccountExist with customerId and accountId
    public Boolean doesSecuritiesAccountExist(Connection connection, Customer customer) throws SQLException{
        int accountId = Objects.hash("CHECK",customer.getPersonId());
        String query = "select * from securities_account where customerId='"+customer.getPersonId()+"' and accountId="+accountId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if(resultSet==null || !resultSet.next())
            return false; //account doesnt exist

        return true;
    }


    //get account based on accountId
    public Object getAccountBasedOnId(Connection connection,String accountId) throws Exception {
        CheckingAccount checkingAccount = new CheckingAccount();
        SavingAccount savingAccount = new SavingAccount();

        //check for checking
        String query1 = "select * from checking_account where accountId='"+accountId+"';";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query1);
        if(resultSet.next()) {

                checkingAccount.setAccountId(accountId);
                checkingAccount.setAccountNum(String.valueOf(resultSet.getInt("accountNum")));
                checkingAccount.setCustomerId(resultSet.getString("customerId"));
                checkingAccount.setPin(resultSet.getInt("pin"));
                checkingAccount.setCurrency(resultSet.getString("currency"));
                checkingAccount.setAmount(resultSet.getDouble("amount"));

                return checkingAccount;
        }else {
            String query2 = "select * from saving_account where accountId='"+accountId+"';";
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(query2);
            if(resultSet1.next()) {
                    savingAccount.setAccountId(accountId);
                    savingAccount.setAccountNum(String.valueOf(resultSet1.getInt("accountNum")));
                    savingAccount.setCustomerId(resultSet1.getString("customerId"));
                    savingAccount.setPin(resultSet1.getInt("pin"));
                    savingAccount.setCurrency(resultSet1.getString("currency"));
                    savingAccount.setAmount(resultSet1.getDouble("amount"));

                    return savingAccount;
                }
            else{
                return new Object();
            }
        }
    }
}