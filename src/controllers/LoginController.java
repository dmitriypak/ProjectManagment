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
import objects.ThreadWait;
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
    ThreadWait threadWait= new ThreadWait();
    public Connection connection;

    @FXML
    public void initialize(){

        initLoader();
    }

    private void initLoader(){
        try {
            fxmlLoader.setLocation(getClass().getResource("..//fxml/main.fxml"));
            fxmlMain = fxmlLoader.load();
            mainDialogController = fxmlLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//    txtPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
//        @Override
//        public void handle(KeyEvent event) {
//            if(event.getCode()== KeyCode.ENTER){
//
//            }
//        }
//    });
    public void hideMessage(ActionEvent actionEvent){
        labelWrongUser.setVisible(false);
    }
    public void actionLogin(ActionEvent actionEvent) throws InterruptedException {
        int checkUser = 0;
//        Object source = actionEvent.getSource();
//        if(!(source instanceof Button))
//            return;
//        Button clickedButton = (Button) source;


        try {
            connection = MSSQLConnection.connector();
            DatabaseMetaData dmd = connection.getMetaData();
            ResultSet rs = dmd.getTables(null,null,null,new String[]{"Table"});
            PreparedStatement pstmt = connection.prepareStatement("select 1 from _apsuser where username = ? and password = ?");
            pstmt.setString(1,txtUsername.getText());
            pstmt.setString(2,txtPassword.getText());
            rs = pstmt.executeQuery();

            if(rs.next())
                checkUser = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(checkUser == 1) {
            if (mainStage == null) {
                mainStage = new Stage();
                mainStage.setTitle("Главное меню");
                mainStage.setScene(new Scene(fxmlMain));
                mainStage.initOwner(loginStage);
                mainStage.setResizable(false);
                labelWrongUser.setVisible(false);
            }

            Thread.sleep(2000);
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
