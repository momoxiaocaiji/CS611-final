package controller;

import model.BankConstants;
import model.CheckingAccount;
import model.Transaction;
import service.AccountService;
import service.LoanService;
import service.TransactionService;

import java.util.List;

public class TransactionController {

    /*
    1. record tx in db -done (in tx service)
    2. types = {Deposit,Loan payment,account transfer,payment,withdrawal}
    3. pay loan if any
    4. transfer to same account - done
    5. transfer to other account - done
    6. make withdrawal - done
    7. make deposit -done
    8. make payment -done
    9. get all transactions for that day -done
     */

    TransactionService transactionService = new TransactionService();
    BankConstants bankConstants = new BankConstants();
    LoanService loanService = new LoanService();
    AccountService accountService = new AccountService();
    DbController dbController = new DbController();

    //function for account transfer
    public int transferToAccount(String senderCustomerId,String senderAccountId,String receiverAccountId,int pin,double amount,String currency) throws Exception {
        int status = transactionService.transferToAccount(senderCustomerId,senderAccountId,receiverAccountId,amount,currency);
        if(status== bankConstants.getSUCCESS_CODE()){
            //add transaction to db
            transactionService.insertTransactionIntoDb(senderCustomerId,senderAccountId,receiverAccountId,amount,currency,"Transfer");
            transactionService.payBankFees(amount, bankConstants.getDEFAULT_BANKID());
        }
        return status;
    }

    //function to get daily report
    public List<Transaction> getDailyReport(String date) throws Exception {
        return transactionService.getDailyReport(date);
    }

    public int makeWithdrawal(String customerId,String accountId,String receiverId,int pin,double amount,String currency) throws Exception {
        Object account = accountService.getAccountBasedOnId(dbController.connectToDb(),accountId);
        String accountType = (account instanceof CheckingAccount)?"checking":"saving";
        int status = transactionService.makeWithdrawal(customerId,accountId,accountType,amount);
        if(status== bankConstants.getSUCCESS_CODE()){
            transactionService.insertTransactionIntoDb(customerId,accountId,"NA",amount,currency,"WITHDRAWAL");
            transactionService.payBankFees(amount, bankConstants.getDEFAULT_BANKID());
        }
        return status;
    }

    public int makeDeposit(String customerId,String accountId,String accountType,double amount) throws Exception {
        int status = transactionService.makeDeposit(customerId,accountId,accountType,amount);
        if(status== bankConstants.getSUCCESS_CODE()){
            transactionService.insertTransactionIntoDb(customerId,accountId,"NA",amount,"USD","DEPOSIT");
            transactionService.payBankFees(amount, bankConstants.getDEFAULT_BANKID());
        }
        return status;
    }

    public int makePayment(String customerId,String accountId,String accountType,double amount,String receiverName) throws Exception {
        int status = transactionService.makePayment(customerId,accountId,accountType,amount);
        if(status== bankConstants.getSUCCESS_CODE()){
            transactionService.insertTransactionIntoDb(customerId,accountId,receiverName,amount,"USD","DEPOSIT");
            transactionService.payBankFees(amount, bankConstants.getDEFAULT_BANKID());
        }
        return status;
    }

    public int payLoan(String customerId,String accountId,String accountType,int loanId,double amount) throws Exception {
        int status = loanService.payLoan(customerId,accountId,loanId,amount,accountType);
        if(status== bankConstants.getSUCCESS_CODE()){
            String receiverName = "LOAN "+loanId;
            transactionService.insertTransactionIntoDb(customerId,accountId,receiverName,amount,"USD","LOAN PAYMENT");
            transactionService.payBankFees(amount, bankConstants.getDEFAULT_BANKID());
        }
        return status;
    }


}
