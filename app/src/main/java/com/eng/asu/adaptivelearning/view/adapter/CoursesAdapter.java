package com.eng.asu.adaptivelearning.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.CoursesListItemBinding;
import com.eng.asu.adaptivelearning.model.Course;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder> {

    private Context context;
    private List<Course> courses;
    private boolean isEnrollmentAllowed;

    public CoursesAdapter(Context context, List<Course> courses, boolean allowEnrollment) {
        this.context = context;
        this.courses = courses;
        this.isEnrollmentAllowed = allowEnrollment;
    }

    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CoursesListItemBinding listBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.courses_list_item, parent, false);
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
        CoursesListItemBinding binding;

        CoursesViewHolder(@NonNull CoursesListItemBinding binding) {
            super(binding.parent);
            this.binding = binding;
        }

        void bind(Course course) {
            binding.courseBackground.setBackground(context.getResources().getDrawable(course.getBackground()));
            binding.courseName.setText(course.getName());
            binding.courseInstructor.setText(course.getInstructor().getName());

            if (isEnrollmentAllowed)
                binding.enrollButton.setVisibility(View.VISIBLE);
            else
                binding.enrollButton.setVisibility(View.INVISIBLE);
        }
    }
}
