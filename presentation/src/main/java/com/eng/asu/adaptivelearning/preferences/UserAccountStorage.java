package com.eng.asu.adaptivelearning.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.adaptivelearning.server.FancyModel.FancyUser;
import com.eng.asu.adaptivelearning.domain.UserStorage;
import com.eng.asu.adaptivelearning.preferences.base.ObjectPreference;
import com.eng.asu.adaptivelearning.preferences.base.StringPreference;

import javax.inject.Inject;

public class UserAccountStorage implements UserStorage {

    private static final String PREF_TOKEN_KEY = "pref_token";
    private final ObjectPreference<FancyUser> userPreference;
    private final StringPreference tokenPreference;
    private OnTokenChangeListener tokenListener;

    @Inject
    UserAccountStorage(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        userPreference = new ObjectPreference<>(sharedPreferences, "pref_user", new FancyUser(), FancyUser.class);
        tokenPreference = new StringPreference(sharedPreferences, PREF_TOKEN_KEY);

        sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPreferences1, key) -> {
            if (key.equals(PREF_TOKEN_KEY) && tokenListener != null)
                tokenListener.onTokenChanged(getAuthToken());
        });
    }

    @Override
    public FancyUser getUser() {
        return userPreference.get();
    }

    @Override
    public void setUser(FancyUser user) {
        userPreference.set(user);
    }

    @Override
    public void removeUser() {
        userPreference.delete();
    }

    @Override
    public String getAuthToken() {
        return tokenPreference.get();
    }

    @Override
    public void setAuthToken(String token) {
        tokenPreference.set(token);
    }

    @Override
    public void removeToken() {
        tokenPreference.delete();
    }

    @Override
    public void setOnTokenChangeListener(OnTokenChangeListener listener) {
        this.tokenListener = listener;
    }

}
