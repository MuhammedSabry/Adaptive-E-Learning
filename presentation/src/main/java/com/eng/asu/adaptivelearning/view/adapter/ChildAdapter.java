package com.eng.asu.adaptivelearning.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.adaptivelearning.server.FancyModel.FancyUser;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ItemviewChildBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder> {

    private Context context;
    private List<FancyUser> children;

    public ChildAdapter(Context context, List<FancyUser> children) {
        this.context = context;
        this.children = children;
    }

    public void setChildren(List<FancyUser> children) {
        this.children = children;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemviewChildBinding childBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.itemview_child, parent, false);
        return new ChildViewHolder(childBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        holder.bind(children.get(position));
    }

    @Override
    public int getItemCount() {
        return children.size();
    }

    class ChildViewHolder extends RecyclerView.ViewHolder {
        private ItemviewChildBinding binding;

        ChildViewHolder(@NonNull ItemviewChildBinding childBinding) {
            super(childBinding.getRoot());
            this.binding = childBinding;
        }

        void bind(FancyUser child) {
            binding.name.setText(child.getFirstName() + " " + child.getLastName());
            binding.username.setText(child.getUsername());
        }
    }
}
