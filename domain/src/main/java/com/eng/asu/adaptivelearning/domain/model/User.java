package com.eng.asu.adaptivelearning.domain.model;

import java.util.Date;
import java.util.List;

public class User {

    private int userId;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

    private Date dateOfBirth;

    private String token;

    private short gender;

    private String grade;

    private List<User> children;

    private User parent;

    private List<Classroom> classrooms;

    private List<Course> courses;

    private List<Classroom> joins;

    private List<Course> enrolls;

    public User() {
    }

    public User(String firstName,
                String lastName,
                String email,
                String username,
                String password,
                Date dateOfBirth,
                short gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public short getGender() {
        return gender;
    }

    public void setGender(short gender) {
        this.gender = gender;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<User> getChildren() {
        return children;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public List<Classroom> getJoins() {
        return joins;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Course> getEnrolls() {
        return enrolls;
    }
}
