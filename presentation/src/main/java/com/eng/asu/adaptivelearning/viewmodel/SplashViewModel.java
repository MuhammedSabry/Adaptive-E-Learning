package com.eng.asu.adaptivelearning.viewmodel;

import android.text.TextUtils;

import com.eng.asu.adaptivelearning.preferences.UserAccountStorage;
import com.eng.asu.adaptivelearning.view.activity.LoginActivity;
import com.eng.asu.adaptivelearning.view.activity.MainActivity;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

public class SplashViewModel extends ViewModel {

    private UserAccountStorage userAccountStorage;

    @Inject
    SplashViewModel(UserAccountStorage userAccountStorage) {
        super();
        this.userAccountStorage = userAccountStorage;
    }

    private boolean isUserLoggedIn() {
        return !TextUtils.isEmpty(userAccountStorage.getUser().getToken());
    }

    public Class<? extends AppCompatActivity> getActivityToOpen() {
        if (isUserLoggedIn())
            return MainActivity.class;
        else
            return LoginActivity.class;
    }
}
