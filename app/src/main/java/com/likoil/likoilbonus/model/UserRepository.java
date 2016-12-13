package com.likoil.likoilbonus.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jambi on 11.12.2016.
 */

public class UserRepository {

    public static final String APP_PREFERENCES = "appsettings";
    public static final String APP_PREFERENCES_API_TOKEN = "api_token";
    private static SharedPreferences mSettings;

    private static Context context;

    public static void setContext(Context context) {
        UserRepository.context = context;
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public boolean isAuth() {
        String token = mSettings.getString(APP_PREFERENCES_API_TOKEN, null);
//        return false;
        return token != null;
    }

    @SuppressLint("CommitPrefEdits")
    public void saveAuth(String token) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_API_TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        return mSettings.getString(APP_PREFERENCES_API_TOKEN, null);
    }

    public void clearAuth() {
        saveAuth(null);
    }

}
