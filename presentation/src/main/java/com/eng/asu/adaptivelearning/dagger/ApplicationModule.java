package com.eng.asu.adaptivelearning.dagger;

import android.content.Context;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.data.NetworkRepository;
import com.eng.asu.adaptivelearning.domain.ClassroomService;
import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;
import com.eng.asu.adaptivelearning.domain.interactor.usecase.PostExecutionThread;
import com.eng.asu.adaptivelearning.executor.BackgroundThreadExecutor;
import com.eng.asu.adaptivelearning.executor.MainThreadExecutor;

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
    UserService getUserService() {
        return new NetworkRepository();
    }

    @Provides
    @Singleton
    ClassroomService getClassroomService() {
        return new NetworkRepository();
    }

    @Provides
    @Singleton
    CourseService getCourseService() {
        return new NetworkRepository();
    }
}
