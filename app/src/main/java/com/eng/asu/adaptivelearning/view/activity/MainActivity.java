package com.eng.asu.adaptivelearning.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityMainBinding;
import com.eng.asu.adaptivelearning.view.adapter.CoursesAdapter;
import com.eng.asu.adaptivelearning.viewmodel.UserViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mainBinding.createClass.setOnClickListener(view -> onCreateClassroomClicked());
        createWelcomeToast();
        initializeActivity();
    }

    @SuppressLint("RestrictedApi")
    private void initializeActivity() {
        setSupportActionBar(mainBinding.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        if (userViewModel.allowCourseCreation())
            mainBinding.createClass.setVisibility(View.VISIBLE);
        else
            mainBinding.createClass.setVisibility(View.GONE);

        mainBinding.coursesList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        mainBinding.coursesList.setAdapter(new CoursesAdapter(this, userViewModel.getCourses(), userViewModel.allowEnrollment()));

        mainBinding.navView.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            mainBinding.drawerLayout.closeDrawers();
            return true;
        });

    }

    private void createWelcomeToast() {
        Toast.makeText(this, "Welcome " + userViewModel.getUserName() + " :)", Toast.LENGTH_SHORT).show();
    }

    public void onCreateClassroomClicked() {
        startActivity(new Intent(MainActivity.this, CreateClassroomActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mainBinding.drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
