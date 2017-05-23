package controllers;

import interfaces.impl.CollectionUsersList;
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
import objects.User;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by HP on 21.05.2017.
 */
public class UserController {
    @FXML
    private TableView tableUsers;
    @FXML
    private TableColumn colUserId;
    @FXML
    private TableColumn colUsername;
    @FXML
    private TableColumn colPassword;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colRole;
    @FXML
    private Button btnAddUser;
    @FXML
    private Button btnEditUser;
    @FXML
    private Button btnDeleteUser;
    @FXML
    private TableColumn colFullName;


    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditUserController editUserController;
    private Stage editUserStage;
    private Node nodesource;
    private CollectionUsersList usersListImpl = new CollectionUsersList();

    @FXML
    public void initialize(){
        initLoader();
    }

    private void initLoader(){
        usersListImpl.clearUserList();
        try {
            fxmlLoader.setLocation(getClass().getResource("..//fxml/editUser.fxml"));
            fxmlEdit = fxmlLoader.load();
            editUserController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }


        colUserId.setCellValueFactory(new PropertyValueFactory<User,String>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
        colEmail.setCellValueFactory(new PropertyValueFactory<User,String>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<User,String>("role"));
        colFullName.setCellValueFactory(new PropertyValueFactory<User,String>("fullname"));
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
            case "btnAddUser":
                User user = new User();
                editUserController.setUser(user);
                user = editUserController.getUser();
                usersListImpl.add(user);
                showDialog();
                break;

            case "btnEditUser":
                editUserController.setUser((User)tableUsers.getSelectionModel().getSelectedItem());
                showDialog();
                break;

            case "btnDeleteUser":
                User deluser;
                deluser = (User)tableUsers.getSelectionModel().getSelectedItem();
                usersListImpl.delete(deluser);
                deleteUser(deluser);
                break;
        }

    }

    private void showDialog() {
        if (editUserStage==null) {
            editUserStage = new Stage();
            editUserStage.setTitle("Редактирование записи");
            editUserStage.setMinHeight(150);
            editUserStage.setMinWidth(300);
            editUserStage.setResizable(false);
            editUserStage.setScene(new Scene(fxmlEdit));
            editUserStage.initModality(Modality.WINDOW_MODAL);
            editUserStage.initOwner((Stage) nodesource.getScene().getWindow());
        }
        editUserStage.showAndWait();
    }

    private void deleteUser(User user){
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.deleteUser(?)}");
            call.setString("id",user.getId());
            call.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void fillData() throws SQLException{
        try {
            CallableStatement call = MSSQLConnection.getConnection().prepareCall("{call dbo.getUsers(?,?,?,?,?,?)}");
            call.registerOutParameter("id", Types.INTEGER);
            call.registerOutParameter("username", Types.NVARCHAR);
            call.registerOutParameter("password", Types.NVARCHAR);
            call.registerOutParameter("email", Types.NVARCHAR);
            call.registerOutParameter("role_code", Types.NVARCHAR);
            call.registerOutParameter("fullname", Types.NVARCHAR);

            ResultSet rs = call.executeQuery();
            while (rs.next()){
                User user = new User(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5),rs.getString(6));
                usersListImpl.add(user);
            }
            tableUsers.setItems(usersListImpl.getUsersList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
