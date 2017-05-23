package objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by HP on 23.05.2017.
 */
public class UserTask extends User {
    private SimpleBooleanProperty include = new SimpleBooleanProperty(false);
    private SimpleStringProperty taskid = new SimpleStringProperty("");
    public UserTask(String id, String role, String fullname, Boolean include, String taskid){
        super(id,role,fullname);
        this.include = new SimpleBooleanProperty(include);
        this.taskid = new SimpleStringProperty(taskid);
    }

    public Boolean getInclude() {
        return include.get();
    }

    public SimpleBooleanProperty includeProperty() {
        return include;
    }

    public void setInclude(Boolean include) {
        this.include.set(include);
    }

    public String getTaskid() {
        return taskid.get();
    }

    public SimpleStringProperty taskidProperty() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid.set(taskid);
    }
}
