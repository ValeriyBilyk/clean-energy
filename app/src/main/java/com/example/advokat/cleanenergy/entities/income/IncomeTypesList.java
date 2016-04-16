package com.example.advokat.cleanenergy.entities.income;

import android.os.Parcel;
import android.os.Parcelable;

public class IncomeTypesList implements Parcelable{

    private long id;
    private String name;

    protected IncomeTypesList(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<IncomeTypesList> CREATOR = new Creator<IncomeTypesList>() {
        @Override
        public IncomeTypesList createFromParcel(Parcel in) {
            return new IncomeTypesList(in);
        }

        @Override
        public IncomeTypesList[] newArray(int size) {
            return new IncomeTypesList[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
    }
}
