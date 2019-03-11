package com.eng.asu.adaptivelearning.viewmodel;

import com.adaptivelearning.server.FancyModel.FancyUser;
import com.eng.asu.adaptivelearning.domain.interactor.GetChildrenInteractor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;

public class ParentalControlViewModel extends ViewModel {

    private final GetChildrenInteractor childrenInteractor;

    @Inject
    ParentalControlViewModel(GetChildrenInteractor childrenInteractor) {
        super();
        this.childrenInteractor = childrenInteractor;
    }

    public LiveData<List<FancyUser>> getChildren() {
        return LiveDataReactiveStreams.fromPublisher(Flowable.interval(0, 10, TimeUnit.SECONDS)
                .flatMapSingle(aLong -> childrenInteractor.execute()));
    }
}
