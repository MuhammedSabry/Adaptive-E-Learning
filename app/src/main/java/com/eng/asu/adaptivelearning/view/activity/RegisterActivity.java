package com.eng.asu.adaptivelearning.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.viewmodel.UserViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.observers.DefaultObserver;
import okhttp3.ResponseBody;

public class RegisterActivity extends AppCompatActivity {

    EditText name, email, password;
    Button signup;
    Spinner type;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        type = findViewById(R.id.type);
        signup = findViewById(R.id.signup);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(RegisterActivity.this, R.layout.spinner_item,
                getResources().getStringArray(R.array.type));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(spinnerAdapter);

        signup.setOnClickListener(view -> Register());
    }

    private void Register() {
        if (!userViewModel.isValidName(name.getText().toString()))
            name.setError("Name is invalid");
        else if (!userViewModel.isValidEmail(email.getText().toString()))
            email.setError("Email address invalid");
        else if (!userViewModel.isValidPassword(password.getText().toString()))
            password.setError("Password is invalid");
        else if (type.getSelectedItemPosition() < 1)
            Toast.makeText(this, "Please choose your type", Toast.LENGTH_SHORT).show();
        else {
            signup.setEnabled(false);
            userViewModel.Register(email.getText().toString()
                    , password.getText().toString()
                    , name.getText().toString()
                    , type.getSelectedItemPosition())
                    .subscribe(new DefaultObserver<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            Toast.makeText(RegisterActivity.this
                                    , "User created successfully"
                                    , Toast.LENGTH_SHORT).
                                    show();
                            new Handler().postDelayed(() -> finish(), 1000);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(RegisterActivity.this
                                    , "Oooops!" + e.getMessage()
                                    , Toast.LENGTH_SHORT)
                                    .show();
                            signup.setEnabled(true);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
