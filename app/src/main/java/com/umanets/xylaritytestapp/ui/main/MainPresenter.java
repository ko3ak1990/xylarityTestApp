package com.umanets.xylaritytestapp.ui.main;

import com.umanets.xylaritytestapp.data.DataManager;
import com.umanets.xylaritytestapp.data.model.SyncEvent;
import com.umanets.xylaritytestapp.data.model.WordItem;
import com.umanets.xylaritytestapp.injection.ConfigPersistent;
import com.umanets.xylaritytestapp.ui.base.BasePresenter;
import com.umanets.xylaritytestapp.util.RxEventBus;
import com.umanets.xylaritytestapp.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private final RxEventBus mBus;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(DataManager dataManager, RxEventBus bus) {
        Timber.d("newInstance");
        mDataManager = dataManager;
        mBus = bus;
        dataManager.syncWords();
        mBus.filteredObservable(SyncEvent.class).subscribe(new Subscriber<SyncEvent>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SyncEvent syncEvent) {
               loadWords();
            }
        });
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadWords() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getWords()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<WordItem>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the words.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<WordItem> wordItems) {
                        if (wordItems.isEmpty()) {
                            getMvpView().showWordsEmpty();
                        } else {
                            getMvpView().showWords(wordItems);
                        }
                    }
                });
    }

}
