package com.eng.asu.adaptivelearning.domain;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("teacher")
    private boolean teacher;

    public User(String firstName, boolean teacher) {
        this.firstName = firstName;
        this.teacher = teacher;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean getTeacher() {
        return teacher;
    }

    public User(){
    }

    public User(User user){
        this.firstName=user.firstName;
        this.teacher=user.teacher;
    }
}
