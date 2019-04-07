package com.eng.asu.adaptivelearning.domain.interactor;

import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetQuizInteractor extends SingleUseCase<FancyQuiz, Long> {

    private final CourseService courseService;

    @Inject
    GetQuizInteractor(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread, CourseService courseService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.courseService = courseService;
    }

    @Override
    protected Single<FancyQuiz> interact(Long quizId) {
        return courseService.getQuiz(quizId)
                .singleOrError();
    }
}
