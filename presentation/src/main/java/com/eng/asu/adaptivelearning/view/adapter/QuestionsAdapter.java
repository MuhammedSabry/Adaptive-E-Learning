package com.eng.asu.adaptivelearning.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.adaptivelearning.server.FancyModel.FancyQuestion;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ItemviewQuestionBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {
    private Context context;
    private List<FancyQuestion> questions;

    public QuestionsAdapter(Context context, List<FancyQuestion> questions) {
        this.context = context;
        this.questions = questions;
    }

    public void setQuestions(List<FancyQuestion> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemviewQuestionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.itemview_question, parent, false);
        return new QuestionsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position) {
        holder.bind(questions.get(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionsViewHolder extends RecyclerView.ViewHolder {
        private ItemviewQuestionBinding dataBinding;

        private QuestionsViewHolder(@NonNull ItemviewQuestionBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }

        private void bind(FancyQuestion question) {
            this.dataBinding.questionBody.setText(question.getBody());
            this.dataBinding.answerList.setAdapter(new AnswersAdapter(context, question.getAnswers(), question.isMultipleChoice()));
        }
    }
}
