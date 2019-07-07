package com.eng.asu.adaptivelearning.domain.interactor;

import android.util.Log;

import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.FlowableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.model.Course;
import com.google.gson.Gson;

import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class GetCourseById extends FlowableUseCase<Course, Long> {

    private CourseService courseService;

    @Inject
    GetCourseById(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread,
                  CourseService courseService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.courseService = courseService;
    }

    @Override
    protected Flowable<Course> interact(Long courseId) {
        return courseService.getCourse(courseId)
                .toFlowable(BackpressureStrategy.BUFFER)
                .map(course -> {
                    if (course.getSections() == null)
                        course.setSections(Collections.emptyList());
                    else
                        course.setSections(Flowable.fromIterable(course.getSections())
                                .map(section -> {
                                    if (section.getFancyLectures() == null)
                                        section.setFancyLectures(Collections.emptyList());
                                    else
                                        section.setFancyLectures(Flowable.fromIterable(section.getFancyLectures())
                                                .map(lecture -> {
                                                    if (lecture.isVideo() == null)
                                                        lecture.setVideo(false);
                                                    if (lecture.isFile() == null)
                                                        lecture.setFile(false);
                                                    return lecture;
                                                })
                                                .toList()
                                                .blockingGet());
                                    return section;
                                })
                                .toList()
                                .blockingGet());
                    Log.d("GetCourseById", "Course = " + new Gson().toJson(course));
                    return course;
                });
    }

}
