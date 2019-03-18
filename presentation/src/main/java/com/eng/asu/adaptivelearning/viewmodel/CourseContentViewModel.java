package com.eng.asu.adaptivelearning.viewmodel;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.eng.asu.adaptivelearning.domain.interactor.GetCourseById;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

public class CourseContentViewModel extends ViewModel {

    private final GetCourseById getCourseByIdInteractor;

    @Inject
    CourseContentViewModel(GetCourseById getCourseByIdInteractor) {
        this.getCourseByIdInteractor = getCourseByIdInteractor;
    }

    public LiveData<FancyCourse> getCourseContent(long courseId) {
        return LiveDataReactiveStreams.fromPublisher(getCourseByIdInteractor.execute(courseId)
                .onErrorReturnItem(new FancyCourse()));
    }
}
