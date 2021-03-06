package info.androidhive.model;

import com.google.firebase.database.Exclude;

public class Station {

    @Exclude
    String id;
    String name;
    Boolean working;
    String available;
    String userName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getWorking() {
        return working;
    }

    public void setWorking(Boolean working) {
        this.working = working;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getUserName() {
        return userName;
    }

    public void setUser(String userName) {
        this.userName = userName;
    }

    public String getId() {        return id;    }

    public void setId(String id) {        this.id = id;    }
}
