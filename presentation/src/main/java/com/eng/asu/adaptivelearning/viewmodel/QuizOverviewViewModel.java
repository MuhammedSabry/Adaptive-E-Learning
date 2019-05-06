package com.eng.asu.adaptivelearning.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.adaptivelearning.server.FancyModel.FancyStudentQuiz;
import com.eng.asu.adaptivelearning.domain.interactor.GetQuizInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.GetSubmittedQuizInteractor;

import javax.inject.Inject;

public class QuizOverviewViewModel extends BaseViewModel {

    private final GetQuizInteractor getQuizInteractor;
    private final GetSubmittedQuizInteractor getSubmittedQuizInteractor;

    @Inject
    QuizOverviewViewModel(GetQuizInteractor getQuizInteractor,
                          GetSubmittedQuizInteractor getSubmittedQuizInteractor) {
        super();
        this.getQuizInteractor = getQuizInteractor;
        this.getSubmittedQuizInteractor = getSubmittedQuizInteractor;
    }

    public LiveData<FancyQuiz> getQuiz(long quizId) {
        return LiveDataReactiveStreams.fromPublisher(getQuizInteractor.execute(quizId)
                .onErrorReturnItem(new FancyQuiz())
                .toFlowable());
    }

    public LiveData<FancyStudentQuiz> getSubmittedQuiz(long quizId) {
        return LiveDataReactiveStreams.fromPublisher(getSubmittedQuizInteractor.execute(quizId)
                .onErrorReturnItem(new FancyStudentQuiz())
                .toFlowable());
    }
}
