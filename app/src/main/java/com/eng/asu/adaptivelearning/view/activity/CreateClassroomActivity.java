package com.eng.asu.adaptivelearning.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eng.asu.adaptivelearning.R;
//import com.eng.asu.adaptivelearning.view.MainActivity;
import com.eng.asu.adaptivelearning.viewmodel.UserViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.observers.DefaultObserver;
import okhttp3.ResponseBody;

public class CreateClassroomActivity extends AppCompatActivity {
    EditText classroomName, classroomCategory;
    Button createClass;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_classroom);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        classroomName = (EditText) findViewById(R.id.classroomName);
        classroomCategory = (EditText) findViewById(R.id.classroomCategory);
        createClass = (Button) findViewById(R.id.createClass);
        createClass.setOnClickListener(view -> createClassroom());
    }

    public void createClassroom(){
        userViewModel.createClassroom(classroomName.getText().toString(), classroomCategory.getText().toString(),userViewModel.getUserId())
                .subscribe(new DefaultObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Toast.makeText(CreateClassroomActivity.this, "Classroom Created Sucessfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreateClassroomActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CreateClassroomActivity.this, "Oooops!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
