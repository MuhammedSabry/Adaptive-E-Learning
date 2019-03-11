package com.eng.asu.adaptivelearning.viewmodel;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.eng.asu.adaptivelearning.domain.interactor.GetEnrolledCourses;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;

public class MyCoursesViewModel extends ViewModel {

    private final GetEnrolledCourses getEnrolledCourses;

    @Inject
    MyCoursesViewModel(GetEnrolledCourses getEnrolledCourses) {
        super();
        this.getEnrolledCourses = getEnrolledCourses;
    }

    public LiveData<List<FancyCourse>> getUserCourses() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(10, TimeUnit.SECONDS)
                        .flatMap(aLong -> getEnrolledCourses.execute()));
    }

}
