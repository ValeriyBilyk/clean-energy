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
