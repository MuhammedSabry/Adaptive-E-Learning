package com.eng.asu.adaptivelearning.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ItemviewCourseBinding;
import com.eng.asu.adaptivelearning.model.BaseListener;
import com.eng.asu.adaptivelearning.viewmodel.HomeViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder> {

    private Context context;
    private List<FancyCourse> courses;
    private HomeViewModel homeViewModel;

    public CoursesAdapter(Context context, List<FancyCourse> courses, HomeViewModel viewModel) {
        this.context = context;
        this.courses = courses;
        this.homeViewModel = viewModel;
    }

    public void setCourses(List<FancyCourse> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemviewCourseBinding listBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.itemview_course,
                parent,
                false);
        return new CoursesViewHolder(listBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder holder, int position) {
        holder.bind(courses.get(position));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class CoursesViewHolder extends RecyclerView.ViewHolder {
        ItemviewCourseBinding binding;

        CoursesViewHolder(@NonNull ItemviewCourseBinding binding) {
            super(binding.parent);
            this.binding = binding;
        }

        void bind(FancyCourse course) {
            binding.courseName.setText(course.getTitle());
            binding.courseInstructor.setText(course.getPublisher().getFirstName());
            binding.courseInstructor.setText(course.getDetailedTitle());
//            if (course.getBackground() != 0) {
//                binding.courseBackground.setImageDrawable(context.getResources().getDrawable(course.getBackground()));
//            } else {
//                int[] backgroundList = new int[]{R.drawable.bg1, R.drawable.bg2, R.drawable.bg3};
//                binding.courseBackground.setImageDrawable(context.getResources().getDrawable(backgroundList[new Random().nextInt(3)]));
//            }
            if (homeViewModel == null) {
                binding.enrollButton.setVisibility(View.GONE);
            } else {
                binding.enrollButton.setVisibility(View.VISIBLE);
                binding.enrollButton.setOnClickListener(v -> enrollInCourse(course));
            }
        }

        private void enrollInCourse(FancyCourse course) {
            binding.enrollButton.setEnabled(false);
            homeViewModel.enrollInCourse(course.getCourseId(), new BaseListener() {
                @Override
                public void onSuccess(String message) {
                    onEnrollSuccess(message);
                }

                @Override
                public void onFail(String message) {
                    onEnrollError(message);
                }

                @Override
                public void onFallBack() {
                }
            });
        }

        private void onEnrollError(String message) {
            binding.enrollButton.setEnabled(true);
            Toasty.error(context, message).show();
        }

        private void onEnrollSuccess(String message) {
            binding.enrollButton.setEnabled(true);
            Toasty.success(context, message).show();
        }
    }
}
