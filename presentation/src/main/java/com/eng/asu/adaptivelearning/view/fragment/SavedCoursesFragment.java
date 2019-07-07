package com.eng.asu.adaptivelearning.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.FragmentSavedCoursesBinding;
import com.eng.asu.adaptivelearning.view.adapter.CoursesAdapter;
import com.eng.asu.adaptivelearning.viewmodel.SavedCoursesViewModel;

import java.util.Collections;

public class SavedCoursesFragment extends Fragment {

    private FragmentSavedCoursesBinding binding;
    private CoursesAdapter savedCoursesAdapter;
    private SavedCoursesViewModel savedCoursesViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_courses, container, false);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        savedCoursesViewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(SavedCoursesViewModel.class);
        initViews();
        subscribe();
    }

    private void initViews() {
        savedCoursesAdapter = new CoursesAdapter(getContext(), Collections.emptyList(), savedCoursesViewModel);
        binding.savedCoursesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.savedCoursesList.setAdapter(savedCoursesAdapter);
    }

    private void subscribe() {
        savedCoursesViewModel.getSavedCourses().observe(this, savedCoursesAdapter::setCourses);
    }
}
