package com.eng.asu.adaptivelearning.viewmodel;

import com.eng.asu.adaptivelearning.domain.interactor.EnrollInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.HotCoursesInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.NewCoursesInteractor;
import com.eng.asu.adaptivelearning.domain.model.Course;
import com.eng.asu.adaptivelearning.model.BaseListener;
import com.eng.asu.adaptivelearning.preferences.UserAccountStorage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class HomeViewModel extends ViewModel {
    private final HotCoursesInteractor hotCoursesInteractor;
    private final NewCoursesInteractor newCoursesInteractor;
    private final UserAccountStorage userAccountStorage;
    private final EnrollInteractor enrollInteractor;
    private CompositeDisposable disposables;

    @Inject
    HomeViewModel(HotCoursesInteractor hotCoursesInteractor, NewCoursesInteractor newCoursesInteractor, UserAccountStorage userAccountStorage, EnrollInteractor enrollInteractor) {
        super();
        this.hotCoursesInteractor = hotCoursesInteractor;
        this.newCoursesInteractor = newCoursesInteractor;
        this.userAccountStorage = userAccountStorage;
        this.enrollInteractor = enrollInteractor;
        this.disposables = new CompositeDisposable();
    }

    public LiveData<List<Course>> getNewCourses() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(5, TimeUnit.SECONDS)
                        .flatMap(aLong -> newCoursesInteractor.execute(userAccountStorage.getAuthToken())));
    }

    public LiveData<List<Course>> getHotCourses() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(5, TimeUnit.SECONDS)
                        .flatMap(aLong -> hotCoursesInteractor.execute(userAccountStorage.getAuthToken())));
    }

    public void enrollInCourse(int courseId, BaseListener listener) {
        disposables.add(enrollInteractor.execute(userAccountStorage.getAuthToken(), courseId)
                .subscribe(() -> listener.onSuccess("Successfully enrolled"),
                        throwable -> listener.onFail(throwable.getMessage())));
    }
}
