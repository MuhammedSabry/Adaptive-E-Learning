package com.eng.asu.adaptivelearning.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentAnswer {

    @SerializedName("question_id")
    private long questionId;

    @SerializedName("answers_ids")
    private List<Long> answers;

    public void setAnswers(List<Long> answers) {
        this.answers = answers;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public List<Long> getAnswers() {
        return answers;
    }

    public long getQuestionId() {
        return questionId;
    }
}
