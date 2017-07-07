package com.umanets.xylaritytestapp.injection.module;

import android.app.Application;
import android.content.Context;

import com.umanets.xylaritytestapp.data.remote.ApiService;
import com.umanets.xylaritytestapp.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ApiService provideApiService() {
        return ApiService.Creator.newApiService();
    }

}
