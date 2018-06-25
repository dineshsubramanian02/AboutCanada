package com.aboutcanada.network;


import com.aboutcanada.models.NewsModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Dinesh on 25/06/18.
 */

public interface NetworkInterface {
    @GET("facts.json")
    Observable<NewsModel> getRows();
}

