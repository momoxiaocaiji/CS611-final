package model;

import java.util.Date;

public class Loan {

    private int loanId;
    private String  customerId;
    private double principalAmount;
    private String currency;
    private int tenure;
    private double rateOfInterest;
    private Date loanCommenceDate;
    private int managerId;
    private int isLoanApproved;

    public Loan(int loanId, String customerId, double principalAmount, String currency, int tenure, double rateOfInterest, Date date, int managerId, int isLoanApproved) {
        this.loanId = loanId;
        this.customerId = customerId;
        this.principalAmount = principalAmount;
        this.currency = currency;
        this.tenure = tenure;
        this.rateOfInterest = rateOfInterest;
        this.loanCommenceDate = date;
        this.managerId = managerId;
        this.isLoanApproved = isLoanApproved;
    }

    public Loan(){}

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getLoanCommenceDate() {
        return loanCommenceDate;
    }

    public void setLoanCommenceDate(Date loanCommenceDate) {
        this.loanCommenceDate = loanCommenceDate;
    }

    public double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getTenure() {
        return tenure;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }

    public double getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(double rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }

    public Date getDate() {
        return loanCommenceDate;
    }

    public void setDate(Date date) {
        this.loanCommenceDate = date;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getIsLoanApproved() {
        return isLoanApproved;
    }

    public void setIsLoanApproved(int isLoanApproved) {
        this.isLoanApproved = isLoanApproved;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", customerId=" + customerId +
                ", principalAmount=" + principalAmount +
                ", currency='" + currency + '\'' +
                ", tenure=" + tenure +
                ", rateOfInterest=" + rateOfInterest +
                ", loanCommenceDate=" + loanCommenceDate +
                ", managerId=" + managerId +
                ", isLoanApproved=" + isLoanApproved +
                '}';
    }
}
