package com.elkusnandi.bakingapp;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.elkusnandi.bakingapp.data.model.CookingStep;
import com.elkusnandi.bakingapp.data.model.Ingredient;
import com.elkusnandi.bakingapp.data.model.Recipe;
import com.elkusnandi.bakingapp.feature.recipe_detail.RecipeListActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by Taruna 98 on 09/10/2017.
 */

public class RecipeListTabletTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> recipeListActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class, false, false);

    private static Intent getIntent() {
        Recipe recipe = new Recipe(0, "Nutella", 8, "");
        recipe.addIngeredients(new Ingredient("ingredient_a", 2, "spoon"));
        recipe.addIngeredients(new Ingredient("ingredient_b", 5, "gram"));
        recipe.addIngeredients(new Ingredient("ingredient_c", 7, "kg"));
        recipe.addSteps(new CookingStep(0, "step_1", "do_step_1", "", ""));
        recipe.addSteps(new CookingStep(1, "step_2", "do_step_2", "", ""));
        recipe.addSteps(new CookingStep(2, "step_3", "do_step_3", "", ""));
        recipe.addSteps(new CookingStep(3, "step_4", "do_step_4", "", ""));

        Intent intent = new Intent();
        intent.putExtra("recipe", recipe);
        return intent;
    }

    private static ViewInteraction matchToolbarTitle(
            CharSequence title) {
        return onView(
                allOf(
                        isAssignableFrom(TextView.class),
                        withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(title.toString())));
    }

    @Test
    public void openRecipeIngredientsList() {

        recipeListActivityTestRule.launchActivity(getIntent());

        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recyclerview_ingredients)).check(matches(isDisplayingAtLeast(2)));
    }

    @Test
    public void openRecipeStepList() {

        recipeListActivityTestRule.launchActivity(getIntent());

        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.viewanimator_player)).check(matches(isDisplayed()));
    }

    @Test
    public void nextButtonHiddenOnStepsFragmentTabletTest() {
        recipeListActivityTestRule.launchActivity(getIntent());

        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.button_next)).check(matches(not(isDisplayed())));
    }

    @Test
    public void prevButtonHiddenOnStepsFragmentTabletTest() {
        recipeListActivityTestRule.launchActivity(getIntent());
        //recipeListActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.button_prev)).check(matches(not(isDisplayed())));
    }

    @Test
    public void toolbarTitleIsNotChangingWhenIngredientsClickedTest() {
        recipeListActivityTestRule.launchActivity(getIntent());

        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        matchToolbarTitle("Nutella");
    }

    @Test
    public void toolbarTitleIsNotChangingWhenStepsTest() {
        recipeListActivityTestRule.launchActivity(getIntent());

        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        matchToolbarTitle("Nutella");
    }

}
