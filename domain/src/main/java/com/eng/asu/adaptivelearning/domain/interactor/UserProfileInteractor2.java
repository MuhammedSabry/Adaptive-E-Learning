package com.eng.asu.adaptivelearning.domain.interactor;

import com.adaptivelearning.server.FancyModel.FancyUser;
import com.eng.asu.adaptivelearning.domain.User;
import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserProfileInteractor2 extends SingleUseCase<User, Void> {

    private UserService userService;

    @Inject
    UserProfileInteractor2(BackgroundExecutionThread backgroundExecutionThread,
                          PostExecutionThread postExecutionThread,
                          UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    @Override
    protected Single<User> interact(Void param) {
        return Single.fromObservable(userService.getUserProfile2());
    }
}
