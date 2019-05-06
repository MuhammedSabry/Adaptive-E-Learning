package com.eng.asu.adaptivelearning.domain;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.adaptivelearning.server.FancyModel.FancyStudentQuiz;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface CourseService {
    Observable<List<FancyCourse>> getHotCourses();

    Observable<List<FancyCourse>> getNewCourses();

    Observable<List<FancyCourse>> getCoursesByCategory(String category);

    Observable<FancyCourse> getCourse(Long courseId);

    Observable<FancyMediaFile> getMediaFile(Long mediaContentId);

    Observable<FancyQuiz> getQuiz(Long quizId);

    Completable startQuiz(Long quizID);

    Completable submitQuizAnswers(Long quizId, List<StudentAnswer> answers);

    Single<FancyStudentQuiz> getSubmittedQuiz(Long quizId);
}