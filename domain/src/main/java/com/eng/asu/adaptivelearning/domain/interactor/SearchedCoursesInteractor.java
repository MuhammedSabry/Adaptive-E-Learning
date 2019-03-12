package com.eng.asu.adaptivelearning.domain.interactor;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.FlowableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class SearchedCoursesInteractor extends FlowableUseCase<List<FancyCourse>, String> {

    private CourseService courseService;

    @Inject
    SearchedCoursesInteractor(BackgroundExecutionThread backgroundExecutionThread,
                              PostExecutionThread postExecutionThread,
                              CourseService courseService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.courseService = courseService;
    }

    @Override
    protected Flowable<List<FancyCourse>> interact(String category) {
        return courseService.getCoursesByCategory(category).toFlowable(BackpressureStrategy.BUFFER);
    }
}
