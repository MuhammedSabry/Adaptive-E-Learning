package com.eng.asu.adaptivelearning.domain;

import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.adaptivelearning.server.FancyModel.FancyStudentQuiz;
import com.eng.asu.adaptivelearning.domain.model.Course;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface CourseService {
    Observable<List<Course>> getHotCourses();

    Observable<List<Course>> getNewCourses();

    Observable<List<Course>> getCoursesByCategory(String category);

    Observable<Course> getCourse(Long courseId);

    Observable<FancyMediaFile> getMediaFile(Long mediaContentId);

    Observable<FancyQuiz> getQuiz(Long quizId);

    Single<FancyQuiz> startQuiz(Long quizID);

    Completable submitQuizAnswers(Long quizId, List<StudentAnswer> answers);

    Single<FancyStudentQuiz> getSubmittedQuiz(Long quizId);
}