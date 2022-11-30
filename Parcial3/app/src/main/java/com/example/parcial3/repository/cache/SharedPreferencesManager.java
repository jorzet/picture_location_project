package com.example.parcial3.repository.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.parcial3.model.User;
import com.google.gson.Gson;

public class SharedPreferencesManager {

    private Context mContext;

    private final String SHARED_PREFERENCES_NAME = "local_shared_preferences";
    private final String JSON_USER = "json_user";

    public SharedPreferencesManager(Context context) {
        this.mContext = context;
    }

    public void saveSession(User user) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        String json = new Gson().toJson(user);
        editor.putString(JSON_USER, json);
        editor.apply();
    }

    public User getSession() {
        SharedPreferences prefs = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(JSON_USER, null);
        return new Gson().fromJson(json, User.class);
    }

    public void removeUserFromCache() {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(JSON_USER);
        editor.apply();
    }
}
