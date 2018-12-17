package com.eng.asu.adaptivelearning.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.eng.asu.adaptivelearning.model.User;
import com.eng.asu.adaptivelearning.preferences.base.ObjectPreference;

import javax.inject.Inject;

public class UserAccountStorage {

    private final ObjectPreference<User> userPreference;

    @Inject
    public UserAccountStorage(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.userPreference = new ObjectPreference<>(sharedPreferences, "pref_user", new User(), User.class);
    }

    public User getUser() {
        return userPreference.get();
    }

    public void setUser(User user) {
        userPreference.set(user);
    }

    public void removeUser() {
        userPreference.delete();
    }

    public String getAuthToken() {
        final User user = getUser();
        return user != null ? user.getToken() : null;
    }

    public void setAuthToken(String token) {
        final User user = getUser();
        user.setToken(token);
        setUser(user);
    }

}
