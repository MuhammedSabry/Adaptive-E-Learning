package com.eng.asu.adaptivelearning.domain;

import com.eng.asu.adaptivelearning.domain.model.Course;

import java.util.List;

import io.reactivex.Flowable;

public interface CourseService {

    Flowable<List<Course>> getAllCourses();
}
