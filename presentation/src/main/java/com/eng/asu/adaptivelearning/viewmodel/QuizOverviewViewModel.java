package com.eng.asu.adaptivelearning.viewmodel;

import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.eng.asu.adaptivelearning.domain.interactor.GetQuizInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.StartQuizInteractor;
import com.eng.asu.adaptivelearning.model.BaseListener;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

public class QuizOverviewViewModel extends BaseViewModel {

    private final StartQuizInteractor startQuizInteractor;
    private final GetQuizInteractor getQuizInteractor;

    @Inject
    QuizOverviewViewModel(StartQuizInteractor startQuizInteractor, GetQuizInteractor getQuizInteractor) {
        super();
        this.startQuizInteractor = startQuizInteractor;
        this.getQuizInteractor = getQuizInteractor;
    }

    public LiveData<FancyQuiz> getQuiz(long quizId) {
        return LiveDataReactiveStreams.fromPublisher(getQuizInteractor.execute(quizId)
                .onErrorReturnItem(new FancyQuiz())
                .toFlowable());
    }

    public void startQuiz(long quizId, BaseListener listener) {
        addDisposable(startQuizInteractor.execute(quizId)
                .subscribe(() -> listener.onSuccess("Successfully started the quiz"),
                        error -> {
                            logError("Couldn't start quiz", error);
                            listener.onFail("Failed to start quiz");
                        }));
    }
}
