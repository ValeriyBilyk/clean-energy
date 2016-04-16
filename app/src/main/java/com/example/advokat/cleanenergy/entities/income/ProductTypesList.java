package com.example.advokat.cleanenergy.entities.income;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductTypesList implements Parcelable {

    private long id;
    private String name;

    protected ProductTypesList(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<ProductTypesList> CREATOR = new Creator<ProductTypesList>() {
        @Override
        public ProductTypesList createFromParcel(Parcel in) {
            return new ProductTypesList(in);
        }

        @Override
        public ProductTypesList[] newArray(int size) {
            return new ProductTypesList[size];
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
