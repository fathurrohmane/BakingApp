package com.elkusnandi.bakingapp.feature.main;

import android.support.annotation.Nullable;
import android.support.test.espresso.idling.CountingIdlingResource;

import com.elkusnandi.bakingapp.common.BasePresenter;
import com.elkusnandi.bakingapp.data.AppDataManager;
import com.elkusnandi.bakingapp.data.model.Recipe;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Taruna 98 on 29/08/2017.
 */

public class MainPresenter extends BasePresenter implements MainContract.Presenter {

    private MainContract.View mainView;
    private AppDataManager repository;
    private CountingIdlingResource countingIdlingResource;


    public MainPresenter(MainContract.View mainView, @Nullable CountingIdlingResource countingIdlingResource) {
        this.mainView = mainView;
        repository = AppDataManager.getInstance();
        this.countingIdlingResource = countingIdlingResource;
    }

    @Override
    public void onAttached() {

    }

    @Override
    public void onLoadRecipe() {
        mainView.showProgress();
        if (countingIdlingResource != null) {
            countingIdlingResource.increment();
        }
        disposable.add(
                repository.getRecipes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<Recipe>>() {
                            @Override
                            public void onSuccess(List<Recipe> value) {
                                mainView.showRecipie(value);
                                mainView.hideProgress();
                                if (countingIdlingResource != null) {
                                    countingIdlingResource.decrement();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                mainView.hideProgress();
                                mainView.showError();
                                mainView.showToast(0, e.getMessage());
                                if (countingIdlingResource != null) {
                                    countingIdlingResource.decrement();
                                }
                            }
                        })
        );
    }

    @Override
    public void onDetached() {
        disposable.clear();
    }

}
