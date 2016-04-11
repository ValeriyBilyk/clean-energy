package com.example.advokat.cleanenergy.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class MeasureUnit implements Parcelable {

    private long id;
    private String name;

    protected MeasureUnit(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<MeasureUnit> CREATOR = new Creator<MeasureUnit>() {
        @Override
        public MeasureUnit createFromParcel(Parcel in) {
            return new MeasureUnit(in);
        }

        @Override
        public MeasureUnit[] newArray(int size) {
            return new MeasureUnit[size];
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
