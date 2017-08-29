package com.elkusnandi.bakingapp.feature.main;

import java.util.List;

/**
 * Created by Taruna 98 on 28/08/2017.
 */

public interface MainContract {

    interface View {
        void showLoading();

        void hideLoading();

        void loadData(List<?> data);

        void showError();

        void showNoData();
    }

    interface Presenter {
        void loadData();

    }

}
