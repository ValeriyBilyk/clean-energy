package com.example.advokat.cleanenergy.app;

import android.app.Application;
import android.content.Context;

import com.example.advokat.cleanenergy.entities.CurrentAsset;
import com.example.advokat.cleanenergy.entities.User;
import com.example.advokat.cleanenergy.entities.cost.Cost;
import com.example.advokat.cleanenergy.entities.income.Incomes;

public class App extends Application {

    private static Context context;
    private static User user;
    private static CurrentAsset currentAsset;
    private static Cost cost;
    private static Incomes incomes;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getAppContext() {
        return context;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        App.user = user;
    }

    public static CurrentAsset getCurrentAsset() {
        return currentAsset;
    }

    public static void setCurrentAsset(CurrentAsset currentAsset) {
        App.currentAsset = currentAsset;
    }

    public static Cost getCost() {
        return cost;
    }

    public static void setCost(Cost cost) {
        App.cost = cost;
    }

    public static Incomes getIncomes() {
        return incomes;
    }

    public static void setIncomes(Incomes incomes) {
        App.incomes = incomes;
    }
}
