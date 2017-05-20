package objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by HP on 07.02.2017.
 */
public class ThreadWait implements Runnable {
    private Parent fxmlWait;
    private Stage waitStage;
    private FXMLLoader fxmlLoaderWait = new FXMLLoader();
    private Stage loginStage;
    //public volatile boolean threadcancel;

    public Thread thread;
    public ThreadWait(){
        thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {
        try {
            fxmlLoaderWait.setLocation(getClass().getResource("..//fxml/wait.fxml"));
            fxmlWait = fxmlLoaderWait.load();
            waitStage = new Stage();
            waitStage.setScene(new Scene(fxmlWait));
            waitStage.initOwner(loginStage);
            waitStage.hide();

            if(thread.isInterrupted()) throw new InterruptedException();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        waitStage.show();
    }
}
