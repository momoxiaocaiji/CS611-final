package model;

public class Customer extends Person {
    private int customerId;

    public Customer(String name, int personId, int customerId) {
        super(name, personId);
        this.customerId = customerId;
    }

    public Customer() {
        super();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
