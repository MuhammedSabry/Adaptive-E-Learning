package com.eng.asu.adaptivelearning.viewmodel;

import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.model.Course;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private final CourseService courseService;

    @Inject
    public HomeViewModel(CourseService courseService) {
        super();
        this.courseService = courseService;
    }

    public LiveData<List<Course>> getCourses() {
        //TODO
        return LiveDataReactiveStreams.fromPublisher(courseService.getAllCourses());
    }
}
