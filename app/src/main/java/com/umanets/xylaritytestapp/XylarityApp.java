package com.umanets.xylaritytestapp;

import android.app.Application;
import android.content.Context;

import com.umanets.xylaritytestapp.injection.component.ApplicationComponent;
import com.umanets.xylaritytestapp.injection.component.DaggerApplicationComponent;
import com.umanets.xylaritytestapp.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by ko3ak_zhn on 7/7/17.
 */

public class XylarityApp extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static XylarityApp get(Context context) {
        return (XylarityApp) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}