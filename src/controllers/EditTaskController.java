package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.MSSQLConnection;
import objects.Status;
import objects.Task;

import java.io.IOException;
import java.sql.*;
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
    private TextField txtTaskComplete;
    @FXML
    private TextField txtTaskPriority;
    @FXML
    private TextField txtTaskID;
    @FXML
    private ComboBox comboStatus;

    private Task task;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private AddWorkersController addWorkersController;
    private Parent fxmlEdit;

    public static ObservableList<String> comboStatusesArrayList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
       createListStatuses();

    }

    private void createListStatuses() {
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.getStatuses(?,?,?)}");
            call.registerOutParameter(1, Types.INTEGER);
            call.registerOutParameter(2, Types.NVARCHAR);
            call.registerOutParameter(3, Types.TINYINT);
            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                Status status = new Status();
                status.setId(rs.getString(1));
                status.setName(rs.getString(2));
                status.setIs_complete(rs.getString(3));
                comboStatusesArrayList.add(status.getName());
            }
            comboStatus.setItems(comboStatusesArrayList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTask(Task task){
        if(task==null){
            return;
        }
        this.task=task;
        txtTaskID.setText(task.getId());
        txtTaskName.setText(task.getName());
        txtTaskDescr.setText(task.getDescr());
        txtTaskEstimatedWorkHrs.setText(task.getEstimatedWorkHrs());
        txtTaskFactWorkHrs.setText(task.getFactWorkHrs());
        comboStatus.setValue(task.getStatus());
        txtTaskComplete.setText(task.getComplete());
        txtTaskPriority.setText(task.getPriority());
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
        PreparedStatement call = null;
        int is_complete = 0;
        task.setName(txtTaskName.getText());
        task.setDescr(txtTaskDescr.getText());
        task.setStartDate(String.valueOf(txtTaskStartDate.getValue()));
        task.setEndDate(String.valueOf(txtTaskEndDate.getValue()));

        try {
            call = MSSQLConnection.getConnection().prepareStatement("select is_complete from statuses where name = ?");
            call.setString(1, String.valueOf(comboStatus.getValue()));
            ResultSet rs = call.executeQuery();
            while (rs.next()){
                is_complete = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(is_complete==1) {
            task.setComplete("100");
        }
        else{
            task.setComplete(txtTaskComplete.getText());
        }

        task.setEstimatedWorkHrs(txtTaskEstimatedWorkHrs.getText());
        task.setFactWorkHrs(txtTaskFactWorkHrs.getText());
        task.setPriority(txtTaskPriority.getText());
        task.setStatus(String.valueOf(comboStatus.getValue()));
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
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.updateTask(?,?,?,?,?,?,?,?,?,?)}");
            call.setInt("id",Integer.parseInt(task.getId()));
            call.setString("name",task.getName());
            call.setString("descr",task.getDescr());
            call.setString("startdate",task.getStartDate());
            call.setString("enddate",task.getEndDate());
            call.setDouble("estimatedWorkHrs",Double.parseDouble(task.getEstimatedWorkHrs()));
            call.setDouble("factWorkHrs",Double.parseDouble(task.getFactWorkHrs()));
            call.setInt("status",Integer.parseInt(String.valueOf(comboStatus.getSelectionModel().getSelectedIndex()+1)));
            call.setInt("priority",Integer.parseInt(task.getPriority()));
            call.setInt("complete",Integer.parseInt(task.getComplete()));
            call.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertTask(Task task) {
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.createTask(?,?,?,?,?,?,?,?,?,?)}");
            call.setString("name",task.getName());
            call.setString("descr",task.getDescr());
            call.setString("projectid",task.getProjectId());
            call.setString("priority",task.getPriority());
            call.setString("status",task.getStatus());
            call.setString("estimatedWorkHrs",task.getEstimatedWorkHrs());
            call.setString("factWorkHrs",task.getFactWorkHrs());
            call.setString("complete",task.getComplete());
            call.setString("startdate",task.getStartDate());
            call.setString("enddate",task.getEndDate());
            call.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/addWorkers.fxml"));
            Parent root = loader.load();
            AddWorkersController addWorkersController = loader.getController();
            addWorkersController.initialize(task);

            stage.setTitle("Добавить исполнителей");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
