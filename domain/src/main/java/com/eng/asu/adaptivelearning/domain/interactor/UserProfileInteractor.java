package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.SingleUseCase;
import com.eng.asu.adaptivelearning.domain.model.User;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserProfileInteractor extends SingleUseCase<User, String> {

    private UserService userService;

    @Inject
    UserProfileInteractor(BackgroundExecutionThread backgroundExecutionThread,
                          PostExecutionThread postExecutionThread,
                          UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    @Override
    protected Single<User> interact(String token) {
        return Single.fromObservable(userService.getUserProfile(token));
    }
}
