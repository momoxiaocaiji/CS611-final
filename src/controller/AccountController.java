package controller;

import model.Customer;
import service.AccountService;

import java.util.List;

public class AccountController {

    AccountService accountService = new AccountService();

    public int createNewAccountForCustomer(Customer customer) throws Exception {
        return accountService.createNewAccountForCustomer(customer);
    }

    public int createNewCheckingOrSavingAccount(Customer customer,String accountType,int pin) throws Exception {
        return accountService.createNewCheckingOrSavingAccount(customer,accountType,pin);
    }

    public List<Object> getAccountInfoForCustomer(Customer customer) throws Exception {
        return accountService.getAccountInfoForCustomer(customer);
    }

}
