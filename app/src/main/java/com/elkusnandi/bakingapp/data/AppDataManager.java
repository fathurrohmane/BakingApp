package com.elkusnandi.bakingapp.data;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Taruna 98 on 29/08/2017.
 */

public class AppDataManager {

    OkHttpClient mHttpClient;

    public AppDataManager() {
        mHttpClient = new OkHttpClient();
    }

    public Observable<Response> getRecipes() {

        return Observable.create(
                new ObservableOnSubscribe<Response>() {
                    @Override
                    public void subscribe(ObservableEmitter<Response> e) throws Exception {
//                        HttpUrl.Builder urlBuilder = HttpUrl.parse(api).newBuilder();
//                        String url = urlBuilder.build().toString();

                        Request request = new Request.Builder()
                                .url("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
                                .build();
                        Response response = mHttpClient.newCall(request).execute();

                        if (response.isSuccessful()) {
                            if (!e.isDisposed()) {
                                e.onNext(response);
                                e.onComplete();
                            }
                        } else {
                            if (!e.isDisposed()) {
                                e.onError(new Exception("Request recipice data error"));
                            }
                        }
                    }
                }
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }
}
