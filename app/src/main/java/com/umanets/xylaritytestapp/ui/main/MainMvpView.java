package com.umanets.xylaritytestapp.ui.main;


import com.umanets.xylaritytestapp.data.model.WordItem;
import com.umanets.xylaritytestapp.ui.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {

    void showWords(List<WordItem> words);

    void showWordsEmpty();

    void showError();

}
