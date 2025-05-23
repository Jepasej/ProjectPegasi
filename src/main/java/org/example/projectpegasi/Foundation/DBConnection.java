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
    private static final String DATABASEURL = "jdbc:sqlserver://localhost;database=DBJobSwapSystem;encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456";

    //Singleton instance
    private static DBConnection instance;
    //Active Database connection
    private Connection connection;

    /**
     * Private constructor that initializes the database connection.
     * Called internally via the getInstance() method.
     *
     * @throws SQLException if the connection cannot be established.
     * @throws ClassNotFoundException if the JDBC driver is not found.
     */
    private DBConnection() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.connection = DriverManager.getConnection(DATABASEURL, USERNAME, PASSWORD);
    }

    /**
     * Returns the singleton instance of DBConnection.
     * If no instance exists or the connection is closed, a new one is created.
     *
     * @return the singleton DBConnection instance
     * @throws SQLException if the connection cannot be established.
     * @throws ClassNotFoundException if the JDBC driver is not found.
     */
    public static DBConnection getInstance() throws SQLException, ClassNotFoundException
    {
        if(instance == null || instance.getConnection().isClosed())
        {
            instance = new DBConnection();
        }
        return instance;
    }

    /**
     * Returns the active SQL Connection object.
     *
     * @return the current database connection
     */
    public Connection getConnection()
    {
        return connection;
    }
}
