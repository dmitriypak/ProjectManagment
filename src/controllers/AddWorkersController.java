package controllers;

import interfaces.impl.CollectionUsersTaskList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import objects.MSSQLConnection;
import objects.Task;
import objects.UserTask;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by HP on 23.05.2017.
 */
public class AddWorkersController {

    @FXML
    private TableView tableUsers;
    @FXML
    private TableColumn colUserId;
    @FXML
    private TableColumn colRole;
    @FXML
    private TableColumn colFullName;
    @FXML
    private TableColumn colInclude;
    @FXML
    private TableView tableTaskUsers;

    private int taskid;
    private CollectionUsersTaskList usersTaskListImpl = new CollectionUsersTaskList();
    private Task task;
    @FXML
    public void initialize(Task task){
        usersTaskListImpl.clearUserList();
        tableTaskUsers.setEditable(true);
        this.task = task;

        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.getTasksNewId(?)}");
            call.registerOutParameter(1, Types.INTEGER);
            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                task.setId(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        taskid = Integer.parseInt(task.getId());

        colUserId.setCellValueFactory(new PropertyValueFactory<UserTask,String>("id"));
        colRole.setCellValueFactory(new PropertyValueFactory<UserTask,String>("role"));
        colFullName.setCellValueFactory(new PropertyValueFactory<UserTask,String>("fullname"));
        colInclude.setEditable(true);
        colInclude.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserTask, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<UserTask, Boolean> param) {
                UserTask userTask = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(userTask.getInclude());

                booleanProp.addListener(new ChangeListener<Boolean>() {

                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        userTask.setInclude(newValue);
                    }
                });
                return booleanProp;
            }
        });

        colInclude.setCellFactory(new Callback<TableColumn<UserTask, Boolean>, //
                TableCell<UserTask, Boolean>>() {
            @Override
            public TableCell<UserTask, Boolean> call(TableColumn<UserTask, Boolean> p) {
                CheckBoxTableCell<UserTask, Boolean> cell = new CheckBoxTableCell<UserTask, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });


        try {
            fillData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTask(Task task){
        if(task==null){
            return;
        }
        this.task=task;
        taskid = Integer.parseInt(task.getId());
        try {
            fillData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void fillData() throws SQLException {
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.getUsersByTask(?,?,?,?,?)}");
            call.setString("taskid",String.valueOf(taskid));
            call.registerOutParameter("userid", Types.INTEGER);
            call.registerOutParameter("rolename", Types.NVARCHAR);
            call.registerOutParameter("fullname", Types.NVARCHAR);
            call.registerOutParameter("include", Types.TINYINT);

            ResultSet rs = call.executeQuery();
            while (rs.next()){
                UserTask userTask = new UserTask(rs.getString("userid"), rs.getString("rolename"), rs.getString("fullname"),
                        rs.getBoolean("include"), String.valueOf(taskid));
                usersTaskListImpl.add(userTask);
            }
            tableTaskUsers.setItems(usersTaskListImpl.getUsersList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }
    public void actionSave(ActionEvent actionEvent) {
        String usersToTask = "";
        String usersNameToTask = "";
        for(int i=0;i<usersTaskListImpl.getUsersList().size();i++){

            if(usersTaskListImpl.getUsersList().get(i).getInclude()==true){
                usersToTask +=usersTaskListImpl.getUsersList().get(i).getId() +",";
                usersNameToTask +=usersTaskListImpl.getUsersList().get(i).getFullname() +",";
            }

        }
        if(usersToTask != ""){
            usersToTask.substring(0,usersToTask.length()-1);
            task.setWorkersList(usersNameToTask);

            try {
                CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.setWorkersToTask(?,?)}");
                call.setString("userslist",usersToTask);
                call.setInt("taskid",taskid);

                call.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        actionClose(actionEvent);
    }

    public Task getTask(){
        return task;
    }

}
