package service;

import java.sql.Date;
import java.util.*;

import bankUI.Constant;
import controller.DbController;
import model.BankConstants;
import model.Customer;
import model.Login;
import java.sql.*;
import java.util.Objects;

public class LoginService {

    DbController dbController = new DbController();

    AccountService accountService = new AccountService();

    BankConstants bankConstants = new BankConstants();

    public int signIn(String userId,String password) throws Exception {
        Login userDetails = getLoginDetails(userId,password);
        if(userDetails==null){
            //no user found;
            System.out.println("No user found");
            return bankConstants.getNOT_FOUND();
        }else{
            if(!userDetails.getUserId().equalsIgnoreCase(userId)) {
                //incorrect user
                System.out.println("No user found");
                return bankConstants.getNOT_FOUND();
            }else {
                if(userDetails.getPassword().equals(password)){
                    //successful login
                    System.out.println("Success Login");
                    return (userDetails.getPersonType().equalsIgnoreCase("manager"))? bankConstants.getMANAGER_LOGIN(): bankConstants.getSUCCESS_CODE();
                }else {
                    //incorrect password
                    System.out.println("Incorrect pwd");
                    return bankConstants.getERROR();
                }
            }
        }
    }
    //----------------------------------- SIGN UP METHODS-----------------------------------//

    //method to create new customer
    public Customer createNewCustomer(Customer customer) {
        Customer customerToAdd = new Customer();
        int personIdHash = createHashCodeForPersonId(customer);
        customerToAdd.setPersonId(personIdHash);
        customerToAdd.setName(customer.getName());
        customerToAdd.setDob(customer.getDob());
        customerToAdd.setEmail(customer.getEmail());
        customerToAdd.setCustomerId(customer.getCustomerId());
        return customerToAdd;
    }

    public int insertIntoPerson(Connection connection,int personId,String name,String email, Date date) throws SQLException {
        String query = "INSERT INTO Person(personId,name,email,dob)"+"VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,personId);
        preparedStatement.setString(2,name);
        preparedStatement.setString(3,email);
        preparedStatement.setDate(4, (Date) date);

        return preparedStatement.executeUpdate();
    }

    public int insertIntoCustomer(Connection connection,String customerId) throws SQLException {
        String query = "INSERT INTO customer(customerId)"+"VALUES(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,customerId);

        return preparedStatement.executeUpdate();
    }

    public int insertIntoCustPerson(Connection connection,int personId,String customerId) throws SQLException {
        String query = "INSERT into cust_person(personId,customerId)"+"VALUES(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,personId);
        preparedStatement.setString(2,customerId);

        return preparedStatement.executeUpdate();
    }

    public int insertIntoLoginDetails(Connection connection,String custId,String pwd) throws SQLException {
        String query = "INSERT into login_details(userId,pwd,personType)"+"VALUES(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,custId);
        preparedStatement.setString(2,pwd);
        preparedStatement.setString(3,"CUSTOMER");

        return preparedStatement.executeUpdate();
    }


    //method to sign up for a customer
    public int signUpCustomer(Customer customer,String pwd) throws Exception {
        Customer customerToAdd = new Customer();
        Connection connection = dbController.connectToDb();
        Statement statement = connection.createStatement();

        int personIdHash = createHashCodeForPersonId(customer);

        //check if user exists or not
        String query = "select * from Person where personId="+personIdHash+";";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet==null || !resultSet.next()) {
            //user doesnt exists in db, insert him
            customerToAdd = createNewCustomer(customer);

            //insert into person
            int status = insertIntoPerson(connection, customerToAdd.getPersonId(), customerToAdd.getName(),customerToAdd.getEmail(), (Date) customerToAdd.getDob());
            if(status>0) {
                //successfully inserted into person
                System.out.println("Inserted into person");
                int status2 = insertIntoCustomer(connection,customerToAdd.getCustomerId());

                //insert into cust_person
                int status3 = insertIntoCustPerson(connection,customerToAdd.getPersonId(),customerToAdd.getCustomerId());

                //insert into login details
                int status4 = insertIntoLoginDetails(connection,customerToAdd.getCustomerId(),pwd);

                if(status2>0 && status3>0 && status4>0) {
                    //successfully inserted customer
                    System.out.println("Sign Up successful");
                    //create new account
                    accountService.createNewAccountForCustomer(customerToAdd);
                    return bankConstants.getSUCCESS_CODE();
                }
            }else {
                //error inserting
                System.out.println("Error signing up");
                return bankConstants.getSERVER_ERROR();
            }
        }else{
            //user already exists in db
            System.out.println("User already exists in DB.Cant create duplicate");
            return Constant.SIGN_UP_DUPLICATE;
        }
        return bankConstants.getERROR();
    }


    public int createHashCodeForPersonId(Customer customer){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(customer.getName());
        stringBuilder.append(customer.getEmail());
        stringBuilder.append(customer.getDob());
        return Objects.hash(stringBuilder.toString());
    }

    public Login getLoginDetails(String userId, String password) throws Exception {
        Connection connection = dbController.connectToDb();
        String query = "select distinct * from login_details where userId='"+userId+"';";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet==null || !resultSet.next()) {
            System.out.println("No user found");
            return null;
        }else {
            String id = resultSet.getString("userId");
            String pwd = resultSet.getString("pwd");
            String personType = resultSet.getString("personType");
            System.out.println(id+" "+pwd);
            return new Login(id,pwd,personType);
        }
    }

    public Customer getCustomerDetails(String customerId) throws Exception {
        Connection connection = dbController.connectToDb();
        String query = "select customer.customerId,cust_person.personId,name,email,dob from customer,cust_person,person where customer.customerId = cust_person.customerId and person.personId = cust_person.personId and customer.customerId = '"+customerId+"';";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        Customer customer = null;
        while(resultSet.next()) {
            customer = new Customer();
            customer.setCustomerId(customerId);

            customer.setPersonId(resultSet.getInt("personId"));
            customer.setName(resultSet.getString("name"));
            customer.setEmail(resultSet.getString("email"));
            customer.setDob(resultSet.getDate("dob"));
        }

        return customer;
    }

    public Map<String,Integer> getPersonIdMap(String userId) throws Exception {
        Map<String,Integer> map = new HashMap<>();
        String query = "select * from cust_person where customerId='"+userId+"';";
        Connection connection = dbController.connectToDb();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()){
            map.put(rs.getString("customerId"),rs.getInt("personId"));
        }

        return map;

    }



}
