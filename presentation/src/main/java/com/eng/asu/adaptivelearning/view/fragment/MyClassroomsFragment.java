package com.eng.asu.adaptivelearning.view.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.FragmentMyClassroomsBinding;
import com.eng.asu.adaptivelearning.model.BaseListener;
import com.eng.asu.adaptivelearning.view.adapter.ClassAdapter;
import com.eng.asu.adaptivelearning.viewmodel.HomeViewModel;
import com.eng.asu.adaptivelearning.viewmodel.MyCoursesViewModel;

import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class MyClassroomsFragment extends Fragment implements View.OnClickListener {

    private MyCoursesViewModel viewModel;
    private HomeViewModel homeViewModel;
    private FragmentMyClassroomsBinding binding;
    private ClassAdapter classAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_classrooms, container, false);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(MyCoursesViewModel.class);
        homeViewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(HomeViewModel.class);
        initViews();
        subscribe();
    }

    private void subscribe() {
        viewModel.getStudentClassrooms().observe(this, classAdapter::setClasses);
    }

    private void initViews() {
        classAdapter = new ClassAdapter(getContext(), Collections.emptyList());
        binding.classesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.classesList.setAdapter(classAdapter);
        binding.joinClassroom.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText passCode = new EditText(getActivity());
        passCode.setInputType(InputType.TYPE_CLASS_TEXT);
        passCode.setHint("classroom passcode");
        builder.setMessage("Classroom")
                .setView(passCode)
                .setPositiveButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("join classroom", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        homeViewModel.joinClassroom(passCode.getText().toString(),
                                new BaseListener() {
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
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
