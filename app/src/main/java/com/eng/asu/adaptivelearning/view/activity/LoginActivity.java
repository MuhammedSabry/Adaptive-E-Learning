package com.eng.asu.adaptivelearning.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.eng.asu.adaptivelearning.EditTextWatcher;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityLoginBinding;
import com.eng.asu.adaptivelearning.model.User;
import com.eng.asu.adaptivelearning.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.observers.DefaultObserver;

public class LoginActivity extends AppCompatActivity {
    UserViewModel userViewModel;
    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        initializeActivity();
    }

    private void initializeActivity() {
        loginBinding.setLoginHandler(this);
        new EditTextWatcher(loginBinding.passwordTextInput);
        new EditTextWatcher(loginBinding.emailTextInput);
    }

    public void onLoginButtonClicked() {
        String email = loginBinding.emailEditText.getText().toString();
        String password = loginBinding.passwordEditText.getText().toString();
        if (!userViewModel.isValidEmail(email))
            invalidInput(loginBinding.emailEditText, "Email address invalid");
        else if (!userViewModel.isValidPassword(password))
            invalidInput(loginBinding.passwordEditText, "Password is invalid");
        else {
            userViewModel.login(email, password).subscribe(new DefaultObserver<User>() {
                @Override
                public void onNext(User user) {
                    if (userViewModel.isUserLoggedIn()) {
                        finish();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    private void invalidInput(TextInputEditText editText, String errorMessage) {
        editText.requestFocus();
        editText.setError(errorMessage);
    }

    public void onRegisterClicked() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
