package objects;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * Created by HP on 22.05.2017.
 */
public class Project {
    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty descr = new SimpleStringProperty("");
    private ArrayList<User> usersList = new ArrayList<User>();
    private ArrayList<Task> tasksList = new ArrayList<Task>();
    private SimpleStringProperty startDate = new SimpleStringProperty("");
    private SimpleStringProperty endDate = new SimpleStringProperty("");

    public String getId() {
        return id.get();
    }
    public void setId(String id) {
        this.id.set(id);
    }

    public SimpleStringProperty idProperty() {
        return id;
    }
    public SimpleStringProperty nameProperty() {
        return name;
    }
    public SimpleStringProperty descrProperty() {
        return descr;
    }
    public SimpleStringProperty startDateProperty() {
        return startDate;
    }
    public SimpleStringProperty endDateProperty() {
        return endDate;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescr() {
        return descr.get();
    }



    public void setDescr(String descr) {
        this.descr.set(descr);
    }

    public ArrayList<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<User> usersList) {
        this.usersList = usersList;
    }

    public ArrayList<Task> getTasksList() {
        return tasksList;
    }

    public void setTasksList(ArrayList<Task> tasksList) {
        this.tasksList = tasksList;
    }

    public String getStartDate() {
        return startDate.get();
    }



    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public String getEndDate() {
        return endDate.get();
    }



    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
    }

    public Project(){

    }
    public Project(String id, String name) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public Project(String id, String name, String descr, String startDate, String endDate) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.descr = new SimpleStringProperty(descr);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
    }
}
