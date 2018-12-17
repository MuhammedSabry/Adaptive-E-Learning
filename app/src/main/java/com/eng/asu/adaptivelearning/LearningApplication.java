package com.eng.asu.adaptivelearning;

import android.app.Application;

import com.eng.asu.adaptivelearning.dagger.ApplicationComponents;
import com.eng.asu.adaptivelearning.dagger.ApplicationModule;
import com.eng.asu.adaptivelearning.dagger.DaggerApplicationComponents;

public class LearningApplication extends Application {

    private static ApplicationComponents applicationComponent;

    public static ApplicationComponents getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponents.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

}
