package bankUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockDetail extends JFrame implements ActionListener {

    private JLabel stock, stockCode, open;
    private JTextField openPrice;
    private JButton create;

    public StockDetail(String code) {

        stock = new JLabel("Stock Code:");
        stock.setFont(new Font("Raleway", Font.BOLD, 15));

        stockCode = new JLabel(code);
        stockCode.setFont(new Font("Arial", Font.BOLD, 15));

        open = new JLabel("Open Price:");
        open.setFont(new Font("Raleway", Font.BOLD, 15));

        openPrice = new JTextField(20);
        openPrice.setFont(new Font("Arial", Font.BOLD, 13));

        create = new JButton("Create");
        create.setForeground(Color.BLACK);
        create.setFont(new Font("Raleway", Font.BOLD, 15));

        setLayout(null);

        stock.setBounds(80,60,150,40);
        add(stock);

        stockCode.setBounds(250, 60, 200, 40);
        add(stockCode);

        open.setBounds(80, 140, 200, 40);
        add(open);

        openPrice.setBounds(250, 140, 150, 40);
        add(openPrice);

        create.setBounds(170, 240, 100, 30);
        add(create);

        getContentPane().setBackground(Color.WHITE);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450,350);
        setLocation(600,250);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new StockDetail("ccccccc").setVisible(true);
    }
}
