package model;

import java.util.Date;

public class Loan {

    private int loanId;
    private int customerId;
    private double principalAmount;
    private String currency;
    private int tenure;
    private double rateOfInterest;
    private Date loanCommenceDate;
    private int managerId;
    private boolean isLoanApproved;

    public Loan(int loanId, int customerId, double principalAmount, String currency, int tenure, double rateOfInterest, Date date, int managerId, boolean isLoanApproved) {
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

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public boolean isLoanApproved() {
        return isLoanApproved;
    }

    public void setLoanApproved(boolean loanApproved) {
        isLoanApproved = loanApproved;
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
