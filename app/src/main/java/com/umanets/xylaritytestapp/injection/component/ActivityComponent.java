package com.umanets.xylaritytestapp.injection.component;


import com.umanets.xylaritytestapp.injection.PerActivity;
import com.umanets.xylaritytestapp.injection.module.ActivityModule;
import com.umanets.xylaritytestapp.ui.main.MainActivity;

import dagger.Subcomponent;


/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
