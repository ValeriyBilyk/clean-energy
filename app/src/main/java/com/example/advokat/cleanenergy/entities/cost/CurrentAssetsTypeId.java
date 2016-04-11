package com.example.advokat.cleanenergy.entities.cost;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.advokat.cleanenergy.entities.ExpenditureTypeId;

public class CurrentAssetsTypeId implements Parcelable {

    private long id;
    private String name;
    private ExpenditureTypeId expenditureTypeId;

    protected CurrentAssetsTypeId(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<CurrentAssetsTypeId> CREATOR = new Creator<CurrentAssetsTypeId>() {
        @Override
        public CurrentAssetsTypeId createFromParcel(Parcel in) {
            return new CurrentAssetsTypeId(in);
        }

        @Override
        public CurrentAssetsTypeId[] newArray(int size) {
            return new CurrentAssetsTypeId[size];
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

    public ExpenditureTypeId getExpenditureTypeId() {
        return expenditureTypeId;
    }

    public void setExpenditureTypeId(ExpenditureTypeId expenditureTypeId) {
        this.expenditureTypeId = expenditureTypeId;
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
