package com.eng.asu.adaptivelearning.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityRegisterBinding;
import com.eng.asu.adaptivelearning.databinding.DialogDatePickerBinding;
import com.eng.asu.adaptivelearning.model.form.RegisterForm;
import com.eng.asu.adaptivelearning.util.InputTextUtils;
import com.eng.asu.adaptivelearning.viewmodel.RegisterViewModel;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity implements RegisterForm.Listener {

    private RegisterViewModel viewModel;
    private ActivityRegisterBinding binding;
    private AlertDialog datePickerDialog;
    private DialogDatePickerBinding dialogBinding;
    public static final String REGISTER_FOR_CHILD = "extra_is_add_child";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
    }

    private void initActivity() {
        initDataBinding();
        initViewModel();
        initViews();
    }

    private void initViews() {
        InputTextUtils.setErrorCanceller(binding.username);
        InputTextUtils.setErrorCanceller(binding.email);
        InputTextUtils.setErrorCanceller(binding.lastName);
        InputTextUtils.setErrorCanceller(binding.password);
        InputTextUtils.setErrorCanceller(binding.firstName);
        if (viewModel.isForChild())
            binding.signup.setText("Add child");
        else
            binding.signup.setText("Signup");
    }

    private void initViewModel() {
        viewModel = ViewModelProviders
                .of(this, LearningApplication.getViewModelFactory())
                .get(RegisterViewModel.class);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null && !extras.isEmpty()) {
            boolean isForChild = extras.getBoolean(REGISTER_FOR_CHILD, false);
            viewModel.setIsForChild(isForChild);
        }
    }

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setHandler(this);
    }

    public void onSignUpClicked() {
        RegisterForm registerForm = new RegisterForm(
                InputTextUtils.getText(binding.firstName),
                InputTextUtils.getText(binding.lastName),
                InputTextUtils.getText(binding.email),
                InputTextUtils.getText(binding.username),
                InputTextUtils.getText(binding.password),
                binding.currentDate.getText().toString(),
                binding.male.isSelected() ? 1 : 2);

        disableRegistering();

        viewModel.register(registerForm, this);
    }

    private void disableRegistering() {
        InputTextUtils.hideSoftKeyboard(this);
        binding.signup.setEnabled(false);
        binding.loadingScreen.setVisibility(View.VISIBLE);
    }

    private void enableRegistering() {
        binding.signup.setEnabled(true);
        binding.loadingScreen.setVisibility(View.GONE);
    }

    public void onDatePicked() {
        int year = dialogBinding.datePicker.getYear();
        int month = dialogBinding.datePicker.getMonth() + 1;
        int day = dialogBinding.datePicker.getDayOfMonth();

        String currentMonth = (month < 10) ? ("0" + month) : String.valueOf(month);
        String currentDay = (day < 10) ? ("0" + day) : String.valueOf(day);

        binding.currentDate.setText(getString(R.string.date_of_birth, year, currentMonth, currentDay));
        datePickerDialog.dismiss();
    }

    public void onPickDateClicked() {
        if (dialogBinding == null) {
            dialogBinding = DataBindingUtil.inflate(getLayoutInflater(),
                    R.layout.dialog_date_picker,
                    null,
                    false);
            dialogBinding.setHandler(this);
        }

        if (datePickerDialog == null)
            datePickerDialog = new AlertDialog.Builder(this)
                    .setView(dialogBinding.getRoot())
                    .create();

        if (!datePickerDialog.isShowing())
            datePickerDialog.show();
    }

    @Override
    public void onEmailError(String message) {
        binding.email.setError(message);
    }

    @Override
    public void onFirstNameError(String message) {
        binding.firstName.setError(message);
    }

    @Override
    public void onLastNameError(String message) {
        binding.lastName.setError(message);
    }

    @Override
    public void onPasswordError(String message) {
        binding.password.setError(message);
    }

    @Override
    public void onDateOfBirthError(String message) {
        binding.dateOfBirth.setError(message);
    }

    @Override
    public void onUsernameError(String message) {
        binding.username.setError(message);
    }

    @Override
    public void onSuccess(String message) {
        Toasty.success(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFail(String message) {
        Toasty.error(this, message, Toast.LENGTH_SHORT).show();
        enableRegistering();
    }

    @Override
    public void onFallBack() {
        enableRegistering();
    }
}
