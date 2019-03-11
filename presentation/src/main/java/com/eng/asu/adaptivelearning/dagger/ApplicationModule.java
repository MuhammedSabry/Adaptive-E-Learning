package com.eng.asu.adaptivelearning.dagger;

import android.content.Context;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.data.NetworkRepository;
import com.eng.asu.adaptivelearning.domain.ClassroomService;
import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.UserStorage;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.executor.BackgroundThreadExecutor;
import com.eng.asu.adaptivelearning.executor.MainThreadExecutor;
import com.eng.asu.adaptivelearning.preferences.UserAccountStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final LearningApplication application;

    public ApplicationModule(LearningApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    BackgroundExecutionThread getBackgroundExecutionThread() {
        return new BackgroundThreadExecutor();
    }

    @Provides
    @Singleton
    PostExecutionThread getPostExecutionThread() {
        return new MainThreadExecutor();
    }

    @Provides
    @Singleton
    UserService getUserService(NetworkRepository networkRepository) {
        return networkRepository;
    }

    @Provides
    @Singleton
    ClassroomService getClassroomService(NetworkRepository networkRepository) {
        return networkRepository;
    }

    @Provides
    @Singleton
    CourseService getCourseService(NetworkRepository networkRepository) {
        return networkRepository;
    }

    @Provides
    @Singleton
    UserStorage getUserStorage(UserAccountStorage userAccountStorage) {
        return userAccountStorage;
    }
}
