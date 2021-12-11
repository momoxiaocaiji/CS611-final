package bankUI.component;

import bankUI.Constant;
import bankUI.entity.Loan;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoanListPanel extends JPanel {

    private List<Loan> loanList;
    private int type;

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
            loanP.setBorder(BorderFactory.createTitledBorder("Loan "+ l.type));
            add(loanP, BorderLayout.CENTER);

            JPanel detail = new JPanel();

            detail.setBackground(Color.white);
            detail.setLayout(new GridLayout(3, 2));
            loanP.add(detail,BorderLayout.CENTER);

            JLabel a = new JLabel("Amount: ");
            a.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
            JLabel aNum = new JLabel(String.valueOf(l.amount));
            aNum.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
            detail.add(a);
            detail.add(aNum);

            JLabel t = new JLabel("Type: ");
            t.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
            JLabel tT = new JLabel(l.type);
            tT.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
            detail.add(t);
            detail.add(tT);

            JLabel i = new JLabel("Interest: ");
            i.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
            JLabel iNum = new JLabel(l.interest.toString());
            iNum.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
            detail.add(i);
            detail.add(iNum);

            if (type != Constant.LOAN_LIST){
                JButton op = new JButton();
                if (type == Constant.LOAN_CUSTOMER) {
                    op.setText("Pay");
                } else {
                    op.setText("Prove");
                }
                op.addActionListener(e -> {
                    loanList.remove(0);
                    this.removeAll();
                    fillPanel();
                    this.revalidate();
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
