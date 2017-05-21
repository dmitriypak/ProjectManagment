package interfaces.impl;

import interfaces.UsersList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.User;

/**
 * Created by HP on 21.05.2017.
 */
public class CollectionUsersList implements UsersList{
    public static ObservableList<User> usersList = FXCollections.observableArrayList();

    @Override
    public void add(User user) {
        usersList.add(user);
    }

    @Override
    public void delete(User user) {
        usersList.remove(user);


    }

    @Override
    public void update(User user) {

    }

    public void clearUserList(){
        usersList.clear();
    }
    public ObservableList<User> getUsersList() {
        return usersList;
    }


}
