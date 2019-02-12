package com.eng.asu.adaptivelearning.viewmodel;

import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.domain.interactor.EnrollInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.HotCoursesInteractor;
import com.eng.asu.adaptivelearning.domain.interactor.NewCoursesInteractor;
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
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class HomeViewModel extends ViewModel {
    private final HotCoursesInteractor hotCoursesInteractor;
    private final NewCoursesInteractor newCoursesInteractor;
    private final UserAccountStorage userAccountStorage;
    private final EnrollInteractor enrollInteractor;
    private List<Course> defaultCourses = new ArrayList<>();

    @Inject
    HomeViewModel(HotCoursesInteractor hotCoursesInteractor, NewCoursesInteractor newCoursesInteractor, UserAccountStorage userAccountStorage, EnrollInteractor enrollInteractor) {
        super();
        this.hotCoursesInteractor = hotCoursesInteractor;
        this.newCoursesInteractor = newCoursesInteractor;
        this.userAccountStorage = userAccountStorage;
        this.enrollInteractor = enrollInteractor;

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

    public LiveData<List<Course>> getNewCourses() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(5, TimeUnit.SECONDS)
                        .flatMap(aLong -> newCoursesInteractor.execute(userAccountStorage.getAuthToken()))
                        .onErrorReturnItem(defaultCourses)
                        .map(courses -> {
                            if (courses.isEmpty())
                                return defaultCourses;
                            else
                                return courses;
                        })
                        .defaultIfEmpty(defaultCourses));
    }

    public LiveData<List<Course>> getHotCourses() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.interval(5, TimeUnit.SECONDS)
                        .flatMap(aLong -> hotCoursesInteractor.execute(userAccountStorage.getAuthToken()))
                        .onErrorReturnItem(defaultCourses)
                        .map(courses -> {
                            if (courses.isEmpty())
                                return defaultCourses;
                            else
                                return courses;
                        })
                        .defaultIfEmpty(defaultCourses));
    }

    public Completable enrollInCourse(int courseId) {
        return enrollInteractor.execute(userAccountStorage.getAuthToken(), courseId);
    }
}
