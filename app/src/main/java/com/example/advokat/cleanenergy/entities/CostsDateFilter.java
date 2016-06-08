package com.example.advokat.cleanenergy.entities;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

public class CostsDateFilter {

    @PrimaryKey
    private long id;
    private RealmList<RealmInteger> year; /*2015 - 2016*/
    private RealmList<RealmInteger> month; /*11 - 8*/
    private String dateStart;
    private String dateStop;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RealmList<RealmInteger> getYear() {
        return year;
    }

    public void setYear(RealmList<RealmInteger> year) {
        this.year = year;
    }

    public RealmList<RealmInteger> getMonth() {
        return month;
    }

    public void setMonth(RealmList<RealmInteger> month) {
        this.month = month;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateStop() {
        return dateStop;
    }

    public void setDateStop(String dateStop) {
        this.dateStop = dateStop;
    }
}
