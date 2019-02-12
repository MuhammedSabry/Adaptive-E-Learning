package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.SingleUseCase;
import com.eng.asu.adaptivelearning.domain.model.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetChildrenInteractor extends SingleUseCase<List<User>, String> {

    private UserService userService;

    @Inject
    GetChildrenInteractor(BackgroundExecutionThread backgroundExecutionThread,
                          PostExecutionThread postExecutionThread,
                          UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    @Override
    protected Single<List<User>> interact(String token) {
        return Single.fromObservable(userService.getChildren(token));
    }
}
