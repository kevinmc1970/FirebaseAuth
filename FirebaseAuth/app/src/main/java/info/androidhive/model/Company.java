package info.androidhive.model;

import com.google.firebase.database.Exclude;

import java.util.List;

public class Company {
    @Exclude
    String id;
    String name;
    List<Station> stationList;

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }

    public String getId() {        return id;    }

    public void setId(String id) {        this.id = id;    }
}
