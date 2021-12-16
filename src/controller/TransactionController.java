package controller;

import model.BankConstants;
import model.Transaction;
import service.TransactionService;

import java.util.List;

public class TransactionController {

    /*
    1. record tx in db -done (in tx service)
    2. types = {Deposit,Loan payment,account transfer,payment,withdrawal}
    3. pay loan if any
    4. transfer to same account - done
    5. transfer to other account - done
    6. make withdrawal
    7. make deposit
    8. make payment
    9. get all transactions for that day
     */

    TransactionService transactionService = new TransactionService();
    BankConstants bankConstants = new BankConstants();

    //function for account transfer
    public int transferToAccount(String senderCustomerId,String senderAccountId,String receiverAccountId,int pin,double amount,String currency) throws Exception {
        int status = transactionService.transferToAccount(senderCustomerId,senderAccountId,receiverAccountId,amount,currency);
        if(status== bankConstants.getSUCCESS_CODE()){
            //add transaction to db
            transactionService.insertTransactionIntoDb(senderCustomerId,senderAccountId,receiverAccountId,amount,currency,"Transfer");
            transactionService.payBankFees(amount,1);
        }
        return status;
    }

    //function to get daily report
    public List<Transaction> getDailyReport(String date) throws Exception {
        return transactionService.getDailyReport(date);
    }



}
