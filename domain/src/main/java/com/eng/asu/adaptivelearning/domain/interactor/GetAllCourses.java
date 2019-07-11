package com.eng.asu.adaptivelearning.domain.interactor;

import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.SingleUseCase;
import com.eng.asu.adaptivelearning.domain.model.Course;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetAllCourses extends SingleUseCase<List<Course>, Void> {

    private UserService userService;

    @Inject
    GetAllCourses(BackgroundExecutionThread backgroundExecutionThread,
                  PostExecutionThread postExecutionThread,
                  UserService userService) {
        super(backgroundExecutionThread, postExecutionThread);
        this.userService = userService;
    }

    @Override
    protected Single<List<Course>> interact(Void param) {
        return Single.fromObservable(userService.getAllCourses());
    }
}
