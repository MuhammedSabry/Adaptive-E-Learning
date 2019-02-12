package com.eng.asu.adaptivelearning.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.eng.asu.adaptivelearning.domain.model.User;
import com.eng.asu.adaptivelearning.preferences.base.ObjectPreference;

import javax.inject.Inject;

public class UserAccountStorage {

    private final ObjectPreference<User> userPreference;

    @Inject
    UserAccountStorage(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        userPreference = new ObjectPreference<>(sharedPreferences, "pref_user", new User(), User.class);
    }

    private User getUser() {
        return userPreference.get();
    }

    private void setUser(User user) {
        userPreference.set(user);
    }

    public void removeUser() {
        userPreference.delete();
    }

    public String getAuthToken() {
        final User user = getUser();
        return user.getToken();
    }

    public void setAuthToken(String token) {
        User user = getUser();
        user.setToken(token);
        setUser(user);
    }

}
