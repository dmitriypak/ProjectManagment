package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.MSSQLConnection;
import objects.User;
import org.controlsfx.control.textfield.CustomTextField;

import java.io.IOException;
import java.sql.*;

/**
 * Created by HP on 06.02.2017.
 */
public class LoginController {
    @FXML
    private Button btnEnter;
    @FXML
    private CustomTextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Label labelWrongUser;

    private Stage loginStage;

    private Parent fxmlMain;

    private MainDialogController mainDialogController;
    private Stage mainStage;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    public Connection connection;
    public static String role = "";

    public Label getLabelWrongUser() {
        return labelWrongUser;
    }

    public static User loginUser = new User();
    ResultSet resultSet;
    @FXML
    public void initialize(){

        initLoader();
    }

    private void initLoader(){
        try {
            fxmlLoader.setLocation(getClass().getResource("../fxml/main.fxml"));
            fxmlMain = fxmlLoader.load();
            mainDialogController = fxmlLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void hideMessage(ActionEvent actionEvent){
        labelWrongUser.setVisible(false);
    }
    public void actionLogin(ActionEvent actionEvent) throws InterruptedException {
        byte checkUser = 0;
        try {
            connection = MSSQLConnection.getConnection();
            CallableStatement call = connection.prepareCall("{call dbo.LoginUser(?,?,?,?)}");
            call.setString("username",txtUsername.getText());
            call.setString("password",txtPassword.getText());
            call.registerOutParameter(3, Types.TINYINT);
            call.registerOutParameter(4, Types.NVARCHAR);
            resultSet = call.executeQuery();

            if (resultSet.next()){
                System.out.println("User Logged");
                checkUser = resultSet.getByte(1);
                role = resultSet.getString(2);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(checkUser == 1) {
            loginUser.setUsername(txtUsername.getText());
            loginUser.setPassword(txtPassword.getText());
            loginUser.setRole(role);
            if (mainStage == null) {
                mainStage = new Stage();
                mainStage.setTitle("Главное меню");
                mainStage.setScene(new Scene(fxmlMain));
                mainStage.initOwner(loginStage);
                mainStage.setResizable(false);
                labelWrongUser.setVisible(false);
                mainDialogController.setLoginUser(loginUser);
            }
            mainStage.show();

            Node sourceWindow = (Node) actionEvent.getSource();
            Stage stage = (Stage) sourceWindow.getScene().getWindow();
            stage.close();
        }
        else{
            labelWrongUser.setVisible(true);
        }

    }

}
