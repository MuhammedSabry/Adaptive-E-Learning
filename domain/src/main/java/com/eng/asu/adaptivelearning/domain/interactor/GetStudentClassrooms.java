package com.eng.asu.adaptivelearning.domain.interactor;

import com.adaptivelearning.server.Model.Classroom;
import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.FlowableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class GetStudentClassrooms extends FlowableUseCase<List<Classroom>, Void> {

    private UserService userService;

    @Inject
    GetStudentClassrooms(BackgroundExecutionThread backgroundExecutionThread,
                         PostExecutionThread postExecutionThread,
                         UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    @Override
    protected Flowable<List<Classroom>> interact(Void param) {
        return userService.getStudentClassrooms().toFlowable(BackpressureStrategy.BUFFER);
    }
}

