package com.eng.asu.adaptivelearning.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.adaptivelearning.server.FancyModel.FancyUser;
import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.viewmodel.MainViewModel;

public class UserProfile extends AppCompatActivity {

    //private ImageView userPhoto;
    private TextView userName, firstName, lastName, email, gender, dateOfBirth;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initViews();
        initViewModel();
        mainViewModel.getUserData().observe(this, this::setUser);
        setViews();
    }

    private void setUser(FancyUser user) {
        userName.setText(user.getUsername());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        gender.setText(user.getGender());
        dateOfBirth.setText(user.getDateOfBirth());
    }

    private void setViews() {

    }

    private void initViews() {
        //userPhoto = findViewById(R.id.userPhoto);
        userName = findViewById(R.id.userName);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);
        dateOfBirth = findViewById(R.id.dateOfBirth);
    }

    private void initViewModel() {
        mainViewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(MainViewModel.class);
    }
}
