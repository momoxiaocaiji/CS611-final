package bankUI;

import bankUI.component.HistoryTable;
import bankUI.component.LoanListPanel;
import bankUI.component.StockListPanel;
import bankUI.entity.Stock;
import controller.BankManagerController;
import controller.LoanController;
import controller.StockController;
import controller.TransactionController;
import model.CheckingAccount;
import model.Loan;
import model.SavingAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Manager extends JFrame implements ActionListener {

    private JTabbedPane outerMenu, checkMenu;
    private JPanel check, report, stock, loanRequest, profit;
    private JPanel checking, saving;
    private LoanListPanel loan, requestList;
    private StockListPanel ownStock, wholeStockList;
    private JTextField customerID, withdrawalNum;
    private JButton search, exc;
    private HistoryTable historyData;
    private JLabel profitNum;

    //MVC
    private BankManagerController bankManagerController = new BankManagerController();
    private LoanController loanController = new LoanController();
    private TransactionController transactionController = new TransactionController();
    private StockController stockController = new StockController();

    public Manager() throws Exception {

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
        historyData = new HistoryTable(transactionController.getDailyReport(new Date(new java.util.Date().getTime()).toString()));
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

        //refresh
        JButton refresh = new JButton("refresh");
        refresh.setFont(new Font("Raleway", Font.BOLD, 14));
        refresh.addActionListener(e -> {
            try {
                historyData.resetData(transactionController.getDailyReport(new Date(new java.util.Date().getTime()).toString()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            historyData.revalidate();
        });
        button.setFont(new Font("Raleway", Font.BOLD, 14));
        filterPanel.add(refresh);

        // stock
        stock = new JPanel();
        stock.setLayout(null);
        List<Stock> stockList = new ArrayList<>();
        int i=0;
        for(model.Stock s : stockController.getStockArrayList()) {
            //let Stock.name = model.Stock.ticker
            stockList.add(new Stock(s.getTicker(), s.getOpen(), s.getHigh(), s.getLow(), s.getPrice()));
        }
        wholeStockList = new StockListPanel(stockList, Constant.STOCK_MANAGER_MODIFY, null);
        wholeStockList.setSize(900, 800);
        stock.add(wholeStockList);

        // loanRequest
        loanRequest = new JPanel();
        loanRequest.setLayout(null);
        requestList = new LoanListPanel(loanController.getAllLoans().stream().filter(loan -> loan.getIsLoanApproved() == 0)
                .collect(Collectors.toList()), Constant.LOAN_MANAGER);
        requestList.setSize(900, 800);
        loanRequest.add(requestList);

        // profit
        profit = new JPanel();
        profit.setLayout(null);
        JLabel bankProfit = new JLabel("Bank Profit: ");
        bankProfit.setFont(new Font("System", Font.BOLD, 25));
        profitNum = new JLabel(String.format("%.2f", bankManagerController.getBankBalance()));
        profitNum.setFont(new Font("System", Font.BOLD, 25));

        JLabel withdrawalProfit = new JLabel("Withdrawal Profit: ");
        withdrawalProfit.setFont(new Font("System", Font.BOLD, 25));
        withdrawalNum = new JTextField(25);
        withdrawalNum.setFont(new Font("System", Font.BOLD, 25));

        exc = new JButton("Withdrawal");
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
        exc.addActionListener(this);
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
                Map<String, Object> customerInfo = bankManagerController.getCustomerData(customerID.getText());
                // -------------------------------
                // checking
                int checkAccountNum = 0;
                checking.removeAll();
                checking.setBorder(BorderFactory.createTitledBorder("Checking"));
                // multiple acconut
//                for (Object amount : customerInfo) {
//                    JPanel oneAccount = new JPanel();
//                    oneAccount.setBorder(BorderFactory.createTitledBorder("Amount"));
//                    oneAccount.setLayout(new GridLayout(amount.currencies.size(), 2, 0, 5));
//                    checking.add(oneAccount);
//                    for (String type : amount.currencies.keySet()) {
//                        JLabel t = new JLabel(type);
//                        t.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
//                        JLabel b = new JLabel(String.valueOf(amount.currencies.get(type)));
//                        b.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
//                        oneAccount.add(t);
//                        oneAccount.add(b);
//                    }
//                }
                if (customerInfo.containsKey("Checking")) {
                    CheckingAccount account = (CheckingAccount) customerInfo.get("Checking");
                    checkAccountNum ++;
                    JPanel oneAccount = new JPanel();
                    oneAccount.setBorder(BorderFactory.createTitledBorder("Amount"));
                    oneAccount.setLayout(new GridLayout(account.getMoney().size(), 2, 0, 5));
                    checking.add(oneAccount);
                    for (String type : account.getMoney().keySet()) {
                        JLabel t = new JLabel(type);
                        t.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                        JLabel b = new JLabel(String.valueOf(account.getMoney().get(type)));
                        b.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                        oneAccount.add(t);
                        oneAccount.add(b);
                    }
                }
                checking.setLayout(new GridLayout(checkAccountNum + 1, 1));
                // -------------------------------

                // -------------------------------
                // saving
                int savingAccountNum = 0;
                saving.removeAll();
                saving.setBorder(BorderFactory.createTitledBorder("Saving"));
//                for (Amount amount : amountList) {
//                    JPanel oneAccount = new JPanel();
//                    oneAccount.setBorder(BorderFactory.createTitledBorder("Amount"));
//                    oneAccount.setLayout(new GridLayout(amount.currencies.size(), 2, 0, 5));
//                    saving.add(oneAccount);
//                    for (String type : amount.currencies.keySet()) {
//                        JLabel t = new JLabel(type);
//                        t.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
//                        JLabel b = new JLabel(String.valueOf(amount.currencies.get(type)));
//                        b.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
//                        oneAccount.add(t);
//                        oneAccount.add(b);
//                    }
//                }
                if (customerInfo.containsKey("Saving")) {
                    SavingAccount account = (SavingAccount) customerInfo.get("Saving");
                    savingAccountNum ++;
                    JPanel oneAccount = new JPanel();
                    oneAccount.setBorder(BorderFactory.createTitledBorder("Amount"));
                    oneAccount.setLayout(new GridLayout(account.getMoney().size(), 2, 0, 5));
                    saving.add(oneAccount);
                    for (String type : account.getMoney().keySet()) {
                        JLabel t = new JLabel(type);
                        t.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                        JLabel b = new JLabel(String.valueOf(account.getMoney().get(type)));
                        b.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                        oneAccount.add(t);
                        oneAccount.add(b);
                    }
                }
                saving.setLayout(new GridLayout(savingAccountNum + 1, 1));
                // -------------------------------

                // -------------------------------
                // loan
                loan.removeAll();
                List<Loan> loanList = loanController.getLoansForCustomer(customerID.getText()).
                        stream().filter(loan -> loan.getIsLoanApproved() == 1).collect(Collectors.toList());
                loan.resetData(loanList);

                // -------------------------------

                // -------------------------------
                // stock
                ownStock.removeAll();
                List<Stock> stockList = new ArrayList<>();
                int i=0;
                for(model.CustomerOwnedStock s : stockController.getCustomerStockArrayList(customerID.getText())) {
                    //let Stock.name = model.Stock.ticker
                    stockList.add(new Stock(s.getTicker(), s.getOpen(), s.getHigh(), s.getLow(), s.getPrice()));
                    stockList.get(i).setNum(s.getQuantity());
                    stockList.get(i).setCost(s.getPurchasePrice());
                    i++;
                }
                ownStock.resetData(stockList);
                // -------------------------------

                checking.revalidate();
                saving.revalidate();
                loan.revalidate();
                ownStock.revalidate();
            }
            else if (ae.getSource() == exc ) {
                int rCode = bankManagerController.withdrawBankProfits(Double.parseDouble(withdrawalNum.getText()));
                if (rCode == Constant.SUCCESS_CODE) {
                    JOptionPane.showMessageDialog(null, "Happy time!! Evil Banker!!");
                    profitNum.setText(String.format("%.2f", bankManagerController.getBankBalance()));
                } else {
                    JOptionPane.showMessageDialog(null, "You can't withdrawal!!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new Manager().setVisible(true);
    }
}
