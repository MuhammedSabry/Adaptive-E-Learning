package com.eng.asu.adaptivelearning.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityMainBinding;
import com.eng.asu.adaptivelearning.view.adapter.MainPagerAdapter;
import com.eng.asu.adaptivelearning.viewmodel.MainViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private MainPagerAdapter mainPagerAdapter;
    private TextView userName;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity();
    }

    private void initializeActivity() {
        initDataBinding();
        initViewModel();
        setupToolBar();
        initViews();
        subscribeToUserData();
    }

    private void initViewModel() {
        mainViewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(MainViewModel.class);
    }

    private void subscribeToUserData() {
        mainViewModel.getUserData().observe(this, user -> userName.setText(user.getUsername()));
    }

    private void initViews() {
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainBinding.viewpager.setAdapter(mainPagerAdapter);
        mainBinding.viewpager.setCurrentItem(1);

        mainBinding.navView.setNavigationItemSelectedListener(this::onDrawerItemClicked);
        mainBinding.navView.getMenu().getItem(0).setChecked(true);

        View headerView = mainBinding.navView.getHeaderView(0);
        userName = headerView.findViewById(R.id.user_name);
    }

    private void initDataBinding() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    public boolean onDrawerItemClicked(MenuItem selectedItem) {
        Menu menu = mainBinding.navView.getMenu();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item == selectedItem) {
                selectedItem.setChecked(true);
                mainBinding.viewpager.setCurrentItem(i + 1);
                setTitle(mainPagerAdapter.getPageTitle(i + 1));
            } else
                item.setChecked(false);
        }

        mainBinding.drawerLayout.closeDrawers();

        return true;
    }

    private void setupToolBar() {
        setSupportActionBar(mainBinding.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
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
