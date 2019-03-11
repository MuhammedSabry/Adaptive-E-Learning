package com.eng.asu.adaptivelearning.domain.interactor;

import com.adaptivelearning.server.FancyModel.FancyUser;
import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserProfileInteractor extends SingleUseCase<FancyUser, Void> {

    private UserService userService;

    @Inject
    UserProfileInteractor(BackgroundExecutionThread backgroundExecutionThread,
                          PostExecutionThread postExecutionThread,
                          UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    @Override
    protected Single<FancyUser> interact(Void param) {
        return Single.fromObservable(userService.getUserProfile());
    }
}
