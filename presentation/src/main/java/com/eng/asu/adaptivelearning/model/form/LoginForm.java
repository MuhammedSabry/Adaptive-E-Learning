package com.eng.asu.adaptivelearning.model.form;

import android.text.Editable;

import com.eng.asu.adaptivelearning.model.BaseListener;

public class LoginForm {
    private Editable emailOrUsername;
    private Editable password;

    public LoginForm(Editable emailOrUsername, Editable password) {
        this.emailOrUsername = emailOrUsername;
        this.password = password;
    }

    public Editable getEmailOrUsername() {
        return emailOrUsername;
    }

    public Editable getPassword() {
        return password;
    }

    public interface Listener extends BaseListener {
        void onEmailError(String error);

        void onPasswordError(String error);
    }
}

