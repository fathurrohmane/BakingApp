package com.elkusnandi.bakingapp.feature.main;

import com.elkusnandi.bakingapp.common.BasePresenter;
import com.elkusnandi.bakingapp.common.IPresenter;
import com.elkusnandi.bakingapp.data.AppDataManager;
import com.elkusnandi.bakingapp.data.Recipe;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Taruna 98 on 29/08/2017.
 */

public class MainPresenter extends BasePresenter implements IPresenter {

    private MainContract.View mainView;

    private AppDataManager repository;

    public MainPresenter(MainContract.View mainView) {
        this.mainView = mainView;
        repository = AppDataManager.getInstance();
    }

    @Override
    public void onAttached() {

    }

    @Override
    public void onLoad() {
        mainView.showProgress();
        disposable.add(
                repository.getRecipes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<Recipe>>() {
                            @Override
                            public void onSuccess(List<Recipe> value) {
                                mainView.showRecipie(value);
                                mainView.hideProgress();
                            }

                            @Override
                            public void onError(Throwable e) {
                                mainView.hideProgress();
                                mainView.showToast(0, e.getMessage());
                            }
                        })
        );
    }

    @Override
    public void onDetached() {
        disposable.clear();
    }
}
