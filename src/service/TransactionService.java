package service;

import controller.DbController;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    AccountService accountService = new AccountService();
    LoginService loginService = new LoginService();
    BankConstants bankConstants = new BankConstants();
    DbController dbController = new DbController();

    public enum TransactionTypes {
        DEPOSIT,LOAN_PAYMENT,ACCOUNT_TRANSFER,PAYMENT,WITHDRAWAL
    };

    //function for account transfer
    public int transferToAccount(String senderCustomerId,String senderAccountId,String receiverAccountId,double amount,String currency) throws Exception {

        Connection connection = dbController.connectToDb();

        //1.get customer details
        Customer customer = loginService.getCustomerDetails(senderCustomerId);

        //1. get senders account details
        List<Object> senderAccounts = accountService.getAccountInfoForCustomer(customer);

        if(senderAccounts==null || senderAccounts.isEmpty()) {
            System.out.println("Sender accounts not found");
            return bankConstants.getNOT_FOUND();
        }

        CheckingAccount checkingAccount = new CheckingAccount();
        SavingAccount savingAccount = new SavingAccount();
        int accountType = 0;

        for(Object obj : senderAccounts) {
            if(obj instanceof CheckingAccount) {
                checkingAccount = (CheckingAccount) obj;
                if(checkingAccount.getAccountId().equalsIgnoreCase(senderAccountId) && checkingAccount.getCustomerId().equalsIgnoreCase(senderCustomerId)) {
                    accountType = 1;
                    break;
                }
            }else if(obj instanceof SavingAccount){
                savingAccount = (SavingAccount) obj;
                if(savingAccount.getAccountId().equalsIgnoreCase(senderAccountId) && savingAccount.getCustomerId().equalsIgnoreCase(senderCustomerId)) {
                    accountType = 2;
                    break;
                }
            }
        }

        Object account = accountService.getAccountBasedOnId(connection,receiverAccountId);

        switch (accountType) {
            case 1:
                //send money from senders checking account

                if(checkingAccount.getAmount()<amount) {
                    return bankConstants.getERROR();
                }
                double amt = checkingAccount.getAmount()-amount;
                String updateC = "Update checking_account set amount="+amt+" where accountId='"+checkingAccount.getAccountId()+"' and customerId='"+checkingAccount.getCustomerId()+"';";
                Statement statement1 = connection.createStatement();
                statement1.executeUpdate(updateC);

                if(account instanceof CheckingAccount) {
                    CheckingAccount checkingAccount1 = (CheckingAccount) account;
                    double totalAmt = checkingAccount1.getAmount()+amount;

                    String update = "Update checking_account set amount="+totalAmt+" where accountId='"+checkingAccount1.getAccountId()+"' and customerId='"+checkingAccount1.getCustomerId()+"';";
                    Statement statement = connection.createStatement();
                    int count = statement.executeUpdate(update);
                    if(count>0) {
                        return bankConstants.getSUCCESS_CODE();
                    }else
                        return bankConstants.getSERVER_ERROR();
                }else if(account instanceof SavingAccount) {
                    SavingAccount savingAccount1 = (SavingAccount) account;
                    double totalAmt = savingAccount1.getAmount()+amount;
                    String update = "Update saving_account set amount="+totalAmt+" where accountId='"+savingAccount1.getAccountId()+"' and customerId='"+savingAccount1.getCustomerId()+"';";
                    Statement statement = connection.createStatement();
                    int count = statement.executeUpdate(update);
                    if(count>0) {
                        return bankConstants.getSUCCESS_CODE();
                    }else
                        return bankConstants.getSERVER_ERROR();
                }else {
                    return bankConstants.getNOT_FOUND();
                }
            case 2:
                if(savingAccount.getAmount()<amount) {
                    return bankConstants.getERROR();
                }

                double amt1 = savingAccount.getAmount()-amount;
                String updateC1 = "Update saving_account set amount="+amt1+" where accountId='"+savingAccount.getAccountId()+"' and customerId='"+savingAccount.getCustomerId()+"';";
                Statement statement2 = connection.createStatement();
                statement2.executeUpdate(updateC1);

                if(account instanceof CheckingAccount) {
                    CheckingAccount checkingAccount1 = (CheckingAccount) account;
                    double totalAmt = checkingAccount1.getAmount()+amount;

                    String update = "Update checking_account set amount="+totalAmt+" where accountId='"+checkingAccount1.getAccountId()+"' and customerId='"+checkingAccount1.getCustomerId()+"';";
                    Statement statement = connection.createStatement();
                    int count = statement.executeUpdate(update);
                    if(count>0) {
                        return bankConstants.getSUCCESS_CODE();
                    }else
                        return bankConstants.getSERVER_ERROR();
                }else if(account instanceof SavingAccount) {
                    SavingAccount savingAccount1 = (SavingAccount) account;
                    double totalAmt = savingAccount1.getAmount()+amount;
                    String update = "Update saving_account set amount="+totalAmt+" where accountId='"+savingAccount1.getAccountId()+"' and customerId='"+savingAccount1.getCustomerId()+"';";
                    Statement statement = connection.createStatement();
                    int count = statement.executeUpdate(update);
                    if(count>0) {
                        return bankConstants.getSUCCESS_CODE();
                    }else
                        return bankConstants.getSERVER_ERROR();
                }else {
                    return bankConstants.getNOT_FOUND();
                }
            default:
                return bankConstants.getERROR();

        }

    }


    //function to insert tx into db
    public void insertTransactionIntoDb(String senderCustomerId,String senderAccountId,String receiverAccountId,double amount,String currency,String txType) throws Exception{
        Connection connection = dbController.connectToDb();
        String query = "INSERT INTO transaction (customerId,sourceAccount,destAccount,amount,currency,txDate,transactionType)"+" VALUES(?,?,?,?,?,?,?)";
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,senderCustomerId);
        preparedStatement.setString(2,senderAccountId);
        preparedStatement.setString(3,receiverAccountId);
        preparedStatement.setDouble(4,amount);
        preparedStatement.setString(5,currency);
        preparedStatement.setDate(6,date);
        preparedStatement.setString(7,txType);

        preparedStatement.executeUpdate();
        System.out.println("transaction inserted into db");
    }

    public void payBankFees(double amount,int bankId) throws Exception {
        Connection connection = dbController.connectToDb();
        Statement statement = connection.createStatement();
        String query = "select * from bank where bankId ="+bankId;
        ResultSet resultSet = statement.executeQuery(query);
        double bankBalance = 0;
        while (resultSet.next()){
            bankBalance = resultSet.getDouble("bankBalance")+((bankConstants.getTRANSACTION_CHARGE_RATE()*amount)/100);
            break;
        }

        String update = "UPDATE bank set bankBalance="+bankBalance+" where bankId ="+bankId;
        Statement statement1 = connection.createStatement();
        int count = statement1.executeUpdate(update);
        System.out.println("transaction fees of "+(bankConstants.getTRANSACTION_CHARGE_RATE()*amount/100)+"collected");
    }

    public List<Transaction> getDailyReport(String date) throws Exception {
        List<Transaction> transactionList = new ArrayList<>();
        String query = "select * from transaction where txDate='"+date+"';";
        Connection connection = dbController.connectToDb();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            Transaction transaction = new Transaction();
            transaction.setTransactionId(resultSet.getInt("transactionId"));
            transaction.setCustomerId(resultSet.getString("customerId"));
            transaction.setTransactionType(resultSet.getString("transactionType"));
            transaction.setAmount(resultSet.getDouble("amount"));
            transaction.setCurrency(resultSet.getString("currency"));
            transaction.setDate(resultSet.getDate("txDate"));
            transaction.setSourceAccountId(resultSet.getString("sourceAccount"));
            transaction.setDestinationAccountId(resultSet.getString("destAccount"));

            transactionList.add(transaction);

        }
        return transactionList;
    }
}
