package com.eng.asu.adaptivelearning.dagger;


import com.eng.asu.adaptivelearning.viewmodel.UserViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponents {

    void inject(UserViewModel userViewModel);
}
