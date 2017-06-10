package demo.util;

import javax.xml.transform.Result;
import java.sql.*;

/**
 * Created by lichengjun on 2017/6/9.
 */
public class Db {
    private static final String URL = "jdbc:mysql:///?user=root&password=system";
    public static Connection getConnection(){
        try {
            new com.mysql.jdbc.Driver();
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(ResultSet resultSet,PreparedStatement PreparedStatement,Connection connection){
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (PreparedStatement != null) {
            try {
                PreparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}


