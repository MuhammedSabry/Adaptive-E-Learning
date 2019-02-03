package com.eng.asu.adaptivelearning.domain.model;

import java.util.List;

public class Classroom {

    private int classroomId;

    private String classroomName;

    private String passCode;

    private User creator;

    private List<User> students;

    private List<Course> courses;

    public Classroom() {
    }

    public Classroom(String classroomName, String passCode) {
        this.classroomName = classroomName;
        this.passCode = passCode;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}
