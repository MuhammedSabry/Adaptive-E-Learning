package com.eng.asu.adaptivelearning.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(HomeViewModel.class);
        initViews();
    }

    private void initViews() {
        binding.newCoursesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        binding.newCoursesList.setAdapter(new CoursesAdapter(getContext(), Collections.emptyList()));
        binding.hotCoursesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        binding.hotCoursesList.setAdapter(new CoursesAdapter(getContext(), Collections.emptyList()));
    }


}
