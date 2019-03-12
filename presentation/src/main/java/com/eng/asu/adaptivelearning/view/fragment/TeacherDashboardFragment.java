package com.eng.asu.adaptivelearning.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.FragmentTeacherDashboardBinding;
import com.eng.asu.adaptivelearning.view.adapter.ClassAdapter;
import com.eng.asu.adaptivelearning.view.adapter.CoursesAdapter;
import com.eng.asu.adaptivelearning.viewmodel.HomeViewModel;
import com.eng.asu.adaptivelearning.viewmodel.TeacherDashboardViewModel;

import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TeacherDashboardFragment extends Fragment {
    private TeacherDashboardViewModel viewModel;
    private FragmentTeacherDashboardBinding binding;
    private CoursesAdapter coursesAdapter;
    private ClassAdapter classAdapter;

    public TeacherDashboardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_dashboard, container, false);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(TeacherDashboardViewModel.class);
        initViews();
        subscribe();
    }

    private void subscribe() {
        viewModel.getTeacherCourses().observe(this, coursesAdapter::setCourses);
        //viewModel.getTeacherClassrooms().observe(this, classAdapter::setClasses);
    }

    private void initViews() {
        coursesAdapter = new CoursesAdapter(getContext(), Collections.emptyList(), (HomeViewModel) null);
        binding.coursesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.coursesList.setAdapter(coursesAdapter);
//        classAdapter = new ClassAdapter(getContext(), Collections.emptyList());
//        binding.classesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
//        binding.classesList.setAdapter(classAdapter);
    }
}
