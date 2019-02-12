package com.eng.asu.adaptivelearning.dagger;

import com.eng.asu.adaptivelearning.viewmodel.AboutViewModel;
import com.eng.asu.adaptivelearning.viewmodel.CreateClassroomViewModel;
import com.eng.asu.adaptivelearning.viewmodel.HomeViewModel;
import com.eng.asu.adaptivelearning.viewmodel.LoginViewModel;
import com.eng.asu.adaptivelearning.viewmodel.MainViewModel;
import com.eng.asu.adaptivelearning.viewmodel.MyCoursesViewModel;
import com.eng.asu.adaptivelearning.viewmodel.ParentalControlViewModel;
import com.eng.asu.adaptivelearning.viewmodel.RegisterViewModel;
import com.eng.asu.adaptivelearning.viewmodel.SettingsViewModel;
import com.eng.asu.adaptivelearning.viewmodel.SplashViewModel;
import com.eng.asu.adaptivelearning.viewmodel.TeacherDashboardViewModel;
import com.eng.asu.adaptivelearning.viewmodel.ViewModelKey;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel provideLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel.class)
    abstract ViewModel provideRegisterViewModel(RegisterViewModel registerViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel provideSplashViewModel(SplashViewModel splashViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel provideMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CreateClassroomViewModel.class)
    abstract ViewModel provideCreateClassroomViewModel(CreateClassroomViewModel createClassroomViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel provideHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MyCoursesViewModel.class)
    abstract ViewModel provideMyCoursesViewModel(MyCoursesViewModel myCoursesViewModel);

    //TODO
    @Binds
    @IntoMap
    @ViewModelKey(ParentalControlViewModel.class)
    abstract ViewModel provideParentalControlViewModel(ParentalControlViewModel myCoursesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel.class)
    abstract ViewModel provideAboutViewModel(AboutViewModel myCoursesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    abstract ViewModel provideSettingsViewModel(SettingsViewModel myCoursesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TeacherDashboardViewModel.class)
    abstract ViewModel provideTeacherDashboardViewModel(TeacherDashboardViewModel myCoursesViewModel);
}