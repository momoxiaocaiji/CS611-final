package bankUI;

import bankUI.component.HistoryTable;
import bankUI.component.StockListPanel;
import bankUI.entity.Stock;
import controller.AccountController;
import controller.LoanController;
import controller.TransactionController;
import model.CheckingAccount;
import model.Loan;
import model.SavingAccount;
import controller.*;
//always access model.Stock as model.Stock to avoid colliding with entity.Stock
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main page for customer
 */
public class Core extends JFrame implements ActionListener {

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
    private LoanController loanController = new LoanController();
    private TransactionController transactionController = new TransactionController();
    private StockController stockController = new StockController();

    public Core(List userInfo, String username) throws Exception {
        userAccountInfo = userInfo;
        this.username = username;

        // -----------------------------------------
        // info
        info = new JPanel();
        info.setLayout(new GridLayout(2, 1));


        checking = new JPanel();
        checking.setBorder(BorderFactory.createTitledBorder("Checking"));

        saving = new JPanel();
        saving.setBorder(BorderFactory.createTitledBorder("Saving"));

        fillInfo(userAccountInfo);

        info.add(checking);
        info.add(saving);

        // -----------------------------------------

        // -----------------------------------------
        // stock
        stock = new JPanel();
        stock.setLayout(new BorderLayout());
        SecuritiesAccount se = accountController.getSecuritiesAccountInfo(username);
        // user doesn't have a securities account.
        if (se == null) {
            createSecurities = new JButton("+ Create New Account");
            stock.add(createSecurities, BorderLayout.NORTH);
            createSecurities.setPreferredSize(new Dimension(0, 120));
            createSecurities.addActionListener(this);
        } else {
            fillStock(se);
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

        historyData = new HistoryTable(transactionController.getDailyReportForCustomer(username));
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

        // refresh
        JButton refresh = new JButton("refresh");
        refresh.setFont(new Font("Raleway", Font.BOLD, 14));
        refresh.addActionListener(e -> {
            try {
                historyData.resetData(transactionController.getDailyReportForCustomer(username));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            historyData.revalidate();
        });
        button.setFont(new Font("Raleway", Font.BOLD, 14));
        filterPanel.add(refresh);
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
            } else if (ae.getSource() == createSaving) {
                new CreateAccount("Saving", "1000000", username, this).setVisible(true);
            }else if (ae.getSource() == deposit) {
                TransactionDetail td = new TransactionDetail(Constant.TRANSACTION_DEPOSIT, null);
                td.setVisible(true);
                td.setUsername(username);
                td.setCore(this);
            } else if (ae.getSource() == withdrawal) {
                Map<String, Map<String, Double>> accountInfo = new HashMap<>();
                for (Object list : accountController.getAccountInfoForCustomer(username)) {
                    if (list instanceof CheckingAccount) {
                        accountInfo.put(((CheckingAccount) list).getAccountId(), ((CheckingAccount) list).getMoney());
                    } else if (list instanceof SavingAccount) {
                        accountInfo.put(((SavingAccount) list).getAccountId(), ((SavingAccount) list).getMoney());
                    }
                }
                TransactionDetail td = new TransactionDetail(Constant.TRANSACTION_WITHDRAWAL, accountInfo);
                td.setVisible(true);
                td.setUsername(username);
                td.setCore(this);
            } else if (ae.getSource() == transfer) {
                Map<String, Map<String, Double>> accountInfo = new HashMap<>();
                for (Object list : accountController.getAccountInfoForCustomer(username)) {
                    if (list instanceof CheckingAccount) {
                        accountInfo.put(((CheckingAccount) list).getAccountId(), ((CheckingAccount) list).getMoney());
                    } else if (list instanceof SavingAccount) {
                        accountInfo.put(((SavingAccount) list).getAccountId(), ((SavingAccount) list).getMoney());
                    }
                }
                TransactionDetail td = new TransactionDetail(Constant.TRANSACTION_TRANSFER, accountInfo);
                td.setVisible(true);
                td.setUsername(username);
                td.setCore(this);
            } else if (ae.getSource() == loan) {
                List<Loan> loanList = loanController.getLoansForCustomer(username).
                        stream().filter(loan -> loan.getIsLoanApproved() == 1).collect(Collectors.toList());
                LoanDetail ld = new LoanDetail(loanList , username);
                ld.setVisible(true);
                ld.setCore(this);
            } else if (ae.getSource() == createSecurities) {
            	// onClick create button:
                new CreateSecuritiesAccount("Securities", "1000001", username, this).setVisible(true);
            	
            	

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error!!!");
        }
    }

    /**
     * re fill the data in info tab
     * @param userAccountInfo
     */
    public void fillInfo(List userAccountInfo){
        // Checking
        checking.removeAll();
        saving.removeAll();
        int checkAccountNum = 0;
        int savingAccountNum = 0;
        for (Object amount : userAccountInfo) {
            if (amount instanceof CheckingAccount) {
                checkAccountNum ++;
                JPanel oneAccount = new JPanel();
                oneAccount.setBorder(BorderFactory.createTitledBorder("Amount"));
                oneAccount.setLayout(new GridLayout(((CheckingAccount) amount).getMoney().size(), 2, 0, 5));
                checking.add(oneAccount);
                for (String type : ((CheckingAccount) amount).getMoney().keySet()) {
                    JLabel t = new JLabel(type);
                    t.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                    JLabel b = new JLabel(String.valueOf(((CheckingAccount) amount).getMoney().get(type)));
                    b.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                    oneAccount.add(t);
                    oneAccount.add(b);
                }
            } else if (amount instanceof SavingAccount) {
                savingAccountNum ++;
                JPanel oneAccount = new JPanel();
                oneAccount.setBorder(BorderFactory.createTitledBorder("Amount"));
                oneAccount.setLayout(new GridLayout(((SavingAccount) amount).getMoney().size(), 2, 0, 5));
                saving.add(oneAccount);
                for (String type : ((SavingAccount) amount).getMoney().keySet()) {
                    JLabel t = new JLabel(type);
                    t.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                    JLabel b = new JLabel(String.valueOf(((SavingAccount) amount).getMoney().get(type)));
                    b.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
                    oneAccount.add(t);
                    oneAccount.add(b);
                }
            }

        }
        checking.setLayout(new GridLayout(checkAccountNum + 1, 1));
        saving.setLayout(new GridLayout(savingAccountNum + 1, 1));
        createChecking = new JButton("+ Create New Account");
        createChecking.addActionListener(this);
        createSaving = new JButton("+ Create New Account");
        createSaving.addActionListener(this);

        checking.add(createChecking);
        checking.revalidate();

        saving.add(createSaving);
        saving.revalidate();
    }

    /**
     * re fill the data in stock tab
     * @param securitiesAccount
     * @throws SQLException
     * @throws Exception
     */
    public void fillStock(SecuritiesAccount securitiesAccount) throws SQLException, Exception {
        stock.removeAll();

        JPanel securitiesAccountPanel = new JPanel();
        stock.add(securitiesAccountPanel, BorderLayout.NORTH);
        securitiesAccountPanel.setBackground(Color.white);
        securitiesAccountPanel.setPreferredSize(new Dimension(0, 120));
        securitiesAccountPanel.setLayout(new GridLayout(3, 2));
        JLabel amount = new JLabel("Banlance Amount: ");
        amount.setFont(new Font("Andale Mono", Font.BOLD, 16));
        amountNum = new JLabel(String.valueOf(securitiesAccount.getInvestmentAmount()));
        amountNum.setFont(new Font("Arial", Font.PLAIN, 16));
        securitiesAccountPanel.add(amount);
        securitiesAccountPanel.add(amountNum);

        JLabel realized = new JLabel("Realized Profit: ");
        realized.setFont(new Font("Andale Mono", Font.BOLD, 16));
        //TODO getRealized profit
        realizedNum = new JLabel(String.valueOf(securitiesAccount.getRealizedProfit()));
        realizedNum.setFont(new Font("Arial", Font.PLAIN, 16));
        securitiesAccountPanel.add(realized);
        securitiesAccountPanel.add(realizedNum);

        JLabel unrealized = new JLabel("Unrealized Profit: ");
        unrealized.setFont(new Font("Andale Mono", Font.BOLD, 16));
        //TODO getUnrealized profit
        unrealizedNum = new JLabel(String.valueOf(securitiesAccount.getUnrealizedProfit()));
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
        //TODO where to call createStock, how to combine with date
        stockController.createStock("AAA", "A1234", 10, 15, 7, 9, null);
        stockController.createStock("BBB", "B1234", 20, 40, 30, 30, null);
        for(model.Stock s : stockController.getStockArrayList()) {
        	//let Stock.name = model.Stock.ticker
        	stockList.add(new Stock(s.getTicker(), s.getOpen(), s.getHigh(), s.getLow(), s.getPrice()));
        }
        //what if arrayList size >3?
        StockListPanel buyList = new StockListPanel(stockList, Constant.STOCK_CUSTOMER_BUY, securitiesAccount);
        buyList.setPreferredSize(new Dimension(750, 650));
        buyStock.add(buyList, BorderLayout.CENTER);
        buyStock.add(new JButton("Buy via Stock Code"), BorderLayout.SOUTH);
        buyList.setAssoFrame(this);
        buyList.setUsername(username);
        // TODO update data display on sell
        List<Stock> customerStockList = new ArrayList<>();
        //TODO where to call createStock, how to combine with date
        int i=0;
        for(model.CustomerOwnedStock s : stockController.getCustomerStockArrayList(username)) {
        	//let Stock.name = model.Stock.ticker
        	customerStockList.add(new Stock(s.getTicker(), s.getOpen(), s.getHigh(), s.getLow(), s.getPrice()));
        	customerStockList.get(i).setNum(s.getQuantity());
        	customerStockList.get(i).setCost(s.getPurchasePrice());
        }
        StockListPanel saleList = new StockListPanel(customerStockList, Constant.STOCK_CUSTOMER_SALE, securitiesAccount);
        saleList.setPreferredSize(new Dimension(750, 650));
        saleStock.add(saleList);
        saleList.setAssoFrame(this);
        saleList.setUsername(username);
    }

//    public static void main(String[] args) {
//
//        new Core().setVisible(true);
//    }
}
