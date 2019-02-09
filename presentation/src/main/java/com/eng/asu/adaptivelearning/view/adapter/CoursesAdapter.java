package com.eng.asu.adaptivelearning.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ItemviewCourseBinding;
import com.eng.asu.adaptivelearning.domain.model.Course;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder> {

    private Context context;
    private List<Course> courses;

    public CoursesAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    public void setCourses(List<Course> courses) {
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

        void bind(Course course) {
            binding.courseName.setText(course.getTitle());
            binding.courseInstructor.setText(course.getPublisher().getFirstName());
            binding.courseInstructor.setText(course.getDetailedTitle());
        }
    }
}
