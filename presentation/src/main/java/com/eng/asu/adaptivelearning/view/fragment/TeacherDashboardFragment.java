package com.eng.asu.adaptivelearning.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adaptivelearning.server.FancyModel.FancyUser;
import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.FragmentTeacherDashboardBinding;
import com.eng.asu.adaptivelearning.domain.User;
import com.eng.asu.adaptivelearning.model.BaseListener;
import com.eng.asu.adaptivelearning.view.adapter.ClassAdapter;
import com.eng.asu.adaptivelearning.view.adapter.CoursesAdapter;
import com.eng.asu.adaptivelearning.viewmodel.HomeViewModel;
import com.eng.asu.adaptivelearning.viewmodel.MainViewModel;
import com.eng.asu.adaptivelearning.viewmodel.TeacherDashboardViewModel;

import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class TeacherDashboardFragment extends Fragment implements View.OnClickListener {
    private TeacherDashboardViewModel viewModel;
    private FragmentTeacherDashboardBinding binding;
    private CoursesAdapter coursesAdapter;
    private ClassAdapter classAdapter;
    private MainViewModel mainViewModel;
    private HomeViewModel homeViewModel;
    private boolean teacher;

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
        mainViewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(MainViewModel.class);
        homeViewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(HomeViewModel.class);
        initViews();
        subscribe();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                if(teacher){
                    binding.yourCoursesLabel.setVisibility(View.VISIBLE);
                    binding.yourClassroomsLabel.setVisibility(View.VISIBLE);
                    binding.coursesList.setVisibility(View.VISIBLE);
                    binding.classesList.setVisibility(View.VISIBLE);
                }
                else{
                    binding.request.setVisibility(View.VISIBLE);
                    binding.notTeacher.setVisibility(view.VISIBLE);
                }
            }
        }, 2000);
    }

    private void subscribe() {
        viewModel.getTeacherCourses().observe(this, coursesAdapter::setCourses);
        viewModel.getTeacherClassrooms().observe(this, classAdapter::setClasses);
        mainViewModel.getUserData2().observe(this, this::setUser);
    }

    private void setUser(User user) {
        this.teacher = user.getTeacher();
    }

    private void initViews() {
        coursesAdapter = new CoursesAdapter(getContext(), Collections.emptyList(), (HomeViewModel) null);
        binding.coursesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.coursesList.setAdapter(coursesAdapter);
        binding.request.setOnClickListener(this);
        classAdapter = new ClassAdapter(getContext(), Collections.emptyList());
        binding.classesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.classesList.setAdapter(classAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view == binding.request){
            homeViewModel.sendTeachingRequest(new BaseListener() {
                @Override
                public void onSuccess(String message) {
                    Toasty.success(getActivity(), message).show();
                }

                @Override
                public void onFail(String message) {
                    Toasty.error(getActivity(), message).show();
                }

                @Override
                public void onFallBack() {

                }
            });
        }
    }
}
