package controllers;

import interfaces.impl.CollectionTasksList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import objects.MSSQLConnection;
import objects.Project;
import objects.Task;
import objects.User;

import java.io.IOException;
import java.sql.*;

import static controllers.LoginController.loginUser;

/**
 * Created by HP on 06.02.2017.
 */
public class MainDialogController {

    @FXML
    private MenuBar menu;
    @FXML
    private Button btnUsersSettings;
    @FXML
    private Button btnAddTask;
    @FXML
    private Button btnEditTask;
    @FXML
    private Button btnDeleteTask;



    @FXML
    private Label labelRole;
    @FXML
    private TreeTableView <Task>tableProjectManage;
    @FXML
    private TreeTableColumn <Task,String> colName;
    @FXML
    private TreeTableColumn <Task,String> colStartDate;
    @FXML
    private TreeTableColumn <Task,String> colEndDate;
    @FXML
    private TreeTableColumn <Task,String> colTaskDescr;
    @FXML
    private TreeTableColumn <Task,String> colEstimatedWorkHrs;
    @FXML
    private TreeTableColumn <Task,String> colFactWorkHrs;
    @FXML
    private TreeTableColumn <Task,String> colComplete;
    @FXML
    private TreeTableColumn <Task,String> colPriority;
    @FXML
    private TreeTableColumn <Task,String> colStatus;

    @FXML
    private ComboBox<String>comboProjects;

    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditTaskController editTaskController;
    private Stage editTaskStage;
    private Node nodesource;
    private CollectionTasksList tasksListImpl = new CollectionTasksList();
    //private User loginUser;
    private MainDialogController mainDialogController;

    TreeItem<Task>root ;
    TreeItem<Task>treeitemtask ;

