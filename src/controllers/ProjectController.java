package controllers;

import interfaces.impl.CollectionProjectsList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.MSSQLConnection;
import objects.Project;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import static controllers.LoginController.loginUser;
import static controllers.MainDialogController.comboProjectArrayList;
import static controllers.MainDialogController.projectArrayList;

/**
 * Created by HP on 21.05.2017.
 */
public class ProjectController {
    @FXML
    private TableView tableProjects;
    @FXML
    private TableColumn colProjectId;
    @FXML
    private TableColumn colProjectName;
    @FXML
    private TableColumn colProjectDescr;
    @FXML
    private TableColumn colStartDate;
    @FXML
    private TableColumn colEndDate;
    @FXML
    private Button btnAddProject;
    @FXML
    private Button btnEditProject;
    @FXML
    private Button btnDeleteProject;

    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditProjectController editProjectController;
    private Stage editProjectStage;
    private Node nodesource;
    private CollectionProjectsList projectsListImpl = new CollectionProjectsList();

    @FXML
    public void initialize()
    {
        if(loginUser.getRole().length()==4){
            btnAddProject.setDisable(true);
            btnEditProject.setDisable(true);
            btnDeleteProject.setDisable(true);
        }
        else{
            btnAddProject.setDisable(false);
            btnEditProject.setDisable(false);
            btnDeleteProject.setDisable(false);
        }

        initLoader();
    }

    private void initLoader(){
        projectsListImpl.clearProjectsList();
        try {
            fxmlLoader.setLocation(getClass().getResource("..//fxml/editProject.fxml"));
            fxmlEdit = fxmlLoader.load();
            editProjectController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        colProjectId.setCellValueFactory(new PropertyValueFactory<Project,String>("id"));
        colProjectName.setCellValueFactory(new PropertyValueFactory<Project,String>("name"));
        colProjectDescr.setCellValueFactory(new PropertyValueFactory<Project,String>("descr"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<Project,String>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<Project,String>("endDate"));
        try {
            fillData();
        } catch (SQLException e) {
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
            case "btnAddProject":
                Project project = new Project();
                editProjectController.setProject(project);
                project = editProjectController.getProject();
                projectsListImpl.add(project);
                showDialog();
                break;

            case "btnEditProject":
                editProjectController.setProject((Project) tableProjects.getSelectionModel().getSelectedItem());
                showDialog();
                break;

            case "btnDeleteProject":
                Project delproject;
                delproject = (Project) tableProjects.getSelectionModel().getSelectedItem();
                comboProjectArrayList.remove(tableProjects.getSelectionModel().getSelectedIndex());
                projectArrayList.remove(delproject);
                projectsListImpl.delete(delproject);
                deleteProject(delproject);
                break;
        }

    }

    private void showDialog() {
        if (editProjectStage==null) {
            editProjectStage = new Stage();
            editProjectStage.setTitle("Редактирование записи");
            editProjectStage.setMinHeight(150);
            editProjectStage.setMinWidth(300);
            editProjectStage.setResizable(false);
            editProjectStage.setScene(new Scene(fxmlEdit));
            editProjectStage.initModality(Modality.WINDOW_MODAL);
            editProjectStage.initOwner((Stage) nodesource.getScene().getWindow());
        }
        editProjectStage.showAndWait();
    }

    private void deleteProject(Project project){
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.deleteProject(?)}");
            call.setString("id",project.getId());
            call.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void fillData() throws SQLException{
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.getProjects(?,?,?,?,?)}");
            call.registerOutParameter("id", Types.INTEGER);
            call.registerOutParameter("name", Types.NVARCHAR);
            call.registerOutParameter("descr", Types.NVARCHAR);
            call.registerOutParameter("startdate", Types.DATE);
            call.registerOutParameter("enddate", Types.DATE);

            ResultSet rs = call.executeQuery();
            while (rs.next()){
                Project project = new Project(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5));
                projectsListImpl.add(project);
            }
            tableProjects.setItems(projectsListImpl.getProjectsList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
