package utils;

import utils.exceptions.DbException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbWrapper {

    public static String DATABASE;

    public static String SCHEMA;

    private static String DRIVER;

    private static String CONNECTION_URI;

    private static String LOGIN;

    private static String PASSWORD;

    private static Connection conn;

    public static Connection getConnection() throws DbException {
        if (conn == null) {
            try {
                Class.forName(DRIVER);
                System.out.println("conn = " + CONNECTION_URI + LOGIN);
                conn = DriverManager.getConnection(CONNECTION_URI, LOGIN, PASSWORD);
            } catch (SQLException ex) {
                throw new DbException("Can't connect to DB (" + ex.getErrorCode() + ": " + ex.getMessage() + ").");
            } catch (ClassNotFoundException e) {
                throw new DbException("Can't find DB driver.");
            }

        }
        return conn;
    }

    public void setConfig(org.springframework.core.env.Environment env) throws DbException {
        DbWrapper.setDRIVER(env.getProperty("DRIVER"));
        DbWrapper.setConnectionUri(env.getProperty("CONNECTION_URI"));
        DbWrapper.setLOGIN(env.getProperty("LOGIN"));
        DbWrapper.setPASSWORD(env.getProperty("PASSWORD"));
    }

    public static String getDATABASE() {
        return DATABASE;
    }

    public static void setDATABASE(String DATABASE) {
        DbWrapper.DATABASE = DATABASE;
    }

    public static String getSCHEMA() {
        return SCHEMA;
    }

    public static void setSCHEMA(String SCHEMA) {
        DbWrapper.SCHEMA = SCHEMA;
    }

    public static String getDRIVER() {
        return DRIVER;
    }

    public static void setDRIVER(String DRIVER) {
        DbWrapper.DRIVER = DRIVER;
    }

    public static String getConnectionUri() {
        return CONNECTION_URI;
    }

    public static void setConnectionUri(String connectionUri) {
        CONNECTION_URI = connectionUri;
    }

    public static String getLOGIN() {
        return LOGIN;
    }

    public static void setLOGIN(String LOGIN) {
        DbWrapper.LOGIN = LOGIN;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String PASSWORD) {
        DbWrapper.PASSWORD = PASSWORD;
    }

}
