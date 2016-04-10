package com.example.advokat.cleanenergy.entities.cost;

import com.example.advokat.cleanenergy.entities.Payer;

public class SumMoney {

    private Payer payer;
    private double money;

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
