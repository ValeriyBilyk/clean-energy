package com.example.advokat.cleanenergy.entities;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrentAsset extends RealmObject{

    @PrimaryKey
    private long id;

    RealmList<ExpenditureTypes> expenditureTypes;
    RealmList<MeasureUnit> measureUnit;
    RealmList<CurrentAssetsType> currentAssetsType;
    RealmList<Payer> payer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RealmList<ExpenditureTypes> getExpenditureTypes() {
        return expenditureTypes;
    }

    public void setExpenditureTypes(RealmList<ExpenditureTypes> expenditureTypes) {
        this.expenditureTypes = expenditureTypes;
    }

    public RealmList<MeasureUnit> getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(RealmList<MeasureUnit> measureUnit) {
        this.measureUnit = measureUnit;
    }

    public RealmList<CurrentAssetsType> getCurrentAssetsType() {
        return currentAssetsType;
    }

    public void setCurrentAssetsType(RealmList<CurrentAssetsType> currentAssetsType) {
        this.currentAssetsType = currentAssetsType;
    }

    public RealmList<Payer> getPayer() {
        return payer;
    }

    public void setPayer(RealmList<Payer> payer) {
        this.payer = payer;
    }
}
