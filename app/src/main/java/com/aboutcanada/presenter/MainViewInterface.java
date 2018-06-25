package com.aboutcanada.presenter;

import com.aboutcanada.models.NewsModel;

/**
 * Created by Dinesh on 25/06/18.
 */

public interface MainViewInterface {
    void showToast(String s);
    void showProgressBar();
    void hideProgressBar();
    void displayNews(NewsModel newsResponse);
    void displayError(String s);
}

