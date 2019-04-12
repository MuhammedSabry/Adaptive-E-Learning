package com.eng.asu.adaptivelearning.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.adaptivelearning.server.FancyModel.FancyAnswer;
import com.eng.asu.adaptivelearning.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswersViewHolder> {

    private final Context context;
    private final List<FancyAnswer> answers;
    private final boolean isMultipleChoice;
    private int checkedPosition;

    AnswersAdapter(Context context, List<FancyAnswer> answers, boolean isMultipleChoice) {
        this.context = context;
        this.answers = answers;
        this.isMultipleChoice = isMultipleChoice;
    }

    @NonNull
    @Override
    public AnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckedTextView view;

        if (isMultipleChoice)
            view = (CheckedTextView) LayoutInflater.from(context).inflate(R.layout.itemview_answer_multi, parent, false);
        else
            view = (CheckedTextView) LayoutInflater.from(context).inflate(R.layout.itemview_answer_single, parent, false);
        return new AnswersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersViewHolder holder, int position) {
        holder.bind(position + 1, answers.get(position));
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    class AnswersViewHolder extends RecyclerView.ViewHolder {

        private final CheckedTextView checkedTextView;

        AnswersViewHolder(@NonNull CheckedTextView itemView) {
            super(itemView);
            this.checkedTextView = itemView;
        }

        @SuppressLint("SetTextI18n")
        void bind(int prefix, FancyAnswer answer) {
            this.checkedTextView.setText(prefix + ". " + answer.getBody());
            this.checkedTextView.setOnClickListener(this::onAnswerClicked);

            if (!isMultipleChoice) {
                if (checkedPosition == getAdapterPosition())
                    this.checkedTextView.setChecked(true);
                else
                    this.checkedTextView.setChecked(false);
            }

        }

        private void onAnswerClicked(View v) {
            if (isMultipleChoice) {
                this.checkedTextView.toggle();
            } else {
                checkedPosition = getAdapterPosition();
                ((CheckedTextView) v).setChecked(true);
                notifyDataSetChanged();
            }
        }
    }
}
