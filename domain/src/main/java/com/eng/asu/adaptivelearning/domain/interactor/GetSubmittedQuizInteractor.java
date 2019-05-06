package com.eng.asu.adaptivelearning.domain.interactor;

import com.adaptivelearning.server.FancyModel.FancyStudentQuiz;
import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetSubmittedQuizInteractor extends SingleUseCase<FancyStudentQuiz, Long> {
    private final CourseService courseService;

    @Inject
    GetSubmittedQuizInteractor(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread, CourseService courseService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.courseService = courseService;
    }

    @Override
    protected Single<FancyStudentQuiz> interact(Long quizId) {
        return courseService.getSubmittedQuiz(quizId);
    }
}
