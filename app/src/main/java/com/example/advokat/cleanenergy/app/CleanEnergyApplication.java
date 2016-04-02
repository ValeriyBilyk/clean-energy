package com.example.advokat.cleanenergy.app;

import android.app.Application;
import android.content.Context;

public class CleanEnergyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getAppContext() {
        return context;
    }
}
