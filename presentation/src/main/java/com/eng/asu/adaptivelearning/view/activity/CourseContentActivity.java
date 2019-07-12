package com.eng.asu.adaptivelearning.view.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.adaptivelearning.server.FancyModel.FancyLecture;
import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.adaptivelearning.server.FancyModel.FancySection;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityCourseContentBinding;
import com.eng.asu.adaptivelearning.domain.model.Course;
import com.eng.asu.adaptivelearning.view.adapter.SectionsAdapter;
import com.eng.asu.adaptivelearning.viewmodel.CourseContentViewModel;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class CourseContentActivity extends AppCompatActivity implements SectionsAdapter.OnLectureClickedListener, OnPreparedListener {

    public static final String COURSE_ID = "course_id_intent_extra";
    private static final int REQUEST_WRITE_EXTERNAL_CODE = 51;
    private ActivityCourseContentBinding binding;
    private CourseContentViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
    }

    private void initActivity() {
        initDataBinding();
        initViewModel();
        initViews();

        viewModel.getVideoLiveData().observe(this, this::playVideo);
        viewModel.getFilesLiveData().observe(this, this::downloadFile);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null && !extras.isEmpty())
            viewModel.getCourseContent(extras.getLong(COURSE_ID)).observe(this, this::initViews);
        else
            closeActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            binding.videoView.stopPlayback();
        } catch (Exception ignored) {

        }
    }

    private void initViews() {
        binding.videoView.setVisibility(View.GONE);
        binding.videoView.setOnPreparedListener(this);
        binding.videoView.setOnCompletionListener(() -> binding.videoView.stopPlayback());
    }

    private void downloadFile(FancyMediaFile mediaFile) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_CODE);
        } else {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mediaFile.getFileDownloadUri()))
                    .setTitle(mediaFile.getFileName())
                    .setDescription("Downloading " + mediaFile.getFileName())
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mediaFile.getFileName());

            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
        }

    }

    private void initViews(Course course) {
        setTitle(course.getTitle());
        List<FancySection> sections = course.getSections();

        if (sections.isEmpty())
            closeActivity();
        else
            binding.sectionsList.setAdapter(new SectionsAdapter(this, sections, this));

    }

    private void playVideo(FancyMediaFile fancyMediaFile) {
        binding.videoView.setVisibility(View.VISIBLE);
        binding.videoView.setVideoURI(Uri.parse(fancyMediaFile.getFileDownloadUri()));
    }

    private void closeActivity() {
        finish();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(CourseContentViewModel.class);
    }

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_course_content);
    }

    @Override
    public void onLectureClicked(FancyLecture lecture) {
        viewModel.onLectureClicked(lecture);
        if (!lecture.isFile() && !lecture.isVideo())
            Toasty.error(this, "Invalid lecture content").show();

    }

    @Override
    public void onQuizClicked(FancyQuiz quiz) {
        Intent quizOverViewIntent = new Intent(this, QuizOverviewActivity.class);
        quizOverViewIntent.putExtra(QuizOverviewActivity.QUIZ_ID_INTENT_EXTRA, quiz.getQuizId());
        startActivity(quizOverViewIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_CODE) {
            if ((grantResults.length > 0))
                Toasty.success(this, "Permissions granted").show();
            else
                Toasty.info(this, "Permissions denied").show();
        }
    }

    @Override
    public void onPrepared() {
        binding.videoView.start();
    }
}
