package com.eng.asu.adaptivelearning.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private int courseId;

    private String title;

    private String detailedTitle;

    private String description;

    private short rate;

    private boolean isPublic;

    private short level;

    private User publisher;

    private List<User> learners;

    private List<Classroom> classrooms = new ArrayList<>();

    private List<Section> sections;

    public Course() {
    }

    public Course(String title,
                  String detailedTitle,
                  String description,
                  boolean isPublic,
                  short level) {
        this.title = title;
        this.detailedTitle = detailedTitle;
        this.description = description;
        this.isPublic = isPublic;
        this.level = level;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailedTitle() {
        return detailedTitle;
    }

    public void setDetailedTitle(String detailedTitle) {
        this.detailedTitle = detailedTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getRate() {
        return rate;
    }

    public void setRate(short rate) {
        this.rate = rate;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public List<User> getLearners() {
        return learners;
    }

    public void setLearners(List<User> learners) {
        this.learners = learners;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
