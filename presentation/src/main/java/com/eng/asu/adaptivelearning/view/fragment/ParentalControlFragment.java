package com.eng.asu.adaptivelearning.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.FragmentParentalControlBinding;
import com.eng.asu.adaptivelearning.domain.model.Course;
import com.eng.asu.adaptivelearning.model.BaseListener;
import com.eng.asu.adaptivelearning.view.activity.RegisterActivity;
import com.eng.asu.adaptivelearning.view.adapter.ChildAdapter;
import com.eng.asu.adaptivelearning.viewmodel.HomeViewModel;
import com.eng.asu.adaptivelearning.viewmodel.ParentalControlViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class ParentalControlFragment extends Fragment implements View.OnClickListener{
    private ParentalControlViewModel viewModel;
    private HomeViewModel homeViewModel;
    private FragmentParentalControlBinding binding;
    private Context context;
    private ChildAdapter childrenAdapter;
    private List<Course> courses = new ArrayList<Course>();

    public ParentalControlFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViewBinding(inflater, container);
        return binding.getRoot();
    }

    private void initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_parental_control, container, false);
        binding.setHandler(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initViews();
        subscribeToChildren();
    }

    private void subscribeToChildren() {
        viewModel.getChildren().observe(this, childrenAdapter::setChildren);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory())
                .get(ParentalControlViewModel.class);
        homeViewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory())
                .get(HomeViewModel.class);
    }

    private void initViews() {
        childrenAdapter = new ChildAdapter(context, Collections.emptyList());
        binding.childrenList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        binding.childrenList.setAdapter(childrenAdapter);
        binding.childrenList.setEmptyView(binding.emptyScreen);
        binding.joinChildToClass.setOnClickListener(this);
        binding.enrollChildToCourse.setOnClickListener(this);
    }

    public void onAddChildClicked() {
        Intent addChildIntent = new Intent(getActivity(), RegisterActivity.class);
        addChildIntent.putExtra(RegisterActivity.REGISTER_FOR_CHILD, true);
        startActivity(addChildIntent);
    }

    @Override
    public void onClick(View view) {
        if(view == binding.joinChildToClass){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater li = LayoutInflater.from(getActivity());
            View layout = li.inflate(R.layout.join_child_to_class, null);
            builder.setMessage("Classroom")
                    .setView(layout)
                    .setPositiveButton("close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .setNegativeButton("join classroom", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            homeViewModel.joinChildToClassroom(((EditText)layout.findViewById(R.id.childFirstName)).getText().toString(),
                                    ((EditText)layout.findViewById(R.id.passcode)).getText().toString(),
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

        else if(view == binding.enrollChildToCourse){

            homeViewModel.getAllCourses().observe(ParentalControlFragment.this, ParentalControlFragment.this::setCourses);
            final String[] courseTitle = new String[1];
            final String[] courseID = new String[1];
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater li = LayoutInflater.from(getActivity());
            View layout = li.inflate(R.layout.join_child_to_course, null);
            builder.setMessage("Course")
                    .setView(layout)
                    .setPositiveButton("close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .setNegativeButton("join course", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            courseTitle[0] = ((EditText)layout.findViewById(R.id.coursetitle)).getText().toString();

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    for(int i=0; i<courses.size(); i++) {
                        if(courseTitle[0].equals((courses.get(i).getTitle()))) {
                            courseID[0] = String.valueOf(courses.get(i).getCourseId());
                        }
                    }
                    homeViewModel.joinChildToCourse((((EditText)layout.findViewById(R.id.childFirstName)).getText().toString()),courseID[0],
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
        }


    }

    private void setCourses(List<Course> courses){
        this.courses = courses;
    }
}
