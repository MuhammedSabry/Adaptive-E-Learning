package com.eng.asu.adaptivelearning.viewmodel;

import android.util.Log;

import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.eng.asu.adaptivelearning.domain.StudentAnswer;
import com.eng.asu.adaptivelearning.domain.interactor.GetQuizInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.SubmitQuizInteractor;
import com.eng.asu.adaptivelearning.model.BaseListener;
import com.eng.asu.adaptivelearning.model.QuizTime;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;

public class QuizViewModel extends BaseViewModel {
    private final GetQuizInteractor getQuizInteractor;
    private final SubmitQuizInteractor submitQuizInteractor;
    private MutableLiveData<QuizTime> getTimeLiveData;
    private FancyQuiz quiz;

    @Inject
    QuizViewModel(GetQuizInteractor getQuizInteractor, SubmitQuizInteractor submitQuizInteractor) {
        this.getQuizInteractor = getQuizInteractor;
        this.submitQuizInteractor = submitQuizInteractor;
        this.getTimeLiveData = new MutableLiveData<>();
    }

    public LiveData<FancyQuiz> getQuiz(long quizId) {
        return LiveDataReactiveStreams.fromPublisher(getQuizInteractor.execute(quizId)
                .onErrorReturnItem(new FancyQuiz())
                .doOnSuccess(fancyQuiz -> this.quiz = fancyQuiz)
                .toFlowable());
    }

    public LiveData<QuizTime> getTime() {
        addDisposable(Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(aLong -> {
                    Log.e("Intervaaaaaaal", String.valueOf(aLong));
                    getTimeLiveData.postValue(new QuizTime(quiz.getTime() - aLong));
                }));
        return getTimeLiveData;
    }

    public void submitQuiz(List<StudentAnswer> answers, BaseListener listener) {
        addDisposable(submitQuizInteractor.execute(quiz.getQuizId(), answers)
                .subscribe(() -> listener.onSuccess("Successfully submitted your answers"),
                        err -> listener.onFail(err.getMessage())));
    }
}
