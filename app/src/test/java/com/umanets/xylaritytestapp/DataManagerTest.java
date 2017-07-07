package com.umanets.xylaritytestapp;

import com.umanets.xylaritytestapp.data.DataManager;
import com.umanets.xylaritytestapp.data.model.BackendResponse;
import com.umanets.xylaritytestapp.data.model.SyncEvent;
import com.umanets.xylaritytestapp.data.model.WordItem;
import com.umanets.xylaritytestapp.data.remote.ApiService;
import com.umanets.xylaritytestapp.util.RxEventBus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static rx.Observable.error;

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitServices or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {
    @Mock
    RxEventBus mMockBus;

    @Mock
    ApiService mMockWordsService;
    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mMockWordsService,mMockBus);
    }

    @Test
    public void syncWordsEmitsValues() {
        List<WordItem> words = Arrays.asList(TestDataFactory.makeWord("w1","UA"),
                TestDataFactory.makeWord("w2","EN"));

        BackendResponse backendResponse =new BackendResponse();
        backendResponse.allAvailableWorlds=words;
        stubSyncWordsHelperCalls(backendResponse);
        TestSubscriber<BackendResponse> result = new TestSubscriber<>();
        mDataManager.syncWords().subscribe(result);
        result.assertNoErrors();
        verify(mMockBus).post(any(SyncEvent.class));
    }



    @Test
    public void syncWordsDoesNotCallDatabaseWhenApiFails() {
        when(mMockWordsService.getWords(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.<BackendResponse>error(new RuntimeException()));

        mDataManager.syncWords().subscribe(new TestSubscriber<BackendResponse>());
        // Verify right calls to helper methods
        verify(mMockWordsService).getWords(anyString(), anyString(), anyString(), anyString());
    }

    private void stubSyncWordsHelperCalls(BackendResponse words) {
        // Stub calls to the word service and database helper.
        when(mMockWordsService.getWords(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(words));

    }

}
