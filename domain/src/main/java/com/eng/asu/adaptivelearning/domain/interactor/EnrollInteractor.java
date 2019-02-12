package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.CompletableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;

import javax.inject.Inject;

import io.reactivex.Completable;

public class EnrollInteractor extends CompletableUseCase<Void> {

    private final UserService userService;
    private String token;
    private int courseId;

    @Inject
    EnrollInteractor(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread, UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    public Completable execute(String token,
                               int courseId) {
        this.token = token;
        this.courseId = courseId;
        return super.execute();
    }

    @Override
    protected Completable interact(Void param) {
        return userService.enrollInCourse(token, courseId);
    }
}
