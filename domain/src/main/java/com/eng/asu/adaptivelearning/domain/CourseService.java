package com.eng.asu.adaptivelearning.domain;

import com.adaptivelearning.server.FancyModel.FancyCourse;

import java.util.List;

import io.reactivex.Observable;

public interface CourseService {
    Observable<List<FancyCourse>> getHotCourses();

    Observable<List<FancyCourse>> getNewCourses();
}
