package model;

public class CustAccounts {

    private int customerId;
    private String[] accountIds;

    public CustAccounts(int customerId, String[] accountIds) {
        this.customerId = customerId;
        this.accountIds = accountIds;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String[] getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(String[] accountIds) {
        this.accountIds = accountIds;
    }
}
