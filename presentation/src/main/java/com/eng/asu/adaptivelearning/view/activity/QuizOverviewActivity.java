package com.eng.asu.adaptivelearning.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.adaptivelearning.server.FancyModel.FancyStudentQuiz;
import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityQuizOverviewBinding;
import com.eng.asu.adaptivelearning.viewmodel.QuizOverviewViewModel;

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

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initViews() {
        disableStart();
    }

    private void validateQuizId() {
        if (getIntent() != null) {
            this.quizId = getIntent().getLongExtra(QUIZ_ID_INTENT_EXTRA, 0);

            if (quizId != 0)
                getData();
            else
                finish();

        } else
            finish();
    }

    private void getData() {
        viewModel.getQuiz(quizId).observe(this, this::onQuizReceived);
        viewModel.getSubmittedQuiz(quizId).observe(this, this::onSubmittedQuizReceived);
    }

    private void onSubmittedQuizReceived(FancyStudentQuiz fancyStudentQuiz) {
        if (fancyStudentQuiz.getQuizId() == null)
            return;
        binding.attemptsCount.setText("Attempts: " + fancyStudentQuiz.getAttempts());
        binding.bestMark.setText("Best mark: " + fancyStudentQuiz.getStudentBestMark() + "/" + fancyStudentQuiz.getRetrievedQuizTotalMark());
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(QuizOverviewViewModel.class);
    }

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_overview);
    }

    private void onQuizReceived(FancyQuiz quiz) {
        enableStart();
        binding.quizInstructions.setText(quiz.getInstructions());
        binding.time.setText(getString(R.string.quiz_time_in_minutes, quiz.getTime()));
        binding.title.setText(quiz.getTitle());
        binding.start.setOnClickListener(v -> this.onStartQuizClicked());
        if (quiz.getNo_of_questions() == 0)
            disableStart();
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
        super.finish();
    }
}
