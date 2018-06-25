package com.aboutcanada.presenter;

import android.util.Log;

import com.aboutcanada.models.NewsModel;
import com.aboutcanada.network.NetworkClient;
import com.aboutcanada.network.NetworkInterface;
import com.aboutcanada.view.activity.R;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Dinesh on 25/06/18.
 */

public class MainPresenter implements MainPresenterInterface {
    private String TAG = MainPresenter.this.getClass().getName();
    private MainViewInterface mvi;

    public MainPresenter(MainViewInterface mvi) {
        this.mvi = mvi;
    }

    @Override
    public void getRows() {
        getObservable().subscribeWith(getObserver());
    }

    public Observable<NewsModel> getObservable(){
        return NetworkClient.getRetrofitService().create(NetworkInterface.class)
                .getRows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<NewsModel> getObserver(){
        return new DisposableObserver<NewsModel>() {

            @Override
            public void onNext(@NonNull NewsModel newsModel) {
                Log.d(TAG,"OnNext"+ newsModel.getRows());
                mvi.displayNews(newsModel);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                mvi.hideProgressBar();
                mvi.displayError(""+ R.string.server_error_message);
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
                mvi.hideProgressBar();
            }
        };
    }
}
