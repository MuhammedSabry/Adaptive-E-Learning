package com.eng.asu.adaptivelearning.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.FragmentParentalControlBinding;
import com.eng.asu.adaptivelearning.view.activity.RegisterActivity;
import com.eng.asu.adaptivelearning.view.adapter.ChildAdapter;
import com.eng.asu.adaptivelearning.viewmodel.ParentalControlViewModel;

import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ParentalControlFragment extends Fragment {
    private ParentalControlViewModel viewModel;
    private FragmentParentalControlBinding binding;
    private Context context;

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
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory())
                .get(ParentalControlViewModel.class);
    }

    private void initViews() {
        binding.childrenList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        binding.childrenList.setAdapter(new ChildAdapter(context, Collections.emptyList()));
        binding.childrenList.setEmptyView(binding.emptyScreen);
    }

    public void onAddChildClicked() {
        Intent addChildIntent = new Intent(getActivity(), RegisterActivity.class);
        addChildIntent.putExtra(RegisterActivity.REGISTER_FOR_CHILD, true);
        startActivity(addChildIntent);
    }
}
