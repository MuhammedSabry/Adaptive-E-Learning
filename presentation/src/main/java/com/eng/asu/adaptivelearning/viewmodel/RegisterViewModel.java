package com.eng.asu.adaptivelearning.viewmodel;

import android.util.Log;

import com.eng.asu.adaptivelearning.domain.interactor.RegisterInteractor;
import com.eng.asu.adaptivelearning.model.form.RegisterForm;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.Disposable;

import static com.eng.asu.adaptivelearning.util.ValidationUtil.isValidEmail;
import static com.eng.asu.adaptivelearning.util.ValidationUtil.isValidName;
import static com.eng.asu.adaptivelearning.util.ValidationUtil.isValidPassword;
import static com.eng.asu.adaptivelearning.util.ValidationUtil.isValidText;

public class RegisterViewModel extends ViewModel {
    private static final String TAG = "RegisterViewModel";

    private final RegisterInteractor registerInteractor;

    private Disposable registerDisposable;

    @Inject
    RegisterViewModel(RegisterInteractor registerInteractor) {
        super();
        this.registerInteractor = registerInteractor;
    }

    public void register(RegisterForm registerForm, RegisterForm.Listener listener) {

        String firstName = registerForm.getFirstName();
        String lastName = registerForm.getLastName();
        String email1 = registerForm.getEmail();
        String userName = registerForm.getUserName();
        String password = registerForm.getPassword();
        String dateOfBirth = registerForm.getDateOfBirth();
        int gender = registerForm.getGender();

        boolean isValidFirstName = isValidName(firstName) && !firstName.contains(" ");
        boolean isValidLastName = isValidName(lastName) && !firstName.contains(" ");
        boolean isValidEmail = isValidEmail(email1);
        boolean isValidUsername = isValidName(userName) && !firstName.contains(" ");
        boolean isValidPassword = isValidPassword(password);
        boolean isValidDate = isValidText(dateOfBirth);

        if (!isValidFirstName)
            listener.onFirstNameError("Invalid first name");

        if (!isValidLastName)
            listener.onLastNameError("Invalid first name");

        if (!isValidEmail)
            listener.onEmailError("Invalid first name");

        if (!isValidPassword)
            listener.onPasswordError("Invalid password");

        if (!isValidUsername)
            listener.onUsernameError("Invalid password");

        if (!isValidDate)
            listener.onDateOfBirthError("Please pick a birth date");

        boolean isValidRegistrationInformation = isValidEmail &&
                isValidFirstName &&
                isValidLastName &&
                isValidPassword &&
                isValidUsername &&
                isValidDate;

        if (isValidRegistrationInformation)
            registerDisposable = registerInteractor.execute(firstName, lastName, email1, userName, password, gender, dateOfBirth)
                    .subscribe(isSuccessful -> {
                        onRegisterSuccess();
                        listener.onSuccess("Register success :)");
                    }, throwable -> {
                        onRegisterError(throwable);
                        listener.onFail(throwable.getMessage());
                    });
        else
            listener.onFallBack();
    }

    private void onRegisterSuccess() {
        Log.i(TAG, "Register success");
    }

    private void onRegisterError(Throwable throwable) {
        Log.e(TAG, "Login attempt error " + throwable.getMessage());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (registerDisposable != null)
            registerDisposable.dispose();
    }

}
