package com.example.advokat.cleanenergy.entities.cost;

import android.os.Parcel;
import android.os.Parcelable;

public class ExpenditureTypesId implements Parcelable {

    private long id;
    private String name;
    private boolean toCurrentAssets;

    protected ExpenditureTypesId(Parcel in) {
        id = in.readLong();
        name = in.readString();
        toCurrentAssets = in.readByte() != 0;
    }

    public static final Creator<ExpenditureTypesId> CREATOR = new Creator<ExpenditureTypesId>() {
        @Override
        public ExpenditureTypesId createFromParcel(Parcel in) {
            return new ExpenditureTypesId(in);
        }

        @Override
        public ExpenditureTypesId[] newArray(int size) {
            return new ExpenditureTypesId[size];
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

    public boolean isToCurrentAssets() {
        return toCurrentAssets;
    }

    public void setToCurrentAssets(boolean toCurrentAssets) {
        this.toCurrentAssets = toCurrentAssets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeByte((byte) (toCurrentAssets ? 1 : 0));
    }
}
