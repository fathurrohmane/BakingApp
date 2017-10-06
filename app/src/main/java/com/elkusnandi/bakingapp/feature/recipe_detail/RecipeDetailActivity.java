package com.elkusnandi.bakingapp.feature.recipe_detail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.elkusnandi.bakingapp.R;
import com.elkusnandi.bakingapp.common.FragmentDataListener;
import com.elkusnandi.bakingapp.data.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity implements FragmentDataListener {

    public static final int VIEW_TYPE_INGREDIENT = 0;
    public static final int VIEW_TYPE_STEP = 1;

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    private int viewType;
    private Recipe recipe;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar;

        viewType = getIntent().getIntExtra("view_type", 0);
        recipe = getIntent().getParcelableExtra("recipe");
        currentPosition = getIntent().getIntExtra("position", 0);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT || viewType == VIEW_TYPE_INGREDIENT) {
            super.onCreate(savedInstanceState);
            setUpUi();
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            setUpToolbar(actionBar, View.VISIBLE);

        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            super.onCreate(savedInstanceState);
            setUpUi();
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            setUpToolbar(actionBar, View.GONE);
        }

        if (savedInstanceState == null) {
            switch (viewType) {
                case VIEW_TYPE_INGREDIENT:
                    IngredientDetailFragment ingredientDetailFragment =
                            IngredientDetailFragment.newInstance(
                                    (ArrayList<? extends Parcelable>) recipe.getIngeredients()
                            );
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.recipe_detail_container, ingredientDetailFragment)
                            .commit();
                    break;
                case VIEW_TYPE_STEP:
                    boolean hasPrev = false, hasNext = false;
                    if (currentPosition > 0) {
                        hasPrev = true;
                    }
                    if (currentPosition < recipe.getSteps().size() - 1) {
                        hasNext = true;
                    }
                    RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(recipe.getSteps().get(currentPosition), currentPosition, hasPrev, hasNext);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.recipe_detail_container, fragment)
                            .commit();
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();// TODO: 04/10/2017 QUESTION is this correct? Because there a class like NavUtil but cant make it work
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpUi() {
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
    }

    private void setUpToolbar(ActionBar actionBar, int visibility) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (visibility == View.VISIBLE) {
                actionBar.show();
                appBarLayout.setVisibility(View.VISIBLE);

            } else if (visibility == View.GONE) {
                actionBar.hide();
                appBarLayout.setVisibility(View.GONE);
            }

            if (getResources().getBoolean(R.bool.is_tablet)) {
                actionBar.setTitle(recipe.getTitle());
            } else {
                if (currentPosition == -1) {
                    actionBar.setTitle(getString(R.string.recipe_list_ingredient));
                } else {
                    actionBar.setTitle(recipe.getSteps().get(currentPosition).getShortDescription());
                }
            }
        }

    }

    @Override
    public void onDataReceived(String action, Bundle data) {
        switch (action) {
            case RecipeDetailFragment.ACTION_NEXT:
                int nextPosition = data.getInt("nextPosition");
                boolean hasPrev = false, hasNext = false;
                if (nextPosition > 0) {
                    hasPrev = true;
                }
                if (nextPosition < recipe.getSteps().size() - 1) {
                    hasNext = true;
                }
                RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(recipe.getSteps().get(nextPosition), nextPosition, hasPrev, hasNext);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_detail_container, fragment)
                        .addToBackStack(null)
                        .commit();
                if (getSupportActionBar() != null) {
                    if (nextPosition == 0) {
                        getSupportActionBar().setTitle(getString(R.string.recipe_list_ingredient));
                    } else {
                        getSupportActionBar().setTitle(recipe.getSteps().get(nextPosition).getShortDescription());
                    }
                }
                break;
        }
    }
}
