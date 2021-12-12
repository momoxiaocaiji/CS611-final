package controller;

import model.Customer;
import service.LoginService;

public class LoginController {

    LoginService loginService = new LoginService();

    public int signIn(int userId, String password) throws Exception {
        return loginService.signIn(userId,password);
    }

    public int signUpCustomer(Customer customer,String pwd) throws Exception {
        return loginService.signUpCustomer(customer,pwd);
    }

}
