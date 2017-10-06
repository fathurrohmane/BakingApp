package com.elkusnandi.bakingapp.common;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Taruna 98 on 29/08/2017.
 */

public abstract class BasePresenter implements IPresenter {

    protected CompositeDisposable disposable
            = new CompositeDisposable();


}
