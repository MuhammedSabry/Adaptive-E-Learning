package com.eng.asu.adaptivelearning.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.view.adapter.CoursesAdapter;
import com.eng.asu.adaptivelearning.view.custom.CustomRecycler;
import com.eng.asu.adaptivelearning.viewmodel.HomeViewModel;
import java.util.Collections;

public class SearchableActivity extends AppCompatActivity{

    private CoursesAdapter searchedCoursesAdapter;
    private CustomRecycler searchedCoursesList;
    private HomeViewModel viewModel;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        category = (String) getIntent().getSerializableExtra(Intent.EXTRA_TEXT);
        Log.d("categoryyyy", category);
        initViewModel();
        initViews();
        subscribeToData();
    }

    private void initViews() {
        searchedCoursesList = findViewById(R.id.search_courses_list);
        searchedCoursesAdapter = new CoursesAdapter(SearchableActivity.this, Collections.emptyList(), viewModel);
        searchedCoursesList.setLayoutManager(new LinearLayoutManager(SearchableActivity.this, RecyclerView.HORIZONTAL, false));
        searchedCoursesList.setAdapter(searchedCoursesAdapter);
    }

    private void subscribeToData() {
        viewModel.getCoursesByCategory(category).observe(this, searchedCoursesAdapter::setCourses);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(HomeViewModel.class);
    }
}
