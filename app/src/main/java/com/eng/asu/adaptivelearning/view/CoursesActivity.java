package com.eng.asu.adaptivelearning.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityMainBinding;
import com.eng.asu.adaptivelearning.view.adapter.Courses2_Adapter;
import com.eng.asu.adaptivelearning.view.adapter.CoursesAdapter;
import com.eng.asu.adaptivelearning.viewmodel.UserViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CoursesActivity extends AppCompatActivity{
    ActivityMainBinding mainBinding;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        initializeActivity();
    }
    @SuppressLint("RestrictedApi")
    private void initializeActivity() {
        if (userViewModel.allowCourseCreation())
            mainBinding.createClass.setVisibility(View.VISIBLE);
        else
            mainBinding.createClass.setVisibility(View.GONE);
        mainBinding.coursesList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mainBinding.coursesList.setAdapter(new Courses2_Adapter(this, userViewModel.getCourses2()));
    }
}
