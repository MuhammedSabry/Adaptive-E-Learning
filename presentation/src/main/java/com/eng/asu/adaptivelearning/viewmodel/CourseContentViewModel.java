package com.eng.asu.adaptivelearning.viewmodel;

import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.FancyModel.FancyLecture;
import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.adaptivelearning.server.FancyModel.FancySection;
import com.eng.asu.adaptivelearning.domain.interactor.GetCourseById;
import com.eng.asu.adaptivelearning.domain.interactor.GetMediaFile;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
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

    public LiveData<FancyCourse> getCourseContent(long courseId) {
        return LiveDataReactiveStreams.fromPublisher(getCourseByIdInteractor.execute(courseId)
                .onErrorReturnItem(new FancyCourse())
                .doOnNext(course -> this.getFirstVideo(course.getSections())));
    }

    private void updateVideoData(FancyLecture lecture) {
        addDisposable(getMediaFileInteractor.execute(lecture.getLectureContentId())
                .subscribe(videoLiveData::postValue, error -> logError("Get media file", error)));
    }

    private void getFirstVideo(List<FancySection> sections) {
        addDisposable(Observable.fromIterable(sections)
                .flatMapIterable(FancySection::getFancyLectures)
                .filter(FancyLecture::getVideo)
                .firstOrError()
                .flatMap(lecture -> getMediaFileInteractor.execute(lecture.getLectureContentId()))
                .subscribe(videoLiveData::postValue,
                        error -> logError("Get First Video", error)));
    }

    public LiveData<FancyMediaFile> getVideoLiveData() {
        return videoLiveData;
    }

    public void onLectureClicked(FancyLecture lecture) {
        if (lecture.getVideo())
            updateVideoData(lecture);
        else if (lecture.getFile())
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
