package com.eng.asu.adaptivelearning;

import android.app.Application;

import com.eng.asu.adaptivelearning.dagger.ApplicationComponents;
import com.eng.asu.adaptivelearning.dagger.ApplicationModule;
import com.eng.asu.adaptivelearning.dagger.DaggerApplicationComponents;
import com.eng.asu.adaptivelearning.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import es.dmoral.toasty.Toasty;

public class LearningApplication extends Application {

    private static ViewModelFactory viewModelFactory;

    public static ViewModelProvider.Factory getViewModelFactory() {
        return viewModelFactory;
    }

    @Inject
    void setViewModelFactory(ViewModelFactory viewModelFactory) {
        LearningApplication.viewModelFactory = viewModelFactory;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationComponents applicationComponent = DaggerApplicationComponents.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);

        Toasty.Config.getInstance().allowQueue(false).apply();
    }
}
