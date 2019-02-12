package com.eng.asu.adaptivelearning.viewmodel;

import com.eng.asu.adaptivelearning.domain.interactor.UserProfileInteractor;
import com.eng.asu.adaptivelearning.domain.model.User;
import com.eng.asu.adaptivelearning.preferences.UserAccountStorage;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final UserAccountStorage userAccountStorage;
    private final UserProfileInteractor userProfileInteractor;

    @Inject
    MainViewModel(UserAccountStorage userAccountStorage, UserProfileInteractor userProfileInteractor) {
        super();
        this.userAccountStorage = userAccountStorage;
        this.userProfileInteractor = userProfileInteractor;
    }

    public LiveData<User> getUserData() {
        return LiveDataReactiveStreams.fromPublisher(userProfileInteractor.execute(userAccountStorage.getAuthToken())
                .toFlowable());
    }
}
