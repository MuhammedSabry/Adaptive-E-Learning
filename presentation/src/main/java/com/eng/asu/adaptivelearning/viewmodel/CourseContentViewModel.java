package com.eng.asu.adaptivelearning.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;

import com.adaptivelearning.server.FancyModel.FancyLecture;
import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.adaptivelearning.server.FancyModel.FancySection;
import com.eng.asu.adaptivelearning.domain.interactor.GetCourseById;
import com.eng.asu.adaptivelearning.domain.interactor.GetMediaFile;
import com.eng.asu.adaptivelearning.domain.model.Course;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CourseContentViewModel extends BaseViewModel {

    private final GetCourseById getCourseByIdInteractor;
    private final GetMediaFile getMediaFileInteractor;
    private final MutableLiveData<FancyMediaFile> videoLiveData;
    private final MutableLiveData<FancyMediaFile> filesLiveData;

    @Inject
    CourseContentViewModel(GetCourseById getCourseByIdInteractor, GetMediaFile getMediaFileInteractor) {
        this.getCourseByIdInteractor = getCourseByIdInteractor;
        this.getMediaFileInteractor = getMediaFileInteractor;
        this.videoLiveData = new MutableLiveData<>();
        this.filesLiveData = new MutableLiveData<>();
    }

    public LiveData<Course> getCourseContent(long courseId) {
        return LiveDataReactiveStreams.fromPublisher(getCourseByIdInteractor.execute(courseId)
                .onErrorReturnItem(new Course())
                .doOnNext(course -> this.getFirstVideo(course.getSections())));
    }

    private void updateVideoData(FancyLecture lecture) {
        addDisposable(getMediaFileInteractor.execute(lecture.getLectureContentId())
                .subscribe(videoLiveData::postValue,
                        error -> logError("Get media file", error)));
    }

    private void getFirstVideo(List<FancySection> sections) {
        if (sections != null && !sections.isEmpty())
            addDisposable(Observable.fromIterable(sections)
                    .flatMapIterable(FancySection::getFancyLectures)
                    .filter(FancyLecture::isVideo)
                    .firstOrError()
                    .flatMap(lecture -> getMediaFileInteractor.execute(lecture.getLectureContentId()))
                    .subscribe(videoLiveData::postValue,
                            error -> logError("Get First Video", error)));
    }

    public LiveData<FancyMediaFile> getVideoLiveData() {
        return videoLiveData;
    }

    public void onLectureClicked(FancyLecture lecture) {
        if (lecture.isVideo())
            updateVideoData(lecture);
        else if (lecture.isFile())
            updateFileLiveData(lecture);
    }

    public MutableLiveData<FancyMediaFile> getFilesLiveData() {
        return filesLiveData;
    }

    private void updateFileLiveData(FancyLecture lecture) {
        addDisposable(getMediaFileInteractor.execute(lecture.getLectureContentId())
                .subscribe(filesLiveData::postValue,
                        error -> logError("Update File LiveData", error)));
    }
}
