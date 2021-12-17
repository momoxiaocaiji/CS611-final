package bankUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import bankUI.component.StockListPanel;
import controller.StockController;
import model.Stock;

/**
 * page to create a stock
 */
public class StockDetail extends JFrame implements ActionListener {

    private JLabel stock, open;
    private JTextField openPrice, stockCode;
    private JButton create;
    private StockController stockController= new StockController();
    private String ticker;
    private double openNum;

    private StockListPanel stockListPanel;

    //MVC

    public StockDetail(StockListPanel stockListPanel) {

        this.stockListPanel = stockListPanel;

        //-----------------------

        stock = new JLabel("Stock Code:");
        stock.setFont(new Font("Raleway", Font.BOLD, 15));

        stockCode = new JTextField(20);
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

        stockCode.setBounds(250, 60, 150, 40);
        add(stockCode);

        open.setBounds(80, 140, 200, 40);
        add(open);

        openPrice.setBounds(250, 140, 150, 40);
        add(openPrice);

        create.setBounds(170, 240, 100, 30);
        add(create);
        create.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450,350);
        setLocation(600,250);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        try {
            if (!validateTickerInput(stockCode.getText())) {
                JOptionPane.showMessageDialog(null, "Invalid value of ticker!!");
                return;
            }
            else if(!validateOpenNumInput(openPrice.getText())) {
                JOptionPane.showMessageDialog(null, "Invalid amount of open!!");
                return;
            }
            else {
            	this.openNum = Double.parseDouble(openPrice.getText());
            	this.ticker = stockCode.getText();
            	int rCode = stockController.createStock(this.ticker, this.openNum);
                if (rCode == Constant.SUCCESS_CODE) {
                    setVisible(false);
                    List<bankUI.entity.Stock> stockList = new ArrayList<>();
                    for(model.Stock s : stockController.getStockArrayList()) {
                        //let Stock.name = model.Stock.ticker
                        stockList.add(new bankUI.entity.Stock(s.getTicker(), s.getOpen(), s.getHigh(), s.getLow(), s.getPrice()));
                    }
                    stockListPanel.resetData(stockList);
                    JOptionPane.showMessageDialog(null, "Success");
                } else {
                    JOptionPane.showMessageDialog(null, "Please try it again");
                }
            }
        
	    } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean validateTickerInput(String t) {
        Pattern pattern = Pattern.compile("[a-zA-Z]*");
    	boolean result = t.length()<5;
    	return result && pattern.matcher(t).matches();
    }
    
    private boolean validateOpenNumInput(String o){
        Pattern pattern = Pattern.compile("[0-9]*");
        boolean checkValue = Double.parseDouble(o)>0;
        return pattern.matcher(o).matches() && checkValue;
    }
}
