package com.eng.asu.adaptivelearning.model;

import androidx.annotation.NonNull;

public class QuizTime {
    private String hour;
    private String min;
    private String second;

    public QuizTime(long timeInSeconds) {
        if (timeInSeconds < 0)
            return;
        this.hour = String.valueOf(timeInSeconds / (60 * 60));
        this.hour = appendZeroes(this.hour);
        this.min = String.valueOf((timeInSeconds / 60) % 60);
        this.min = appendZeroes(this.min);
        this.second = String.valueOf(timeInSeconds % 60);
        this.second = appendZeroes(this.second);
    }

    private String appendZeroes(String time) {
        String returnedValue = time;
        while (returnedValue.length() < 2)
            returnedValue = "0".concat(returnedValue);
        return returnedValue;
    }

    @NonNull
    @Override
    public String toString() {
        return this.hour + ":" + this.min + ":" + this.second;
    }

    public boolean isTimeOut() {
        return this.hour.equals("00") && this.min.equals("00") && this.second.equals("00");
    }
}
