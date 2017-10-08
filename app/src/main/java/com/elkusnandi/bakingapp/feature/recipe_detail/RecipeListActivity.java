package com.elkusnandi.bakingapp.feature.recipe_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elkusnandi.bakingapp.R;
import com.elkusnandi.bakingapp.data.model.CookingStep;
import com.elkusnandi.bakingapp.data.model.Ingredient;
import com.elkusnandi.bakingapp.data.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipe = getIntent().getParcelableExtra("recipe");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(recipe.getTitle());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (getResources().getBoolean(R.bool.is_tablet)) {
            mTwoPane = true;
        }

        if (savedInstanceState == null && mTwoPane) {
            showIngredientFragment();
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(recipe));
    }

    private void showIngredientFragment() {
        IngredientDetailFragment fragment = IngredientDetailFragment.newInstance((ArrayList<Ingredient>) recipe.getIngeredients());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_container, fragment)
                .commit();
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int VIEW_INGREDIENTS = 0;
        private static final int VIEW_COOKING_STEPS = 1;

        private Recipe recipe;

        public SimpleItemRecyclerViewAdapter(Recipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);

            switch (viewType) {
                case VIEW_INGREDIENTS:
                    return new ViewHolderIngredients(view);
                case VIEW_COOKING_STEPS:
                    return new ViewHolderCookingSteps(view);
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            if (position == 0) {
                final ViewHolderIngredients holderIngredients = (ViewHolderIngredients) holder;
                holderIngredients.mContentView.setText(getString(R.string.recipe_list_item_content));
                holderIngredients.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTwoPane) {
                            showIngredientFragment();
                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, RecipeDetailActivity.class);
                            intent.putExtra("recipe", recipe);
                            intent.putExtra("currentPosition", position - 1);
                            intent.putExtra("view_type", VIEW_INGREDIENTS);
                            context.startActivity(intent);
                        }
                    }
                });
            } else {
                final ViewHolderCookingSteps holderCookingSteps = (ViewHolderCookingSteps) holder;
                final CookingStep cookingStep = recipe.getSteps().get(position - 1);
                holderCookingSteps.mContentView.setText((cookingStep.getId() + 1) + " " + (cookingStep.getShortDescription()));
                holderCookingSteps.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean hasPrev = false, hasNext = false;
                        if (mTwoPane) {
                            if (position > 0) {
                                hasPrev = true;
                            }
                            if (position < recipe.getSteps().size() - 1) {
                                hasNext = true;
                            }
                            RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(cookingStep, position - 1, hasPrev, hasNext);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.recipe_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, RecipeDetailActivity.class);
                            intent.putExtra("recipe", recipe);
                            intent.putExtra("position", position - 1);
                            intent.putExtra("view_type", VIEW_COOKING_STEPS);
                            context.startActivity(intent);
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return recipe.getSteps().size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return VIEW_INGREDIENTS;
            } else {
                return VIEW_COOKING_STEPS;
            }
        }

        public class ViewHolderIngredients extends RecyclerView.ViewHolder {
            public final View mView;

            @BindView(R.id.textview_id)
            public TextView mIdView;
            @BindView(R.id.textview_content)
            public TextView mContentView;

            public ViewHolderIngredients(View view) {
                super(view);
                mView = view;
                ButterKnife.bind(this, view);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }

        public class ViewHolderCookingSteps extends RecyclerView.ViewHolder {
            public final View mView;

            @BindView(R.id.textview_id)
            public TextView mIdView;
            @BindView(R.id.textview_content)
            public TextView mContentView;

            public ViewHolderCookingSteps(View view) {
                super(view);
                mView = view;
                ButterKnife.bind(this, view);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
