package com.example.advokat.cleanenergy.entities;

import java.util.List;

public class CurrentAsset {

    List<ExpenditureTypes> expenditureTypes;
    List<MeasureUnit> measureUnit;
    List<CurrentAssetsType> currentAssetsType;
    List<Payer> payer;

    public List<ExpenditureTypes> getExpenditureTypes() {
        return expenditureTypes;
    }

    public void setExpenditureTypes(List<ExpenditureTypes> expenditureTypes) {
        this.expenditureTypes = expenditureTypes;
    }

    public List<MeasureUnit> getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(List<MeasureUnit> measureUnit) {
        this.measureUnit = measureUnit;
    }

    public List<CurrentAssetsType> getCurrentAssetsType() {
        return currentAssetsType;
    }

    public void setCurrentAssetsType(List<CurrentAssetsType> currentAssetsType) {
        this.currentAssetsType = currentAssetsType;
    }

    public List<Payer> getPayer() {
        return payer;
    }

    public void setPayer(List<Payer> payer) {
        this.payer = payer;
    }
}
