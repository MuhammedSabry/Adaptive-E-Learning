package com.eng.asu.adaptivelearning.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;

import com.adaptivelearning.server.FancyModel.FancyAnswer;
import com.adaptivelearning.server.FancyModel.FancyQuestion;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ItemviewQuestionBinding;
import com.eng.asu.adaptivelearning.domain.StudentAnswer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.databinding.DataBindingUtil;

public class QuestionsAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<FancyQuestion> questions;
    private List<Integer> selectedAnswers;
    private List<Set<Long>> answersSet;

    public QuestionsAdapter(Context context, List<FancyQuestion> questions) {
        this.context = context;
        this.questions = questions;
    }

    public void setQuestions(List<FancyQuestion> questions) {
        this.questions = questions;
        selectedAnswers = new ArrayList<>(this.questions.size());
        this.answersSet = new ArrayList<>(this.questions.size());
        for (int i = 0; i < questions.size(); i++) {
            selectedAnswers.add(0);
            answersSet.add(new HashSet<>());
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return questions.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return questions.get(groupPosition).getAnswers().size();
    }

    @Override
    public FancyQuestion getGroup(int groupPosition) {
        return questions.get(groupPosition);
    }

    @Override
    public FancyAnswer getChild(int groupPosition, int childPosition) {
        return questions.get(groupPosition).getAnswers().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return questions.get(groupPosition).getQuestionId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return questions.get(groupPosition).getAnswers().get(childPosition).getAnswerId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ItemviewQuestionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.itemview_question, parent, false);
        bindQuestion(binding, groupPosition);
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        return binding.getRoot();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CheckedTextView view;

        FancyQuestion question = questions.get(groupPosition);
        if (question.isMultipleChoice())
            view = (CheckedTextView) LayoutInflater.from(context).inflate(R.layout.itemview_answer_multi, parent, false);
        else
            view = (CheckedTextView) LayoutInflater.from(context).inflate(R.layout.itemview_answer_single, parent, false);

        bindAnswer(view, question.getAnswers().get(childPosition), question.isMultipleChoice(), groupPosition, childPosition);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void bindQuestion(ItemviewQuestionBinding binding, int position) {

        FancyQuestion question = questions.get(position);
        binding.questionBody.setText((position + 1) + ". " + question.getBody());
    }

    private void bindAnswer(CheckedTextView checkedTextView,
                            FancyAnswer answer,
                            boolean isMultipleChoice,
                            int groupPosition,
                            int childPosition) {
        checkedTextView.setText(childPosition + ". " + answer.getBody());
        checkedTextView.setOnClickListener(v -> this.onAnswerClicked(answer, checkedTextView, isMultipleChoice, groupPosition, childPosition));

        if (!isMultipleChoice) {
            if (selectedAnswers.get(groupPosition) == childPosition)
                checkedTextView.setChecked(true);
            else
                checkedTextView.setChecked(false);
        }
    }

    private void onAnswerClicked(FancyAnswer answer, CheckedTextView v, boolean isMultipleChoice, int groupPosition, int childPosition) {
        if (isMultipleChoice) {
            v.toggle();
            if (v.isChecked())
                answersSet.get(groupPosition).remove(answer.getAnswerId());
            else
                answersSet.get(groupPosition).add(answer.getAnswerId());
        } else {
            selectedAnswers.set(groupPosition, childPosition);
            v.setChecked(true);
            notifyDataSetChanged();
        }
    }

    public List<StudentAnswer> getAnswers() {
        List<StudentAnswer> list = new ArrayList<>();
        for (int i = 0; i < getGroupCount(); i++) {

            FancyQuestion question = questions.get(i);

            StudentAnswer studentAnswer = new StudentAnswer();
            studentAnswer.setQuestionId(question.getQuestionId());
            List<Long> answers = new ArrayList<>();

            if (question.isMultipleChoice())
                answers.addAll(answersSet.get(i));
            else
                answers.add(question.getAnswers().get(selectedAnswers.get(i)).getAnswerId());

            studentAnswer.setAnswers(answers);
            list.add(studentAnswer);
        }


        return list;
    }

}
