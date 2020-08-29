package com.revature.ers.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static ConnectionFactory connFactory = new ConnectionFactory();

    private ConnectionFactory() { super(); }

    /**
     * Since connFactory is a private field, we need a getter method to access it
     * @return
     */
    public static ConnectionFactory getInstance() {
        return connFactory;
    }

    /**
     * the getConnection method loads the JDB and connects to the remote db.
     * @return
     */
    public Connection getConnection() {

        Connection conn = null;

        try {

            /**
             * Load and Register the Driver.
             * Force the JVM to load the PostGreSQL JDBC driver
             */
            Class.forName("org.postgresql.Driver"); // returns the Class object associated
            // with the class or interface with the
            // given string name, using the given class loader.

            /**
             * Create a Connection
             */
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://java-ng-usf-200727.czcb2bh9usg6.us-east-2.rds.amazonaws.com:5432/postgres",
                    "postgres",
                    "Yellow507!");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        if (conn == null) {
            throw new RuntimeException("Failed to establish connection.");
        }

        return conn;

    }

    /**
     * We do not want to clone any instances.
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}
