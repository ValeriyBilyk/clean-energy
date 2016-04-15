package com.example.advokat.cleanenergy.entities.income;

import com.example.advokat.cleanenergy.entities.MeasureUnit;
import com.example.advokat.cleanenergy.entities.Payer;

import java.util.Date;

public class IncomeList {

    private long id;
    private BuyerList buyerId;
    private String buyer;
    private Date incomeDate;
    private IncomeTypesList incomeTypes;
    private IncomeSourceList incomeSources;
    private ProductTypesList productTypesTypes;
    private double amount;
    private long bags;
    private double money;
    private Locations locations;
    private String comment;
    private MeasureUnit measureUnit;
    private Payer payer;
    private Recepient recepient;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BuyerList getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(BuyerList buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public Date getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    public IncomeTypesList getIncomeTypes() {
        return incomeTypes;
    }

    public void setIncomeTypes(IncomeTypesList incomeTypes) {
        this.incomeTypes = incomeTypes;
    }

    public IncomeSourceList getIncomeSources() {
        return incomeSources;
    }

    public void setIncomeSources(IncomeSourceList incomeSources) {
        this.incomeSources = incomeSources;
    }

    public ProductTypesList getProductTypesTypes() {
        return productTypesTypes;
    }

    public void setProductTypesTypes(ProductTypesList productTypesTypes) {
        this.productTypesTypes = productTypesTypes;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getBags() {
        return bags;
    }

    public void setBags(long bags) {
        this.bags = bags;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Locations getLocations() {
        return locations;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public Recepient getRecepient() {
        return recepient;
    }

    public void setRecepient(Recepient recepient) {
        this.recepient = recepient;
    }
}
