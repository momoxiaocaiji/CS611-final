package bankUI;

import bankUI.component.HistoryTable;
import bankUI.component.LoanListPanel;
import bankUI.component.StockListPanel;
import bankUI.entity.Loan;
import bankUI.entity.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Manager extends JFrame implements ActionListener {

    private static List<Amount> amountList = new ArrayList<>();

    private JTabbedPane outerMenu, checkMenu;
    private JPanel check, report, stock, loanRequest, profit;
    private JPanel checking, saving;
    private LoanListPanel loan, requestList;
    private StockListPanel ownStock, wholeStockList;
    private JTextField customerID;
    private JButton search;
    private HistoryTable historyData;

    public Manager() {

        amountList.add(new Amount());
        amountList.add(new Amount());

        // check
        check = new JPanel();
        check.setLayout(new BorderLayout());
        // check north
        JPanel checkNorth = new JPanel();
        check.add(checkNorth, BorderLayout.NORTH);
        customerID = new JTextField("Enter the customer's ID you want to search:", 30);
        customerID.setFont(new Font("Raleway", Font.PLAIN, 14));
        search = new JButton("search");
        search.setFont(new Font("Raleway", Font.BOLD, 14));
        search.addActionListener(this);
        checkNorth.add(customerID);
        checkNorth.add(search);
        // check south
        JPanel checkSouth = new JPanel();
        checkSouth.setLayout(null);
        checkMenu = new JTabbedPane();
        checkMenu.setBounds(50,50,830,650);
        // --------------------------------------
        checking = new JPanel();
        checking.setBackground(Color.white);
        // --------------------------------------

        // --------------------------------------
        saving = new JPanel();
        saving.setBackground(Color.white);
        // --------------------------------------

        // --------------------------------------
        loan = new LoanListPanel();
        loan.setBackground(Color.white);
        // --------------------------------------

        // --------------------------------------
        ownStock = new StockListPanel();
        ownStock.setBackground(Color.white);
        // --------------------------------------

        checkMenu.add("checking", checking);
        checkMenu.add("saving", saving);
        checkMenu.add("loan", loan);
        checkMenu.add("stock", ownStock);
        checkSouth.add(checkMenu);
        check.add(checkSouth, BorderLayout.CENTER);


        // report
        report = new JPanel();

        historyData = new HistoryTable();
        report.setLayout(new BorderLayout());
        report.add(new JScrollPane(historyData), BorderLayout.CENTER);

        //filter
        JPanel filterPanel = new JPanel();
        report.add(filterPanel, BorderLayout.NORTH);
        JButton button = new JButton("filter");
        button.setFont(new Font("Raleway", Font.BOLD, 14));
        JTextField filterText = new JTextField(20);
        button.addActionListener(e -> {
            String text = filterText.getText();
            if (text.length() == 0) {
                historyData.getSorter().setRowFilter(null);
            } else {
                historyData.getSorter().setRowFilter(RowFilter.regexFilter(".*" +text+".*"));
            }
        });
        filterPanel.add(filterText);
        filterPanel.add(button);

        // stock
        stock = new JPanel();
        stock.setLayout(null);
        List<Stock> stockList = new ArrayList<>();
        stockList.add(new Stock("JJ", 10.0, 12.0, 9.0, 11.0));
        stockList.add(new Stock("ZZ", 5.0, 7.8, 4.3, 4.9));
        stockList.add(new Stock("AA", 5.0, 7.8, 4.3, 4.9));
        stockList.add(new Stock("BB", 5.0, 7.8, 4.3, 4.9));
        wholeStockList = new StockListPanel(stockList, Constant.STOCK_MANAGER_MODIFY);
        wholeStockList.setSize(900, 800);
        stock.add(wholeStockList);

        // loanRequest
        loanRequest = new JPanel();
        loanRequest.setLayout(null);
        List<Loan> loanList = new ArrayList<>();
        loanList.add(new Loan());
        loanList.add(new Loan());
        requestList = new LoanListPanel(loanList, Constant.LOAN_MANAGER);
        requestList.setSize(900, 800);
        loanRequest.add(requestList);

        // profit
        profit = new JPanel();
        profit.setLayout(null);
        JLabel bankProfit = new JLabel("Bank Profit: ");
        bankProfit.setFont(new Font("System", Font.BOLD, 25));
        JLabel profitNum = new JLabel("$10000.0");
        profitNum.setFont(new Font("System", Font.BOLD, 25));

        JLabel withdrawalProfit = new JLabel("Withdrawal Profit: ");
        withdrawalProfit.setFont(new Font("System", Font.BOLD, 25));
        JTextField withdrawalNum = new JTextField(25);
        withdrawalNum.setFont(new Font("System", Font.BOLD, 25));

        JButton exc = new JButton("Withdrawal");
        setFont(new Font("Raleway", Font.BOLD, 25));


        bankProfit.setBounds(170, 200, 300, 60);
        profit.add(bankProfit);


        profitNum.setBounds(570, 200, 300, 60);
        profit.add(profitNum);


        withdrawalProfit.setBounds(170, 350, 300, 60);
        profit.add(withdrawalProfit);


        withdrawalNum.setBounds(570, 350, 300, 60);
        profit.add(withdrawalNum);

        exc.setBounds(390, 500, 200, 50);
        profit.add(exc);


        outerMenu = new JTabbedPane();
        outerMenu.add("Check Customer", check);
        outerMenu.add("Daily Report", report);
        outerMenu.add("Stock", stock);
        outerMenu.add("Loan Request", loanRequest);
        outerMenu.add("Bank Profit", profit);


        add(outerMenu);

        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 850);
        setLocation(300, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == search) {
                amountList.add(new Amount());
                // -------------------------------
                // checking
                checking.removeAll();
                checking.setBorder(BorderFactory.createTitledBorder("Checking"));
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
                // -------------------------------

                // -------------------------------
                // saving
                saving.removeAll();
                saving.setBorder(BorderFactory.createTitledBorder("Saving"));
                saving.setLayout(new GridLayout(amountList.size() + 1, 1));
                for (Amount amount : amountList) {
                    JPanel oneAccount = new JPanel();
                    oneAccount.setBorder(BorderFactory.createTitledBorder("Amount"));
                    oneAccount.setLayout(new GridLayout(amount.currencies.size(), 2, 0, 5));
                    saving.add(oneAccount);
                    for (String type : amount.currencies.keySet()) {
                        JLabel t = new JLabel(type);
                        t.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                        JLabel b = new JLabel(String.valueOf(amount.currencies.get(type)));
                        b.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                        oneAccount.add(t);
                        oneAccount.add(b);
                    }
                }
                // -------------------------------

                // -------------------------------
                // loan
                loan.removeAll();
                List<Loan> loanList = new ArrayList<>();
                loanList.add(new Loan());
                loanList.add(new Loan());
                loan.resetData(loanList);

                // -------------------------------

                // -------------------------------
                // stock
                ownStock.removeAll();
                List<Stock> stockList = new ArrayList<>();
                stockList.add(new Stock("JJ", 10.0, 12.0, 9.0, 11.0));
                stockList.add(new Stock("ZZ", 5.0, 7.8, 4.3, 4.9));
                ownStock.resetData(stockList);
                // -------------------------------

                checking.revalidate();
                saving.revalidate();
                loan.revalidate();
                ownStock.revalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Manager().setVisible(true);
    }
}
