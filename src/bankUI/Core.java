package bankUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Core extends JFrame implements ActionListener {
    private JTabbedPane tabMenu;
    private JPanel info, stock, transaction;
    private JPanel checking, saving;

    private JButton deposit;
    JButton b1,b2,b3,b4,b5,b6,b7;

    public Core(){
        // info
        info =new JPanel();
        info.setLayout(new GridLayout (2,1));

        checking = new JPanel();
        checking.setBorder(BorderFactory.createTitledBorder("Checking"));


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

        b2 = new JButton("CASH WITHDRAWL");
        b2.setFont(new Font("System", Font.BOLD, 18));
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);

        b3 = new JButton("FAST CASH");
        b3.setFont(new Font("System", Font.BOLD, 18));
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.WHITE);

        b4 = new JButton("MINI STATEMENT");
        b4.setFont(new Font("System", Font.BOLD, 18));
        b4.setBackground(Color.BLACK);
        b4.setForeground(Color.WHITE);

        b5 = new JButton("PIN CHANGE");
        b5.setFont(new Font("System", Font.BOLD, 18));
        b5.setBackground(Color.BLACK);
        b5.setForeground(Color.WHITE);

        b6 = new JButton("BALANCE ENQUIRY");
        b6.setFont(new Font("System", Font.BOLD, 18));
        b6.setBackground(Color.BLACK);
        b6.setForeground(Color.WHITE);

        b7 = new JButton("EXIT");
        b7.setFont(new Font("System", Font.BOLD, 18));
        b7.setBackground(Color.BLACK);
        b7.setForeground(Color.WHITE);

        transaction.setLayout(null);

        deposit.setBounds(150,100,300,60);
        transaction.add(deposit);

        b2.setBounds(440,250,300,60);
        transaction.add(b2);

        b3.setBounds(40,360,300,60);
        transaction.add(b3);

        b4.setBounds(440,360,300,60);
        transaction.add(b4);

        b5.setBounds(40,470,300,60);
        transaction.add(b5);

        b6.setBounds(440,470,300,60);
        transaction.add(b6);

        b7.setBounds(240,600,300,60);
        transaction.add(b7);

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

    public void actionPerformed(ActionEvent ae){}

    public static void main(String[] args){
        new Core().setVisible(true);
    }
}
