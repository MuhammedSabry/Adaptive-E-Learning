package com.eng.asu.adaptivelearning.view.activity;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.viewmodel.UserViewModel;

public class SplashScreen extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        new Handler().postDelayed(this::splashScreenFinished, 2000);
    }

    private void splashScreenFinished() {
        startActivity(new Intent(this, userViewModel.getActivityToOpen()));
        finish();
    }
}
