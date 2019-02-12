package com.eng.asu.adaptivelearning.viewmodel;

import android.text.TextUtils;
import android.util.Log;

import com.eng.asu.adaptivelearning.domain.interactor.AddChildInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.RegisterInteractor;
import com.eng.asu.adaptivelearning.model.form.RegisterForm;
import com.eng.asu.adaptivelearning.preferences.UserAccountStorage;

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
    private final AddChildInteractor addChildInteractor;
    private final UserAccountStorage userAccountStorage;

    private Disposable registerDisposable;
    private boolean isForChild = false;

    @Inject
    RegisterViewModel(RegisterInteractor registerInteractor, AddChildInteractor addChildInteractor, UserAccountStorage userAccountStorage) {
        super();
        this.registerInteractor = registerInteractor;
        this.addChildInteractor = addChildInteractor;
        this.userAccountStorage = userAccountStorage;
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
        boolean isValidEmail = isValidEmail(email1) || (TextUtils.isEmpty(email1) && isForChild);
        boolean isValidUsername = isValidName(userName) && !firstName.contains(" ");
        boolean isValidPassword = isValidPassword(password);
        boolean isValidDate = isValidText(dateOfBirth);

        if (!isValidFirstName)
            listener.onFirstNameError("Invalid first name");

        if (!isValidLastName)
            listener.onLastNameError("Invalid Last name");

        if (!isValidEmail)
            listener.onEmailError("Invalid email address");

        if (!isValidPassword)
            listener.onPasswordError("Invalid password");

        if (!isValidUsername)
            listener.onUsernameError("Invalid username");

        if (!isValidDate)
            listener.onDateOfBirthError("Please pick a birth date");

        boolean isValidRegistrationInformation = isValidEmail &&
                isValidFirstName &&
                isValidLastName &&
                isValidPassword &&
                isValidUsername &&
                isValidDate;

        if (isValidRegistrationInformation) {
            if (isForChild)
                registerForChild(listener, firstName, lastName, email1, userName, password, dateOfBirth, gender);
            else
                normalRegister(listener, firstName, lastName, email1, userName, password, dateOfBirth, gender);
        } else
            listener.onFallBack();
    }

    private void registerForChild(RegisterForm.Listener listener, String firstName, String lastName, String email1, String userName, String password, String dateOfBirth, int gender) {
        registerDisposable = addChildInteractor.execute(userAccountStorage.getAuthToken(),
                firstName,
                lastName,
                email1,
                userName,
                password,
                gender,
                dateOfBirth)
                .subscribe(() -> {
                    onRegisterSuccess();
                    if (isForChild)
                        listener.onSuccess("Child added successfully");
                    else
                        listener.onSuccess("Register success :)");
                }, throwable -> {
                    onRegisterError(throwable);
                    listener.onFail(throwable.getMessage());
                });
    }

    private void normalRegister(RegisterForm.Listener listener, String firstName, String lastName, String email1, String userName, String password, String dateOfBirth, int gender) {
        registerDisposable = registerInteractor.execute(firstName,
                lastName,
                email1,
                userName,
                password,
                gender,
                dateOfBirth)
                .subscribe(() -> {
                    onRegisterSuccess();
                    if (isForChild)
                        listener.onSuccess("Child added successfully");
                    else
                        listener.onSuccess("Register success :)");
                }, throwable -> {
                    onRegisterError(throwable);
                    listener.onFail(throwable.getMessage());
                });
    }

    private void onRegisterSuccess() {
        if (isForChild)
            Log.i(TAG, "Child added successfully");
        else
            Log.i(TAG, "Register success");
    }

    private void onRegisterError(Throwable throwable) {
        Log.e(TAG, "Register attempt error " + throwable.getMessage());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (registerDisposable != null)
            registerDisposable.dispose();
    }

    public boolean isForChild() {
        return isForChild;
    }

    public void setIsForChild(boolean isForChild) {
        this.isForChild = isForChild;
    }
}
