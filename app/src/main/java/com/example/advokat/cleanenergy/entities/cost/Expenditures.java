package com.example.advokat.cleanenergy.entities.cost;

import com.example.advokat.cleanenergy.entities.MeasureUnit;
import com.example.advokat.cleanenergy.entities.Payer;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Expenditures extends RealmObject {

    @PrimaryKey
    private long id;
    private ExpenditureTypesId expenditureTypesId;
    private CurrentAssetsTypeId currentAssetsTypeId;
    private Date expenditureDate;
    private double amount;
    private MeasureUnit measureUnit;
    private double money;
    private String description;
    private String comment;
    private Payer payer;

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

    public Date getExpenditureDate() {
        return expenditureDate;
    }

    public void setExpenditureDate(Date expenditureDate) {
        this.expenditureDate = expenditureDate;
    }

    public void setCurrentAssetsTypeId(CurrentAssetsTypeId currentAssetsTypeId) {
        this.currentAssetsTypeId = currentAssetsTypeId;
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
}
