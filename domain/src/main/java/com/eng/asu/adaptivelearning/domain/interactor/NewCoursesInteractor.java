package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.FlowableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.model.Course;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class NewCoursesInteractor extends FlowableUseCase<List<Course>, String> {

    private CourseService courseService;

    @Inject
    NewCoursesInteractor(BackgroundExecutionThread backgroundExecutionThread,
                         PostExecutionThread postExecutionThread,
                         CourseService courseService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.courseService = courseService;
    }

    @Override
    protected Flowable<List<Course>> interact(String token) {
        return courseService.getNewCourses().toFlowable(BackpressureStrategy.BUFFER);
    }
}