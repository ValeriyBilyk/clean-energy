package com.example.advokat.cleanenergy.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Expenditure implements Parcelable {

    private long id;
    private long currentAssets;
    private long measureUnitSm;
    private double amount;
    private double money;
    private long payer;
    private String description;
    private long categoryExpenditure;
    private String nameExpenditure;
    private Date dateExpenditure;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCurrentAssets() {
        return currentAssets;
    }

    public void setCurrentAssets(long currentAssets) {
        this.currentAssets = currentAssets;
    }

    public long getMeasureUnitSm() {
        return measureUnitSm;
    }

    public void setMeasureUnitSm(long measureUnitSm) {
        this.measureUnitSm = measureUnitSm;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public long getPayer() {
        return payer;
    }

    public void setPayer(long payer) {
        this.payer = payer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCategoryExpenditure() {
        return categoryExpenditure;
    }

    public void setCategoryExpenditure(long categoryExpenditure) {
        this.categoryExpenditure = categoryExpenditure;
    }

    public String getNameExpenditure() {
        return nameExpenditure;
    }

    public void setNameExpenditure(String nameExpenditure) {
        this.nameExpenditure = nameExpenditure;
    }

    public Date getDateExpenditure() {
        return dateExpenditure;
    }

    public void setDateExpenditure(Date dateExpenditure) {
        this.dateExpenditure = dateExpenditure;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.currentAssets);
        dest.writeLong(this.measureUnitSm);
        dest.writeDouble(this.amount);
        dest.writeDouble(this.money);
        dest.writeLong(this.payer);
        dest.writeString(this.description);
        dest.writeLong(this.categoryExpenditure);
        dest.writeString(this.nameExpenditure);
        dest.writeLong(dateExpenditure != null ? dateExpenditure.getTime() : -1);
    }

    public Expenditure() {
    }

    protected Expenditure(Parcel in) {
        this.id = in.readLong();
        this.currentAssets = in.readLong();
        this.measureUnitSm = in.readLong();
        this.amount = in.readDouble();
        this.money = in.readDouble();
        this.payer = in.readLong();
        this.description = in.readString();
        this.categoryExpenditure = in.readLong();
        this.nameExpenditure = in.readString();
        long tmpDateExpenditure = in.readLong();
        this.dateExpenditure = tmpDateExpenditure == -1 ? null : new Date(tmpDateExpenditure);
    }

    public static final Parcelable.Creator<Expenditure> CREATOR = new Parcelable.Creator<Expenditure>() {
        @Override
        public Expenditure createFromParcel(Parcel source) {
            return new Expenditure(source);
        }

        @Override
        public Expenditure[] newArray(int size) {
            return new Expenditure[size];
        }
    };
}
