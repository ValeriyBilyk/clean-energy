package com.example.advokat.cleanenergy.entities.income;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationsTypesList implements Parcelable {

    private long id;
    private String name;

    protected LocationsTypesList(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<LocationsTypesList> CREATOR = new Creator<LocationsTypesList>() {
        @Override
        public LocationsTypesList createFromParcel(Parcel in) {
            return new LocationsTypesList(in);
        }

        @Override
        public LocationsTypesList[] newArray(int size) {
            return new LocationsTypesList[size];
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
