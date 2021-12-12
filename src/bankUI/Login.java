package bankUI;

import controller.LoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {

    private JLabel welcome,userID,password;
    private JTextField userIDInput;
    private JPasswordField passwordField;
    private JButton signIn,clear,signUp;

    // MVC
    private LoginController loginController = new LoginController();

    public Login(){

        welcome = new JLabel("WELCOME TO ATM");
        welcome.setFont(new Font("Arial", Font.BOLD, 38));

        userID = new JLabel("UserID:");
        userID.setFont(new Font("Arial", Font.BOLD, 28));

        password = new JLabel("Password:");
        password.setFont(new Font("Arial", Font.BOLD, 28));

        userIDInput = new JTextField(15);
        passwordField = new JPasswordField(15);

        signIn = new JButton("SIGN IN");
        signIn.setForeground(Color.BLACK);

        clear = new JButton("CLEAR");
        clear.setBackground(Color.YELLOW);
        clear.setForeground(Color.BLACK);

        signUp = new JButton("SIGN UP");
        signUp.setBackground(Color.YELLOW);
        signUp.setForeground(Color.BLACK);

        setLayout(null);

        welcome.setBounds(175,50,450,200);
        add(welcome);

        userID.setBounds(125,150,375,200);
        add(userID);

        userIDInput.setBounds(300,235,230,30);
        add(userIDInput);

        password.setBounds(125,225,375,200);
        add(password);

        userIDInput.setFont(new Font("Arial", Font.BOLD, 14));


        passwordField.setFont(new Font("Arial", Font.BOLD, 14));
        passwordField.setBounds(300,310,230,30);
        add(passwordField);

        signIn.setFont(new Font("Arial", Font.BOLD, 14));
        signIn.setBounds(300,400,100,30);
        add(signIn);

        clear.setFont(new Font("Arial", Font.BOLD, 14));
        clear.setBounds(430,400,100,30);
        add(clear);

        signUp.setFont(new Font("Arial", Font.BOLD, 14));
        signUp.setBounds(300,450,230,30);
        add(signUp);


        signIn.addActionListener(this);
        clear.addActionListener(this);
        signUp.addActionListener(this);

        getContentPane().setBackground(Color.GRAY);

        setSize(750,650);
        setLocation(400,100);
        setVisible(true);



    }


    public void actionPerformed(ActionEvent ae){

        try{

            if(ae.getSource()==signIn){
                // todo
                if(!userIDInput.getText().equals("admin")){
                    new Core().setVisible(true);
                    setVisible(false);
                } else if (userIDInput.getText().equals("admin")) {
                    new Manager().setVisible(true);
                    setVisible(false);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect Card Number or Password");
                }
            }else if(ae.getSource()==clear){
                userIDInput.setText("");
                passwordField.setText("");
            }else if(ae.getSource()==signUp){
                new Registry().setVisible(true);
                setVisible(false);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("error: "+e);
        }

    }


    public static void main(String[] args){
        new Login().setVisible(true);
    }
}
