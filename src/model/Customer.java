package model;

public class Customer extends Person {
    private String customerId;

    public Customer(String name, int personId, String customerId) {
        super(name, personId);
        this.customerId = customerId;
    }

    public Customer() {
        super();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
