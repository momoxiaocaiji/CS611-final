package controller;

import model.BankConstants;
import model.Loan;
import service.LoanService;

import java.sql.Date;
import java.util.List;

public class LoanController {

    /*
    1. show active loans - done
    2. show approved loans - done
    3. show loans belonging to customer - done
    4. approve loans - done
    5. apply for loan - done
     */

    LoanService loanService = new LoanService();
    BankConstants bankConstants = new BankConstants();

    //function to show all loans
    public List<Loan> getAllLoans() throws Exception {
        return loanService.getAllLoans();
    }

    public List<Loan> getApprovedLoans() throws Exception {
        return loanService.getApprovedLoans();
    }

    public List<Loan> getLoansForCustomer(String customerId) throws Exception {
        return loanService.getLoansForCustomer(customerId);
    }

    public int makeLoanDecision(int loanId) throws Exception{
        return loanService.makeLoanDecision(loanId);
    }

    public int applyForLoan(String customerId, int managerId, double amount, int tenure, double rateOfInterest, Date loanDate) throws Exception {
        return loanService.applyForLoan(customerId,managerId,amount,tenure,rateOfInterest,loanDate);
    }

}
