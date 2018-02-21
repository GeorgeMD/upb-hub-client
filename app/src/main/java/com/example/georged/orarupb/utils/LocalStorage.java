package com.example.georged.orarupb.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import com.example.georged.orarupb.R;

/**
 * Created by George D on 23-Jun-17.
 */

public class LocalStorage {
    private SharedPreferences sharedPreferences;

    public LocalStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.sharedPreferencesKey), Context.MODE_PRIVATE);
    }

    public boolean hasKey(String key) { return sharedPreferences.contains(key); }

    public <T> T get(String key, Class<T> type) {
        String value = sharedPreferences.getString(key, "");
        return deserialize(value, type);
    }

    public <T> void set(String key, T value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, serialize(value));
        editor.commit();
    }

    public void remove(String key) {
        if (hasKey(key)) {
            sharedPreferences.edit().remove(key).commit();
        }
    }

    public <T> String serialize(T value) {return new Gson().toJson(value); }
    private <T> T deserialize(String value, Class<T> type) {
        return new Gson().fromJson(value, type);
    }
}
