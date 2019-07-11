package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.CompletableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;

import javax.inject.Inject;

import io.reactivex.Completable;

public class JoinChildToCourse extends CompletableUseCase<Void> {
    private String firstName;
    private String courseID;
    private UserService userService;

    @Inject
    JoinChildToCourse(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread, UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    public Completable execute(String firstName, String courseID) {
        this.firstName = firstName;
        this.courseID = courseID;
        return super.execute();
    }

    @Override
    protected Completable interact(Void param) {
        return userService.joinChildToCourse(firstName, courseID);
    }
}
