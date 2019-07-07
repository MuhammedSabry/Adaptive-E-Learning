package com.eng.asu.adaptivelearning.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.adaptivelearning.server.Model.Classroom;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ItemviewClassroomBinding;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private Context context;
    private List<Classroom> classes;

    public ClassAdapter(Context context, List<Classroom> classrooms) {
        this.context = context;
        this.classes = classrooms;
    }

    public void setClasses(List<Classroom> classrooms) {
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

        void bind(Classroom classroom) {
            binding.className.setText(classroom.getClassroomName());
            binding.classCreator.setText(classroom.getCreator().getFirstName());
        }
    }
}
