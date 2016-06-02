package com.example.advokat.cleanenergy.entities.cost;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ExpenditureTypesId extends RealmObject{

    @PrimaryKey
    private long id;
    private String name;
    private boolean toCurrentAssets;

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

    public boolean isToCurrentAssets() {
        return toCurrentAssets;
    }

    public void setToCurrentAssets(boolean toCurrentAssets) {
        this.toCurrentAssets = toCurrentAssets;
    }

}
