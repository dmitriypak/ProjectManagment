package interfaces.impl;

import interfaces.TasksList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Task;

/**
 * Created by HP on 21.05.2017.
 */
public class CollectionTasksList implements TasksList{
    public static ObservableList<Task> tasksList = FXCollections.observableArrayList();

    @Override
    public void add(Task task) {
        tasksList.add(task);
    }

    @Override
    public void delete(Task task) {
        tasksList.remove(task);


    }
    @Override
    public void update(Task task) {

    }

    public void clearTasksList(){
        tasksList.clear();
    }
    public ObservableList<Task> getTasksList() {
        return tasksList;
    }

}
