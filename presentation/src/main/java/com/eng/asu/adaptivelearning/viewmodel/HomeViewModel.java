package com.eng.asu.adaptivelearning.viewmodel;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.eng.asu.adaptivelearning.domain.interactor.EnrollInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.HotCoursesInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.NewCoursesInteractor;
import com.eng.asu.adaptivelearning.model.BaseListener;

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
    private final EnrollInteractor enrollInteractor;
    private CompositeDisposable disposables;

    @Inject
    HomeViewModel(HotCoursesInteractor hotCoursesInteractor, NewCoursesInteractor newCoursesInteractor, EnrollInteractor enrollInteractor) {
        super();
        this.hotCoursesInteractor = hotCoursesInteractor;
        this.newCoursesInteractor = newCoursesInteractor;
        this.enrollInteractor = enrollInteractor;
        this.disposables = new CompositeDisposable();
    }

    public LiveData<List<FancyCourse>> getNewCourses() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(5, TimeUnit.SECONDS)
                        .flatMap(aLong -> newCoursesInteractor.execute()));
    }

    public LiveData<List<FancyCourse>> getHotCourses() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(5, TimeUnit.SECONDS)
                        .flatMap(aLong -> hotCoursesInteractor.execute()));
    }

    public void enrollInCourse(long courseId, BaseListener listener) {
        disposables.add(enrollInteractor.execute(courseId)
                .subscribe(() -> listener.onSuccess("Successfully enrolled"),
                        throwable -> listener.onFail(throwable.getMessage())));
    }
}
