package com.eng.asu.adaptivelearning.viewmodel;

import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.domain.interactor.GetEnrolledCourses;
import com.eng.asu.adaptivelearning.domain.model.Course;
import com.eng.asu.adaptivelearning.domain.model.User;
import com.eng.asu.adaptivelearning.preferences.UserAccountStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;

public class MyCoursesViewModel extends ViewModel {

    private final UserAccountStorage userAccountStorage;
    private final GetEnrolledCourses getEnrolledCourses;
    private List<Course> defaultCourses = new ArrayList<>();

    @Inject
    MyCoursesViewModel(UserAccountStorage userAccountStorage, GetEnrolledCourses getEnrolledCourses) {
        super();
        this.userAccountStorage = userAccountStorage;
        this.getEnrolledCourses = getEnrolledCourses;

        for (int i = 0; i <= 10; i++)
            defaultCourses.add(getRandomCourse());
    }

    private Course getRandomCourse() {
        Course course = new Course();
        course.setTitle("Course title");

        User publisher = new User();
        publisher.setFirstName("Teacher");
        course.setPublisher(publisher);
        course.setDetailedTitle("Detailed title of the course is here");

        int[] backgroundList = new int[]{R.drawable.bg1, R.drawable.bg2, R.drawable.bg3};
        course.setBackground(backgroundList[new Random().nextInt(3)]);
        return course;
    }

    public LiveData<List<Course>> getUserCourses() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(10, TimeUnit.SECONDS)
                        .flatMap(aLong -> getEnrolledCourses.execute(userAccountStorage.getAuthToken()))
                        .onErrorReturnItem(defaultCourses)
                        .map(courses -> {
                            if (courses.isEmpty())
                                return defaultCourses;
                            else
                                return courses;
                        })
                        .defaultIfEmpty(defaultCourses));
    }

}
