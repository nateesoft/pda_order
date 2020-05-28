package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import util.MSG;

public class MySQLConnect {

    public static Connection connect;
    public static String DB;
    public static String server;
    public static String db;
    public static String username;
    public static String password;
    public static String port;
    public static String charset;
    public static String fontSize;
    public static String inputTable;

    public MySQLConnect() {
        try {
            InputStream input = new FileInputStream("C:/softpos/pda-connect.ini");
            Properties prop = new Properties();
            prop.load(input);

            server = prop.getProperty("server");
            db = prop.getProperty("db");
            DB = prop.getProperty("db");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            port = prop.getProperty("port");
            charset = prop.getProperty("charset");

            try {
                fontSize = prop.getProperty("font-size");
                if (fontSize == null || fontSize.equals("") || fontSize.equals("null")) {
                    fontSize = "170%";
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                fontSize = "170%";
            }

            try {
                inputTable = prop.getProperty("input-table");
                if (inputTable == null || inputTable.equals("") || inputTable.equals("null")) {
                    inputTable = "text";//number, text
                } else if (!inputTable.equals("text") && !inputTable.equals("number")) {
                    inputTable = "text";
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                inputTable = "text";
            }
//            try {
//                inputTable = prop.getProperty("input-table");
//                if (inputTable == null || inputTable.equals("") || inputTable.equals("null")) {
//                    inputTable = "number";//number, text
//                } else if (!inputTable.equals("text") && !inputTable.equals("number")) {
//                    inputTable = "number";
//                }
//            } catch (Exception e) {
//                System.err.println(e.getMessage());
//                inputTable = "number";
//            }
            input.close();
        } catch (IOException ex) {
        }

        connectMySQL();
    }

    private static Connection connectMySQL() {
        try {
            String classname = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://" + server + ":" + port + "/" + db + "?charset=" + charset;

            if (connect == null) {
                Class.forName(classname);
                connect = DriverManager.getConnection(url, username, password);
            }

        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
        }

        //System.out.println("Connected DB. = " + Value.countConnectDB);
        return connect;
    }

    public static Statement getStatement() throws Exception {
        if (connect == null) {
            connectMySQL();
        }

        return connect.createStatement();

    }

    public static PreparedStatement getPreparedStatement(String sql) throws Exception {
        if (connect == null) {
            connectMySQL();
        }

        return connect.prepareStatement(sql);
    }

    public static int exeUpdate(String sql) throws Exception {
        if (connect == null) {
            connectMySQL();
        }

        return connect.createStatement().executeUpdate(sql);
    }

    public static ResultSet getResultSet(String sql) throws Exception {
        if (connect == null) {
            connectMySQL();
        }

        return getStatement().executeQuery(sql);

    }

    public static void close() {
        try {
            if (connect != null) {
                connect.close();
                connect = null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connect;
    }

}
