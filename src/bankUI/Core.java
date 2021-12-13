package bankUI;

import bankUI.component.HistoryTable;
import bankUI.component.StockListPanel;
import bankUI.entity.Stock;
import controller.AccountController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

class Amount {
    Map<String, Double> currencies;

    public Amount() {
        currencies = new HashMap<>();
        currencies.put("USD", 1000.00);
        currencies.put("CNY", 1000.00);
        currencies.put("HKD", 1000.00);
    }
}

public class Core extends JFrame implements ActionListener {

    private static List<Amount> amountList = new ArrayList<>();

    private JTabbedPane tabMenu;
    private JPanel info, stock, transaction;
    private JPanel history;
    private JPanel checking, saving;

    private JButton deposit, withdrawal, transfer, loan;
    private JButton createChecking, createSaving, createSecurities;
    private HistoryTable historyData;

    private JLabel amountNum, realizedNum, unrealizedNum;

    private String test;


    // DATA
    private List userAccountInfo;
    private String username;


    // MVC
    private AccountController accountController = new AccountController();

    public Core(List userInfo, String username) {
        //amountList.add(new Amount());
        //amountList.add(new Amount());
        userAccountInfo = userInfo;
        this.username = username;

        // -----------------------------------------
        // info
        info = new JPanel();
        info.setLayout(new GridLayout(2, 1));

        checking = new JPanel();
        fillInfo(userAccountInfo);
        checking.setBorder(BorderFactory.createTitledBorder("Checking"));

        saving = new JPanel();
        saving.setBorder(BorderFactory.createTitledBorder("Saving"));


        info.add(checking);
        info.add(saving);

        // -----------------------------------------

        // -----------------------------------------
        // stock
        stock = new JPanel();
        stock.setLayout(new BorderLayout());
        Amount se = new Amount();
        // user doesn't have a securities account.
        if (se == null) {
            createSecurities = new JButton("+ Create New Account");
            stock.add(createSecurities, BorderLayout.NORTH);
            createSecurities.setPreferredSize(new Dimension(0, 120));
        } else {
            JPanel securitiesAccountPanel = new JPanel();
            stock.add(securitiesAccountPanel, BorderLayout.NORTH);
            securitiesAccountPanel.setBackground(Color.white);
            securitiesAccountPanel.setPreferredSize(new Dimension(0, 120));
            securitiesAccountPanel.setLayout(new GridLayout(3, 2));
            List<String> testNum = new ArrayList<>();
            testNum.add("$2000.0");
            JLabel amount = new JLabel("Amount: ");
            amount.setFont(new Font("Andale Mono", Font.BOLD, 16));
            amountNum = new JLabel(testNum.get(0));
            amountNum.setFont(new Font("Arial", Font.PLAIN, 16));
            securitiesAccountPanel.add(amount);
            securitiesAccountPanel.add(amountNum);

            JLabel realized = new JLabel("Realized Profit: ");
            realized.setFont(new Font("Andale Mono", Font.BOLD, 16));
            realizedNum = new JLabel("$20.0");
            realizedNum.setFont(new Font("Arial", Font.PLAIN, 16));
            securitiesAccountPanel.add(realized);
            securitiesAccountPanel.add(realizedNum);

            JLabel unrealized = new JLabel("Unrealized Profit: ");
            unrealized.setFont(new Font("Andale Mono", Font.BOLD, 16));
            unrealizedNum = new JLabel("$20.0");
            unrealizedNum.setFont(new Font("Arial", Font.PLAIN, 16));
            securitiesAccountPanel.add(unrealized);
            securitiesAccountPanel.add(unrealizedNum);

            // stock deal
            JTabbedPane stockTransactionMenu = new JTabbedPane();
            JPanel buyStock = new JPanel();
            JPanel saleStock = new JPanel();
            stockTransactionMenu.add("Buy", buyStock);
            stockTransactionMenu.add("Sale", saleStock);
            stock.add(stockTransactionMenu, BorderLayout.CENTER);

            buyStock.setLayout(new BorderLayout());
            List<Stock> stockList = new ArrayList<>();
            stockList.add(new Stock("JJ", 10.0, 12.0, 9.0, 11.0));
            stockList.add(new Stock("ZZ", 5.0, 7.8, 4.3, 4.9));
            StockListPanel buyList = new StockListPanel(stockList, Constant.STOCK_CUSTOMER_BUY);
            buyList.setPreferredSize(new Dimension(750, 650));
            buyStock.add(buyList, BorderLayout.CENTER);
            buyStock.add(new JButton("Buy via Stock Code"), BorderLayout.SOUTH);
            buyList.setAssoFrame(this);

            stockList.get(0).setCost(9.0);
            stockList.get(0).setNum(100);
            stockList.get(1).setCost(8.0);
            stockList.get(1).setNum(200);
            StockListPanel saleList = new StockListPanel(stockList, Constant.STOCK_CUSTOMER_SALE);
            saleList.setPreferredSize(new Dimension(750, 650));
            saleStock.add(saleList);
        }


        // -----------------------------------------

        // -----------------------------------------
        // transaction
        transaction = new JPanel();
        deposit = new JButton("DEPOSIT");
        deposit.setFont(new Font("System", Font.BOLD, 18));
        deposit.setForeground(Color.BLACK);

        withdrawal = new JButton("WITHDRAWAL");
        withdrawal.setFont(new Font("System", Font.BOLD, 18));
        withdrawal.setForeground(Color.BLACK);

        transfer = new JButton("TRANSFER");
        transfer.setFont(new Font("System", Font.BOLD, 18));
        transfer.setForeground(Color.BLACK);

        loan = new JButton("LOAN");
        loan.setFont(new Font("System", Font.BOLD, 18));
        loan.setForeground(Color.BLACK);

        transaction.setLayout(null);

        deposit.setBounds(40, 200, 300, 60);
        transaction.add(deposit);
        deposit.addActionListener(this);

        withdrawal.setBounds(440, 200, 300, 60);
        transaction.add(withdrawal);
        withdrawal.addActionListener(this);

        transfer.setBounds(40, 400, 300, 60);
        transaction.add(transfer);
        transfer.addActionListener(this);

        loan.setBounds(440, 400, 300, 60);
        transaction.add(loan);
        loan.addActionListener(this);
        // -----------------------------------------

        // -----------------------------------------
        // History

        historyData = new HistoryTable();
        history = new JPanel();
        history.setLayout(new BorderLayout());
        history.add(new JScrollPane(historyData), BorderLayout.CENTER);

        //filter
        JPanel filterPanel = new JPanel();
        history.add(filterPanel, BorderLayout.NORTH);
        JButton button = new JButton("filter");
        JTextField filterText = new JTextField(20);
        button.addActionListener(e -> {
            String text = filterText.getText();
            if (text.length() == 0) {
                historyData.getSorter().setRowFilter(null);
            } else {
                historyData.getSorter().setRowFilter(RowFilter.regexFilter(".*" + text + ".*"));
            }
        });
        filterPanel.add(filterText);
        filterPanel.add(button);
        // -----------------------------------------

        //
        tabMenu = new JTabbedPane();
        tabMenu.setSize(850, 750);
        tabMenu.add("Accounts", info);
        tabMenu.add("Stock", stock);
        tabMenu.add("Transaction", transaction);
        tabMenu.add("History", history);

        add(tabMenu);

        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == createChecking) {
                new CreateAccount("Checking", "10000000", username, this).setVisible(true);
                //setVisible(false);
            } else if (ae.getSource() == deposit) {
                new TransactionDetail(Constant.TRANSACTION_DEPOSIT).setVisible(true);
            } else if (ae.getSource() == withdrawal) {
                new TransactionDetail(Constant.TRANSACTION_WITHDRAWAL).setVisible(true);
            } else if (ae.getSource() == transfer) {
                new TransactionDetail(Constant.TRANSACTION_TRANSFER).setVisible(true);
            } else if (ae.getSource() == loan) {
                new LoanDetail().setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error!!!");
        }
    }

    public void changeInfo(String test) {
        amountNum.setText(test);
    }

    public void fillInfo(List userAccountInfo){
        // Checking
        checking.removeAll();
        amountList.add(new Amount());
        checking.setLayout(new GridLayout(amountList.size() + 1, 1));
        for (Amount amount : amountList) {
            JPanel oneAccount = new JPanel();
            oneAccount.setBorder(BorderFactory.createTitledBorder("Amount"));
            oneAccount.setLayout(new GridLayout(amount.currencies.size(), 2, 0, 5));
            checking.add(oneAccount);
            for (String type : amount.currencies.keySet()) {
                JLabel t = new JLabel(type);
                t.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                JLabel b = new JLabel(String.valueOf(amount.currencies.get(type)));
                b.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                oneAccount.add(t);
                oneAccount.add(b);
            }
        }
        createChecking = new JButton("+ Create New Account");
        createChecking.addActionListener(this);
        checking.add(createChecking);
        checking.revalidate();
    }

//    public static void main(String[] args) {
//
//        new Core().setVisible(true);
//    }
}
