package com.eng.asu.adaptivelearning.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.domain.model.Course;
import com.eng.asu.adaptivelearning.viewmodel.MyCoursesViewModel;

public class CourseInfo extends AppCompatActivity {
    private TextView courseName, detailedTitle, courseDescription, courseCreatorName, courseCreatorEmail, courseContent;
    private ImageView courseBackground;
    Long courseId;
    private MyCoursesViewModel myCoursesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);
        initViews();
        initViewModel();
        courseId = getIntent().getLongExtra("itemClicked", 0L);
        subscribeToCourseData();
    }

    private void subscribeToCourseData() {
        //Integer i = courseId.intValue();
        myCoursesViewModel.getCourse(courseId).observe(this, this::setViews);
    }

    private void initViewModel() {
        myCoursesViewModel = ViewModelProviders.of(this, LearningApplication.getViewModelFactory()).get(MyCoursesViewModel.class);
    }

    private void setViews(Course course) {
        //courseBackground.setImageResource(course.getBackground());
        courseBackground.setImageResource(R.drawable.bg2);
        courseName.setText(course.getTitle());
        detailedTitle.setText(course.getDetailedTitle());
        courseDescription.setText(course.getDescription());
        if (course.getPublisher() != null) {
            courseCreatorName.setText(course.getPublisher().getFirstName() + " " + course.getPublisher().getLastName());
            courseCreatorEmail.setText(course.getPublisher().getEmail());
        }
        if (course.getSections() != null)
            for (int i = 0; i < course.getSections().size(); i++) {
                courseContent.append(course.getSections().get(i).getTitle() + "\n");
            }
    }

    private void initViews() {
        courseBackground = findViewById(R.id.courseBackground);
        courseName = findViewById(R.id.courseName);
        detailedTitle = findViewById(R.id.detailedTitle);
        courseDescription = findViewById(R.id.courseDescription);
        courseCreatorName = findViewById(R.id.courseCreatorName);
        courseCreatorEmail = findViewById(R.id.courseCreatorEmail);
        courseContent = findViewById(R.id.courseContent);
        courseContent.setText("");
    }

    public void watchContent(View view) {
        Intent intent = new Intent(this, CourseContentActivity.class);
        intent.putExtra(CourseContentActivity.COURSE_ID, courseId);
        startActivity(intent);
    }
}
