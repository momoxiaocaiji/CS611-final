package bankUI;

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

    private JButton deposit, create, withdrawal, transfer, loan;
    private JTable historyData;

    public Core() {
        amountList.add(new Amount());
        amountList.add(new Amount());
        // info
        info = new JPanel();
        info.setLayout(new GridLayout(2, 1));

        checking = new JPanel();
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
        create = new JButton("+ Create New Account");
        create.addActionListener(this);
        checking.add(create);


        saving = new JPanel();
        saving.setBorder(BorderFactory.createTitledBorder("Saving"));


        info.add(checking);
        info.add(saving);

        // stock
        stock = new JPanel();

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

        // History

        String column[] = {"TYPE", "SENDER", "CTYPE", "AMOUNT", "RECEIVER", "DATE"};
        String data[][] = {{"Deposit", "", "USD", "1000.0", "12345", new Date().toString()},
                           {"Withdrawal", "12345", "CYN", "1200.0", "", new Date().toString()},
                           {"Transfer", "12345", "HKD", "1300.0", "23456", new Date().toString()}};
        historyData = new JTable(data, column){//Implement table cell tool tips.
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                int realColumnIndex = convertColumnIndexToModel(colIndex);

                if (realColumnIndex == column.length - 1) { //Sport column
                    tip = "The date of this transaction is "
                            + getValueAt(rowIndex, colIndex);
                } else { //another column
                    //You can omit this part if you know you don't
                    //have any renderers that supply their own tool
                    //tips.
                    tip = super.getToolTipText(e);
                }
                return tip;
            }};
        history = new JPanel();
        history.setLayout(new BorderLayout());
        history.add(new JScrollPane(historyData), BorderLayout.CENTER);
        resetTable(historyData, column);


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
            if (ae.getSource() == create) {
                new CreateAccount("Checking", "10000000").setVisible(true);
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

    private void resetTable(JTable table, String[] column){

        // sorter
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        //filte
        JPanel filterPanel = new JPanel();
        history.add(filterPanel, BorderLayout.SOUTH);
        JButton button = new JButton("filter");
        JTextField filterText = new JTextField(20);
        button.addActionListener(e -> {
            String text = filterText.getText();
            if (text.length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter(".*" +text+".*"));
            }
        });
        filterPanel.add(filterText);
        filterPanel.add(button);

        //set table header
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        header.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));

        //set table row height
        table.setRowHeight(20);

        //set table color
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable t, Object v, boolean isS, boolean hasF,
                                                           int row, int column) {
                if (row % 2 == 0) {
                    setBackground(Color.PINK);
                } else {
                    setBackground(Color.white);
                }
                return super.getTableCellRendererComponent(t, v , isS, hasF, row, column);
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumn(column[i]).setCellRenderer(renderer);
            TableColumn tableColumn = table.getColumnModel().getColumn(i);
            if(i == 0 || i == 2 || i == 3) {
                tableColumn.setPreferredWidth(20);
            }
        }


    }

    private void newFilter() {
    }

    public static void main(String[] args) {

        new Core().setVisible(true);
    }
}
