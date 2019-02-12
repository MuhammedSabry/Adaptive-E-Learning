package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Single;

public class LoginInteractor extends SingleUseCase<String, Void> {

    private String userNameOrEmail;
    private String password;
    private UserService userService;

    @Inject
    LoginInteractor(BackgroundExecutionThread backgroundExecutionThread,
                    PostExecutionThread postExecutionThread,
                    UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    public Single<String> execute(String userNameOrEmail, String password) {
        this.userNameOrEmail = userNameOrEmail;
        this.password = password;
        return super.execute();
    }

    @Override
    protected Single<String> interact(Void param) {
        if (userNameOrEmail.contains("@"))
            return Single.fromObservable(userService.loginWithEmail(userNameOrEmail, password));
        else
            return Single.fromObservable(userService.loginWithUserName(userNameOrEmail, password));
    }

}
