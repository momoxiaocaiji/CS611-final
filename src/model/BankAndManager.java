package model;

public class BankAndManager {
    private int bankId;
    private int managerId;

    public BankAndManager(int bankId, int managerId) {
        this.bankId = bankId;
        this.managerId = managerId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "BankAndManager{" +
                "bankId=" + bankId +
                ", managerId=" + managerId +
                '}';
    }
}
