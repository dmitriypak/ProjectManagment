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

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private TreeTableView <Project>tableProjectManage;
    @FXML
    private TreeTableColumn <Project,String> colName;
    @FXML
    private TreeTableColumn <Project,String> colStartDate;
    @FXML
    private TreeTableColumn <Project,String> colEndDate;
    @FXML
    private ComboBox<String>comboProjects;

    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditTaskController editTaskController;
    private Stage editTaskStage;
    private Node nodesource;
    private CollectionTasksList tasksListImpl = new CollectionTasksList();

    TreeItem<Project>root ;
    TreeItem<Project>task ;
    ObservableList<Project> projectArrayList = FXCollections.observableArrayList();
    ObservableList<String>comboProjectArrayList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        initLoader();
        root = new TreeItem<>(new Project(comboProjects.getId(),comboProjects.getValue()));
        task = new TreeItem<>(new Project("1","Task1"));
        root.getChildren().setAll(task);

        colName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Project,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Project, String > param) {
                return param.getValue().getValue().nameProperty();
            }
        });
        colName.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        colName.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Project, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Project, String> event) {
                System.out.println("ok");
            }
        });
        tableProjectManage.setEditable(true);
        tableProjectManage.setRoot(root);
        tableProjectManage.setShowRoot(true);

    }

    private void initLoader(){

        createProjectsList();
        try {
            fxmlLoader.setLocation(getClass().getResource("..//fxml/editTask.fxml"));
            fxmlEdit = fxmlLoader.load();
            editTaskController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        labelRole.setText("Вы вошли как " + loginUser.getRole());
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
       root = new TreeItem<>(new Project(comboProjects.getId(),comboProjects.getValue()));
       root.getChildren().addAll(task);
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
                Task task = new Task();
                editTaskController.setTask(task);
                task = editTaskController.getTask();
                tasksListImpl.add(task);
                showDialog();
                break;

            case "btnEditTask":
                //editProjectController.setTask((Task) tableProjectManage.getSelectionModel().getSelectedItem());
                showDialog();
                break;

            case "btnDeleteTask":
                Task deltask;
               // deltask = (Task) tableProjectManage.getSelectionModel().getSelectedItem();
               // tasksListImpl.delete(deltask);
               // deleteTask(deltask);
                break;
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

    }

}
