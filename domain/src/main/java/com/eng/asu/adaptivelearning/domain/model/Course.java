package com.eng.asu.adaptivelearning.domain.model;

import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.adaptivelearning.server.FancyModel.FancySection;
import com.adaptivelearning.server.FancyModel.FancyUser;

import java.util.Date;
import java.util.List;

public class Course {

    private Long courseId;
    private String title;
    private String detailedTitle;
    private String description;
    private float rate;
    private boolean isPublic;
    private short level;
    private String category;
    private Date publishDate;
    private Integer numberOfStudents = 0;
    private Integer numberOfRaters = 0;
    private FancyUser publisher;
    private List<FancySection> sections;
    private FancyMediaFile course_picture;
    private String role = "";

    public Course() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(Integer numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public Integer getNumberOfRaters() {
        return numberOfRaters;
    }

    public void setNumberOfRaters(Integer numberOfRaters) {
        this.numberOfRaters = numberOfRaters;
    }

    public FancyUser getPublisher() {
        return publisher;
    }

    public void setPublisher(FancyUser publisher) {
        this.publisher = publisher;
    }

    public List<FancySection> getSections() {
        return sections;
    }

    public void setSections(List<FancySection> sections) {
        this.sections = sections;
    }

    public FancyMediaFile getCourse_picture() {
        return course_picture;
    }

    public void setCourse_picture(FancyMediaFile course_picture) {
        this.course_picture = course_picture;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
