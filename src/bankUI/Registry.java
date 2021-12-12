package bankUI;

import controller.LoginController;
import model.Customer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.sql.Date;
import java.util.regex.Pattern;

public class Registry extends JFrame implements ActionListener {

    private JLabel title, realName, username, password, passwordConfirm, birthday, day, month, year, email;
    private JPasswordField passwordField, passwordConfirmField;
    private JLabel l16;
    private JTextField realNameInput, usernameInput, emailInput;
    private JButton b;
    private JComboBox c1, c2, c3;
    private Map<String, Integer> months;

    // MVC

    private final LoginController loginController = new LoginController();


    public Registry() {

        title = new JLabel("APPLICATION FORM");
        title.setFont(new Font("Arial Black", Font.BOLD, 38));

        realName = new JLabel("Real Name:");
        realName.setFont(new Font("Raleway", Font.BOLD, 20));

        username = new JLabel("Username:");
        username.setFont(new Font("Raleway", Font.BOLD, 20));

        password = new JLabel("Password");
        password.setFont(new Font("Raleway", Font.BOLD, 20));

        passwordConfirm = new JLabel("Confirm password");
        passwordConfirm.setFont(new Font("Raleway", Font.BOLD, 20));

        birthday = new JLabel("Date of Birth:");
        birthday.setFont(new Font("Raleway", Font.BOLD, 20));

        email = new JLabel("Email Address:");
        email.setFont(new Font("Raleway", Font.BOLD, 20));

        day = new JLabel("Day");
        day.setFont(new Font("Raleway", Font.BOLD, 14));

        month = new JLabel("Month");
        month.setFont(new Font("Raleway", Font.BOLD, 14));

        year = new JLabel("Year");
        year.setFont(new Font("Raleway", Font.BOLD, 14));


        realNameInput = new JTextField(20);
        realNameInput.setFont(new Font("Raleway", Font.BOLD, 14));

        usernameInput = new JTextField(20);
        usernameInput.setFont(new Font("Raleway", Font.BOLD, 14));

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Raleway", Font.BOLD, 14));

        passwordConfirmField = new JPasswordField(20);
        passwordConfirmField.setFont(new Font("Raleway", Font.BOLD, 14));

        emailInput = new JTextField();
        emailInput.setFont(new Font("Raleway", Font.BOLD, 14));

        b = new JButton("Finish");
        b.setFont(new Font("Raleway", Font.BOLD, 14));
        b.setForeground(Color.BLACK);

        l16 = new JLabel("*You'd better take a screenshot for this page:");
        l16.setFont(new Font("Raleway", Font.ITALIC, 20));

        String[] days = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        c1 = new JComboBox(days);
        c1.setSelectedIndex(0);
        c1.setBackground(Color.WHITE);

        String[] monthsName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        months = new HashMap();
        for (int i = 0; i < monthsName.length; i++) {
            months.put(monthsName[i], i + 1);
        }
        c2 = new JComboBox(monthsName);
        c2.setSelectedIndex(0);
        c2.addActionListener(this);
        c2.setBackground(Color.WHITE);

        String[] years = new String[400];
        for (int i = 0; i < 400; i++) {
            years[i] = String.valueOf(i + 1900);
        }
        c3 = new JComboBox(years);
        c3.setSelectedIndex(0);
        c3.setBackground(Color.WHITE);


        setLayout(new BorderLayout(0, 10));
        JPanel jpNorth = new JPanel();
        add(jpNorth, BorderLayout.NORTH);
        jpNorth.add(title);

        // Center
        JPanel jpCenter = new JPanel();
        jpCenter.setBackground(Color.WHITE);
        jpCenter.setLayout(null);
        jpCenter.setBorder(BorderFactory.createRaisedBevelBorder());
        add(jpCenter, BorderLayout.CENTER);

        realName.setBounds(100, 30, 200, 30);
        jpCenter.add(realName);

        realNameInput.setBounds(350, 30, 400, 30);
        realNameInput.setBorder(BorderFactory.createEtchedBorder());
        jpCenter.add(realNameInput);

        username.setBounds(100, 90, 200, 30);
        jpCenter.add(username);

        usernameInput.setBounds(350, 90, 400, 30);
        usernameInput.setBorder(BorderFactory.createEtchedBorder());
        jpCenter.add(usernameInput);

        password.setBounds(100, 150, 100, 30);
        jpCenter.add(password);

        passwordField.setBounds(350, 150, 400, 30);
        passwordField.setBorder(BorderFactory.createEtchedBorder());
        JProgressBar jb = new JProgressBar(0,150);
        jb.setBounds(350, 175, 400, 30);
        verifyThePassword(jb);
        jpCenter.add(jb);
        jpCenter.add(passwordField);

        passwordConfirm.setBounds(100, 210, 200, 30);
        jpCenter.add(passwordConfirm);

        passwordConfirmField.setBounds(350, 210, 400, 30);
        passwordConfirmField.setBorder(BorderFactory.createEtchedBorder());
        confirmThePassword();
        jpCenter.add(passwordConfirmField);

        birthday.setBounds(100, 270, 200, 30);
        jpCenter.add(birthday);

        year.setBounds(350, 270, 40, 30);
        jpCenter.add(year);

        c3.setBounds(390, 270, 90, 30);
        jpCenter.add(c3);

        month.setBounds(490, 270, 50, 30);
        jpCenter.add(month);

        c2.setBounds(540, 270, 100, 30);
        jpCenter.add(c2);

        day.setBounds(650, 270, 40, 30);
        jpCenter.add(day);

        c1.setBounds(690, 270, 80, 30);
        jpCenter.add(c1);

        email.setBounds(100, 330, 200, 30);
        jpCenter.add(email);

        emailInput.setBounds(350, 330, 400, 30);
        emailInput.setBorder(BorderFactory.createEtchedBorder());
        jpCenter.add(emailInput);


        // South
        JPanel jpSouth = new JPanel();
        add(jpSouth, BorderLayout.SOUTH);
        jpSouth.setBackground(Color.darkGray);
        jpSouth.add(l16);
        jpSouth.add(b);

        b.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);

