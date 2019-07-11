package com.eng.asu.adaptivelearning.viewmodel;

import com.adaptivelearning.server.FancyModel.FancyUser;
import com.eng.asu.adaptivelearning.domain.User;
import com.eng.asu.adaptivelearning.domain.interactor.UserProfileInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.UserProfileInteractor2;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import okhttp3.RequestBody;

public class MainViewModel extends ViewModel {

    private final UserProfileInteractor userProfileInteractor;
    private final UserProfileInteractor2 userProfileInteractor2;

    @Inject
    MainViewModel(UserProfileInteractor userProfileInteractor, UserProfileInteractor2 userProfileInteractor2) {
        super();
        this.userProfileInteractor = userProfileInteractor;
        this.userProfileInteractor2 = userProfileInteractor2;
    }

    public LiveData<FancyUser> getUserData() {
        return LiveDataReactiveStreams.fromPublisher(userProfileInteractor.execute()
                .toFlowable()
                .onErrorReturnItem(new FancyUser()));
    }

    public LiveData<User> getUserData2() {
        return LiveDataReactiveStreams.fromPublisher(userProfileInteractor2.execute()
                .toFlowable()
                .onErrorReturnItem(new User()));
    }
}
