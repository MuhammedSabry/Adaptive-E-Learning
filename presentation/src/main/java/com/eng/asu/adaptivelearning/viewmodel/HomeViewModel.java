package com.eng.asu.adaptivelearning.viewmodel;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.eng.asu.adaptivelearning.domain.interactor.EnrollInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.HotCoursesInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.JoinClassroomInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.NewCoursesInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.SaveCourseInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.SearchedCoursesInteractor;
import com.eng.asu.adaptivelearning.model.BaseListener;
import com.eng.asu.adaptivelearning.preferences.UserAccountStorage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class HomeViewModel extends ViewModel {
    private final HotCoursesInteractor hotCoursesInteractor;
    private final NewCoursesInteractor newCoursesInteractor;
    private final SearchedCoursesInteractor searchedCoursesInteractor;
    private final EnrollInteractor enrollInteractor;
    private final SaveCourseInteractor saveCourseInteractor;
    private final JoinClassroomInteractor joinClassroomInteractor;
    private CompositeDisposable disposables;

    @Inject
    HomeViewModel(HotCoursesInteractor hotCoursesInteractor, NewCoursesInteractor newCoursesInteractor, SearchedCoursesInteractor searchedCoursesInteractor,EnrollInteractor enrollInteractor,
                  SaveCourseInteractor saveCourseInteractor, JoinClassroomInteractor joinClassroomInteractor) {
        super();
        this.hotCoursesInteractor = hotCoursesInteractor;
        this.newCoursesInteractor = newCoursesInteractor;
        this.searchedCoursesInteractor = searchedCoursesInteractor;
        this.enrollInteractor = enrollInteractor;
        this.disposables = new CompositeDisposable();
        this.saveCourseInteractor = saveCourseInteractor;
        this.joinClassroomInteractor = joinClassroomInteractor;
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

    public LiveData<List<FancyCourse>> getCoursesByCategory(String category) {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(5, TimeUnit.SECONDS)
                        .flatMap(aLong -> searchedCoursesInteractor.execute(category)));
    }

    public void enrollInCourse(long courseId, BaseListener listener) {
        disposables.add(enrollInteractor.execute(courseId)
                .subscribe(() -> listener.onSuccess("Successfully enrolled"),
                        throwable -> listener.onFail(throwable.getMessage())));
    }

    public void saveCourse(long courseId, BaseListener listener){
        disposables.add(saveCourseInteractor.execute(courseId)
                .subscribe(() -> listener.onSuccess("Successfully saved"),
                        throwable -> listener.onFail(throwable.getMessage())));
    }

    public void joinClassroom(String passcode, BaseListener listener){
        disposables.add(joinClassroomInteractor.execute(passcode)
                .subscribe(() -> listener.onSuccess("Successfully joined"),
                        throwable -> listener.onFail(throwable.getMessage())));
    }


}
