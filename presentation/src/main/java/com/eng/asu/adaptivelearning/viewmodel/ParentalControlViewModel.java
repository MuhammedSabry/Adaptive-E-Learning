package com.eng.asu.adaptivelearning.viewmodel;

import com.eng.asu.adaptivelearning.domain.interactor.GetChildrenInteractor;
import com.eng.asu.adaptivelearning.domain.model.User;
import com.eng.asu.adaptivelearning.preferences.UserAccountStorage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;

public class ParentalControlViewModel extends ViewModel {

    private final GetChildrenInteractor childrenInteractor;
    private final UserAccountStorage userAccountStorage;

    @Inject
    ParentalControlViewModel(GetChildrenInteractor childrenInteractor, UserAccountStorage userAccountStorage) {
        super();
        this.childrenInteractor = childrenInteractor;
        this.userAccountStorage = userAccountStorage;
    }

    public LiveData<List<User>> getChildren() {
        return LiveDataReactiveStreams.fromPublisher(Flowable.interval(0, 10, TimeUnit.SECONDS)
                .flatMapSingle(aLong -> childrenInteractor.execute(userAccountStorage.getAuthToken())));
    }
}
