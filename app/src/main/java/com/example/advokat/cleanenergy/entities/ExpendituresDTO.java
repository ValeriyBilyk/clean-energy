package com.example.advokat.cleanenergy.entities;

public class ExpendituresDTO {

    private Long id;
    private Long currentAssets;
    private Long measureUnitSm;
    private double amount;
    private double money;
    private Long payer;
    private String description;
    private Long categoryExpenditure;
    private String nameExpenditure;
    private String dateExpenditure;

    public ExpendituresDTO() {

    }

    public ExpendituresDTO(Long currentAssets, Long measureUnitSm, double amount, double money, Long payer, String description, String dateExpenditure) {
        this.currentAssets = currentAssets;
        this.measureUnitSm = measureUnitSm;
        this.amount = amount;
        this.money = money;
        this.payer = payer;
        this.description = description;
        this.dateExpenditure = dateExpenditure;
    }

    public ExpendituresDTO(Long measureUnitSm, double amount, double money, Long payer, String description, Long categoryExpenditure, String nameExpenditure, String dateExpenditure) {
        this.measureUnitSm = measureUnitSm;
        this.amount = amount;
        this.money = money;
        this.payer = payer;
        this.description = description;
        this.categoryExpenditure = categoryExpenditure;
        this.nameExpenditure = nameExpenditure;
        this.dateExpenditure = dateExpenditure;
    }

    public ExpendituresDTO(Long id, Long currentAssets, Long measureUnitSm, double amount, double money, Long payer, String description, String dateExpenditure) {
        this.id = id;
        this.currentAssets = currentAssets;
        this.measureUnitSm = measureUnitSm;
        this.amount = amount;
        this.money = money;
        this.payer = payer;
        this.description = description;
        this.dateExpenditure = dateExpenditure;
    }

    public ExpendituresDTO(Long id, Long measureUnitSm, double amount, double money, Long payer, String description, Long categoryExpenditure, String nameExpenditure, String dateExpenditure) {
        this.id = id;
        this.measureUnitSm = measureUnitSm;
        this.amount = amount;
        this.money = money;
        this.payer = payer;
        this.description = description;
        this.categoryExpenditure = categoryExpenditure;
        this.nameExpenditure = nameExpenditure;
        this.dateExpenditure = dateExpenditure;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurrentAssets() {
        return currentAssets;
    }

    public void setCurrentAssets(Long currentAssets) {
        this.currentAssets = currentAssets;
    }

    public Long getMeasureUnitSm() {
        return measureUnitSm;
    }

    public void setMeasureUnitSm(Long measureUnitSm) {
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

    public Long getPayer() {
        return payer;
    }

    public void setPayer(Long payer) {
        this.payer = payer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryExpenditure() {
        return categoryExpenditure;
    }

    public void setCategoryExpenditure(Long categoryExpenditure) {
        this.categoryExpenditure = categoryExpenditure;
    }

    public String getNameExpenditure() {
        return nameExpenditure;
    }

    public void setNameExpenditure(String nameExpenditure) {
        this.nameExpenditure = nameExpenditure;
    }

    public String getDateExpenditure() {
        return dateExpenditure;
    }

    public void setDateExpenditure(String dateExpenditure) {
        this.dateExpenditure = dateExpenditure;
    }
}

