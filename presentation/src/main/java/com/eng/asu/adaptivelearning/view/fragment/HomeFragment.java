package com.eng.asu.adaptivelearning.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.FragmentHomeBinding;
import com.eng.asu.adaptivelearning.view.adapter.CoursesAdapter;
import com.eng.asu.adaptivelearning.viewmodel.HomeViewModel;

import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private CoursesAdapter newCoursesAdapter;
    private CoursesAdapter hotCoursesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refresh_action) {
            subscribeToData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initViews();
        subscribeToData();
    }

    private void subscribeToData() {
        viewModel.getNewCourses().observe(this, newCoursesAdapter::setCourses);
        viewModel.getHotCourses().observe(this, hotCoursesAdapter::setCourses);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(HomeViewModel.class);
    }

    private void initViews() {
        newCoursesAdapter = new CoursesAdapter(getContext(), Collections.emptyList(), viewModel);
        binding.newCoursesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.newCoursesList.setAdapter(newCoursesAdapter);

        hotCoursesAdapter = new CoursesAdapter(getContext(), Collections.emptyList(), viewModel);
        binding.hotCoursesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.hotCoursesList.setAdapter(hotCoursesAdapter);

    }

}
