package com.eng.asu.adaptivelearning.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
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
        holder.bind(answers.get(position));
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    class AnswersViewHolder extends RecyclerView.ViewHolder {

        private final CheckedTextView checkedTextView;
        private long checkedTextViewId;

        AnswersViewHolder(@NonNull CheckedTextView itemView) {
            super(itemView);
            this.checkedTextView = itemView;
        }

        void bind(FancyAnswer answer) {
            this.checkedTextView.setText(answer.getBody());
            this.checkedTextView.setOnClickListener(v -> this.onAnswerClicked(answer));
            if (!isMultipleChoice && checkedTextViewId == answer.getAnswerId())
                this.checkedTextView.setChecked(true);
            else if (!isMultipleChoice)
                this.checkedTextView.setChecked(false);
        }

        private void onAnswerClicked(FancyAnswer answer) {
            if (isMultipleChoice) {
                this.checkedTextView.toggle();
            } else {
                this.checkedTextViewId = answer.getAnswerId();
            }
            notifyDataSetChanged();
        }
    }
}
