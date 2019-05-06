package com.eng.asu.adaptivelearning.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.eng.asu.adaptivelearning.domain.interactor.GetQuizInteractor;

import javax.inject.Inject;

public class QuizOverviewViewModel extends BaseViewModel {

    private final GetQuizInteractor getQuizInteractor;

    @Inject
    QuizOverviewViewModel(GetQuizInteractor getQuizInteractor) {
        super();
        this.getQuizInteractor = getQuizInteractor;
    }

    public LiveData<FancyQuiz> getQuiz(long quizId) {
        return LiveDataReactiveStreams.fromPublisher(getQuizInteractor.execute(quizId)
                .onErrorReturnItem(new FancyQuiz())
                .toFlowable());
    }
}
