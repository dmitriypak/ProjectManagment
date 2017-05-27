package objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by HP on 07.02.2017.
 */
public class MSSQLConnection {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn==null){
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://localhost:1434;databaseName=db_projectManagement";
                conn = DriverManager.getConnection(url, "sa", "Rightstep2015");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return conn;
    }
}
