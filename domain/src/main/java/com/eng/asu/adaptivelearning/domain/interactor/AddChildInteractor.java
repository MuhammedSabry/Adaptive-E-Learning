package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.CompletableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AddChildInteractor extends CompletableUseCase<Void> {
    private final UserService userService;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private int gender;
    private String dateOfBirth;

    @Inject
    AddChildInteractor(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread, UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    public Completable execute(String firstName,
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
    protected Completable interact(Void param) {
        return userService.addChild(firstName, lastName, email, userName, password, gender, dateOfBirth);
    }

}
