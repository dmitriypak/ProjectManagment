package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.MSSQLConnection;
import objects.Task;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.time.LocalDate;


/**
 * Created by HP on 21.05.2017.
 */
public class EditTaskController {
    @FXML
    private TextField txtTaskName;
    @FXML
    private TextField txtTaskDescr;
    @FXML
    private DatePicker txtTaskStartDate;
    @FXML
    private DatePicker txtTaskEndDate;
    @FXML
    private TextField txtTaskWorker;
    @FXML
    private TextField txtTaskEstimatedWorkHrs;
    @FXML
    private TextField txtTaskFactWorkHrs;
    @FXML
    private TextField txtTaskStatus;
    @FXML
    private TextField txtTaskComplete;

    private Task task;

    @FXML
    public void initialize(){
    }

    public void setTask(Task task){
        if(task==null){
            return;
        }
        this.task=task;
        txtTaskName.setText(task.getName());
        txtTaskDescr.setText(task.getDescr());
        txtTaskEstimatedWorkHrs.setText(task.getEstimatedWorkHrs());
        txtTaskFactWorkHrs.setText(task.getFactWorkHrs());
        txtTaskStatus.setText(task.getStatus());
        txtTaskComplete.setText(task.getComplete());
        if(task.getStartDate() == ""){
            txtTaskStartDate.setValue(LocalDate.now());
        }
        else{
            txtTaskStartDate.setValue(LocalDate.parse(task.getStartDate()));
        }
        if(task.getStartDate() == ""){
            txtTaskEndDate.setValue(LocalDate.now());
        }
        else{
            txtTaskEndDate.setValue(LocalDate.parse(task.getStartDate()));
        }
    }

    public Task getTask(){
        return task;
    }
    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }
    public void actionSave(ActionEvent actionEvent) {
        task.setName(txtTaskName.getText());
        task.setDescr(txtTaskDescr.getText());
        task.setStartDate(String.valueOf(txtTaskStartDate.getValue()));
        task.setEndDate(String.valueOf(txtTaskEndDate.getValue()));
        task.setComplete(txtTaskComplete.getText());
        task.setEstimatedWorkHrs(txtTaskEstimatedWorkHrs.getText());
        task.setFactWorkHrs(txtTaskFactWorkHrs.getText());
        task.setStatus(txtTaskStatus.getText());
        if(task.getId()!=""){
            updateTask(task);
        }
        else{
            insertTask(task);
        }
        actionClose(actionEvent);
    }

    private void updateTask(Task task) {
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.updateTask(?,?,?,?,?)}");
            call.setString("id",task.getId());
            call.setString("name",task.getName());
            call.setString("descr",task.getDescr());
            call.setString("startdate",task.getStartDate());
            call.setString("enddate",task.getEndDate());
            call.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertTask(Task task) {
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.createTask(?,?,?,?)}");
            call.setString("name",task.getName());
            call.setString("descr",task.getDescr());
            call.setString("startdate",task.getStartDate());
            call.setString("enddate",task.getEndDate());
            call.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
