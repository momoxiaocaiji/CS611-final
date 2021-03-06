package bankUI;

import bankUI.component.LoanListPanel;
import model.Loan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Loan detail of the customer
 */
public class LoanDetail extends JFrame implements ActionListener {

    private List<Loan> loanList;
    private String username;
    private Core core;

    private JButton request = new JButton("request a loan");
    private LoanListPanel jCenter;

    public Core getCore() {
        return core;
    }

    public void setCore(Core core) {
        this.core = core;
        jCenter.setCore(core);
    }

    public LoanDetail(List<Loan> loanList, String username) {
        this.loanList = loanList;
        this.username = username;
        // -----------------------

        request.setFont(new Font("Raleway", Font.BOLD, 20));

        setLayout(new BorderLayout());

        // north
        JPanel jNorth = new JPanel();
        jNorth.setLayout(new FlowLayout(FlowLayout.LEADING, 15, 10));
        jNorth.add(request);
        request.addActionListener(this);

        add(jNorth, BorderLayout.NORTH);

        // center
        jCenter = new LoanListPanel(loanList, Constant.LOAN_CUSTOMER);
        jCenter.setUsername(username);

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
                TransactionDetail td = new TransactionDetail(Constant.TRANSACTION_LOAN, null);
                td.setUsername(username);
                td.setVisible(true);
                setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}