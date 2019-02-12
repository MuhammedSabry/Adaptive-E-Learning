package com.eng.asu.adaptivelearning.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.FragmentMyCoursesBinding;
import com.eng.asu.adaptivelearning.view.adapter.CoursesAdapter;
import com.eng.asu.adaptivelearning.viewmodel.MyCoursesViewModel;

import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyCoursesFragment extends Fragment {
    private MyCoursesViewModel viewModel;
    private FragmentMyCoursesBinding binding;
    private CoursesAdapter coursesAdapter;

    public MyCoursesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_courses, container, false);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(MyCoursesViewModel.class);
        initViews();
        subscribe();
    }

    private void subscribe() {
        viewModel.getUserCourses().observe(this, coursesAdapter::setCourses);
    }

    private void initViews() {
        coursesAdapter = new CoursesAdapter(getContext(), Collections.emptyList(), null);
        binding.coursesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.coursesList.setAdapter(coursesAdapter);
    }
}
