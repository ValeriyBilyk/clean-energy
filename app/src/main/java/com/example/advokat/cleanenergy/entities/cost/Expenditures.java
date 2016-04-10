package com.example.advokat.cleanenergy.entities.cost;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.advokat.cleanenergy.entities.MeasureUnit;
import com.example.advokat.cleanenergy.entities.Payer;

public class Expenditures implements Parcelable {

    private long id;
    private ExpenditureTypesId expenditureTypesId;
    private CurrentAssetsTypeId currentAssetsTypeId;
    private String expenditureDate;
    private double amount;
    private MeasureUnit measureUnit;
    private double money;
    private String description;
    private String comment;
    private Payer payer;

    protected Expenditures(Parcel in) {
        id = in.readLong();
        expenditureDate = in.readString();
        amount = in.readLong();
        money = in.readDouble();
        description = in.readString();
        comment = in.readString();
    }

    public static final Creator<Expenditures> CREATOR = new Creator<Expenditures>() {
        @Override
        public Expenditures createFromParcel(Parcel in) {
            return new Expenditures(in);
        }

        @Override
        public Expenditures[] newArray(int size) {
            return new Expenditures[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ExpenditureTypesId getExpenditureTypesId() {
        return expenditureTypesId;
    }

    public void setExpenditureTypesId(ExpenditureTypesId expenditureTypesId) {
        this.expenditureTypesId = expenditureTypesId;
    }

    public CurrentAssetsTypeId getCurrentAssetsTypeId() {
        return currentAssetsTypeId;
    }

    public void setCurrentAssetsTypeId(CurrentAssetsTypeId currentAssetsTypeId) {
        this.currentAssetsTypeId = currentAssetsTypeId;
    }

    public String getExpenditureDate() {
        return expenditureDate;
    }

    public void setExpenditureDate(String expenditureDate) {
        this.expenditureDate = expenditureDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(expenditureDate);
        dest.writeDouble(amount);
        dest.writeDouble(money);
        dest.writeString(description);
        dest.writeString(comment);
    }
}
