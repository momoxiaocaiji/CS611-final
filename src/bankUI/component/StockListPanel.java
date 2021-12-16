package bankUI.component;

import bankUI.Constant;
import bankUI.Core;
import bankUI.StockDetail;
import bankUI.entity.Stock;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StockListPanel extends JPanel{
    private List<Stock> stockList;
    private int type;
    private Core assoCore;
    private List<String> testData;

    public JFrame getAssoFrame() {
        return assoCore;
    }

    public void setAssoFrame(Core assoFrame) {
        this.assoCore = assoFrame;
    }

    public List<String> getTestData() {
        return testData;
    }

    public void setTestData(List<String> testData) {
        this.testData = testData;
    }

    public StockListPanel(List<Stock> stockList, int type) {
        this.stockList = stockList;
        this.type = type;
        fillPanel();
    }

    public StockListPanel(int type) {
        this(new ArrayList<>(), type);
    }

    public StockListPanel(){
        this(Constant.STOCK_MANAGER_CHECK);
    }

    public void resetData(List<Stock> stockList) {
        this.stockList = stockList;
        this.removeAll();
        fillPanel();
    }

    private void fillPanel(){
        setLayout(new GridLayout(stockList.size() + 1, 1));
        for(Stock s : stockList){
            // loan panel
            JPanel stockP = new JPanel();
            stockP.setLayout(new BorderLayout());
            stockP.setBorder(BorderFactory.createTitledBorder("Stock " + s.getName()));
            add(stockP, BorderLayout.CENTER);

            JPanel detail = new JPanel();

            detail.setBackground(Color.white);
            detail.setLayout(new GridLayout(2, 4));
            stockP.add(detail,BorderLayout.CENTER);

            JLabel open = new JLabel("Open: ");
            open.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
            JLabel openPrice = new JLabel(String.valueOf(s.getOpen()));
            openPrice.setFont(new Font("Arial", Font.PLAIN, 16));
            detail.add(open);
            detail.add(openPrice);

            JLabel current = new JLabel("Current: ");
            current.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
            JTextField currentPrice = new JTextField(String.valueOf(s.getCurrent()), 20);
            if (type != Constant.STOCK_MANAGER_MODIFY) {
                currentPrice.setEditable(false);
            }
            currentPrice.setFont(new Font("Arial", Font.PLAIN, 16));
            if (s.getCurrent() > s.getOpen()) {
                currentPrice.setForeground(Color.GREEN);
            } else if (s.getCurrent() < s.getOpen()) {
                currentPrice.setForeground(Color.red);
            }
            detail.add(current);
            detail.add(currentPrice);

            JLabel high = new JLabel("High: ");
            high.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
            JLabel highPrice = new JLabel(String.valueOf(s.getHigh()));
            highPrice.setFont(new Font("Arial", Font.PLAIN, 16));
            detail.add(high);
            detail.add(highPrice);

            JLabel low = new JLabel("Low: ");
            low.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
            JLabel lowPrice = new JLabel(String.valueOf(s.getLow()));
            lowPrice.setFont(new Font("Arial", Font.PLAIN, 16));
            detail.add(low);
            detail.add(lowPrice);

            if (type == Constant.STOCK_CUSTOMER_SALE || type == Constant.STOCK_MANAGER_CHECK) {
                detail.setLayout(new GridLayout(3, 4));
                JLabel cost = new JLabel("Cost: ");
                cost.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
                JLabel costPrice = new JLabel(String.valueOf(s.getCost()));
                costPrice.setFont(new Font("Arial", Font.BOLD, 16));
                detail.add(cost);
                detail.add(costPrice);

                JLabel num = new JLabel("Num: ");
                num.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
                JLabel numN = new JLabel(String.valueOf(s.getNum()));
                numN.setFont(new Font("Arial", Font.BOLD, 16));
                detail.add(num);
                detail.add(numN);
            }

            if (type == Constant.STOCK_CUSTOMER_BUY || type == Constant.STOCK_CUSTOMER_SALE){
                JPanel operationPanel = new JPanel();
                JTextField num = new JTextField(25);

                JButton op = type == Constant.STOCK_CUSTOMER_BUY ? new JButton("buy") : new JButton("sale");
                op.setFont(new Font("Raleway", Font.BOLD, 14));
                op.setBorder(BorderFactory.createLineBorder(Color.blue));

                op.addActionListener(e -> {
                    if (type == Constant.STOCK_CUSTOMER_SALE && Integer.parseInt(num.getText()) > s.getNum()) {
                        JOptionPane.showMessageDialog(null, "Don't have enough # of stock");
                    }
                    assoCore.changeInfo("$1200.0");
                });

                operationPanel.add(num);
                operationPanel.add(op);

                stockP.add(operationPanel, BorderLayout.SOUTH);
            }

            if (type == Constant.STOCK_MANAGER_MODIFY) {
                JPanel opPanel = new JPanel();
                opPanel.setLayout(new GridLayout(2,1));

                JButton modify = new JButton("Modify");
                modify.setFont(new Font("Raleway", Font.BOLD, 14));
                modify.setBorder(BorderFactory.createLineBorder(Color.blue));
                modify.addActionListener(e -> {
                    s.setCurrent(Double.parseDouble(currentPrice.getText()));
                    currentPrice.setText(String.valueOf(s.getCurrent()));
                    if (s.getCurrent() > s.getOpen()) {
                        currentPrice.setForeground(Color.GREEN);
                    } else if (s.getCurrent() < s.getOpen()) {
                        currentPrice.setForeground(Color.red);
                    }
                });
                opPanel.add(modify);

                JButton remove = new JButton("Remove");
                remove.setFont(new Font("Raleway", Font.BOLD, 14));
                remove.setBorder(BorderFactory.createLineBorder(Color.blue));
                remove.addActionListener(e -> {
                    stockList.remove(s);
                    resetData(stockList);
                });
                opPanel.add(remove);


                stockP.add(opPanel, BorderLayout.EAST);
                opPanel.setPreferredSize(new Dimension(80, 0));
            }
        }

        if (type == Constant.STOCK_MANAGER_MODIFY){
            JButton addB = new JButton("+ Add a new stock");
            add(addB);
            addB.addActionListener( e -> {
                new StockDetail("111111").setVisible(true);
            });
        } else {
            JPanel empty = new JPanel();
            add(empty);
        }

    }
}
