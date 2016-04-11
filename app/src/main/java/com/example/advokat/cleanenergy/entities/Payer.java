package com.example.advokat.cleanenergy.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Payer implements Parcelable {

    private long id;
    private String name;

    protected Payer(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<Payer> CREATOR = new Creator<Payer>() {
        @Override
        public Payer createFromParcel(Parcel in) {
            return new Payer(in);
        }

        @Override
        public Payer[] newArray(int size) {
            return new Payer[size];
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
