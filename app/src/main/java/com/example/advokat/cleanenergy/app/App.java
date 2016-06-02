package com.example.advokat.cleanenergy.app;

import android.app.Application;
import android.content.Context;

import com.example.advokat.cleanenergy.utils.PreferenceManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    private static Context context;
//    private static User user;
    /*private static CurrentAsset currentAsset;
    private static Cost cost;
    private static Incomes incomes;
    private static IncomeCategory incomeCategory;*/

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
        PreferenceManager.init(this);
    }

    public static Context getAppContext() {
        return context;
    }

   /* public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        App.user = user;
    }*/

    /*public static CurrentAsset getCurrentAsset() {
        return currentAsset;
    }*/

    /*public static void setCurrentAsset(CurrentAsset currentAsset) {
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

    public static IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    public static void setIncomeCategory(IncomeCategory incomeCategory) {
        App.incomeCategory = incomeCategory;
    }*/
}
