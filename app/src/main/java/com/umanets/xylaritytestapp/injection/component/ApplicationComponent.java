package com.umanets.xylaritytestapp.injection.component;

import android.app.Application;
import android.content.Context;

import com.umanets.xylaritytestapp.data.DataManager;
import com.umanets.xylaritytestapp.data.SyncService;
import com.umanets.xylaritytestapp.data.local.PreferencesHelper;
import com.umanets.xylaritytestapp.data.remote.ApiService;
import com.umanets.xylaritytestapp.injection.ApplicationContext;
import com.umanets.xylaritytestapp.injection.module.ApplicationModule;
import com.umanets.xylaritytestapp.util.RxEventBus;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext
    Context context();
    Application application();
    ApiService apiService();
    PreferencesHelper preferencesHelper();
    DataManager dataManager();
    RxEventBus eventBus();

}
