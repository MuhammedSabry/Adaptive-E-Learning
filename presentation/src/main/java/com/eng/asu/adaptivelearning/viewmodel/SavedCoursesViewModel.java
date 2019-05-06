package com.eng.asu.adaptivelearning.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.eng.asu.adaptivelearning.domain.interactor.GetSavedCourses;

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

    public LiveData<List<FancyCourse>> getSavedCourses() {
        return LiveDataReactiveStreams.fromPublisher(getSavedCourses.execute()
                .onErrorReturnItem(Collections.emptyList()));
    }
}
