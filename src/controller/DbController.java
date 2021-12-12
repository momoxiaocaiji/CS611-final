package controller;

import service.DbService;

import java.sql.Connection;
import java.sql.SQLException;

public class DbController {

    private DbService dbService = new DbService();

    public Connection connectToDb() throws Exception {
        return dbService.connectToDb();
    }

    public void closeDb(Connection connection) throws SQLException {
        dbService.closeDbConnection(connection);
    }

}
