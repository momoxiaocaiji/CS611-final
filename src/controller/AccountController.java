package controller;

import model.BankConstants;
import model.Customer;
import service.AccountService;
import service.LoginService;
import model.SecuritiesAccount;

import java.util.List;
import java.util.Map;

public class AccountController {

    AccountService accountService = new AccountService();

    LoginService loginService = new LoginService();

    BankConstants bankConstants = new BankConstants();

    public int createNewAccountForCustomer(int personId,String customerId) throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setPersonId(personId);
        return accountService.createNewAccountForCustomer(customer);
    }

    public int createNewCheckingOrSavingAccount(String customerId,String accountType,int pin) throws Exception {
        //Customer customer = loginService.getCustomerDetails(customerId);
        Map<String,Integer> map = loginService.getPersonIdMap(customerId);
        Customer customer = null;
        if(map.containsKey(customerId)) {
            customer = new Customer();
            customer.setCustomerId(customerId);
            customer.setPersonId(map.get(customerId));
        }
        if(customer==null) return bankConstants.getNOT_FOUND();
        return accountService.createNewCheckingOrSavingAccount(customer,accountType,pin);
    }

    public List<Object> getAccountInfoForCustomer(String customerId) throws Exception {
        Customer customer = loginService.getCustomerDetails(customerId);
        return accountService.getAccountInfoForCustomer(customer);
    }
    
    public int createSecuritiesAccount (String customerId, double depositAmount) throws Exception {
        Customer customer = loginService.getCustomerDetails(customerId);
        if(customer==null)
            return bankConstants.getNOT_FOUND();
        
        return accountService.createNewSecuritiesAccount(customer, depositAmount);
    }
    
    public SecuritiesAccount getSecuritiesAccountInfo (String customerId) throws Exception {
        Customer customer = loginService.getCustomerDetails(customerId);
        SecuritiesAccount securitiesAccount = accountService.getSecuritiesInfo(customer);
        return securitiesAccount;
    }
}
