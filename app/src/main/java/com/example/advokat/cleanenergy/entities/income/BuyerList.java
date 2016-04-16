package com.example.advokat.cleanenergy.entities.income;

import android.os.Parcel;
import android.os.Parcelable;

public class BuyerList implements Parcelable {

    private long id;
    private String name;
    private Locations locations;

    protected BuyerList(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<BuyerList> CREATOR = new Creator<BuyerList>() {
        @Override
        public BuyerList createFromParcel(Parcel in) {
            return new BuyerList(in);
        }

        @Override
        public BuyerList[] newArray(int size) {
            return new BuyerList[size];
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

    public Locations getLocations() {
        return locations;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
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
