package org.example.projectpegasi.Foundation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
    private static final String DATABASEURL = "jdbc:sqlserver://localhost;database=DBJobSwapSystem";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456";

    //Singleton instance
    private static DBConnection instance;
    private Connection connection;

    //Private constructor
    private DBConnection() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.connection = DriverManager.getConnection(DATABASEURL, USERNAME, PASSWORD);
    }

    //Public method to get singleton-instance
    public static DBConnection getInstance() throws SQLException, ClassNotFoundException
    {
        if(instance == null || instance.getConnection().isClosed())
        {
            instance = new DBConnection();
        }
        return instance;
    }

    //Getter to get Connection
    public Connection getConnection()
    {
        return connection;
    }
}
