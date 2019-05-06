package com.eng.asu.adaptivelearning.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityQuizOverviewBinding;
import com.eng.asu.adaptivelearning.viewmodel.QuizOverviewViewModel;

import es.dmoral.toasty.Toasty;

public class QuizOverviewActivity extends AppCompatActivity {
    public static final String QUIZ_ID_INTENT_EXTRA = "quiz_id";
    private ActivityQuizOverviewBinding binding;
    private long quizId = 0;
    private QuizOverviewViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
    }

    private void initActivity() {
        initDataBinding();
        initViewModel();
        validateQuizId();
        initViews();
    }

    private void initViews() {
        disableStart();
    }

    private void validateQuizId() {
        if (getIntent() != null) {
            this.quizId = getIntent().getLongExtra(QUIZ_ID_INTENT_EXTRA, 0);

            if (quizId != 0)
                viewModel.getQuiz(quizId).observe(this, this::onQuizReceived);
            else
                finish();
        } else
            finish();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(QuizOverviewViewModel.class);
    }

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_overview);
    }

    private void onQuizReceived(FancyQuiz quiz) {
        if (quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
            finish();
            return;
        }
        enableStart();
        binding.quizInstructions.setText(quiz.getInstructions());
        binding.time.setText(getString(R.string.quiz_time_in_minutes, quiz.getTime()));
        binding.title.setText(quiz.getTitle());
        binding.totalMark.setText(getString(R.string.quiz_total_marks, quiz.getTotalMark()));
        binding.start.setOnClickListener(v -> this.onStartQuizClicked());
    }

    private void enableStart() {
        binding.start.setEnabled(true);
    }

    private void disableStart() {
        binding.start.setEnabled(false);
    }

    private void onStartQuizClicked() {
        Intent intent = new Intent(QuizOverviewActivity.this, QuizActivity.class);
        intent.putExtra(QUIZ_ID_INTENT_EXTRA, quizId);
        startActivity(intent);
    }

    @Override
    public void finish() {
        Toasty.error(this, "Invalid quiz id").show();
        super.finish();
    }
}
