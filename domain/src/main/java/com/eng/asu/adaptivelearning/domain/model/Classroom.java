package com.eng.asu.adaptivelearning.domain.model;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.adaptivelearning.server.FancyModel.FancyUser;

import java.util.List;

public class Classroom {
    private Long classroomId;
    private FancyUser creator;
    private String classroomName;
    private String passCode;
    private int studentsNumber;
    private short coursesNumber;
    private List<FancyCourse> courses;
    private FancyMediaFile classroom_picture;

    public Classroom() {
    }

    public Long getClassroomId() {
        return this.classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public FancyUser getCreator() {
        return this.creator;
    }

    public void setCreator(FancyUser creator) {
        this.creator = creator;
    }

    public String getClassroomName() {
        return this.classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getPassCode() {
        return this.passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public int getStudentsNumber() {
        return this.studentsNumber;
    }

    public void setStudentsNumber(int studentsNumber) {
        this.studentsNumber = studentsNumber;
    }

    public short getCoursesNumber() {
        return this.coursesNumber;
    }

    public void setCoursesNumber(short coursesNumber) {
        this.coursesNumber = coursesNumber;
    }

    public List<FancyCourse> getCourses() {
        return this.courses;
    }

    public void setCourses(List<FancyCourse> courses) {
        this.courses = courses;
    }

    public FancyMediaFile getClassroom_picture() {
        return this.classroom_picture;
    }

    public void setClassroom_picture(FancyMediaFile classroom_picture) {
        this.classroom_picture = classroom_picture;
    }

}
