package com.eng.asu.adaptivelearning.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityQuizBinding;
import com.eng.asu.adaptivelearning.model.BaseListener;
import com.eng.asu.adaptivelearning.model.QuizTime;
import com.eng.asu.adaptivelearning.view.adapter.QuestionsAdapter;
import com.eng.asu.adaptivelearning.viewmodel.QuizViewModel;

import java.util.Collections;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import es.dmoral.toasty.Toasty;

public class QuizActivity extends AppCompatActivity implements BaseListener {
    private ActivityQuizBinding binding;
    private QuizViewModel viewModel;
    private long quizId;
    private QuestionsAdapter questionsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
    }

    private void initActivity() {
        initDataBinding();
        initViewModel();
        initViews();
        Intent launchingIntent = getIntent();
        long intentQuizId = 0;
        if (launchingIntent != null)
            intentQuizId = launchingIntent.getLongExtra(QuizOverviewActivity.QUIZ_ID_INTENT_EXTRA, 0);
        if (intentQuizId != 0)
            this.quizId = intentQuizId;
        else
            finish();
        viewModel.getQuiz(this.quizId).observe(this, this::onQuizReceived);
    }

    private void onQuizReceived(FancyQuiz quiz) {
        binding.submit.setEnabled(true);
        if (quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
            finish();
        } else {
            this.questionsAdapter.setQuestions(quiz.getQuestions());
            this.viewModel.getTime().observe(this, this::setTime);
        }
    }

    private void setTime(QuizTime quizTime) {
        if (quizTime.isTimeOut()) {
            this.onQuizTimeout();
        } else
            binding.timer.setText(quizTime.getFormattedTime());
    }

    private void onQuizTimeout() {
        Toasty.info(this, "Quiz timeout,quiz will auto submit now").show();
        binding.submit.setEnabled(false);
        viewModel.submitQuiz(this);
    }

    @Override
    public void finish() {
        Toasty.error(this, "Error getting quiz data").show();
        super.finish();
    }

    private void initViewModel() {
        this.viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(QuizViewModel.class);
    }

    private void initDataBinding() {
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);
    }

    private void initViews() {
        binding.submit.setEnabled(false);
        binding.timer.setText("00:00:00");
        questionsAdapter = new QuestionsAdapter(this, Collections.emptyList());
        binding.questionList.setAdapter(questionsAdapter);
    }

    @Override
    public void onSuccess(String message) {
        Toasty.success(this, message).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onFail(String message) {
        Toasty.error(this, message).show();
    }

    @Override
    public void onFallBack() {

    }
}
