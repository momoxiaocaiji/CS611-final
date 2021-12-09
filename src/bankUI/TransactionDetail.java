package bankUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TransactionDetail extends JFrame implements ActionListener {

    private static Map<String, Double> cun;

    // 演示布局的相关按钮
    JLabel senderLabel = new JLabel("Sender Account：");

    JTextField senderAccount = new JTextField(20);

    JLabel currencyType = new JLabel("Type:");

    JComboBox<String> cType;

    JLabel balance = new JLabel("Balance：");

    JLabel balanceNum = new JLabel("");

    JLabel amount = new JLabel("Amount: ");

    JTextField amountNum = new JTextField(20);

    JLabel receiverLabel = new JLabel("Receiver Account：");

    JTextField receiverAccount = new JTextField(20);

    JButton execute = new JButton("execute");

    public TransactionDetail(int type) {

        cun = new HashMap<>();
        cun.put("USD", 1000.0);
        cun.put("CNY", 1000.0);
        cun.put("HKD", 1000.0);

        setLayout(new BorderLayout());

        JPanel jCenter = new JPanel();
        add(jCenter, BorderLayout.CENTER);


        senderLabel.setFont(new Font("Raleway", Font.BOLD, 20));

        currencyType.setFont(new Font("Raleway", Font.BOLD, 20));

        balance.setFont(new Font("Raleway", Font.BOLD, 20));

        balanceNum.setFont(new Font("Raleway", Font.BOLD, 20));

        amount.setFont(new Font("Raleway", Font.BOLD, 20));

        amount.setFont(new Font("Raleway", Font.BOLD, 20));

        receiverLabel.setFont(new Font("Raleway", Font.BOLD, 20));

        jCenter.setLayout(null);

        if (type != Constant.TRANSACTION_DEPOSIT) {
            senderLabel.setBounds(130, 80, 200, 30);
            jCenter.add(senderLabel);

            senderAccount.setBounds(350, 80, 400, 30);
            senderAccount.setBorder(BorderFactory.createEtchedBorder());
            jCenter.add(senderAccount);
        }

        currencyType.setBounds(130, 150, 200,  30);
        jCenter.add(currencyType);

        cType = new JComboBox<>(cun.keySet().toArray(new String[0]));
        cType.setBounds(350, 150, 150, 30);
        cType.addActionListener(this);
        cType.setSelectedIndex(0);
        jCenter.add(cType);

        balance.setBounds(130, 220, 200,30);
        jCenter.add(balance);

        balanceNum.setBounds(350, 220, 200,30);
        jCenter.add(balanceNum);

        amount.setBounds(130, 290, 200,30);
        jCenter.add(amount);

        amountNum.setBounds(350, 290, 400,30);
        amountNum.setBorder(BorderFactory.createEtchedBorder());
        jCenter.add(amountNum);

        if (type != Constant.TRANSACTION_WITHDRAWAL) {
            receiverLabel.setBounds(130, 360, 200,30);
            jCenter.add(receiverLabel);

            receiverAccount.setBounds(350, 360, 400,30);
            receiverAccount.setBorder(BorderFactory.createEtchedBorder());
            jCenter.add(receiverAccount);
        }

        //South
        JPanel jSouth = new JPanel();

        add(jSouth, BorderLayout.SOUTH);

        jSouth.setLayout(new FlowLayout(FlowLayout.TRAILING, 20, 25));

        execute.setFont(new Font("Raleway", Font.BOLD, 14));
        execute.setForeground(Color.BLACK);

        jSouth.add(execute);


        getContentPane().setBackground(Color.WHITE);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850,550);
        setLocation(400,150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        try {
            if (ae.getSource() == cType) {
                balanceNum.setText(cun.get(cType.getItemAt(cType.getSelectedIndex())).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new TransactionDetail(1).setVisible(true);
    }
}
