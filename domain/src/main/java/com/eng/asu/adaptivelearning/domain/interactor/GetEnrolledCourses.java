package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.FlowableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.model.Course;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class GetEnrolledCourses extends FlowableUseCase<List<Course>, String> {

    private UserService userService;

    @Inject
    GetEnrolledCourses(BackgroundExecutionThread backgroundExecutionThread,
                       PostExecutionThread postExecutionThread,
                       UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    @Override
    protected Flowable<List<Course>> interact(String token) {
        return userService.getStudentCourses(token).toFlowable(BackpressureStrategy.BUFFER);
    }
}
