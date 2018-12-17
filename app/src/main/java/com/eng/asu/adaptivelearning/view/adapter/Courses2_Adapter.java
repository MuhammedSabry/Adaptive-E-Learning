package com.eng.asu.adaptivelearning.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.model.Course;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class Courses2_Adapter extends RecyclerView.Adapter<Courses2_Adapter.ViewHolder> {

    private Context context;
    private List<Course> courses;

    public Courses2_Adapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses2_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(courses.get(position));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView courseBackground;
        TextView courseName;
        ConstraintLayout parent;

        public ViewHolder(View itemView) {
            super(itemView);
            courseBackground = itemView.findViewById(R.id.courseBackground);
            courseName = itemView.findViewById(R.id.courseName);
            parent = itemView.findViewById(R.id.parent);
        }

        void bind(Course course) {
            courseBackground.setBackground(context.getResources().getDrawable(course.getBackground()));
            courseName.setText(course.getName());
        }
    }
}
