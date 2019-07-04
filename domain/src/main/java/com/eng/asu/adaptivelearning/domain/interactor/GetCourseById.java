package com.eng.asu.adaptivelearning.domain.interactor;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.FlowableUseCase;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class GetCourseById extends FlowableUseCase<FancyCourse, Long> {

    private CourseService courseService;

    @Inject
    GetCourseById(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread,
                  CourseService courseService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.courseService = courseService;
    }

    @Override
    protected Flowable<FancyCourse> interact(Long courseId) {
        return courseService.getCourse(courseId)
                .toFlowable(BackpressureStrategy.BUFFER)
                .map(course -> {
                    course.setSections(Flowable.fromIterable(course.getSections())
                            .map(section -> {
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
                    return course;
                });
    }

}
