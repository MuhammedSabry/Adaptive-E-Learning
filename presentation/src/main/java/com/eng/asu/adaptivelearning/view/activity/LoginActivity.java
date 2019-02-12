package com.eng.asu.adaptivelearning.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityLoginBinding;
import com.eng.asu.adaptivelearning.model.form.LoginForm;
import com.eng.asu.adaptivelearning.util.InputTextUtils;
import com.eng.asu.adaptivelearning.viewmodel.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import es.dmoral.toasty.Toasty;

import static com.eng.asu.adaptivelearning.util.InputTextUtils.getTextEditable;
import static com.eng.asu.adaptivelearning.util.InputTextUtils.setErrorCanceller;

public class LoginActivity extends AppCompatActivity implements LoginForm.Listener {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity();
    }

    private void initializeActivity() {
        initDataBinding();
        initViewModel();
        initViews();
    }

    private void initViews() {
        setErrorCancellers();
    }

    private void setErrorCancellers() {
        setErrorCanceller(loginBinding.passwordTextInput);
        setErrorCanceller(loginBinding.emailTextInput);
    }

    private void initViewModel() {
        loginViewModel = ViewModelProviders
                .of(this, LearningApplication.getViewModelFactory())
                .get(LoginViewModel.class);
    }

    private void initDataBinding() {
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginBinding.setLoginHandler(this);
    }

    public void onLoginButtonClicked() {
        LoginForm loginForm = new LoginForm(getTextEditable(loginBinding.emailTextInput),
                getTextEditable(loginBinding.passwordTextInput));

        disableLogin();

        loginViewModel.login(loginForm, this);
    }

    private void disableLogin() {
        InputTextUtils.hideSoftKeyboard(this);
        loginBinding.loginButton.setEnabled(false);
        loginBinding.loadingScreen.setVisibility(View.VISIBLE);
    }

    private void enableLogin() {
        loginBinding.loginButton.setEnabled(true);
        loginBinding.loadingScreen.setVisibility(View.GONE);
    }

    public void onRegisterClicked() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void onEmailError(String error) {
        loginBinding.emailTextInput.setError(error);
    }

    @Override
    public void onPasswordError(String error) {
        loginBinding.passwordTextInput.setError(error);
    }

    @Override
    public void onSuccess(String message) {
        Toasty.success(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void onFail(String message) {
        Toasty.error(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        enableLogin();
    }

    @Override
    public void onFallBack() {
        enableLogin();
    }

}
