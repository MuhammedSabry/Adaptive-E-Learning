package com.eng.asu.adaptivelearning.model;

import androidx.annotation.DrawableRes;

public class Course {
    private String name;
    private User instructor;
    @DrawableRes
    private int background;

    public Course(String name, User instructor, int background) {
        this.name = name;
        this.instructor = instructor;
        this.background = background;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }
}
