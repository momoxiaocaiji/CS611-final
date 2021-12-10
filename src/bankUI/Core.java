package bankUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

class Amount {
    Map<String, Double> currencies;
    public Amount(){
        currencies = new HashMap<>();
        currencies.put("USD", 1000.00);
        currencies.put("CNY", 1000.00);
        currencies.put("HKD", 1000.00);
    }
}

public class Core extends JFrame implements ActionListener {

    private static List<Amount> amountList = new ArrayList<>();

    private JTabbedPane tabMenu;
    private JPanel info, stock, transaction;
    private JPanel checking, saving;

    private JButton deposit, create, withdrawal, transfer, loan;
    JButton b1,b2,b3,b4,b5,b6,b7;

    public Core(){
        amountList.add(new Amount());
        amountList.add(new Amount());
        // info
        info =new JPanel();
        info.setLayout(new GridLayout (2,1));

        checking = new JPanel();
        checking.setBorder(BorderFactory.createTitledBorder("Checking"));
        checking.setLayout(new GridLayout(amountList.size() + 1,1));
        for (Amount amount : amountList) {
            JPanel oneAccount = new JPanel();
            oneAccount.setBorder(BorderFactory.createTitledBorder("Amount"));
            oneAccount.setLayout(new GridLayout(amount.currencies.size(), 2, 0 , 5));
            checking.add(oneAccount);
            for (String type : amount.currencies.keySet()) {
                JLabel t = new JLabel(type);
                t.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                JLabel b = new JLabel(String.valueOf(amount.currencies.get(type)));
                b.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                oneAccount.add(t);
                oneAccount.add(b);
            }
        }
        create = new JButton("+ Create New Account");
        create.addActionListener(this);
        checking.add(create);


        saving = new JPanel();
        saving.setBorder(BorderFactory.createTitledBorder("Saving"));


        info.add(checking);
        info.add(saving);

        // stock
        stock=new JPanel();

        // transaction
        transaction=new JPanel();
        deposit = new JButton("DEPOSIT");
        deposit.setFont(new Font("System", Font.BOLD, 18));
        deposit.setForeground(Color.BLACK);

        withdrawal = new JButton("WITHDRAWAL");
        withdrawal.setFont(new Font("System", Font.BOLD, 18));
        withdrawal.setForeground(Color.BLACK);

        transfer = new JButton("TRANSFER");
        transfer.setFont(new Font("System", Font.BOLD, 18));
        transfer.setForeground(Color.BLACK);

        loan = new JButton("LOAN");
        loan.setFont(new Font("System", Font.BOLD, 18));
        loan.setForeground(Color.BLACK);

        transaction.setLayout(null);

        deposit.setBounds(40,200,300,60);
        transaction.add(deposit);
        deposit.addActionListener(this);

        withdrawal.setBounds(440,200,300,60);
        transaction.add(withdrawal);
        withdrawal.addActionListener(this);

        transfer.setBounds(40,400,300,60);
        transaction.add(transfer);
        transfer.addActionListener(this);

        loan.setBounds(440,400,300,60);
        transaction.add(loan);
        loan.addActionListener(this);

        //
        tabMenu = new JTabbedPane();
        tabMenu.setSize(850, 750);
        tabMenu.add("Accounts",info);
        tabMenu.add("Stock",stock);
        tabMenu.add("Transaction",transaction);

        add(tabMenu);

        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850,750);
        setLocation(400,50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        try {
            if (ae.getSource() == create){
                new CreateAccount("Checking", "10000000").setVisible(true);
                //setVisible(false);
            } else if (ae.getSource() == deposit) {
                new TransactionDetail(Constant.TRANSACTION_DEPOSIT).setVisible(true);
            } else if (ae.getSource() == withdrawal) {
                new TransactionDetail(Constant.TRANSACTION_WITHDRAWAL).setVisible(true);
            } else if (ae.getSource() == transfer) {
                new TransactionDetail(Constant.TRANSACTION_TRANSFER).setVisible(true);
            } else if (ae.getSource() == loan) {
                new LoanDetail().setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error!!!");
        }
    }

    public static void main(String[] args){

        new Core().setVisible(true);
    }
}
