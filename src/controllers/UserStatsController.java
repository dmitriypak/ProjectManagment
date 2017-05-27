package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import objects.MSSQLConnection;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


/**
 * Created by HP on 24.05.2017.
 */
public class UserStatsController {
    @FXML
    private ListView txtUsersStat;
    private int projectid;
    @FXML
    public void initialize(int projectid){
        this.projectid = projectid;
        fillData();

    }

    private void fillData() {
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.getUsersStat(?,?,?,?)}");
            call.setInt("projectid",projectid);
            call.registerOutParameter("user_fullname", Types.NVARCHAR);
            call.registerOutParameter("rolename", Types.NVARCHAR);
            call.registerOutParameter("task_name", Types.NVARCHAR);
            ResultSet rs = call.executeQuery();
            while (rs.next()){
                String s = rs.getString(1) + "          " +  rs.getString(2) + "    " + rs.getString(3);

                txtUsersStat.getItems().add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
