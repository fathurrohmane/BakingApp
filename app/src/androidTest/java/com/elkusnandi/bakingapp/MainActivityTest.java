package com.elkusnandi.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.elkusnandi.bakingapp.feature.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 *
 * Created by Taruna 98 on 03/10/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource idlingResource;

    private static ViewInteraction matchToolbarTitle(
            CharSequence title) {
        return onView(
                allOf(
                        isAssignableFrom(TextView.class),
                        withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(title.toString())));
    }

    @Before
    public void registerIdlingResource() {
        idlingResource = mainActivityRule.getActivity().getCountingIdlingResource();
        Espresso.registerIdlingResources(idlingResource);// TODO: 08/10/2017 INFO its depricated. Not sure with the alternative
    }

    @Test
    public void recyclerviewDataIsLoadedTest() {

        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
    }

    @Test
    public void goToDetailActivityTest() {

        onView(withId(R.id.recyclerview_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        matchToolbarTitle("Nutella Pie");
    }
}
