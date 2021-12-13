package controller;

import model.Customer;
import service.LoginService;

import java.sql.Date;

public class LoginController {

    LoginService loginService = new LoginService();

    public int signIn(String userId, String password) throws Exception {
        return loginService.signIn(userId,password);
    }

    public int signUpCustomer(String customerId, String name, String email, Date dob, String pwd) throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setEmail(email);
        customer.setName(name);
        customer.setDob(dob);
        return loginService.signUpCustomer(customer,pwd);
    }

}
