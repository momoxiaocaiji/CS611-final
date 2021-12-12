package service;

import controller.AccountController;
import controller.DbController;
import model.Customer;
import model.Login;
import java.sql.*;
import java.util.Objects;

public class LoginService {

    DbController dbController = new DbController();

    AccountController accountController = new AccountController();

    public int signIn(int userId,String password) throws Exception {
        Login userDetails = getLoginDetails(userId,password);
        if(userDetails==null){
            //no user found;
            System.out.println("No user found");
            return 404;
        }else{
            if(userDetails.getUserId()!=userId) {
                //incorrect user
                System.out.println("No user found");
                return 404;
            }else {
                if(userDetails.getPassword().equals(password)){
                    //successful login
                    System.out.println("Success Login");
                    return (userDetails.getPersonType().equalsIgnoreCase("manager"))?201:200;
                }else {
                    //incorrect password
                    System.out.println("Incorrect pwd");
                    return 400;
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
        customerToAdd.setCustomerId(Objects.hash("CUST",customerToAdd.getPersonId()));
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

    public int insertIntoCustomer(Connection connection,int customerId) throws SQLException {
        String query = "INSERT INTO customer(customerId)"+"VALUES(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,customerId);

        return preparedStatement.executeUpdate();
    }

    public int insertIntoCustPerson(Connection connection,int personId,int customerId) throws SQLException {
        String query = "INSERT into cust_person(personId,customerId)"+"VALUES(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,personId);
        preparedStatement.setInt(2,customerId);

        return preparedStatement.executeUpdate();
    }

    public int insertIntoLoginDetails(Connection connection,int custId,String pwd) throws SQLException {
        String query = "INSERT into login_details(userId,pwd,personType)"+"VALUES(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,custId);
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
                    accountController.createNewAccountForCustomer(customerToAdd);
                    return 200;
                }
            }else {
                //error inserting
                System.out.println("Error signing up");
                return 500;
            }
        }else{
            //user already exists in db
            System.out.println("User already exists in DB.Cant create duplicate");
            return 501;
        }
        return 400;
    }


    public int createHashCodeForPersonId(Customer customer){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(customer.getName());
        stringBuilder.append(customer.getEmail());
        stringBuilder.append(customer.getDob());
        return Objects.hash(stringBuilder.toString());
    }

    public Login getLoginDetails(int userId, String password) throws Exception {
        Connection connection = dbController.connectToDb();
        String query = "select distinct * from login_details where userId="+userId+";";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet==null || !resultSet.next()) {
            System.out.println("No user found");
            return null;
        }else {
            int id = resultSet.getInt("userId");
            String pwd = resultSet.getString("pwd");
            String personType = resultSet.getString("personType");
            System.out.println(id+" "+pwd);
            return new Login(id,pwd,personType);
        }
    }



}
