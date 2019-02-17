package com.eng.asu.adaptivelearning.viewmodel;

import com.eng.asu.adaptivelearning.domain.interactor.GetEnrolledCourses;
import com.eng.asu.adaptivelearning.domain.model.Course;
import com.eng.asu.adaptivelearning.preferences.UserAccountStorage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;

public class MyCoursesViewModel extends ViewModel {

    private final UserAccountStorage userAccountStorage;
    private final GetEnrolledCourses getEnrolledCourses;

    @Inject
    MyCoursesViewModel(UserAccountStorage userAccountStorage, GetEnrolledCourses getEnrolledCourses) {
        super();
        this.userAccountStorage = userAccountStorage;
        this.getEnrolledCourses = getEnrolledCourses;
    }

    public LiveData<List<Course>> getUserCourses() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(10, TimeUnit.SECONDS)
                        .flatMap(aLong -> getEnrolledCourses.execute(userAccountStorage.getAuthToken())));
    }

}
