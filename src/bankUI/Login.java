package bankUI;

import Service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {

    JLabel l1,l2,l3;
    JTextField tf1;
    JPasswordField pf2;
    JButton b1,b2,b3;

    private UserService userService;

    public Login(){

        l1 = new JLabel("WELCOME TO ATM");
        l1.setFont(new Font("Arial", Font.BOLD, 38));

        l2 = new JLabel("Username:");
        l2.setFont(new Font("Arial", Font.BOLD, 28));

        l3 = new JLabel("Password:");
        l3.setFont(new Font("Arial", Font.BOLD, 28));

        tf1 = new JTextField(15);
        pf2 = new JPasswordField(15);

        b1 = new JButton("SIGN IN");
        b1.setBackground(Color.YELLOW);
        b1.setForeground(Color.BLACK);

        b2 = new JButton("CLEAR");
        b2.setBackground(Color.YELLOW);
        b2.setForeground(Color.BLACK);

        b3 = new JButton("SIGN UP");
        b3.setBackground(Color.YELLOW);
        b3.setForeground(Color.BLACK);

        setLayout(null);

        l1.setBounds(175,50,450,200);
        add(l1);

        l2.setBounds(125,150,375,200);
        add(l2);

        tf1.setBounds(300,235,230,30);
        add(tf1);

        l3.setBounds(125,225,375,200);
        add(l3);

        tf1.setFont(new Font("Arial", Font.BOLD, 14));


        pf2.setFont(new Font("Arial", Font.BOLD, 14));
        pf2.setBounds(300,310,230,30);
        add(pf2);

        b1.setFont(new Font("Arial", Font.BOLD, 14));
        b1.setBounds(300,400,100,30);
        add(b1);

        b2.setFont(new Font("Arial", Font.BOLD, 14));
        b2.setBounds(430,400,100,30);
        add(b2);

        b3.setFont(new Font("Arial", Font.BOLD, 14));
        b3.setBounds(300,450,230,30);
        add(b3);


        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        getContentPane().setBackground(Color.GRAY);

        setSize(750,650);
        setLocation(400,100);
        setVisible(true);



    }


    public void actionPerformed(ActionEvent ae){

        try{

            if(ae.getSource()==b1){
                // todo
                if(!tf1.getText().equals("admin")){
                    new Core().setVisible(true);
                    setVisible(false);
                } else if (tf1.getText().equals("admin")) {
                    new Manager().setVisible(true);
                    setVisible(false);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect Card Number or Password");
                }
            }else if(ae.getSource()==b2){
                tf1.setText("");
                pf2.setText("");
            }else if(ae.getSource()==b3){
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
