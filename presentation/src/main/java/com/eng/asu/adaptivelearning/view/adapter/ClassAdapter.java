package com.eng.asu.adaptivelearning.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.adaptivelearning.server.FancyModel.FancyClassroom;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ItemviewClassroomBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private Context context;
    private List<FancyClassroom> classes;

    public ClassAdapter(Context context, List<FancyClassroom> classrooms) {
        this.context = context;
        this.classes = classrooms;
    }

    public void setCourses(List<FancyClassroom> classrooms) {
        this.classes = classrooms;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemviewClassroomBinding listBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.itemview_classroom,
                parent,
                false);
        return new ClassViewHolder(listBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.bind(classes.get(position));
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    class ClassViewHolder extends RecyclerView.ViewHolder {
        ItemviewClassroomBinding binding;

        ClassViewHolder(@NonNull ItemviewClassroomBinding binding) {
            super(binding.parent);
            this.binding = binding;
        }

        void bind(FancyClassroom classroom) {
            binding.className.setText(classroom.getClassroomName());
            binding.classCreator.setText(classroom.getCreator().getFirstName());
        }
    }
}
