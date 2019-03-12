package com.eng.asu.adaptivelearning.domain.interactor;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.FlowableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class GetSavedCourses extends FlowableUseCase<List<FancyCourse>, Void> {

    private UserService userService;

    @Inject
    GetSavedCourses(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread,
                    UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    @Override
    protected Flowable<List<FancyCourse>> interact(Void param) {
        return userService.getSavedCourses().toFlowable(BackpressureStrategy.BUFFER);
    }
}
