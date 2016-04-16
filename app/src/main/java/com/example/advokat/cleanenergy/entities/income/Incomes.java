package com.example.advokat.cleanenergy.entities.income;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Incomes implements Parcelable {

    private List<IncomeList> incomeList;

    protected Incomes(Parcel in) {
        incomeList = in.createTypedArrayList(IncomeList.CREATOR);
    }

    public static final Creator<Incomes> CREATOR = new Creator<Incomes>() {
        @Override
        public Incomes createFromParcel(Parcel in) {
            return new Incomes(in);
        }

        @Override
        public Incomes[] newArray(int size) {
            return new Incomes[size];
        }
    };

    public List<IncomeList> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(List<IncomeList> incomeList) {
        this.incomeList = incomeList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(incomeList);
    }
}
