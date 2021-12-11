package bankUI.entity;

public class Loan {
    public int amount;
    public String type;
    public Double interest;


    public Loan(){
        amount = 1000;
        type = "USD";
        interest = 15.0;
    }
}
