package com.eng.asu.adaptivelearning.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.adaptivelearning.server.FancyModel.FancyClassroom;
import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.eng.asu.adaptivelearning.domain.interactor.GetCourseById;
import com.eng.asu.adaptivelearning.domain.interactor.GetEnrolledCourses;
import com.eng.asu.adaptivelearning.domain.interactor.GetStudentClassrooms;
import com.eng.asu.adaptivelearning.domain.interactor.JoinClassroomInteractor;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class MyCoursesViewModel extends ViewModel {

    private final GetEnrolledCourses getEnrolledCourses;
    private final GetStudentClassrooms getStudentClassrooms;
    private final GetCourseById getCourseById;

    @Inject
    MyCoursesViewModel(GetEnrolledCourses getEnrolledCourses,
                       GetStudentClassrooms getStudentClassrooms,
                       GetCourseById getCourseById,
                       JoinClassroomInteractor joinClassroomInteractor) {
        super();
        this.getEnrolledCourses = getEnrolledCourses;
        this.getStudentClassrooms = getStudentClassrooms;
        this.getCourseById = getCourseById;
    }

    public LiveData<List<FancyCourse>> getUserCourses() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(10, TimeUnit.SECONDS)
                        .flatMap(aLong -> getEnrolledCourses.execute())
                        .onErrorReturnItem(Collections.emptyList()));
    }

    public LiveData<List<FancyClassroom>> getStudentClassrooms() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(10, TimeUnit.SECONDS)
                        .flatMap(aLong -> getStudentClassrooms.execute())
                        .onErrorReturnItem(Collections.emptyList())
                        .onErrorReturnItem(Collections.emptyList()));
    }

    public LiveData<FancyCourse> getCourse(long courseId) {
        return LiveDataReactiveStreams.fromPublisher(getCourseById.execute(courseId)
                .onErrorReturnItem(new FancyCourse()));
    }

}
