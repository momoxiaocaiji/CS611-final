package bankUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Registry extends JFrame implements ActionListener {

    private JLabel title, name, password, passwordConfirm, birthday, day, month, year, email;
    private JPasswordField passwordField, passwordConfirmField;
    JLabel l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15,l16;
    private JTextField nameInput, emailInput;
    JTextField t1,t2,t3,t4,t5,t6,t7;
    JButton b;
    JComboBox c1,c2,c3;


    public Registry() {

        title = new JLabel("APPLICATION FORM");
        title.setFont(new Font("Arial Black", Font.BOLD, 38));

        name = new JLabel("Name:");
        name.setFont(new Font("Raleway", Font.BOLD, 20));

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


        nameInput = new JTextField(20);
        nameInput.setFont(new Font("Raleway", Font.BOLD, 14));

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

        String[] days = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        c1 = new JComboBox(days);
        c1.setBackground(Color.WHITE);

        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        c2 = new JComboBox(months);
        c2.setBackground(Color.WHITE);

        String[] years = new String[200];
        for(int i = 0; i < 200; i++){
            years[i] = String.valueOf(i + 1990);
        }
        c3 = new JComboBox(years);
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

        name.setBounds(100,30,100,30);
        jpCenter.add(name);

        nameInput.setBounds(350,30,400,30);
        nameInput.setBorder(BorderFactory.createEtchedBorder());
        jpCenter.add(nameInput);

        password.setBounds(100,90,100,30);
        jpCenter.add(password);

        passwordField.setBounds(350,90,400,30);
        passwordField.setBorder(BorderFactory.createEtchedBorder());
        jpCenter.add(passwordField);

        passwordConfirm.setBounds(100,150,200,30);
        jpCenter.add(passwordConfirm);

        passwordConfirmField.setBounds(350,150,400,30);
        passwordConfirmField.setBorder(BorderFactory.createEtchedBorder());
        jpCenter.add(passwordConfirmField);

        birthday.setBounds(100,210,200,30);
        jpCenter.add(birthday);

        day.setBounds(350,210,40,30);
        jpCenter.add(day);

        c1.setBounds(390,210,60,30);
        jpCenter.add(c1);

        month.setBounds(460,210,50,30);
        jpCenter.add(month);

        c2.setBounds(510,210,100,30);
        jpCenter.add(c2);

        year.setBounds(620,210,40,30);
        jpCenter.add(year);

        c3.setBounds(660,210,90,30);
        jpCenter.add(c3);

        email.setBounds(100,270,200,30);
        jpCenter.add(email);

        emailInput.setBounds(350,270,400,30);
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

        setSize(850,750);
        setLocation(400,50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){}

    public static void main(String[] args){
        new Registry().setVisible(true);
    }
}
