package com.elkusnandi.bakingapp.feature.main;

import com.elkusnandi.bakingapp.common.IPresenter;
import com.elkusnandi.bakingapp.common.IView;
import com.elkusnandi.bakingapp.data.model.Recipe;

import java.util.List;

/**
 * Created by Taruna 98 on 28/08/2017.
 */

public interface MainContract {

    interface View extends IView {
        void showRecipie(List<Recipe> recipeList);
    }

    interface Presenter extends IPresenter {
        void onLoadRecipe();
    }

}
