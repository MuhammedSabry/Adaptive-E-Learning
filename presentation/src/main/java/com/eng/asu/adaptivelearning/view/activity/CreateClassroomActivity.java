package com.eng.asu.adaptivelearning.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.viewmodel.CreateClassroomViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

//import com.eng.asu.adaptivelearning.view.MainActivity;

public class CreateClassroomActivity extends AppCompatActivity {
    EditText classroomName, classroomCategory;
    Button createClass;
    CreateClassroomViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_classroom);

        viewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(CreateClassroomViewModel.class);
        classroomName = findViewById(R.id.classroomName);
        classroomCategory = findViewById(R.id.classroomCategory);
        createClass = findViewById(R.id.createClass);
        createClass.setOnClickListener(view -> createClassroom());
    }

    public void createClassroom() {
        //TODO - re implement the createClassroom activity like others and after looking into the new API
//        viewModel.createClassroom(classroomName.getText().toString(),
//                classroomCategory.getText().toString(),
//                viewModel.getUserId())
//                .subscribe(new DefaultObserver<ResponseBody>() {
//                    @Override
//                    public void onNext(ResponseBody responseBody) {
//                        Toast.makeText(CreateClassroomActivity.this, "Classroom Created Sucessfully", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(CreateClassroomActivity.this, MainActivity.class));
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(CreateClassroomActivity.this, "Oooops!" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

    }

}
