package com.example.advokat.cleanenergy.entities.cost;

import java.util.List;

public class Cost {

    private List<SumMoney> sumMoney;
    private List<Expenditures> expenditures;

    public List<SumMoney> getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(List<SumMoney> sumMoney) {
        this.sumMoney = sumMoney;
    }

    public List<Expenditures> getExpenditures() {
        return expenditures;
    }

    public void setExpenditures(List<Expenditures> expenditures) {
        this.expenditures = expenditures;
    }
}
