package com.eng.asu.adaptivelearning.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ItemviewCourseBinding;
import com.eng.asu.adaptivelearning.model.BaseListener;
import com.eng.asu.adaptivelearning.view.activity.CourseInfo;
import com.eng.asu.adaptivelearning.viewmodel.HomeViewModel;
import com.eng.asu.adaptivelearning.viewmodel.SavedCoursesViewModel;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder> {

    private Context context;
    private List<FancyCourse> courses;
    private HomeViewModel homeViewModel;
    private SavedCoursesViewModel savedCoursesViewModel;

    public CoursesAdapter(Context context, List<FancyCourse> courses, HomeViewModel viewModel) {
        this.context = context;
        this.courses = courses;
        this.homeViewModel = viewModel;
    }

    public CoursesAdapter(Context context, List<FancyCourse> courses, SavedCoursesViewModel savedCoursesViewModel) {
        this.context = context;
        this.courses = courses;
        this.savedCoursesViewModel = savedCoursesViewModel;
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

    class CoursesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemviewCourseBinding binding;

        CoursesViewHolder(@NonNull ItemviewCourseBinding binding) {
            super(binding.parent);
            this.binding = binding;
            binding.courseBackground.setOnClickListener(this);
            binding.courseName.setOnClickListener(this);
            binding.bookmark.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if(view == binding.courseBackground || view == binding.courseName) {
                Intent intent = new Intent(context, CourseInfo.class);
                intent.putExtra("itemClicked", courses.get(this.getAdapterPosition()).getCourseId());
                context.startActivity(intent);
            }
            else if(view == binding.bookmark){
                saveCourse(courses.get(this.getAdapterPosition()).getCourseId());
            }
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

        private void saveCourse(long courseId){
            homeViewModel.saveCourse(courseId, new BaseListener() {
                @Override
                public void onSuccess(String message) {
                    Toasty.success(context, message).show();
                }

                @Override
                public void onFail(String message) {
                    Toasty.error(context, message).show();
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
