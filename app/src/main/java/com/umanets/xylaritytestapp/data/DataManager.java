package com.umanets.xylaritytestapp.data;

import com.umanets.xylaritytestapp.data.local.PreferencesHelper;
import com.umanets.xylaritytestapp.data.model.BackendResponse;
import com.umanets.xylaritytestapp.data.model.SyncEvent;
import com.umanets.xylaritytestapp.data.model.WordItem;
import com.umanets.xylaritytestapp.data.remote.ApiService;
import com.umanets.xylaritytestapp.util.RxEventBus;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


@Singleton
public class DataManager {

    private final ApiService mApiService;
    private final RxEventBus mBus;

    @Inject
    public DataManager(ApiService apiService, RxEventBus bus) {
        mApiService = apiService;
        mBus = bus;
    }



    public Observable<BackendResponse> syncWords() {
        return mApiService
                .getWords("android.test@xyrality.com", "password", "Nougat", "a813734fd1f63096")
                .doOnNext(new Action1<BackendResponse>() {
                    @Override
                    public void call(BackendResponse backendResponse) {
                        boolean success = backendResponse != null && backendResponse.allAvailableWorlds != null;
                        if(success){
                            items=backendResponse.allAvailableWorlds;
                        }
                        mBus.post(new SyncEvent(success));
                    }
                });
    }

    List<WordItem> items = null;

    public Observable<List<WordItem>> getWords() {
        if (items == null) {
            return Observable.empty();
        } else {
            return Observable.just(items);
        }
    }

}
