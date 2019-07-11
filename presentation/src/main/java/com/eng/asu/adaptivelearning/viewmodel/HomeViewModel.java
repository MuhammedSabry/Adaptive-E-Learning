package com.eng.asu.adaptivelearning.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.eng.asu.adaptivelearning.domain.interactor.EnrollInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.GetAllCourses;
import com.eng.asu.adaptivelearning.domain.interactor.HotCoursesInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.JoinChildToClassroom;
import com.eng.asu.adaptivelearning.domain.interactor.JoinChildToCourse;
import com.eng.asu.adaptivelearning.domain.interactor.JoinClassroomInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.NewCoursesInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.SaveCourseInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.SearchedCoursesInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.SendTeachingRequest;
import com.eng.asu.adaptivelearning.domain.model.Course;
import com.eng.asu.adaptivelearning.model.BaseListener;

import java.util.ArrayList;
import java.util.Collections;
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
    private final SearchedCoursesInteractor searchedCoursesInteractor;
    private final EnrollInteractor enrollInteractor;
    private final SaveCourseInteractor saveCourseInteractor;
    private final JoinClassroomInteractor joinClassroomInteractor;
    private final JoinChildToClassroom joinChildToClassroom;
    private final GetAllCourses getAllCourses;
    private final JoinChildToCourse joinChildToCourse;
    private final SendTeachingRequest sendTeachingRequest;
    private CompositeDisposable disposables;

    @Inject
    HomeViewModel(HotCoursesInteractor hotCoursesInteractor, NewCoursesInteractor newCoursesInteractor, SearchedCoursesInteractor searchedCoursesInteractor,EnrollInteractor enrollInteractor,
                  SaveCourseInteractor saveCourseInteractor, JoinClassroomInteractor joinClassroomInteractor, JoinChildToClassroom joinChildToClassroom, GetAllCourses getAllCourses,
                  JoinChildToCourse joinChildToCourse, SendTeachingRequest sendTeachingRequest) {
        super();
        this.hotCoursesInteractor = hotCoursesInteractor;
        this.newCoursesInteractor = newCoursesInteractor;
        this.searchedCoursesInteractor = searchedCoursesInteractor;
        this.enrollInteractor = enrollInteractor;
        this.disposables = new CompositeDisposable();
        this.saveCourseInteractor = saveCourseInteractor;
        this.joinClassroomInteractor = joinClassroomInteractor;
        this.joinChildToClassroom = joinChildToClassroom;
        this.getAllCourses = getAllCourses;
        this.joinChildToCourse = joinChildToCourse;
        this.sendTeachingRequest = sendTeachingRequest;
    }

    public LiveData<List<Course>> getNewCourses() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(5, TimeUnit.SECONDS)
                        .flatMap(aLong -> newCoursesInteractor.execute())
                        .onErrorReturnItem(Collections.emptyList())
                        .distinctUntilChanged());
    }

    public LiveData<List<Course>> getHotCourses() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(5, TimeUnit.SECONDS)
                        .flatMap(aLong -> hotCoursesInteractor.execute())
                        .onErrorReturnItem(Collections.emptyList())
                        .distinctUntilChanged());
    }

    public LiveData<List<Course>> getCoursesByCategory(String category) {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(5, TimeUnit.SECONDS)
                        .flatMap(aLong -> searchedCoursesInteractor.execute(category))
                        .onErrorReturnItem(Collections.emptyList())
                        .distinctUntilChanged());
    }

    public void enrollInCourse(long courseId, BaseListener listener) {
        disposables.add(enrollInteractor.execute(courseId)
                .subscribe(() -> listener.onSuccess("Successfully enrolled"),
                        throwable -> listener.onFail(throwable.getMessage())));
    }

    public void saveCourse(long courseId, BaseListener listener) {
        disposables.add(saveCourseInteractor.execute(courseId)
                .subscribe(() -> listener.onSuccess("Successfully saved"),
                        throwable -> listener.onFail(throwable.getMessage())));
    }

    public void joinClassroom(String passcode, BaseListener listener) {
        disposables.add(joinClassroomInteractor.execute(passcode)
                .subscribe(() -> listener.onSuccess("Successfully joined"),
                        throwable -> listener.onFail(throwable.getMessage())));
    }

    public void joinChildToClassroom(String firstName, String passcode, BaseListener listener){
        disposables.add(joinChildToClassroom.execute(firstName, passcode)
                        .subscribe(() -> {
                                    listener.onSuccess("Child added successfully");
                                }
                        , throwable -> {
                                    listener.onFail(throwable.getMessage());
                                }
                        )
        );
    }

    public void joinChildToCourse(String firstName, String courseID, BaseListener listener){
        disposables.add(joinChildToCourse.execute(firstName, courseID)
                .subscribe(() -> {
                            listener.onSuccess("Child enrolled successfully");
                        }
                        , throwable -> {
                            listener.onFail(throwable.getMessage());
                        }
                )
        );
    }

    public LiveData<List<Course>> getAllCourses(){
        return LiveDataReactiveStreams.fromPublisher(getAllCourses.execute()
                .toFlowable()
                .onErrorReturnItem(new ArrayList<Course>()));

    }

    public void sendTeachingRequest(BaseListener listener){
        disposables.add(sendTeachingRequest.execute()
                .subscribe(() -> {
                            listener.onSuccess("Request sent successfully");
                        }
                        , throwable -> {
                            listener.onFail(throwable.getMessage());
                        }
                )
        );
    }


}
