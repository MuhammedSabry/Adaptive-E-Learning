package com.eng.asu.adaptivelearning.viewmodel;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.eng.asu.adaptivelearning.domain.interactor.GetSavedCourses;
import com.eng.asu.adaptivelearning.domain.interactor.SaveCourseInteractor;
import com.eng.asu.adaptivelearning.model.BaseListener;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class SavedCoursesViewModel extends ViewModel {
    private final GetSavedCourses getSavedCourses;
    private final SaveCourseInteractor saveCourseInteractor;
    private CompositeDisposable disposables;

    @Inject
    SavedCoursesViewModel(GetSavedCourses getSavedCourses, SaveCourseInteractor saveCourseInteractor) {
        super();
        this.getSavedCourses = getSavedCourses;
        this.saveCourseInteractor = saveCourseInteractor;
        this.disposables = new CompositeDisposable();
    }

    public LiveData<List<FancyCourse>> getSavedCourses() {
        return LiveDataReactiveStreams.fromPublisher(getSavedCourses.execute()
                                        .onErrorReturnItem(Collections.emptyList()));
    }

//    public void saveCourse(long courseId, BaseListener listener){
//        disposables.add(saveCourseInteractor.execute(courseId)
//                .subscribe(() -> listener.onSuccess("Successfully saved"),
//                        throwable -> listener.onFail(throwable.getMessage())));
//    }
}
