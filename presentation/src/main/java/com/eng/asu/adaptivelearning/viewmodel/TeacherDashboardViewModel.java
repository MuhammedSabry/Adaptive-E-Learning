package com.eng.asu.adaptivelearning.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.eng.asu.adaptivelearning.domain.interactor.GetTeacherClassrooms;
import com.eng.asu.adaptivelearning.domain.interactor.GetTeacherCourses;
import com.eng.asu.adaptivelearning.domain.model.Classroom;
import com.eng.asu.adaptivelearning.domain.model.Course;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class TeacherDashboardViewModel extends ViewModel {

    private final GetTeacherCourses getTeacherCourses;
    private final GetTeacherClassrooms getTeacherClassrooms;

    @Inject
    TeacherDashboardViewModel(GetTeacherCourses getTeacherCourses,
                              GetTeacherClassrooms getTeacherClassrooms) {
        super();
        this.getTeacherCourses = getTeacherCourses;
        this.getTeacherClassrooms = getTeacherClassrooms;
    }

    public LiveData<List<Course>> getTeacherCourses() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(10, TimeUnit.SECONDS)
                        .flatMap(aLong -> getTeacherCourses.execute())
                        .onErrorReturnItem(Collections.emptyList()));
    }

    public LiveData<List<Classroom>> getTeacherClassrooms() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(10, TimeUnit.SECONDS)
                        .flatMap(aLong -> getTeacherClassrooms.execute())
                        .distinctUntilChanged()
                        .onErrorReturnItem(Collections.emptyList()));
    }
}
