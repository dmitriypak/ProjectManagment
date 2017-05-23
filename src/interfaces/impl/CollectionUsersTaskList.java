package interfaces.impl;

import interfaces.UsersTaskList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.UserTask;

/**
 * Created by HP on 23.05.2017.
 */
public class CollectionUsersTaskList implements UsersTaskList {


    public static ObservableList<UserTask> usersTaskList = FXCollections.observableArrayList();

    @Override
    public void add(UserTask userTask) {
        usersTaskList.add(userTask);
    }

    @Override
    public void delete(UserTask userTask) {
        usersTaskList.remove(userTask);
    }

    @Override
    public void update(UserTask userTask) {

    }

    public void clearUserList(){
        usersTaskList.clear();
    }
    public ObservableList<UserTask> getUsersList() {
        return usersTaskList;
    }


}
