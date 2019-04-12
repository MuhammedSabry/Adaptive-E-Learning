package com.eng.asu.adaptivelearning.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityQuizOverviewBinding;
import com.eng.asu.adaptivelearning.model.BaseListener;
import com.eng.asu.adaptivelearning.viewmodel.QuizOverviewViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import es.dmoral.toasty.Toasty;

public class QuizOverviewActivity extends AppCompatActivity {
    public static final String QUIZ_ID_INTENT_EXTRA = "quiz_id";
    private ActivityQuizOverviewBinding binding;
    private QuizOverviewViewModel viewModel;
    private long quizId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
    }

    private void initActivity() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_overview);
        viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(QuizOverviewViewModel.class);
        if (getIntent() != null) {
            this.quizId = getIntent().getLongExtra(QUIZ_ID_INTENT_EXTRA, 0);

            if (quizId != 0)
                viewModel.getQuiz(quizId).observe(this, this::onQuizReceived);
            else
                finish();
        } else
            finish();
        binding.start.setEnabled(false);
    }

    private void onQuizReceived(FancyQuiz quiz) {
        if (quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
            finish();
            return;
        }
        binding.start.setEnabled(true);
        binding.quizInstructions.setText(quiz.getInstructions());
        binding.time.setText(getString(R.string.quiz_time_in_minutes, quiz.getTime()));
        binding.title.setText(quiz.getTitle());
        binding.totalMark.setText(getString(R.string.quiz_total_marks, quiz.getTotalMark()));
        binding.start.setOnClickListener(v -> this.onStartQuizClicked());
    }

    private void onStartQuizClicked() {
        viewModel.startQuiz(quizId, new BaseListener() {
            @Override
            public void onSuccess(String message) {
                Intent intent = new Intent(QuizOverviewActivity.this, QuizActivity.class);
                intent.putExtra(QUIZ_ID_INTENT_EXTRA, quizId);
                startActivity(intent);
            }

            @Override
            public void onFail(String message) {
                Toasty.error(QuizOverviewActivity.this, message).show();
            }

            @Override
            public void onFallBack() {

            }
        });
    }

    @Override
    public void finish() {
        Toasty.error(this, "Invalid quiz id").show();
        super.finish();
    }
}
