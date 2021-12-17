package service;

import controller.DbController;
import model.BankConstants;
import model.Customer;
import model.Loan;
import model.SavingAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanService {

    DbController dbController = new DbController();

    AccountService accountService = new AccountService();

    BankConstants bankConstants = new BankConstants();

    LoginService loginService = new LoginService();

    //function to get all loans
    public List<Loan> getAllLoans() throws Exception{
        Connection connection = dbController.connectToDb();
        String query = "select * from loan;";
        List<Loan> loans = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            loans.add(createLoanObject(rs));
        }
        return loans;
    }

    //function to getApproved loans
    public List<Loan> getApprovedLoans() throws Exception{
        Connection connection = dbController.connectToDb();
        String query = "select * from loan where isLoanApproved=1";
        List<Loan> loans = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            loans.add(createLoanObject(rs));
        }
        return loans;
    }

    /*
    function to make loan decision
    approves loan if balance in savings account is min 1/10th of principal requested
     */
    public int makeLoanDecision(int loanId) throws Exception {
        Connection connection = dbController.connectToDb();
        String query = "select * from loan where loanId="+loanId;
        Loan loan = new Loan();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            loan = createLoanObject(rs);
        }

        //get savings account balance for customer
        String customerId = loan.getCustomerId();
        Customer customer = loginService.getCustomerDetails(customerId);

        List<Object> accounts = accountService.getAccountInfoForCustomer(customer);
        if(accounts==null || accounts.size()==0|| accounts.isEmpty()) {
            //no savings account, reject loan
            String update = "Update loan set isLoanApproved=false where loanId="+loan.getLoanId()+";";
            statement.executeUpdate(update);
            System.out.println("Loan rejected");
            return bankConstants.getREJECT_LOAN_CODE();
        }

        double totalAmt = 0;
        for(Object obj : accounts) {
            if(obj instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount)obj;
                totalAmt+=savingAccount.getAmount();
            }
        }

        if(totalAmt>=loan.getPrincipalAmount()/10) {
            //minimum 10% balance in sum of saving accounts
            String success = "Update loan set isLoanApproved=true where loanId="+loan.getLoanId()+";";
            statement.executeUpdate(success);
            System.out.println("Loan approved");
            return bankConstants.getAPPROVE_LOAN_CODE();
        }else {
            String update = "Update loan set isLoanApproved=false where loanId="+loan.getLoanId()+";";
            statement.executeUpdate(update);
            System.out.println("Loan rejected");
            return bankConstants.getREJECT_LOAN_CODE();
        }
    }

    //function to get loans belonging to a customer
    public List<Loan> getLoansForCustomer(String customerId) throws Exception {
        Connection connection = dbController.connectToDb();
        String query = "select * from loan where customerId='"+customerId+"';";
        List<Loan> loans = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            loans.add(createLoanObject(rs));
        }
        return loans;
    }

    //function to apply for loan
    public int applyForLoan(String customerId, int managerId, double amount, int tenure, double rateOfInterest, Date loanDate) throws Exception {
        Loan loan = new Loan();
        Connection connection = dbController.connectToDb();
        String query = "INSERT into loan(customerId,managerId,amount,currency,loanCommenceDate,rateOfInterest,tenure,isLoanApproved)"+" VALUES(?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,customerId);
        preparedStatement.setString(2,String.valueOf(managerId));
        preparedStatement.setDouble(3,amount);
        preparedStatement.setString(4,"USD");
        preparedStatement.setDate(5,loanDate);
        preparedStatement.setDouble(6,rateOfInterest);
        preparedStatement.setInt(7,tenure);
        preparedStatement.setInt(8,0);

        int status = preparedStatement.executeUpdate();
        if(status>0)
            return bankConstants.getSUCCESS_CODE();
        else
            return bankConstants.getERROR();
    }

    private Loan createLoanObject(ResultSet resultSet) throws Exception {
        Loan loan = new Loan();
        loan.setLoanId(resultSet.getInt("loanId"));
        loan.setLoanCommenceDate(resultSet.getDate("loanCommenceDate"));
        loan.setIsLoanApproved(resultSet.getInt("isLoanApproved"));
        loan.setCustomerId(resultSet.getString("customerId"));
        loan.setCurrency(resultSet.getString("currency"));
        loan.setPrincipalAmount(resultSet.getDouble("amount"));
        loan.setManagerId(Integer.valueOf(resultSet.getString("managerId")));
        loan.setTenure(resultSet.getInt("tenure"));
        loan.setRateOfInterest(resultSet.getDouble("rateOfInterest"));
        loan.setLoanId(resultSet.getInt("loanId"));

        return loan;

    }

    public void deleteLoanFromDb(Connection connection,int loanId) throws SQLException {
        String query = "delete from loan where loanId="+loanId;
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public int payLoan(String customerId,String accountId,int loanId,double amount,String accountType) throws Exception {
        //get loan details
        Connection connection = dbController.connectToDb();
        String query = "select * from loan where loanId="+loanId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        Loan loan = null;
        if(resultSet.next()){
            loan = createLoanObject(resultSet);
        }else{
            return bankConstants.getLOAN_NOT_FOUND();
        }

        double totalPaymentPending = ((loan.getPrincipalAmount()*loan.getRateOfInterest()*loan.getTenure())/100)+loan.getPrincipalAmount();

        if(totalPaymentPending<=0) {
            //delete loan from db
            deleteLoanFromDb(connection,loanId);
            return bankConstants.getSUCCESS_CODE();
        }

        if(accountType.equalsIgnoreCase("saving")) {
            String query1 = "select * from saving_account where customerId='"+customerId+"' and accountId='"+accountId+"';";
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(query1);
            double currentBalance = 0;
            if(resultSet1.next()){
                currentBalance=resultSet1.getDouble("amount");
                if(currentBalance<amount){
                    return bankConstants.getINSUFFICIENT_FUNDS();
                }else{
                    double amt = currentBalance-amount;
                    String update = "update saving_account set amount="+amt+" where customerId='"+customerId+"' and accountId='"+accountId+"';";
                    statement.executeUpdate(update);
                }
            }else{
                return bankConstants.getACCOUNT_NOT_FOUND();
            }
        }else {
            String query1 = "select * from checking_account where customerId='"+customerId+"' and accountId='"+accountId+"';";
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(query1);
            double currentBalance = 0;
            if(resultSet1.next()){
                currentBalance=resultSet1.getDouble("amount");
                if(currentBalance<amount){
                    return bankConstants.getINSUFFICIENT_FUNDS();
                }else{
                    double amt = currentBalance-amount;
                    String update = "update checking_account set amount="+amt+" where customerId='"+customerId+"' and accountId='"+accountId+"';";
                    statement.executeUpdate(update);
                }
            }else{
                return bankConstants.getACCOUNT_NOT_FOUND();
            }
        }

        //reduce amount in loan
        double outstanding = totalPaymentPending-amount;
        String payLoan = "update loan set amount="+outstanding+" where loanId="+loanId+";";
        statement.executeUpdate(payLoan);

        return bankConstants.getSUCCESS_CODE();


    }
}
