package com.eng.asu.adaptivelearning.domain.interactor;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.FlowableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class GetCourseById extends FlowableUseCase<FancyCourse, Integer>{
    CourseService courseService;

    @Inject
    GetCourseById(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread,
                    CourseService courseService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.courseService = courseService;
    }

    @Override
    protected Flowable<FancyCourse> interact(Integer param) {
        return courseService.getCourse(param).toFlowable(BackpressureStrategy.BUFFER);
    }

}
