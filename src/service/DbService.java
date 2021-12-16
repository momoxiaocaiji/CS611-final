package service;

import model.BankConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbService {

    BankConstants bankConstants = new BankConstants();

    //connect to DB
    public Connection connectToDb() throws Exception {
        System.out.println("Connecting to DB");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(bankConstants.getDBURL(), bankConstants.getDBUSERNAME(), bankConstants.getDBPWD());
            System.out.println("Connected Succesfully");
            return conn;
        }catch(Exception e){
            System.out.println("Failed to connect");
            throw new Exception(e.getLocalizedMessage());
        }
    }

    public void closeDbConnection(Connection connection) throws SQLException {
        if(connection!=null || !connection.isClosed()){
            connection.close();
        }else {
            System.out.println("Connection already closed");
        }

        System.out.println("Connection closed");
    }
}
