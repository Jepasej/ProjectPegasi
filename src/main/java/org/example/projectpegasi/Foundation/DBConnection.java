package org.example.projectpegasi.Foundation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class for managing the database connection to the JobSwap system.
 * Ensures that only one instance of the connection is created and reused
 * throughout the application.
 */
public class DBConnection
{
    private final String DATABASEURL = "jdbc:sqlserver://localhost;database=DBJobSwapSystem;encrypt=true;trustServerCertificate=true";
    private final String USERNAME = "sa";
    private final String PASSWORD = "123456";

    //Singleton instance
    private static DBConnection instance;
    //Active Database connection
    private Connection connection;

    /**
     * Private constructor that initializes the database connection.
     * Called internally via the getInstance() method.
     */
    private DBConnection()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.connection = DriverManager.getConnection(DATABASEURL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Returns the singleton instance of DBConnection.
     * If no instance exists or the connection is closed, a new one is created.
     *
     * @return the singleton DBConnection instance connectionk, or null if connection could not be established
     */
    public static Connection getInstance()
    {
        try {
            if (instance == null || instance.getConnection() == null || instance.getConnection().isClosed())
            {
                instance = new DBConnection();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return instance.getConnection();
    }

    /**
     * Returns the active SQL Connection object.
     */
    public Connection getConnection()
    {
        return connection;
    }


    /**
     * Closes the current connection
     * Accessible from other classes
     */
    public void closeConnection()
    {
        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
