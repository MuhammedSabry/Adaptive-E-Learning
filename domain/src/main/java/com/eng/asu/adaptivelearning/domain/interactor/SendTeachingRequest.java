package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.CompletableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;

import javax.inject.Inject;

import io.reactivex.Completable;

public class SendTeachingRequest extends CompletableUseCase<Void> {

    private UserService userService;

    @Inject
    SendTeachingRequest(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread, UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    public Completable execute() {
        return super.execute();
    }

    @Override
    protected Completable interact(Void param) {
        return userService.sendTeachingRequest();
    }
}
