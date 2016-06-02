package com.example.advokat.cleanenergy.entities.income;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BuyerList extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private Locations locations;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Locations getLocations() {
        return locations;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
    }

}
