package com.eng.asu.adaptivelearning.domain.model;

public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String dateOfBirth;
    private short age;
    private String token;
    private String gender;
    private String grade;
    private boolean isTeacher = false;
    private boolean isChild = false;
    private boolean isParent = false;

    public User() {
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public short getAge() {
        return this.age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isTeacher() {
        return this.isTeacher;
    }

    public void setTeacher(boolean teacher) {
        this.isTeacher = teacher;
    }

    public boolean isChild() {
        return this.isChild;
    }

    public void setChild(boolean child) {
        this.isChild = child;
    }

    public boolean isParent() {
        return this.isParent;
    }

    public void setParent(boolean parent) {
        this.isParent = parent;
    }
}
