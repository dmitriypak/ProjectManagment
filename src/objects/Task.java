package objects;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * Created by HP on 22.05.2017.
 */
public class Task {
   // private Project project;
   private ArrayList<User>userTaskList = new ArrayList<User>();

    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty descr = new SimpleStringProperty("");
    private SimpleStringProperty startDate = new SimpleStringProperty("");
    private SimpleStringProperty endDate = new SimpleStringProperty("");
    private SimpleStringProperty estimatedWorkHrs = new SimpleStringProperty("");
    private SimpleStringProperty factWorkHrs = new SimpleStringProperty("");
    private SimpleStringProperty priority = new SimpleStringProperty("");
    private SimpleStringProperty complete = new SimpleStringProperty();
    private SimpleStringProperty status = new SimpleStringProperty();
    private SimpleStringProperty projectId = new SimpleStringProperty();

    public Task(){

    }

    public Task(String projectId){
        this.projectId = new SimpleStringProperty(projectId);
    }
    public Task(String id, String name){
        this.id = new SimpleStringProperty(name);
        this.name = new SimpleStringProperty(name);
    }

    public Task(String id, String name, String descr, String startDate, String endDate, String estimatedWorkHrs, String factWorkHrs, String priority,
                String complete, String status, String projectId) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.descr = new SimpleStringProperty(descr);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
        this.estimatedWorkHrs = new SimpleStringProperty(estimatedWorkHrs);
        this.factWorkHrs = new SimpleStringProperty(factWorkHrs);
        this.priority = new SimpleStringProperty(priority);
        this.complete = new SimpleStringProperty(complete);
        this.status = new SimpleStringProperty(status);
        this.projectId = new SimpleStringProperty(projectId);
    }

    private void addWorkers(User user){
        userTaskList.add(user);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescr() {
        return descr.get();
    }

    public SimpleStringProperty descrProperty() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr.set(descr);
    }

    public String getStartDate() {
        return startDate.get();
    }

    public SimpleStringProperty startDateProperty() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public String getEndDate() {
        return endDate.get();
    }

    public SimpleStringProperty endDateProperty() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
    }

    public String getEstimatedWorkHrs() {
        return estimatedWorkHrs.get();
    }

    public SimpleStringProperty estimatedWorkHrsProperty() {
        return estimatedWorkHrs;
    }

    public void setEstimatedWorkHrs(String estimatedWorkHrs) {
        this.estimatedWorkHrs.set(estimatedWorkHrs);
    }

    public String getFactWorkHrs() {
        return factWorkHrs.get();
    }

    public SimpleStringProperty factWorkHrsProperty() {
        return factWorkHrs;
    }

    public void setFactWorkHrs(String factWorkHrs) {
        this.factWorkHrs.set(factWorkHrs);
    }

    public String getPriority() {
        return priority.get();
    }

    public SimpleStringProperty priorityProperty() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public String getComplete() {
        return complete.get();
    }

    public SimpleStringProperty completeProperty() {
        return complete;
    }

    public void setComplete(String completed) {
        this.complete.set(completed);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getProjectId() {
        return projectId.get();
    }

    public SimpleStringProperty projectIdProperty() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId.set(projectId);
    }



}
