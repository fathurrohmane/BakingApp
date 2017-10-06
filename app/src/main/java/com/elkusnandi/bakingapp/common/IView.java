package com.elkusnandi.bakingapp.common;

/**
 * Created by Taruna 98 on 29/08/2017.
 */

public interface IView {

    void showProgress();

    void hideProgress();

    void showNoData();

    void showNoResult();

    void showSnackBar(int type, String info);

    void showToast(int type, String info);

}
