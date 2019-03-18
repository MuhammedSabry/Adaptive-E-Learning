package com.eng.asu.adaptivelearning.viewmodel;

import com.adaptivelearning.server.FancyModel.FancyUser;
import com.eng.asu.adaptivelearning.domain.interactor.UserProfileInteractor;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final UserProfileInteractor userProfileInteractor;

    @Inject
    MainViewModel(UserProfileInteractor userProfileInteractor) {
        super();
        this.userProfileInteractor = userProfileInteractor;
    }

    public LiveData<FancyUser> getUserData() {
        return LiveDataReactiveStreams.fromPublisher(userProfileInteractor.execute()
                .toFlowable()
                .onErrorReturnItem(new FancyUser()));
    }
}