    public static ObservableList<Project> projectArrayList = FXCollections.observableArrayList();
    public static ObservableList<String>comboProjectArrayList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){

        initLoader();
        //наименование
        colName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Task,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Task, String > param) {
                return param.getValue().getValue().nameProperty();
            }
        });
        colName.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        colName.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Task, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Task, String> event) {
                System.out.println("ok");
            }
        });

        //описание
        colTaskDescr.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Task,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Task, String > param) {
                return param.getValue().getValue().descrProperty();
            }
        });
        colTaskDescr.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        colTaskDescr.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Task, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Task, String> event) {
                System.out.println("ok");
            }
        });

        //startdate
        colStartDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Task,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Task, String > param) {
                return param.getValue().getValue().startDateProperty();
            }
        });
        colStartDate.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        colStartDate.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Task, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Task, String> event) {
                System.out.println("ok");
            }
        });

        //enddate
        colEndDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Task,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Task, String > param) {
                return param.getValue().getValue().endDateProperty();
            }
        });

        //estimatedWokrHrs
        colEstimatedWorkHrs.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Task,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Task, String > param) {
                return param.getValue().getValue().estimatedWorkHrsProperty();
            }
        });

        //factWokrHrs
        colFactWorkHrs.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Task,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Task, String > param) {
                return param.getValue().getValue().factWorkHrsProperty();
            }
        });

        //priority
        colPriority.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Task,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Task, String > param) {
                return param.getValue().getValue().priorityProperty();
            }
        });

        //status
        colStatus.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Task,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Task, String > param) {
                return param.getValue().getValue().statusProperty();
            }
        });

        //complete
        colComplete.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Task,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Task, String > param) {
                return param.getValue().getValue().completeProperty();
            }
        });

        tableProjectManage.setEditable(true);
        tableProjectManage.setRoot(root);
        tableProjectManage.setShowRoot(true);
    }

    private void initLoader(){
        createProjectsList();
        root = new TreeItem<>(new Task(comboProjects.getId(),comboProjects.getValue()));
        getStatProject();
        try {
            fillData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            fxmlLoader.setLocation(getClass().getResource("..//fxml/editTask.fxml"));
            fxmlEdit = fxmlLoader.load();
            editTaskController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLoginUser(User user){
        if(user==null){
            return;
        }
        loginUser.setRole(user.getRole());
        labelRole.setText(user.getRole());
        System.out.println(loginUser.getRole());
        checkUser();
    }
    private void checkUser(){
        System.out.println(labelRole.getText().length());
        if(labelRole.getText().length()==5) {
            btnAddTask.setDisable(false);
        }
        else
            btnAddTask.setDisable(true);
    }

    private void createProjectsList(){
        PreparedStatement call = null;
        try {
            call = MSSQLConnection.getConnection().prepareStatement("select id,name from projects");
            ResultSet rs = call.executeQuery();
            while (rs.next()){
                Project project = new Project(rs.getString(1), rs.getString(2));
                projectArrayList.add(project);
                comboProjectArrayList.add(project.getName());
            }
            comboProjects.setItems(comboProjectArrayList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        comboProjects.setValue(projectArrayList.get(0).getName());
    }

    public void actionSelectcomboProjects (ActionEvent actionEvent) {
        root = new TreeItem<>(new Task(comboProjects.getId(),comboProjects.getValue()));
        try {
            fillData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getStatProject();

        tableProjectManage.setRoot(root);

    }

    public void pushBtnUsersSettings(){
        btnUsersSettings.fire();
    }

    public void showUsers(ActionEvent actionEvent){
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/users.fxml"));
            stage.setTitle("Управление пользователями");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void showProjects(ActionEvent actionEvent){
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/projects.fxml"));
            stage.setTitle("Управление проектами");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        nodesource = (Node) actionEvent.getSource();
        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "btnAddTask":
                Task task = new Task(projectArrayList.get(comboProjects.getSelectionModel().getSelectedIndex()).getId());
                editTaskController.setTask(task);
                task = editTaskController.getTask();
                tasksListImpl.add(task);
                treeitemtask = new TreeItem<>(task);
                root.getChildren().add(treeitemtask);
                showDialog();
                break;

            case "btnEditTask":
                editTaskController.setTask(tasksListImpl.getTasksList().get(tableProjectManage.getSelectionModel().getSelectedIndex()-1));
                showDialog();
                break;

            case "btnDeleteTask":
                Task deltask;
                deltask = tasksListImpl.getTasksList().get(tableProjectManage.getSelectionModel().getSelectedIndex()-1);
                tasksListImpl.delete(deltask);
                deleteTask(deltask);
                root.getChildren().remove(tableProjectManage.getSelectionModel().getSelectedIndex()-1);
                break;
        }

    }

    private void deleteTask(Task deltask) {
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.deleteTask(?)}");
            call.setString("id",deltask.getId());
            call.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showDialog() {
        if (editTaskStage==null) {
            editTaskStage = new Stage();
            editTaskStage.setTitle("Редактирование задачи");
            editTaskStage.setMinHeight(150);
            editTaskStage.setMinWidth(300);
            editTaskStage.setResizable(false);
            editTaskStage.setScene(new Scene(fxmlEdit));
            editTaskStage.initModality(Modality.WINDOW_MODAL);
            editTaskStage.initOwner((Stage) nodesource.getScene().getWindow());
        }
        editTaskStage.showAndWait();
        getStatProject();
    }

    private void getStatProject() {
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.getStatProject(?,?,?,?,?,?)}");
            call.setInt("id", Integer.parseInt(projectArrayList.get(comboProjects.getSelectionModel().getSelectedIndex()).getId()));
            call.registerOutParameter(2, Types.DECIMAL);
            call.registerOutParameter(3, Types.DECIMAL);
            call.registerOutParameter(4, Types.DATE);
            call.registerOutParameter(5, Types.DATE);
            call.registerOutParameter(6, Types.INTEGER);

            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                root.getValue().setEstimatedWorkHrs(rs.getString(1));
                root.getValue().setFactWorkHrs(rs.getString(2));
                root.getValue().setStartDate(rs.getString(3));
                root.getValue().setEndDate(rs.getString(4));
                root.getValue().setComplete(rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillData() throws SQLException{
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.getTasks(?,?,?,?,?,?,?,?,?,?,?)}");
            call.registerOutParameter("id", Types.INTEGER);
            call.registerOutParameter("name", Types.NVARCHAR);
            call.registerOutParameter("descr", Types.NVARCHAR);
            call.registerOutParameter("startdate", Types.DATE);
            call.registerOutParameter("enddate", Types.DATE);
            call.registerOutParameter("estimatedworkhrs", Types.DECIMAL);
            call.registerOutParameter("factworkhrs", Types.DECIMAL);
            call.registerOutParameter("complete", Types.INTEGER);
            call.registerOutParameter("status", Types.NVARCHAR);
            call.registerOutParameter("priority", Types.TINYINT);
            call.setInt("projectid", Integer.parseInt(projectArrayList.get(comboProjects.getSelectionModel().getSelectedIndex()).getId()));
            ResultSet rs = call.executeQuery();
            System.out.println(Integer.parseInt(projectArrayList.get(comboProjects.getSelectionModel().getSelectedIndex()).getId()));
            while (rs.next()){
                Task task = new Task(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("descr"),
                        rs.getString("startdate"),
                        rs.getString("enddate"),
                        rs.getString("estimatedworkhrs"),
                        rs.getString("factworkhrs"),
                        rs.getString("priority"),
                        rs.getString("complete"),
                        rs.getString("status"),
                        rs.getString("projectid"));
                tasksListImpl.add(task);

                treeitemtask = new TreeItem<>(task);
                root.getChildren().add(treeitemtask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
