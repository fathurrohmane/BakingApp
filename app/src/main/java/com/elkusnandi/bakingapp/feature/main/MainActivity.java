package com.elkusnandi.bakingapp.feature.main;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.elkusnandi.bakingapp.R;
import com.elkusnandi.bakingapp.adapter.RecipeAdapter;
import com.elkusnandi.bakingapp.common.BaseActivity;
import com.elkusnandi.bakingapp.common.RecyclerViewListener;
import com.elkusnandi.bakingapp.data.MySharedPreference;
import com.elkusnandi.bakingapp.data.model.Recipe;
import com.elkusnandi.bakingapp.feature.recipe_detail.RecipeListActivity;
import com.elkusnandi.bakingapp.ui.widget.RecipeWidget;
import com.elkusnandi.bakingapp.util.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View, RecyclerViewListener {

    @BindView(R.id.recyclerview_recipe)
    RecyclerView recyclerViewRecipe;

    @BindView(R.id.viewanimator)
    ViewAnimator viewAnimator;

    @BindView(R.id.button_reload)
    Button buttonReload;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    private CountingIdlingResource countingIdlingResource = new CountingIdlingResource("recipe_network_call");

    private MainPresenter presenter;
    private RecipeAdapter recipeAdapter;
    private boolean isPickingWidgetMode = false;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        int column = 1;
        if (getResources().getBoolean(R.bool.is_tablet)) {
            column = 2;
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            column *= 2;
        }

        gridLayoutManager = new GridLayoutManager(this, column);
        recyclerViewRecipe.setLayoutManager(gridLayoutManager);

        recipeAdapter = new RecipeAdapter(this, this);
        recyclerViewRecipe.setAdapter(recipeAdapter);

        countingIdlingResource = new CountingIdlingResource("recipe_network_call");

        presenter = new MainPresenter(this, countingIdlingResource);

        if (savedInstanceState == null) {
            presenter.onLoadRecipe();

        } else {
            recipeAdapter.setData(savedInstanceState.<Recipe>getParcelableArrayList("recipe"));
            if (gridLayoutManager != null) {
                gridLayoutManager.scrollToPosition(savedInstanceState.getInt("scrollPosition"));
            }

        }

        if (getIntent().hasExtra("pick_widget")) {
            isPickingWidgetMode = true;
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.pick_widget_title));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (recipeAdapter != null) {
            outState.putParcelableArrayList("recipe", recipeAdapter.getRecipes());
        }

        if (gridLayoutManager != null) {
            outState.putInt("scrollPosition", gridLayoutManager.findFirstVisibleItemPosition());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetached();
    }

    @Override
    public void onRecyclerViewItemClicked(View view, Bundle bundle) {
        Recipe recipe = bundle.getParcelable("recipe");
        Intent intent;

        if (isPickingWidgetMode) {
            int[] ids = AppWidgetManager.getInstance(
                    getApplication()).getAppWidgetIds(
                    new ComponentName(getApplication()
                            , RecipeWidget.class)
            );
            if (recipe != null) {
                MySharedPreference.setWidgetRecipe(this, recipe);
                intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                getApplicationContext().sendBroadcast(intent);
                showToast(0, getString(R.string.info_widget_updated, recipe.getTitle()));
            } else {
                showToast(0, getString(R.string.no_recipe));
            }

            onBackPressed();
        } else {
            intent = new Intent(this, RecipeListActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        }

    }

    @Override
    public void showError() {
        viewAnimator.setDisplayedChild(Constant.ERROR);
    }

    @Override
    public void showNoResult() {
        viewAnimator.setDisplayedChild(Constant.ERROR);
    }

    @Override
    public void showProgress() {
        viewAnimator.setDisplayedChild(Constant.LOADING);
    }

    @Override
    public void hideProgress() {
        viewAnimator.setDisplayedChild(Constant.CONTENT);
    }

    @Override
    public void showRecipie(List<Recipe> recipeList) {
        recipeAdapter.setData((ArrayList<Recipe>) recipeList);
    }

    @Override
    public void showSnackBar(int type, String info) {
        Snackbar.make(recyclerViewRecipe, info, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showToast(int type, String info) {
        Toast.makeText(this, info, Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.button_reload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_reload:
                presenter.onLoadRecipe();
                break;
        }
    }

    @VisibleForTesting
    public CountingIdlingResource getCountingIdlingResource() {
        return countingIdlingResource;
    }

}
