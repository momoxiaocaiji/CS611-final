package service;

import controller.DbController;
import model.CheckingAccount;
import model.Customer;
import model.SavingAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountService {

    DbController dbController = new DbController();

    //create new account on sign up for new user
    public int createNewAccountForCustomer(Customer customer) throws Exception {
        Connection connection = dbController.connectToDb();

        boolean accountExists = doesAccountExist(connection,customer);
        if(!accountExists) {
            //insert new record into account table
            int status = insertIntoAccount(connection,customer);
            if(status>0)
                return 200;
            else
                return 400;
        }else{
            return 500;
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
                        responseStatus = 200;
                    }else {
                        responseStatus = 400;
                    }
                }else {
                    responseStatus = 500;
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
                        responseStatus = 200;
                    }else {
                        responseStatus = 400;
                    }
                }else {
                    responseStatus = 500;
                }
                break;
            default:
                responseStatus = 504;
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
                preparedStatement.setInt(3,customer.getCustomerId());
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
                preparedStatement1.setInt(3,customer.getCustomerId());
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
        preparedStatement.setInt(2,customer.getCustomerId());

        return preparedStatement.executeUpdate();
    }

    //get account for customer
    public boolean doesAccountExist(Connection connection,Customer customer) throws Exception {
        String query = "select * from account where accountNum="+customer.getPersonId()+" and customerId="+customer.getCustomerId();
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
        CheckingAccount checkingAccount = new CheckingAccount();
        SavingAccount savingAccount = new SavingAccount();
        //1.get checking account
        boolean checkingExists = doesCheckingAccountExist(connection,customer);
        if(checkingExists) {
            String query = "SELECT * from checking_account where customerId="+customer.getCustomerId()+" and accountNum="+customer.getPersonId()+";";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet!=null && resultSet.next()) {
                checkingAccount.setAccountId(resultSet.getString("accountId"));
                checkingAccount.setAccountNum(String.valueOf(resultSet.getInt("accountNum")));
                checkingAccount.setCustomerId(customer.getCustomerId());
                checkingAccount.setAmount(resultSet.getDouble("amount"));
                checkingAccount.setCurrency(resultSet.getString("currency"));
                checkingAccount.setPin(resultSet.getInt("pin"));
            }
        }

        //2.get savings account
        boolean savingExists = doesSavingAccountExist(connection,customer);
        if(savingExists) {
            String query = "SELECT * from saving_account where customerId="+customer.getCustomerId()+" and accountNum="+customer.getPersonId()+";";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet!=null && resultSet.next()) {
                savingAccount.setAccountId(resultSet.getString("accountId"));
                savingAccount.setAccountNum(String.valueOf(resultSet.getInt("accountNum")));
                savingAccount.setCustomerId(customer.getCustomerId());
                savingAccount.setAmount(resultSet.getDouble("amount"));
                savingAccount.setCurrency(resultSet.getString("currency"));
                savingAccount.setPin(resultSet.getInt("pin"));
            }
        }

        accounts.add(checkingAccount);
        accounts.add(savingAccount);

        return accounts;

    }
}
