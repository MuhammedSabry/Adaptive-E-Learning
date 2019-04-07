package com.eng.asu.adaptivelearning.viewmodel;

import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.eng.asu.adaptivelearning.domain.interactor.GetQuizInteractor;
import com.eng.asu.adaptivelearning.model.BaseListener;
import com.eng.asu.adaptivelearning.model.QuizTime;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import io.reactivex.Flowable;

public class QuizViewModel extends BaseViewModel {
    private final GetQuizInteractor getQuizInteractor;
    private FancyQuiz quiz;

    @Inject
    QuizViewModel(GetQuizInteractor getQuizInteractor) {
        this.getQuizInteractor = getQuizInteractor;
    }

    public LiveData<FancyQuiz> getQuiz(long quizId) {
        return LiveDataReactiveStreams.fromPublisher(getQuizInteractor.execute(quizId)
                .onErrorReturnItem(new FancyQuiz())
                .doOnSuccess(fancyQuiz -> this.quiz = fancyQuiz)
                .toFlowable());
    }

    public LiveData<QuizTime> getTime() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(1, TimeUnit.SECONDS)
                        .map(aLong -> new QuizTime(quiz.getTime() - aLong))
                        .flatMap(quizTime -> quizTime.isTimeOut() ? Flowable.empty() : Flowable.just(quizTime)));
    }

    public void submitQuiz(BaseListener listener) {
        //TODO -- implement this
    }
}
