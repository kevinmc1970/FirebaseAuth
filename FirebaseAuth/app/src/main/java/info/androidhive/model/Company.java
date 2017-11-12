package info.androidhive.model;

import java.util.List;

public class Company {

    String name;
    List<Station> stationList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }
}
