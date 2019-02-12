package com.eng.asu.adaptivelearning.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.viewmodel.SplashViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class SplashActivity extends AppCompatActivity {

    private SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashViewModel = ViewModelProviders
                .of(this, LearningApplication.getViewModelFactory())
                .get(SplashViewModel.class);

        new Handler().postDelayed(this::splashScreenFinished, 1000);
    }

    private void splashScreenFinished() {
        splashViewModel.getActivityToOpen().observe(this, this::onActivityToOpenRetrieved);
    }

    private void onActivityToOpenRetrieved(Class<? extends AppCompatActivity> activityToOpen) {
        startActivity(new Intent(this, activityToOpen));
        finish();

    }

}
