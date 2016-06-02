package com.example.advokat.cleanenergy.entities.cost;

import com.example.advokat.cleanenergy.entities.ExpenditureTypeId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrentAssetsTypeId extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private ExpenditureTypeId expenditureTypeId;

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

    public ExpenditureTypeId getExpenditureTypeId() {
        return expenditureTypeId;
    }

    public void setExpenditureTypeId(ExpenditureTypeId expenditureTypeId) {
        this.expenditureTypeId = expenditureTypeId;
    }

}
