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

public class CreateSecuritiesAccount extends JFrame implements ActionListener {

    private JLabel account, accountID, investmentAmount, tips;
    private JTextField investmentAmountInput;
    private JButton create;

    // DATA
    private String username;
    private String type;
    private Core core;

    // MVC
    private AccountController accountController = new AccountController();

    public CreateSecuritiesAccount(String type, String id, String username, Core core) {

        this.username = username;
        this.type = type;
        this.core = core;

        // -------------------------
        account = new JLabel("Account #:");
        account.setFont(new Font("Raleway", Font.BOLD, 15));

        accountID = new JLabel(id);
        accountID.setFont(new Font("Arial", Font.BOLD, 15));

        tips = new JLabel("Tips: Enter an investment > 1000");
        tips.setFont(new Font("Arial", Font.BOLD, 15));
        tips.setForeground(Color.red.brighter());

        investmentAmount = new JLabel("Investment:");
        investmentAmount.setFont(new Font("Raleway", Font.BOLD, 15));

        investmentAmountInput = new JTextField(20);
        investmentAmountInput.setFont(new Font("Arial", Font.BOLD, 13));

        create = new JButton("Create");
        create.setForeground(Color.BLACK);
        create.setFont(new Font("Raleway", Font.BOLD, 15));

        setLayout(null);

        account.setBounds(80,40,150,40);
        add(account);

        accountID.setBounds(250, 40, 200, 40);
        add(accountID);

        investmentAmount.setBounds(80, 100, 200, 40);
        add(investmentAmount);

        investmentAmountInput.setBounds(250, 100, 150, 40);
        add(investmentAmountInput);
        investmentAmountInput.setToolTipText("Should be >1000!");
        checkInvestmentAmountInput();

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
                if (!validateInvestmentAmountInput(investmentAmountInput.getText())) {
                    JOptionPane.showMessageDialog(null, "Invalid amount!!");
                    return;
                }

                int returnCode = accountController.createSecuritiesAccount(username, Double.parseDouble(investmentAmountInput.getText()));
                if (returnCode == Constant.SUCCESS_CODE) {
                    JOptionPane.showMessageDialog(null, "Success!!");
                    //TODO new method to fill info
                    //core.fillInfo(accountController.getSecuritiesAccountInfo(username));
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Please try again!!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void checkInvestmentAmountInput(){
        Document doc = investmentAmountInput.getDocument();
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!validateInvestmentAmountInput(investmentAmountInput.getText())) {
                	investmentAmountInput.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                	investmentAmountInput.setBorder(BorderFactory.createEtchedBorder());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!validateInvestmentAmountInput(investmentAmountInput.getText())) {
                	investmentAmountInput.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                	investmentAmountInput.setBorder(BorderFactory.createEtchedBorder());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!validateInvestmentAmountInput(investmentAmountInput.getText())) {
                	investmentAmountInput.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                	investmentAmountInput.setBorder(BorderFactory.createEtchedBorder());
                }
            }
        });
    }

    private boolean validateInvestmentAmountInput(String investment){
        Pattern pattern = Pattern.compile("[0-9]*");
        boolean checkValue = Double.parseDouble(investment)>=1000;
        return pattern.matcher(investment).matches() && checkValue;
    }
}
