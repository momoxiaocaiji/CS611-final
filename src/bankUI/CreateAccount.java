package bankUI;

import controller.AccountController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

/**
 * create the saving / checking account
 */
public class CreateAccount extends JFrame implements ActionListener {

    private JLabel account, accountID, pin, tips;
    private JTextField pinInput;
    private JButton create;

    // DATA
    private String username;
    private String type;
    private Core core;

    // MVC
    private AccountController accountController = new AccountController();

    public CreateAccount(String type, String id, String username, Core core) {

        this.username = username;
        this.type = type;
        this.core = core;

        // -------------------------
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
        //add(account);

        accountID.setBounds(250, 40, 200, 40);
        //add(accountID);

        pin.setBounds(80, 100, 200, 40);
        add(pin);

        pinInput.setBounds(250, 100, 150, 40);
        add(pinInput);
        pinInput.setToolTipText("Should be 6-digit number!!");
        checkPin();

        tips.setBounds(90,160,350,40);
        add(tips);

        create.setBounds(170, 220, 100, 30);
        add(create);
        create.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setTitle("Create    " + type + "    Account");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450,350);
        setLocation(600,250);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        try {
            if (ae.getSource() == create){
                if (!validatePin(pinInput.getText())) {
                    JOptionPane.showMessageDialog(null, "Invalid pin!!");
                    return;
                }

                int returnCode = accountController.createNewCheckingOrSavingAccount(username,
                        type.toUpperCase(), Integer.parseInt(pinInput.getText()));
                if (returnCode == Constant.SUCCESS_CODE) {
                    JOptionPane.showMessageDialog(null, "Success!!");
                    core.fillInfo(accountController.getAccountInfoForCustomer(username));
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Please try again!!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * modify the pattern of pin Input according to the validation of pin
     */
    private void checkPin(){
        Document doc = pinInput.getDocument();
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!validatePin(pinInput.getText())) {
                    pinInput.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    pinInput.setBorder(BorderFactory.createEtchedBorder());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!validatePin(pinInput.getText())) {
                    pinInput.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    pinInput.setBorder(BorderFactory.createEtchedBorder());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!validatePin(pinInput.getText())) {
                    pinInput.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    pinInput.setBorder(BorderFactory.createEtchedBorder());
                }
            }
        });
    }

    /**
     * check the validation of pin
     * @param pin
     * @return
     */
    private boolean validatePin(String pin){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(pin).matches() && pin.length() == 6;
    }
}
