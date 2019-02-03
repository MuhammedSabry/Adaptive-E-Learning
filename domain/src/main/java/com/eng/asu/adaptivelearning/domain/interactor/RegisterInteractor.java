package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Single;

public class RegisterInteractor extends SingleUseCase<Boolean, Void> {

    private final UserService userService;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private int gender;
    private String dateOfBirth;

    @Inject
    RegisterInteractor(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread, UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    public Single<Boolean> execute(String firstName,
                                   String lastName,
                                   String email,
                                   String userName,
                                   String password,
                                   int gender,
                                   String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        return super.execute();
    }

    @Override
    protected Single<Boolean> interact(Void param) {
        return Single.fromObservable(userService.createUser(firstName, lastName, email, userName, password, gender, dateOfBirth));
    }

}
