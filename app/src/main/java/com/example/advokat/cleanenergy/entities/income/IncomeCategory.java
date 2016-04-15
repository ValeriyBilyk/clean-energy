package com.example.advokat.cleanenergy.entities.income;

import java.util.List;

public class IncomeCategory {

    private List<BuyerList> buyerList;
    private List<LocationsTypesList> locationsTypesList;
    private List<IncomeTypesList> incomeTypesList;
    private List<IncomeSourceList> incomeSourceList;
    private List<ProductTypesList> productTypesList;

    public List<BuyerList> getBuyerList() {
        return buyerList;
    }

    public void setBuyerList(List<BuyerList> buyerList) {
        this.buyerList = buyerList;
    }

    public List<LocationsTypesList> getLocationsTypesList() {
        return locationsTypesList;
    }

    public void setLocationsTypesList(List<LocationsTypesList> locationsTypesList) {
        this.locationsTypesList = locationsTypesList;
    }

    public List<IncomeTypesList> getIncomeTypesList() {
        return incomeTypesList;
    }

    public void setIncomeTypesList(List<IncomeTypesList> incomeTypesList) {
        this.incomeTypesList = incomeTypesList;
    }

    public List<IncomeSourceList> getIncomeSourceList() {
        return incomeSourceList;
    }

    public void setIncomeSourceList(List<IncomeSourceList> incomeSourceList) {
        this.incomeSourceList = incomeSourceList;
    }

    public List<ProductTypesList> getProductTypesList() {
        return productTypesList;
    }

    public void setProductTypesList(List<ProductTypesList> productTypesList) {
        this.productTypesList = productTypesList;
    }
}
