package com.umanets.xylaritytestapp;

import com.umanets.xylaritytestapp.data.DataManager;
import com.umanets.xylaritytestapp.data.model.SyncEvent;
import com.umanets.xylaritytestapp.data.model.WordItem;
import com.umanets.xylaritytestapp.ui.main.MainMvpView;
import com.umanets.xylaritytestapp.ui.main.MainPresenter;
import com.umanets.xylaritytestapp.util.RxEventBus;
import com.umanets.xylaritytestapp.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.observers.TestSubscriber;


import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {
    @Mock
    RxEventBus mMockBus;
    @Mock
    MainMvpView mMockMainMvpView;
    @Mock
    DataManager mMockDataManager;
    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();
    TestSubscriber<SyncEvent> testSubscriber;
    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter(mMockDataManager,mMockBus);
        testSubscriber = new TestSubscriber<>();
        when(mMockBus.filteredObservable(SyncEvent.class)).thenReturn(Observable.<SyncEvent>empty());
        mMainPresenter.attachView(mMockMainMvpView);
        verify(mMockDataManager).syncWords();


    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void loadWordsReturnsWords() {
        List<WordItem> wordItems = TestDataFactory.makeListWords(10);
        when(mMockDataManager.getWords())
                .thenReturn(Observable.just(wordItems));

        mMainPresenter.loadWords();
        verify(mMockMainMvpView).showWords(wordItems);
        verify(mMockMainMvpView, never()).showWordsEmpty();
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadWordsReturnsEmptyList() {
        when(mMockDataManager.getWords())
                .thenReturn(Observable.just(Collections.<WordItem>emptyList()));

        mMainPresenter.loadWords();
        verify(mMockMainMvpView).showWordsEmpty();
        verify(mMockMainMvpView, never()).showWords(ArgumentMatchers.<WordItem>anyList());
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadWordsFails() {
        when(mMockDataManager.getWords())
                .thenReturn(Observable.<List<WordItem>>error(new RuntimeException()));

        mMainPresenter.loadWords();
        verify(mMockMainMvpView).showError();
        verify(mMockMainMvpView, never()).showWordsEmpty();
        verify(mMockMainMvpView, never()).showWords(ArgumentMatchers.<WordItem>anyList());
    }

}
