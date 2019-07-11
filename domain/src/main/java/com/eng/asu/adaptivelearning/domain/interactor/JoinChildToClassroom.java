package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.CompletableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class JoinChildToClassroom extends CompletableUseCase<Void> {

    private String firstName;
    private String passcode;
    private UserService userService;

    @Inject
    JoinChildToClassroom(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread, UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    public Completable execute(String firstName, String passcode) {
        this.firstName = firstName;
        this.passcode = passcode;
        return super.execute();
    }

    @Override
    protected Completable interact(Void param) {
        return userService.joinChildToClassroom(firstName, passcode);
    }
}
