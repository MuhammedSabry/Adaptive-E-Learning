package com.eng.asu.adaptivelearning.domain;

import com.eng.asu.adaptivelearning.domain.model.User;

import io.reactivex.Observable;

public interface UserService {

    Observable<Boolean> createUser(String firstName,
                                   String lastName,
                                   String email,
                                   String userName,
                                   String password,
                                   int gender,
                                   String dateOfBirth);

    Observable<User> loginWithEmail(String email, String password);

    Observable<User> loginWithUserName(String userName, String password);

}
