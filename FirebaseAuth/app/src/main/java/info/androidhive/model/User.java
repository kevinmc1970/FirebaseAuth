package info.androidhive.model;

import com.google.firebase.database.Exclude;

import java.util.Map;

public class User {

    String id;
    String name;
    Map<String, Boolean> companies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Boolean> getCompanies() {
        return companies;
    }

    public void setCompanies(Map<String, Boolean> companies) {
        this.companies = companies;
    }

    @Exclude
    public String getId() {        return id;    }

    public void setId(String id) {        this.id = id;    }
}
