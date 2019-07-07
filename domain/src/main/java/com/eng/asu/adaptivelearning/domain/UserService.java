package com.eng.asu.adaptivelearning.domain;

import com.adaptivelearning.server.FancyModel.FancyUser;
import com.eng.asu.adaptivelearning.domain.model.Classroom;
import com.eng.asu.adaptivelearning.domain.model.Course;

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

    Completable saveCourse(long courseId);

    Observable<List<Course>> getSavedCourses();

    Observable<List<Course>> getStudentCourses();

    Observable<List<FancyUser>> getChildren();

    Observable<List<Course>> getTeacherCourses();

    Completable addChild(String firstName, String lastName, String email, String userName, String password, int gender, String dateOfBirth);

    Observable<List<Classroom>> getTeacherClassrooms();

    Observable<List<Classroom>> getStudentClassrooms();

    Completable joinClassroom(String passcode);
}
