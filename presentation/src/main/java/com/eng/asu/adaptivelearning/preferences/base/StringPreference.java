package com.eng.asu.adaptivelearning.preferences.base;

import android.content.SharedPreferences;

public class StringPreference extends BasePreference {

    public StringPreference(SharedPreferences preferences, String key) {
        super(preferences, key);
    }

    public String get() {
        return preferences.getString(key, null);
    }

    public void set(String value) {
        preferences.edit().putString(key, value).apply();
    }
}
