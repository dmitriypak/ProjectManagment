package objects;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by HP on 21.05.2017.
 */
public class User {
    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty username = new SimpleStringProperty("");
    private SimpleStringProperty role = new SimpleStringProperty("");
    private SimpleStringProperty password = new SimpleStringProperty("");
    private SimpleStringProperty email = new SimpleStringProperty("");
    private SimpleStringProperty fullname = new SimpleStringProperty("");

    public User(){

    }
    public User(String id, String role, String fullname) {
        this.id = new SimpleStringProperty(id);
        this.role = new SimpleStringProperty(role);
        this.fullname = new SimpleStringProperty(fullname);
    }
    public User(String id, String username, String password, String email, String role, String fullname) {
        this.id = new SimpleStringProperty(id);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.role = new SimpleStringProperty(role);
        this.fullname = new SimpleStringProperty(fullname);
    }

    public SimpleStringProperty id() {
        return id;
    }
    public SimpleStringProperty usernameProperty() {
        return username;
    }
    public SimpleStringProperty roleProperty() {
        return role;
    }
    public SimpleStringProperty passwordProperty() {
        return password;
    }
    public SimpleStringProperty emailProperty() {
        return email;
    }
    public SimpleStringProperty fullnameProperty() {
        return fullname;
    }

    public String getId() {
        return id.get();
    }
    public String getUsername() {
        return username.get();
    }
    public String getPassword() {
        return password.get();
    }
    public String getRole() {
        return role.get();
    }
    public String getEmail() {
        return email.get();
    }
    public String getFullname() {
        return fullname.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }
    public void setUsername(String username) {
        this.username.set(username);
    }
    public void setPassword(String password) {
        this.password.set(password);
    }
    public void setEmail(String email) {
        this.email.set(email);
    }
    public void setFullname(String fullname) {
        this.fullname.set(fullname);
    }
}

