package com.example.advokat.cleanenergy.entities.income;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class IncomeCategory extends RealmObject {

    @PrimaryKey
    private long id;
    RealmList<BuyerList> buyerList;
    RealmList<LocationsTypesList> locationsTypesList;
    RealmList<IncomeTypesList> incomeTypesList;
    RealmList<IncomeSourceList> incomeSourceList;
    RealmList<ProductTypesList> productTypesList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RealmList<BuyerList> getBuyerList() {
        return buyerList;
    }

    public void setBuyerList(RealmList<BuyerList> buyerList) {
        this.buyerList = buyerList;
    }

    public RealmList<LocationsTypesList> getLocationsTypesList() {
        return locationsTypesList;
    }

    public void setLocationsTypesList(RealmList<LocationsTypesList> locationsTypesList) {
        this.locationsTypesList = locationsTypesList;
    }

    public RealmList<IncomeTypesList> getIncomeTypesList() {
        return incomeTypesList;
    }

    public void setIncomeTypesList(RealmList<IncomeTypesList> incomeTypesList) {
        this.incomeTypesList = incomeTypesList;
    }

    public RealmList<IncomeSourceList> getIncomeSourceList() {
        return incomeSourceList;
    }

    public void setIncomeSourceList(RealmList<IncomeSourceList> incomeSourceList) {
        this.incomeSourceList = incomeSourceList;
    }

    public RealmList<ProductTypesList> getProductTypesList() {
        return productTypesList;
    }

    public void setProductTypesList(RealmList<ProductTypesList> productTypesList) {
        this.productTypesList = productTypesList;
    }
}
