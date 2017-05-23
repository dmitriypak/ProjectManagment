package objects;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by HP on 22.05.2017.
 */
public class Status {
    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty is_complete = new SimpleStringProperty("");

    public Status(){

    }

    public Status(String id, String name, String is_complete) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.is_complete = new SimpleStringProperty(is_complete);
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

    public String getIs_complete() {
        return is_complete.get();
    }

    public SimpleStringProperty is_completeProperty() {
        return is_complete;
    }

    public void setIs_complete(String is_complete) {
        this.is_complete.set(is_complete);
    }
}