        setSize(850, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == b) {

                if (!Arrays.equals(passwordField.getPassword(), passwordConfirmField.getPassword())) {
                    JOptionPane.showMessageDialog(null, "Please confirm your password");
                    return;
                }

                // create new customer
                Customer customer = new Customer();
                customer.setName(realNameInput.getText());
                customer.setEmail(emailInput.getText());
                Date date = Date.valueOf(c3.getSelectedItem().toString() + "-" +
                        months.get(c2.getSelectedItem().toString()) + "-" +
                        c1.getSelectedItem().toString());
                customer.setDob(date);

                customer.setPersonId(Objects.hash(customer.getName() + customer.getEmail() + customer.getDob()));
                customer.setCustomerId(Objects.hash("CUST", customer.getPersonId()));

                int returnCode = loginController.signUpCustomer(customer, Arrays.toString(passwordField.getPassword()));

                if (returnCode == Constant.SIGN_UP_OK) {
                    JOptionPane.showMessageDialog(null, "Registration Success!!!");
                    new Login().setVisible(true);
                    setVisible(false);
                } else if (returnCode == Constant.SIGN_UP_DUPLICATE) {
                    JOptionPane.showMessageDialog(null, "Username repeat!!!");
                } else if (returnCode == Constant.SIGN_UP_DATABASE_ERROR || returnCode == Constant.SIGN_UP_ERROR) {
                    JOptionPane.showMessageDialog(null, "Something wrong! Please try again!");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmThePassword() {
        Document doc = passwordConfirmField.getDocument();
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!Arrays.equals(passwordField.getPassword(), passwordConfirmField.getPassword())) {
                    passwordConfirmField.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    passwordConfirmField.setBorder(BorderFactory.createEtchedBorder());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!Arrays.equals(passwordField.getPassword(), passwordConfirmField.getPassword())) {
                    passwordConfirmField.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    passwordConfirmField.setBorder(BorderFactory.createEtchedBorder());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!Arrays.equals(passwordField.getPassword(), passwordConfirmField.getPassword())) {
                    passwordConfirmField.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    passwordConfirmField.setBorder(BorderFactory.createEtchedBorder());
                }
            }
        });
    }

    private int calculateTheStrength(String password) {
        int point = 0;
        Pattern patternNum = Pattern.compile("[0-9]");
        Pattern patternCh = Pattern.compile("[a-zA-Z]");
        Pattern patternSym = Pattern.compile("[!@#$%&*()_+=|<>?{}~-]");
        if (patternNum.matcher(password).find()) {
            point++;
        }
        if (patternCh.matcher(password).find()) {
            point++;
        }
        if (patternSym.matcher(password).find()) {
            point++;
        }
        return point;
    }

    private void verifyThePassword(JProgressBar jb) {
        Document d = passwordField.getDocument();
        d.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (calculateTheStrength(Arrays.toString(passwordField.getPassword())) == 1) {
                    jb.setValue(50);
                    jb.setStringPainted(true);
                    jb.setString("Weak");
                } else if (calculateTheStrength(Arrays.toString(passwordField.getPassword())) == 2) {
                    jb.setValue(100);
                    jb.setStringPainted(true);
                    jb.setString("Medium");
                } else if (calculateTheStrength(Arrays.toString(passwordField.getPassword())) == 3) {
                    jb.setValue(150);
                    jb.setStringPainted(true);
                    jb.setString("Strong");
                } else {
                    jb.setValue(0);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (calculateTheStrength(Arrays.toString(passwordField.getPassword())) == 1) {
                    jb.setValue(50);
                    jb.setStringPainted(true);
                    jb.setString("Weak");
                } else if (calculateTheStrength(Arrays.toString(passwordField.getPassword())) == 2) {
                    jb.setValue(100);
                    jb.setStringPainted(true);
                    jb.setString("Medium");
                } else if (calculateTheStrength(Arrays.toString(passwordField.getPassword())) == 3) {
                    jb.setValue(150);
                    jb.setStringPainted(true);
                    jb.setString("Strong");
                } else {
                    jb.setValue(0);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (calculateTheStrength(Arrays.toString(passwordField.getPassword())) == 1) {
                    jb.setValue(50);
                    jb.setStringPainted(true);
                    jb.setString("Weak");
                } else if (calculateTheStrength(Arrays.toString(passwordField.getPassword())) == 2) {
                    jb.setValue(100);
                    jb.setStringPainted(true);
                    jb.setString("Medium");
                } else if (calculateTheStrength(Arrays.toString(passwordField.getPassword())) == 3) {
                    jb.setValue(150);
                    jb.setStringPainted(true);
                    jb.setString("Strong");
                } else {
                    jb.setValue(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        new Registry().setVisible(true);
    }
}
