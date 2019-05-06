package com.eng.asu.adaptivelearning.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;

import com.adaptivelearning.server.FancyModel.FancyAnswer;
import com.adaptivelearning.server.FancyModel.FancyQuestion;
import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.eng.asu.adaptivelearning.domain.StudentAnswer;
import com.eng.asu.adaptivelearning.domain.interactor.GetQuizInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.SubmitQuizInteractor;
import com.eng.asu.adaptivelearning.model.BaseListener;
import com.eng.asu.adaptivelearning.model.QuizTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

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
        FancyQuiz onErrorQuiz = new FancyQuiz();
        onErrorQuiz.setTitle("Best quiz");
        onErrorQuiz.setQuizId(100L);
        ArrayList<FancyQuestion> questions = new ArrayList<>();

        FancyQuestion question = new FancyQuestion();
        question.setBody("Body of question");
        question.setQuestionId(1L);
        question.setMultipleChoice(false);

        ArrayList<FancyAnswer> answers = new ArrayList<>();

        FancyAnswer answer1 = new FancyAnswer();
        answer1.setAnswerId(10L);
        answer1.setBody("Best answer 1");
        answer1.setCorrect(true);

        FancyAnswer answer2 = new FancyAnswer();
        answer2.setAnswerId(20L);
        answer2.setBody("Best answer 2");
        answer2.setCorrect(false);

        answers.add(answer1);
        answers.add(answer2);
        question.setAnswers(answers);

        questions.add(question);
        onErrorQuiz.setQuestions(questions);
        return LiveDataReactiveStreams.fromPublisher(getQuizInteractor.execute(quizId)
                .onErrorReturnItem(onErrorQuiz)
                .doOnSuccess(fancyQuiz -> this.quiz = fancyQuiz)
                .toFlowable());
    }

    public LiveData<QuizTime> getTime() {
        addDisposable(Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(aLong -> getTimeLiveData.postValue(new QuizTime(quiz.getTime() - aLong))));
        return getTimeLiveData;
    }

    public void submitQuiz(List<StudentAnswer> answers, BaseListener listener) {
        addDisposable(submitQuizInteractor.execute(quiz.getQuizId(), answers)
                .subscribe(() -> listener.onSuccess("Successfully submitted your answers"),
                        err -> listener.onFail(err.getMessage())));
    }
}
