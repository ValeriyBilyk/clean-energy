package com.example.advokat.cleanenergy.entities;

public class IncomeDTO {

    private long id;
    private double amount;
    private double bags;
    private long buyerId;
    private String comment;
    private long payer;
    private long incomeSourceId;
    private long incomeTypeId;
    private long measureUnitId;
    private double money;
    private long productTypeId;
    private long recepient;
    private String incomeDate;

    public IncomeDTO() {
    }

    public IncomeDTO(long id, double amount, double bags, long buyerId, String comment, long payer, long incomeSourceId, long incomeTypeId, long measureUnitId, double money, long productTypeId, long recepient, String incomeDate) {
        this.id = id;
        this.amount = amount;
        this.bags = bags;
        this.buyerId = buyerId;
        this.comment = comment;
        this.payer = payer;
        this.incomeSourceId = incomeSourceId;
        this.incomeTypeId = incomeTypeId;
        this.measureUnitId = measureUnitId;
        this.money = money;
        this.productTypeId = productTypeId;
        this.recepient = recepient;
        this.incomeDate = incomeDate;
    }

    public IncomeDTO(double amount, double bags, long buyerId, String comment, long payer, long incomeSourceId, long incomeTypeId, long measureUnitId, double money, long productTypeId, long recepient, String incomeDate) {
        this.amount = amount;
        this.bags = bags;
        this.buyerId = buyerId;
        this.comment = comment;
        this.payer = payer;
        this.incomeSourceId = incomeSourceId;
        this.incomeTypeId = incomeTypeId;
        this.measureUnitId = measureUnitId;
        this.money = money;
        this.productTypeId = productTypeId;
        this.recepient = recepient;
        this.incomeDate = incomeDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBags() {
        return bags;
    }

    public void setBags(double bags) {
        this.bags = bags;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getPayer() {
        return payer;
    }

    public void setPayer(long payer) {
        this.payer = payer;
    }

    public long getIncomeSourceId() {
        return incomeSourceId;
    }

    public void setIncomeSourceId(long incomeSourceId) {
        this.incomeSourceId = incomeSourceId;
    }

    public long getIncomeTypeId() {
        return incomeTypeId;
    }

    public void setIncomeTypeId(long incomeTypeId) {
        this.incomeTypeId = incomeTypeId;
    }

    public long getMeasureUnitId() {
        return measureUnitId;
    }

    public void setMeasureUnitId(long measureUnitId) {
        this.measureUnitId = measureUnitId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public long getRecepient() {
        return recepient;
    }

    public void setRecepient(long recepient) {
        this.recepient = recepient;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }
}

/*
      {
        "accessKeyDto": {
            "accessKey":"07dbe74b-7bbd-477f-85bf-1a6ec0ab6b0b"
                        },
        "incomeDTO": {
            "amount":"3",
            "bags":"1",
            "buyerId":"1",
            "comment":"kokokokok",
            "payer":"1",
            "incomeSourceId":"1",
            "incomeTypeId":"1",
            "measureUnitId":"1",
            "money": "21",
            "productTypeId": "2",
            "recepient": "1",
            "incomeDate":"2016-04-21"
        }
    }
    * */
