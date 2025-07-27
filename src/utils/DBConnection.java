package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * Utility class for database connection management
 * Supports MySQL, SQL Server, and Oracle databases
 */
public class DBConnection {

    // Database configuration constants
    private static final String CONFIG_FILE = "/db.properties";

    // Default configuration (can be overridden by properties file)
    private static String DB_URL = "jdbc:mysql://localhost:3306/DULIEU";
    private static String DB_USERNAME = "root";
    private static String DB_PASSWORD = "";
    private static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    // Static block to load database configuration
    static {
        loadDatabaseConfig();
        loadDriver();
    }

    /**
     * Load database configuration from properties file
     */
    private static void loadDatabaseConfig() {
        try (InputStream input = DBConnection.class.getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                Properties prop = new Properties();
                prop.load(input);

                DB_URL = prop.getProperty("db.url", DB_URL);
                DB_USERNAME = prop.getProperty("db.username", DB_USERNAME);
                DB_PASSWORD = prop.getProperty("db.password", DB_PASSWORD);
                DB_DRIVER = prop.getProperty("db.driver", DB_DRIVER);

                System.out.println("Database configuration loaded from properties file");
            } else {
                System.out.println("Properties file not found, using default configuration");
            }
        } catch (IOException e) {
            System.err.println("Error loading database configuration: " + e.getMessage());
        }
    }

    /**
     * Load database driver
     */
    private static void loadDriver() {
        try {
            Class.forName(DB_DRIVER);
            System.out.println("Database driver loaded successfully: " + DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + DB_DRIVER);
            e.printStackTrace();
        }
    }

    /**
     * Get database connection
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Set connection properties
            conn.setAutoCommit(true);

            return conn;
        } catch (SQLException e) {
            System.err.println("Failed to create database connection");
            System.err.println("URL: " + DB_URL);
            System.err.println("Username: " + DB_USERNAME);
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Get database connection with custom auto-commit setting
     * 
     * @param autoCommit auto-commit setting
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection(boolean autoCommit) throws SQLException {
        Connection conn = getConnection();
        conn.setAutoCommit(autoCommit);
        return conn;
    }

    /**
     * Close database connection safely
     * 
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Test database connection
     * 
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection test successful!");
                System.out.println("Connected to: " + conn.getMetaData().getDatabaseProductName());
                System.out.println("Database URL: " + DB_URL);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get database connection with transaction support
     * 
     * @return Connection with auto-commit disabled
     * @throws SQLException if connection fails
     */
    public static Connection getTransactionConnection() throws SQLException {
        Connection conn = getConnection();
        conn.setAutoCommit(false);
        return conn;
    }

    /**
     * Commit transaction
     * 
     * @param conn Connection to commit
     */
    public static void commitTransaction(Connection conn) {
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                System.err.println("Error committing transaction: " + e.getMessage());
            }
        }
    }

    /**
     * Rollback transaction
     * 
     * @param conn Connection to rollback
     */
    public static void rollbackTransaction(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back transaction: " + e.getMessage());
            }
        }
    }

    /**
     * Get current database configuration info
     * 
     * @return String containing configuration details
     */
    public static String getConfigInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Database Configuration:\n");
        info.append("URL: ").append(DB_URL).append("\n");
        info.append("Username: ").append(DB_USERNAME).append("\n");
        info.append("Driver: ").append(DB_DRIVER).append("\n");
        return info.toString();
    }

    // Private constructor to prevent instantiation
    private DBConnection() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}