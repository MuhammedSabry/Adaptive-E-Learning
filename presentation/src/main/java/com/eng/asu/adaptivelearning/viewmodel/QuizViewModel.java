package com.eng.asu.adaptivelearning.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.eng.asu.adaptivelearning.domain.StudentAnswer;
import com.eng.asu.adaptivelearning.domain.interactor.StartQuizInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.SubmitQuizInteractor;
import com.eng.asu.adaptivelearning.model.BaseListener;
import com.eng.asu.adaptivelearning.model.QuizTime;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class QuizViewModel extends BaseViewModel {
    private final StartQuizInteractor startQuizInteractor;
    private final SubmitQuizInteractor submitQuizInteractor;
    private FancyQuiz quiz;

    @Inject
    QuizViewModel(StartQuizInteractor startQuizInteractor, SubmitQuizInteractor submitQuizInteractor) {
        this.startQuizInteractor = startQuizInteractor;
        this.submitQuizInteractor = submitQuizInteractor;
    }

    public LiveData<FancyQuiz> startQuiz(long quizId) {
        FancyQuiz onErrorQuiz = new FancyQuiz();
        return LiveDataReactiveStreams.fromPublisher(startQuizInteractor.execute(quizId)
                .onErrorReturnItem(onErrorQuiz)
                .doOnSuccess(fancyQuiz -> this.quiz = fancyQuiz)
                .toFlowable());
    }

    public LiveData<QuizTime> getTime() {
        return LiveDataReactiveStreams.fromPublisher(Flowable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(aLong -> new QuizTime(this.quiz.getTime() * 60 - aLong)));
    }

    public void submitQuiz(List<StudentAnswer> answers, BaseListener listener) {
        addDisposable(submitQuizInteractor.execute(quiz.getQuizId(), answers)
                .subscribe(() -> listener.onSuccess("Successfully submitted your answers"),
                        err -> listener.onFail(err.getMessage())));
    }
}
