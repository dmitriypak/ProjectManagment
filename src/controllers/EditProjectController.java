package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.MSSQLConnection;
import objects.Project;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static controllers.LoginController.loginUser;
import static controllers.MainDialogController.comboProjectArrayList;
import static controllers.MainDialogController.projectArrayList;


/**
 * Created by HP on 21.05.2017.
 */
public class EditProjectController {
    @FXML
    private TextField txtProjectName;
    @FXML
    private TextField txtProjectDescr;
    @FXML
    private DatePicker txtStartDate;
    @FXML
    private DatePicker txtEndDate;


    private Project project;

    @FXML
    public void initialize(){


    }


    public void setProject(Project project){
        if(project==null){
            return;
        }
        this.project=project;
        txtProjectName.setText(project.getName());
        txtProjectDescr.setText(project.getDescr());
        if(project.getStartDate() == ""){
            txtStartDate.setValue(LocalDate.now());
        }
        else{
            txtStartDate.setValue(LocalDate.parse(project.getStartDate()));
        }
        if(project.getStartDate() == ""){
            txtEndDate.setValue(LocalDate.now());
        }
        else{
            txtEndDate.setValue(LocalDate.parse(project.getStartDate()));
        }

        System.out.println(project.getId());
    }

    public Project getProject(){
        return project;
    }
    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }
    public void actionSave(ActionEvent actionEvent) {
        project.setName(txtProjectName.getText());
        project.setDescr(txtProjectDescr.getText());
        project.setStartDate(String.valueOf(txtStartDate.getValue()));
        project.setEndDate(String.valueOf(txtEndDate.getValue()));
        System.out.println(project.getId());
        if(project.getId()!=""){
            updateProject(project);
        }
        else{
            insertProject(project);

            PreparedStatement call = null;
            try {
                call = MSSQLConnection.getConnection().prepareStatement("select id from projects where name = ?");
                call.setString(1,project.getName());
                ResultSet rs = call.executeQuery();

                while (rs.next()){
                    project.setId(rs.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            projectArrayList.add(project);
            comboProjectArrayList.add(project.getName());
        }

        actionClose(actionEvent);
    }


    private void updateProject(Project project) {
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.updateProject(?,?,?,?,?)}");
            call.setString("id",project.getId());
            call.setString("name",project.getName());
            call.setString("descr",project.getDescr());
            call.setString("startdate",project.getStartDate());
            call.setString("enddate",project.getEndDate());
            call.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertProject(Project project) {
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.createProject(?,?,?,?)}");
            call.setString("name",project.getName());
            call.setString("descr",project.getDescr());
            call.setString("startdate",project.getStartDate());
            call.setString("enddate",project.getEndDate());
            call.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
