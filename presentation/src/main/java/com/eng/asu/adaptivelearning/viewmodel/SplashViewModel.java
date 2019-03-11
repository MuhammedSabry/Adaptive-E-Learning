package com.eng.asu.adaptivelearning.viewmodel;

import com.adaptivelearning.server.FancyModel.FancyUser;
import com.eng.asu.adaptivelearning.domain.interactor.UserProfileInteractor;
import com.eng.asu.adaptivelearning.preferences.UserAccountStorage;
import com.eng.asu.adaptivelearning.view.activity.LoginActivity;
import com.eng.asu.adaptivelearning.view.activity.MainActivity;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

public class SplashViewModel extends ViewModel {

    private final UserAccountStorage userAccountStorage;
    private final UserProfileInteractor userProfileInteractor;

    @Inject
    SplashViewModel(UserAccountStorage userAccountStorage, UserProfileInteractor userProfileInteractor) {
        super();
        this.userAccountStorage = userAccountStorage;
        this.userProfileInteractor = userProfileInteractor;
    }

    public LiveData<Class<? extends AppCompatActivity>> getActivityToOpen() {
        FancyUser defaultErrorValue = new FancyUser();
        return LiveDataReactiveStreams.fromPublisher(userProfileInteractor.execute()
                .onErrorReturnItem(defaultErrorValue)
                .map(user -> {
                    if (defaultErrorValue == user) {
                        userAccountStorage.removeUser();
                        userAccountStorage.removeToken();
                        return LoginActivity.class;
                    }
                    return MainActivity.class;
                })
                .toFlowable());
    }
}
