package com.eng.asu.adaptivelearning.domain;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.FancyModel.FancyUser;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface UserService {

    Completable createUser(String firstName,
                           String lastName,
                           String email,
                           String userName,
                           String password,
                           int gender,
                           String dateOfBirth);

    Observable<String> loginWithEmail(String email, String password);

    Observable<String> loginWithUserName(String userName, String password);

    Observable<FancyUser> getUserProfile();

    Completable enrollInCourse(long courseId);

    Observable<List<FancyCourse>> getStudentCourses();

    Observable<List<FancyUser>> getChildren();

    Completable addChild(String firstName, String lastName, String email, String userName, String password, int gender, String dateOfBirth);

}
