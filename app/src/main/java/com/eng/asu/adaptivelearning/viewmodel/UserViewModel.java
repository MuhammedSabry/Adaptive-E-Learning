package com.eng.asu.adaptivelearning.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Patterns;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.data.service.UserService;
import com.eng.asu.adaptivelearning.model.Course;
import com.eng.asu.adaptivelearning.model.User;
import com.eng.asu.adaptivelearning.preferences.UserAccountStorage;
import com.eng.asu.adaptivelearning.view.activity.LoginActivity;
import com.eng.asu.adaptivelearning.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.eng.asu.adaptivelearning.model.UserType.PARENT;
import static com.eng.asu.adaptivelearning.model.UserType.STUDENT;
import static com.eng.asu.adaptivelearning.model.UserType.TEACHER;

public class UserViewModel extends AndroidViewModel {
    @Inject
    UserAccountStorage userAccountStorage;
    @Inject
    UserService userService;

    public UserViewModel(@NonNull Application application) {
        super(application);
        LearningApplication.getApplicationComponent().inject(this);
    }

    public Class<? extends AppCompatActivity> getActivityToOpen() {
        if (isUserLoggedIn())
            return MainActivity.class;
        else
            return LoginActivity.class;
    }

    public boolean isUserLoggedIn() {
        return !TextUtils.isEmpty(userAccountStorage.getUser().getToken());
    }

    public boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 8 && !password.contains(" ") && password.length() <= 15;
    }

    public boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidName(String name) {
        Pattern name_pattern = Pattern.compile("[a-zA-Z\\s]+");
        return !TextUtils.isEmpty(name) && name_pattern.matcher(name).matches();
    }

    public String getUserName() {
        return userAccountStorage.getUser().getName();
    }

    public int getUserId() { return userAccountStorage.getUser().getId(); }

    public Observable<User> login(String email, String password) {
        return userService.login(email, password).doOnNext(userAccountStorage::setUser);
    }

    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
            courses.add(new Course("Classroom " + i, new User(i, "Dr.Number " + i, "", TEACHER, ""), getRandomBackground()));

        return courses;
    }
    public List<Course> getCourses2() {
        List<Course> courses = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
            courses.add(new Course("Course " + i, new User(i, "", "", TEACHER, ""), getRandomBackground()));

        return courses;
    }

    private int getRandomBackground() {
        List<Integer> backgrounds = new ArrayList<>(3);
        Collections.addAll(backgrounds, R.drawable.bg1, R.drawable.bg2, R.drawable.bg3);
        return backgrounds.get(new Random().nextInt(3));
    }

    public Observable<ResponseBody> Register(String email, String password, String name, int type) {
        return userService.createUser(email, password, name, type);
    }

    public boolean allowCourseCreation() {
        return userAccountStorage.getUser().getType() == TEACHER;
    }

    public boolean allowEnrollment() {
        return userAccountStorage.getUser().getType() == STUDENT || userAccountStorage.getUser().getType() == PARENT;
    }

    public Observable<ResponseBody> createClassroom(String name, String category, int creator_id) {
        return userService.createClassroom(name, category, creator_id);
    }
}
