package bankUI.component;

import bankUI.Constant;
import bankUI.Core;
import controller.AccountController;
import controller.LoanController;
import controller.TransactionController;
import model.Loan;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanListPanel extends JPanel {

    private List<Loan> loanList;
    private int type;
    private String username;
    private Core core;

    // MVC
    private LoanController loanController = new LoanController();
    private TransactionController transactionController = new TransactionController();
    private AccountController accountController = new AccountController();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Core getCore() {
        return core;
    }

    public void setCore(Core core) {
        this.core = core;
    }

    public LoanListPanel(List<Loan> loanList, int type) {
        this.loanList = loanList;
        this.type = type;
        fillPanel();
    }

    public LoanListPanel(int type) {
        this(new ArrayList<>(), type);
    }

    public LoanListPanel(){
        this(Constant.LOAN_LIST);
    }

    public void resetData(List<Loan> loanList) {
        this.loanList = loanList;
        this.removeAll();
        fillPanel();
    }

    private void fillPanel(){
        setLayout(new GridLayout(loanList.size() + 1, 1));
        for(Loan l : loanList){
            // loan panel
            JPanel loanP = new JPanel();
            loanP.setLayout(new BorderLayout());
            loanP.setBorder(BorderFactory.createTitledBorder("Loan "+ l.getCurrency()));
            add(loanP, BorderLayout.CENTER);

            JPanel detail = new JPanel();

            detail.setBackground(Color.white);
            detail.setLayout(new GridLayout(4, 2));
            loanP.add(detail,BorderLayout.CENTER);

            JLabel a = new JLabel("Amount: ");
            a.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
            JLabel aNum = new JLabel(String.valueOf(l.getPrincipalAmount()));
            aNum.setFont(new Font("Arial", Font.PLAIN, 16));
            detail.add(a);
            detail.add(aNum);

            JLabel t = new JLabel("Type: ");
            t.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
            JLabel tT = new JLabel(l.getCurrency());
            tT.setFont(new Font("Arial", Font.PLAIN, 16));
            detail.add(t);
            detail.add(tT);

            JLabel i = new JLabel("Rate of Interest: ");
            i.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
            JLabel iNum = new JLabel(new BigDecimal(String.valueOf(l.getRateOfInterest())).toPlainString());
            iNum.setFont(new Font("Arial", Font.PLAIN, 16));
            detail.add(i);
            detail.add(iNum);

            JLabel d = new JLabel("Commence Date: ");
            d.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
            JLabel dDetail = new JLabel(l.getLoanCommenceDate().toString());
            dDetail.setFont(new Font("Arial", Font.PLAIN, 16));
            detail.add(d);
            detail.add(dDetail);

            if (type != Constant.LOAN_LIST){
                JButton op = new JButton();
                if (type == Constant.LOAN_CUSTOMER) {
                    op.setText("Pay");
                } else {
                    op.setText("Prove");
                }
                op.addActionListener(e -> {
                    if (type == Constant.LOAN_CUSTOMER) {
                        try {
                            int rCode = transactionController.payLoan(username, l.getLoanId(), l.getPrincipalAmount());
                            if (rCode == Constant.INSUFFICIENT_FUNDS) {
                                JOptionPane.showMessageDialog(null, "You don't have enough money.");
                            } else if (rCode == Constant.SUCCESS_CODE) {
                                JOptionPane.showMessageDialog(null, "Success!!");
                                loanList = loanController.getAllLoans().stream().filter(loan -> loan.getIsLoanApproved() == 0)
                                        .collect(Collectors.toList());
                                this.removeAll();
                                fillPanel();
                                this.revalidate();
                                core.fillInfo(accountController.getAccountInfoForCustomer(username));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        try {
                            int rCode = loanController.makeLoanDecision(l.getLoanId());
                            if (rCode == Constant.REJECT_LOAN_CODE) {
                                JOptionPane.showMessageDialog(null, "You can't prove this loan.");
                            } else if (rCode == Constant.APPROVE_LOAN_CODE) {
                                JOptionPane.showMessageDialog(null, "Success!!");
                                loanList = loanController.getAllLoans().stream().filter(loan -> loan.getIsLoanApproved() == 0)
                                        .collect(Collectors.toList());
                                this.removeAll();
                                fillPanel();
                                this.revalidate();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                op.setFont(new Font("Raleway", Font.BOLD, 14));
                op.setBorder(BorderFactory.createLineBorder(Color.blue));
                loanP.add(op, BorderLayout.EAST);
                op.setPreferredSize(new Dimension(80, 0));
            }
        }
        JPanel empty = new JPanel();
        add(empty);
    }
}
