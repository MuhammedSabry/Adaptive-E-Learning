package com.eng.asu.adaptivelearning.model.form;

import com.eng.asu.adaptivelearning.model.BaseListener;

public class RegisterForm {

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private String dateOfBirth;
    private int gender;

    public RegisterForm(String firstName, String lastName, String email, String userName, String password, String dateOfBirth, int gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public interface Listener extends BaseListener {
        void onEmailError(String message);

        void onFirstNameError(String message);

        void onLastNameError(String message);

        void onPasswordError(String message);

        void onDateOfBirthError(String message);

        void onUsernameError(String message);
    }
}
