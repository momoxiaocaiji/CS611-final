package bankUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Loan {
    int amount;
    String type;
    Double interest;


    public Loan(){
        amount = 1000;
        type = "USD";
        interest = 15.0;
    }
}

public class LoanDetail extends JFrame implements ActionListener {

    private static List<Loan> loanList = new ArrayList<>();

    JButton request = new JButton("request a loan");

    public LoanDetail() {

        loanList.add(new Loan());
        loanList.add(new Loan());

        request.setFont(new Font("Raleway", Font.BOLD, 20));

        setLayout(new BorderLayout());

        // north
        JPanel jNorth = new JPanel();
        jNorth.setLayout(new FlowLayout(FlowLayout.LEADING, 15, 10));
        jNorth.add(request);
        request.addActionListener(this);

        add(jNorth, BorderLayout.NORTH);

        // center
        JPanel jCenter = new JPanel();
        jCenter.setLayout(new GridLayout(loanList.size() + 1, 1));

        for(Loan l : loanList){
            // loan panel
            JPanel loanP = new JPanel();
            loanP.setBackground(Color.white);
            loanP.setBorder(BorderFactory.createTitledBorder("Loan "+ l.type));
            loanP.setLayout(new GridLayout(3, 2));
            jCenter.add(loanP);

            JLabel a = new JLabel("Amount: ");
            a.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
            JLabel aNum = new JLabel(String.valueOf(l.amount));
            aNum.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
            loanP.add(a);
            loanP.add(aNum);

            JLabel t = new JLabel("Type: ");
            t.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
            JLabel tT = new JLabel(l.type);
            tT.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
            loanP.add(t);
            loanP.add(tT);

            JLabel i = new JLabel("Interest: ");
            i.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
            JLabel iNum = new JLabel(l.interest.toString());
            iNum.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
            loanP.add(i);
            loanP.add(iNum);
        }
        JPanel empty = new JPanel();
        jCenter.add(empty);

        add(jCenter, BorderLayout.CENTER);


        getContentPane().setBackground(Color.WHITE);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550,750);
        setLocation(500,100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == request) {
                new TransactionDetail(Constant.TRANSACTION_LOAN).setVisible(true);
                setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new LoanDetail().setVisible(true);
    }
}