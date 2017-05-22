package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.MSSQLConnection;
import objects.User;

import java.sql.CallableStatement;
import java.sql.SQLException;


/**
 * Created by HP on 21.05.2017.
 */
public class EditUserController {
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox comboRole;


    private User user;
    @FXML
    public void initialize(){
        createComboBox();
    }




    public void setUser(User user){
        if(user==null){
            return;
        }
        this.user=user;
        txtUsername.setText(user.getUsername());
        txtPassword.setText(user.getPassword());
        txtEmail.setText(user.getEmail());
        comboRole.setValue(user.getRole());
        System.out.println(user.getId());
    }

    public User getUser(){
        return user;
    }
    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }
    public void actionSave(ActionEvent actionEvent) {
        user.setUsername(txtUsername.getText());
        user.setPassword(txtPassword.getText());
        user.setEmail(txtEmail.getText());
        user.setRole((String) comboRole.getValue());
        if(user.getId()!=""){
            updateUser(user);
        }
        else{
            insertUser(user);
        }

        actionClose(actionEvent);
    }

    private void updateUser(User user) {
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.updateUser(?,?,?,?,?)}");
            call.setString("id",user.getId());
            call.setString("username",user.getUsername());
            call.setString("password",user.getPassword());
            call.setString("email",user.getEmail());
            call.setString("role",user.getRole());
            call.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertUser(User user) {
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.createUser(?,?,?,?)}");
            call.setString("username",user.getUsername());
            call.setString("password",user.getPassword());
            call.setString("email",user.getEmail());
            call.setString("role",user.getRole());
            call.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createComboBox(){
        ObservableList<String> rolesList = FXCollections.observableArrayList(
                "admin",
                "user");
        comboRole.setItems(rolesList);
    }

}
