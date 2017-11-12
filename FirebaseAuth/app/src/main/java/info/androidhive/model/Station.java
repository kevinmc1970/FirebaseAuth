package info.androidhive.model;

public class Station {

    String name;
    Boolean working;
    long available;
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

    public long getAvailable() {
        return available;
    }

    public void setAvailable(long available) {
        this.available = available;
    }

    public String getUserName() {
        return userName;
    }

    public void setUser(String userName) {
        this.userName = userName;
    }
}