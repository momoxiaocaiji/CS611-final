package model;

public class BankManager extends Person {
    private int managerId;

    public BankManager(String name, int personId, int managerId) {
        super(name, personId);
        this.managerId = managerId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "BankManager{" +
                "managerId=" + managerId +
                '}';
    }
}
