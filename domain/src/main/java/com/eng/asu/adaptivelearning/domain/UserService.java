package com.eng.asu.adaptivelearning.domain;

import com.eng.asu.adaptivelearning.domain.model.Course;
import com.eng.asu.adaptivelearning.domain.model.User;

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

    Observable<User> getUserProfile(String token);

    Completable enrollInCourse(String token, int courseId);

    Observable<List<Course>> getStudentCourses(String token);

    Observable<List<User>> getChildren(String token);

    Completable addChild(String token, String firstName, String lastName, String email, String userName, String password, int gender, String dateOfBirth);

}
