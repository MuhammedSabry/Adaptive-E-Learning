package com.eng.asu.adaptivelearning.view.adapter;

import com.eng.asu.adaptivelearning.view.fragment.AboutFragment;
import com.eng.asu.adaptivelearning.view.fragment.HomeFragment;
import com.eng.asu.adaptivelearning.view.fragment.MyCoursesFragment;
import com.eng.asu.adaptivelearning.view.fragment.ParentalControlFragment;
import com.eng.asu.adaptivelearning.view.fragment.SavedCoursesFragment;
import com.eng.asu.adaptivelearning.view.fragment.SettingsFragment;
import com.eng.asu.adaptivelearning.view.fragment.TeacherDashboardFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new HomeFragment();
            case 2:
                return new MyCoursesFragment();
            case 3:
                return new ParentalControlFragment();
            case 4:
                return new TeacherDashboardFragment();
            case 5:
                return new SavedCoursesFragment();
            case 6:
                return new SettingsFragment();
            case 7:
                return new AboutFragment();
            default:
                return new HomeFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return "Home";
            case 2:
                return "My Courses";
            case 3:
                return "Parental Control";
            case 4:
                return "Dashboard";
            case 5:
                return "Saved Courses";
            case 6:
                return "Settings";
            case 7:
                return "About";
            default:
                return "Home";
        }
    }

    @Override
    public int getCount() {
        return 7;
    }
}
