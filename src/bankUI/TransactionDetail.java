package bankUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionDetail extends JFrame implements ActionListener {

    // 演示布局的相关按钮
    JLabel usernameLabel = new JLabel("Sender Account：");

    JTextField usernameText = new JTextField("");

    JLabel usernameMsg = new JLabel("Type:");

    JLabel pwdLabel = new JLabel("Balance：");

    JPasswordField pwdText = new JPasswordField("");

    JLabel pwdMsg = new JLabel("Amount: ");

    JLabel receiverLabel = new JLabel("Receiver Account：");

    public TransactionDetail(String type) {

        // 创建网格包布局管理器
        GridBagLayout gridbag = new GridBagLayout();

        // 创建网格包约束器
        GridBagConstraints constraints = new GridBagConstraints();

        // 设置布局
        setLayout(gridbag);

        // 网格包约束器的默认填充方式
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(20, 20, 20 , 20);
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.anchor = GridBagConstraints.NORTH;

        gridbag.setConstraints(usernameLabel, constraints);
        add(usernameLabel);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(usernameText, constraints);
        add(usernameText);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(usernameMsg, constraints);
        add(usernameMsg);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(pwdLabel, constraints);
        add(pwdLabel);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(pwdText, constraints);
        add(pwdText);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(pwdMsg, constraints);
        add(pwdMsg);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(receiverLabel, constraints);
        add(receiverLabel);

        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850,750);
        setLocation(400,50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){}

    public static void main(String[] args){
        new TransactionDetail("Checking").setVisible(true);
    }
}
