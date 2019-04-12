package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.StudentAnswer;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.CompletableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

public class SubmitQuizInteractor extends CompletableUseCase<Void> {
    private final CourseService courseService;
    private List<StudentAnswer> questionsWithAnswers;
    private long quizId;

    @Inject
    SubmitQuizInteractor(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread, CourseService courseService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.courseService = courseService;
    }

    public Completable execute(long quizId, List<StudentAnswer> questionsWithAnswers) {
        this.questionsWithAnswers = questionsWithAnswers;
        this.quizId = quizId;
        return super.execute();
    }

    @Override
    protected Completable interact(Void param) {
        return courseService.submitQuizAnswers(quizId, questionsWithAnswers);
    }
}
