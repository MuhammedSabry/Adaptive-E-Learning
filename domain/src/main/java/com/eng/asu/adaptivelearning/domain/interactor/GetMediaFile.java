package com.eng.asu.adaptivelearning.domain.interactor;

import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetMediaFile extends SingleUseCase<FancyMediaFile, Long> {
    private final CourseService courseService;

    @Inject
    GetMediaFile(BackgroundExecutionThread backgroundExecutionThread, PostExecutionThread postExecutionThread, CourseService courseService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.courseService = courseService;
    }

    @Override
    protected Single<FancyMediaFile> interact(Long lectureContentId) {
        return courseService.getMediaFile(lectureContentId)
                .singleOrError();
    }
}
