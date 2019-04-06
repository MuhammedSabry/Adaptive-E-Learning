package com.eng.asu.adaptivelearning.view.activity;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.FancyModel.FancyLecture;
import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.adaptivelearning.server.FancyModel.FancySection;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityCourseContentBinding;
import com.eng.asu.adaptivelearning.view.adapter.SectionsAdapter;
import com.eng.asu.adaptivelearning.viewmodel.CourseContentViewModel;

import java.io.File;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import es.dmoral.toasty.Toasty;

public class CourseContentActivity extends AppCompatActivity implements SectionsAdapter.OnLectureClickedListener, OnPreparedListener {

    public static final String COURSE_ID = "course_id_intent_extra";
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
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        binding.videoView.setOnPreparedListener(this);
        viewModel.getVideoLiveData().observe(this, this::playVideo);
        viewModel.getFilesLiveData().observe(this, this::downloadFile);

        if (extras != null && !extras.isEmpty())
            viewModel.getCourseContent(extras.getLong(COURSE_ID)).observe(this, this::initViews);
        else
            closeActivity();
    }

    private void downloadFile(FancyMediaFile mediaFile) {
        File file = new File(getExternalFilesDir(mediaFile.getFileType()), "Adaptive E-Learning");

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mediaFile.getFileDownloadUri()))
                .setTitle(mediaFile.getFileName())
                .setDescription("Downloading " + mediaFile.getFileName())
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file));

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);

    }

    private void initViews(FancyCourse fancyCourse) {
        setTitle(fancyCourse.getTitle());
        List<FancySection> sections = fancyCourse.getSections();

        if (sections.isEmpty())
            closeActivity();
        else

            binding.sectionsList.setAdapter(new SectionsAdapter(this, sections, this));

    }

    private void playVideo(FancyMediaFile fancyMediaFile) {
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

        if (lecture.getQuiz())
            Toasty.info(this, "Quiz clicked").show();
        else if (lecture.getFile())
            Toasty.info(this, "File clicked").show();
        else if (lecture.getVideo())
            Toasty.info(this, "Video clicked").show();
        else
            Toasty.error(this, "Invalid lecture content").show();

    }

    @Override
    public void onPrepared() {
        binding.videoView.start();
    }
}
