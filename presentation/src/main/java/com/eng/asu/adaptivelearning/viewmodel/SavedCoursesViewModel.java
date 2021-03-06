package com.eng.asu.adaptivelearning.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.eng.asu.adaptivelearning.domain.interactor.GetSavedCourses;
import com.eng.asu.adaptivelearning.domain.model.Course;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class SavedCoursesViewModel extends ViewModel {
    private final GetSavedCourses getSavedCourses;

    @Inject
    SavedCoursesViewModel(GetSavedCourses getSavedCourses) {
        super();
        this.getSavedCourses = getSavedCourses;
    }

    public LiveData<List<Course>> getSavedCourses() {
        return LiveDataReactiveStreams.fromPublisher(getSavedCourses.execute()
                .onErrorReturnItem(Collections.emptyList()));
    }
}
