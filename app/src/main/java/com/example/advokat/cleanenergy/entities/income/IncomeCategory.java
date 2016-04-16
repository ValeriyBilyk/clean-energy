package com.example.advokat.cleanenergy.entities.income;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class IncomeCategory implements Parcelable {

    private List<BuyerList> buyerList;
    private List<LocationsTypesList> locationsTypesList;
    private List<IncomeTypesList> incomeTypesList;
    private List<IncomeSourceList> incomeSourceList;
    private List<ProductTypesList> productTypesList;

    protected IncomeCategory(Parcel in) {
        buyerList = in.createTypedArrayList(BuyerList.CREATOR);
    }

    public static final Creator<IncomeCategory> CREATOR = new Creator<IncomeCategory>() {
        @Override
        public IncomeCategory createFromParcel(Parcel in) {
            return new IncomeCategory(in);
        }

        @Override
        public IncomeCategory[] newArray(int size) {
            return new IncomeCategory[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(buyerList);
    }
}
