package com.eng.asu.adaptivelearning.viewmodel;

import android.util.Log;

import com.eng.asu.adaptivelearning.domain.interactor.LoginInteractor;
import com.eng.asu.adaptivelearning.model.form.LoginForm;
import com.eng.asu.adaptivelearning.preferences.UserAccountStorage;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.Disposable;

import static com.eng.asu.adaptivelearning.util.TextUtil.getText;
import static com.eng.asu.adaptivelearning.util.ValidationUtil.isValidEmail;
import static com.eng.asu.adaptivelearning.util.ValidationUtil.isValidPassword;
import static com.eng.asu.adaptivelearning.util.ValidationUtil.isValidText;

public class LoginViewModel extends ViewModel {

    private static final String TAG = "LoginViewModel";

    private final UserAccountStorage userAccountStorage;
    private final LoginInteractor loginInteractor;

    private Disposable loginDisposable;

    @Inject
    LoginViewModel(UserAccountStorage userAccountStorage, LoginInteractor loginInteractor) {
        super();
        this.userAccountStorage = userAccountStorage;
        this.loginInteractor = loginInteractor;
    }

    public void login(LoginForm loginForm, LoginForm.Listener listener) {
        String emailOrUsername = getText(loginForm.getEmailOrUsername());
        String password = getText(loginForm.getPassword());

        boolean isValidEmailOrUsername = true;
        if (isValidText(emailOrUsername)) {
            if (isInvalidEmail(emailOrUsername)) {
                listener.onEmailError("Invalid email address or username");
                isValidEmailOrUsername = false;
            }
        } else {
            listener.onEmailError("Field cannot be empty");
            isValidEmailOrUsername = false;
        }

        boolean isValidPassword = true;
        if (!isValidText(password) || !isValidPassword(password)) {
            isValidPassword = false;
            listener.onPasswordError("Invalid password");
        }

        if (isValidPassword && isValidEmailOrUsername)
            loginDisposable = loginInteractor.execute(emailOrUsername, password)
                    .subscribe(user -> {
                        onLoginSuccess(user);
                        listener.onSuccess("Login success :)");
                    }, throwable -> {
                        onLoginError(throwable);
                        listener.onFail(throwable.getMessage());
                    });
        else
            listener.onFallBack();
    }

    private boolean isInvalidEmail(String emailOrUsername) {
        return emailOrUsername.contains("@") && !isValidEmail(emailOrUsername);
    }

    private void onLoginSuccess(String token) {
        userAccountStorage.setAuthToken(token);
        Log.i(TAG, "Login successful with token " + token);
    }

    private void onLoginError(Throwable throwable) {
        Log.e(TAG, "Login attempt error " + throwable.getMessage());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (loginDisposable != null)
            loginDisposable.dispose();
    }
}
