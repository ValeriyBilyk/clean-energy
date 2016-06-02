package com.example.advokat.cleanenergy.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String KEY_ACCESS_TOKEN = "prefs_key_access_token";
    private static final String KEY_USER_ID = "prefs_key_user_id";
    private static final String STAY_IN_SYSTEM = "prefs_key_stay_in_system";

    private static SharedPreferences sPrefs;

    private PreferenceManager() {
    }

    public static void init(Context context) {
        if (sPrefs == null) {
            sPrefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        }
    }

    public static void setAccessToken(String value) {
        updateValue(KEY_ACCESS_TOKEN, value);
    }

    public static String getAccessToken() {
        return sPrefs.getString(KEY_ACCESS_TOKEN, null);
    }

    public static void setUserId(String value) {
        updateValue(KEY_USER_ID, value);
    }

    public static String getUserId() {
        return sPrefs.getString(KEY_USER_ID, null);
    }

    public static void setStayInSystem(boolean value) {
        updateValue(STAY_IN_SYSTEM, value);
    }

    public static boolean getStayInSystem() {
        return sPrefs.getBoolean(STAY_IN_SYSTEM, false);
    }

    private static void updateValue(String key, boolean value) {
        sPrefs.edit()
                .putBoolean(key, value)
                .apply();
    }

    private static void updateValue(String key, String value) {
        sPrefs.edit()
                .putString(key, value)
                .apply();
    }
}
