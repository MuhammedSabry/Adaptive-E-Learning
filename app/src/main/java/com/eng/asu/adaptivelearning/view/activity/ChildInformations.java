package com.eng.asu.adaptivelearning.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.viewmodel.UserViewModel;

public class ChildInformations extends AppCompatActivity {
    private EditText childUserName, childPassword;
    private Spinner schoolGrade;
    private Spinner childGender;
    private Button add;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_informations);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        childUserName =findViewById(R.id.childUserName);
        childPassword =findViewById(R.id.childPassword);
        schoolGrade =findViewById(R.id.schoolGrade);
        childGender =findViewById(R.id.childGender);
        add = findViewById(R.id.add);
        ArrayAdapter<String> gradeSpinnerAdapter = new ArrayAdapter<>(ChildInformations.this, R.layout.spinner_item,
                getResources().getStringArray(R.array.grade));
        gradeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolGrade.setAdapter(gradeSpinnerAdapter);

        ArrayAdapter<String> genderSpinnerAdapter = new ArrayAdapter<>(ChildInformations.this, R.layout.spinner_item,
                getResources().getStringArray(R.array.gender));
        gradeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        childGender.setAdapter(genderSpinnerAdapter);

        add.setOnClickListener(view -> addChild());

    }

    private void addChild() {
        if (!userViewModel.isValidPassword(childPassword.getText().toString()))
            childPassword.setError("Password is invalid");
        else if (schoolGrade.getSelectedItemPosition() < 1)
            Toast.makeText(this, "Please choose child's school grade", Toast.LENGTH_SHORT).show();
        else if (childGender.getSelectedItemPosition() < 1)
            Toast.makeText(this, "Please choose child's gender", Toast.LENGTH_SHORT).show();
        else{
            //TODO -save childUserName.getText().toString() && childPassword.getText().toString() && schoolGrade/childGender.getSelectedItem().toString();
            startActivity(new Intent(this, ParentActivity.class));
        }

    }
}
