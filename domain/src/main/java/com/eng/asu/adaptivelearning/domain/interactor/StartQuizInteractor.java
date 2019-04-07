package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.CompletableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;

import javax.inject.Inject;

import io.reactivex.Completable;

public class StartQuizInteractor extends CompletableUseCase<Long> {
    private final CourseService courseService;

    @Inject
    StartQuizInteractor(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread, CourseService courseService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.courseService = courseService;
    }

    @Override
    protected Completable interact(Long quizId) {
        return courseService.startQuiz(quizId);
    }
}
