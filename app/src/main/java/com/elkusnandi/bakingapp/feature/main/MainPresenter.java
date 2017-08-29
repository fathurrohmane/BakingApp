package com.elkusnandi.bakingapp.feature.main;

import com.elkusnandi.bakingapp.common.BasePresenter;
import com.elkusnandi.bakingapp.common.IPresenter;

/**
 * Created by Taruna 98 on 29/08/2017.
 */

public class MainPresenter extends BasePresenter implements IPresenter {

    private MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onAttached() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onDetached() {

    }
}
