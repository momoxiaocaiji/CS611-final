package bankUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccount extends JFrame implements ActionListener {

    private JLabel account, accountID, pin, tips;
    private JTextField pinInput;
    private JButton create;

    public CreateAccount(String type, String id) {
        account = new JLabel("Account #:");
        account.setFont(new Font("Raleway", Font.BOLD, 15));

        accountID = new JLabel(id);
        accountID.setFont(new Font("Arial", Font.BOLD, 15));

        tips = new JLabel("Tips: Saving $100 at least, 20$ for fee");
        tips.setFont(new Font("Arial", Font.BOLD, 15));
        tips.setForeground(Color.red.brighter());

        pin = new JLabel("Pin:");
        pin.setFont(new Font("Raleway", Font.BOLD, 15));

        pinInput = new JTextField(20);
        pinInput.setFont(new Font("Arial", Font.BOLD, 13));

        create = new JButton("Create");
        create.setForeground(Color.BLACK);
        create.setFont(new Font("Raleway", Font.BOLD, 15));

        setLayout(null);

        account.setBounds(80,40,150,40);
        add(account);

        accountID.setBounds(250, 40, 200, 40);
        add(accountID);

        pin.setBounds(80, 100, 200, 40);
        add(pin);

        pinInput.setBounds(250, 100, 150, 40);
        add(pinInput);

        tips.setBounds(90,160,350,40);
        add(tips);

        create.setBounds(170, 220, 100, 30);
        add(create);

        getContentPane().setBackground(Color.WHITE);
        setTitle("Create    " + type + "    Account");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450,350);
        setLocation(600,250);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){}

    public static void main(String[] args){
        new CreateAccount("Checking", "111111111").setVisible(true);
    }
}
