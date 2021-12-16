package controller;

import model.BankConstants;
import model.Stock;
import service.BankManagerService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BankManagerController {

    BankConstants bankConstants = new BankConstants();

    BankManagerService bankManagerService = new BankManagerService();

    /*
    functions to write
    1. pay interest to saving_accounts - done
    2. collect interest from loans and update bank balance
    3. approve loans
    4. change stock prices -done
    5. get daily report
    6. check up on customer -done
    7. list of stocks available to trade - done (moved to stock controller)
     */

    public int payInterestToAccounts() throws Exception {
        return bankManagerService.payInterestToAccounts();
    }

    //function to change stock prices - stock id
    public int changeStockPrice(int stockId,double price) throws Exception{
        return bankManagerService.changeStockPrice(stockId,price);
    }

    public int changeStockPrice(String ticker,double price) throws Exception {
        int stockId = bankManagerService.getStockId(ticker);
        return changeStockPrice(stockId,price);
    }

    public Map<String, Object> getCustomerData(String customerId) throws Exception {
        return bankManagerService.getCustomerData(customerId);
    }

}
