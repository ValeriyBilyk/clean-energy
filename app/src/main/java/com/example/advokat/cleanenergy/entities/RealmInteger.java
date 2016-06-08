package com.example.advokat.cleanenergy.entities;

import io.realm.RealmObject;

public class RealmInteger extends RealmObject{

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
