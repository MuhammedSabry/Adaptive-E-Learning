package com.eng.asu.adaptivelearning.dagger;

import android.content.Context;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.data.repository.UserRepository;
import com.eng.asu.adaptivelearning.data.service.UserService;

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
    UserService provideUserService() {
        return new UserRepository();
    }
}
