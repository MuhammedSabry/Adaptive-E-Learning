package com.eng.asu.adaptivelearning.dagger;


import com.eng.asu.adaptivelearning.LearningApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ViewModelModule.class})
public interface ApplicationComponents {

    void inject(LearningApplication application);
}
