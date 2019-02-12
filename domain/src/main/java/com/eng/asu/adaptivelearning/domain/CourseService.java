package com.eng.asu.adaptivelearning.domain;

import com.eng.asu.adaptivelearning.domain.model.Course;

import java.util.List;

import io.reactivex.Observable;

public interface CourseService {
    Observable<List<Course>> getHotCourses();

    Observable<List<Course>> getNewCourses();
}
