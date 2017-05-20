package objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by HP on 07.02.2017.
 */
public class MSSQLConnection {
    public static Connection connector() throws SQLException {
        Connection conn;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=DB_MGTU";
            conn = DriverManager.getConnection(url, "sa", "Rightstep2015");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return conn;
    }
}
