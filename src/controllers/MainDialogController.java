package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
    private Label labelRole;
    @FXML
    public void initialize(){

        initLoader();
    }

    private void initLoader(){
        labelRole.setText("Вы вошли как " + loginUser.getRole());
    }


    public void pushBtnUsersSettings(){
        btnUsersSettings.fire();
    }

    public void showUsers(ActionEvent actionEvent){
        System.out.println(loginUser.getUsername());
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
}
