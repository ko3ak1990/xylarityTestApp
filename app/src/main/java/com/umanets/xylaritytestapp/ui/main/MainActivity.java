package com.umanets.xylaritytestapp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.umanets.xylaritytestapp.R;
import com.umanets.xylaritytestapp.data.DataManager;
import com.umanets.xylaritytestapp.data.SyncService;
import com.umanets.xylaritytestapp.data.model.WordItem;
import com.umanets.xylaritytestapp.ui.base.BaseActivity;
import com.umanets.xylaritytestapp.util.DialogFactory;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class MainActivity extends BaseActivity implements MainMvpView {
    private final static String LIST_STATE_KEY = "recycler_list_state";
    Parcelable listState;
    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "com.umanets.xylaritytestapp.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    MainPresenter mMainPresenter;
    @Inject
    WordItemsAdapter mWordsAdapter;


    @Inject
    DataManager dataManager;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mWordsAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mMainPresenter.attachView(this);
        mMainPresenter.loadWords();
        if (savedInstanceState==null&&
                getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        listState = mLayoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, listState);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        // Retrieve list state and list/item positions
        if(state != null)
            listState = state.getParcelable(LIST_STATE_KEY);
    }



    /***** MVP View methods implementation *****/

    @Override
    public void showWords(List<WordItem> words) {
        mWordsAdapter.setWordItems(words);
        mWordsAdapter.notifyDataSetChanged();
        if (listState != null) {
            Timber.d("restoring recylcer state");
            mLayoutManager.onRestoreInstanceState(listState);
        }
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_words))
                .show();
    }

    @Override
    public void showWordsEmpty() {
        mWordsAdapter.setWordItems(Collections.<WordItem>emptyList());
        mWordsAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_words, Toast.LENGTH_LONG).show();
    }

}
