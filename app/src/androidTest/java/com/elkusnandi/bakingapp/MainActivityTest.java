package com.elkusnandi.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.elkusnandi.bakingapp.feature.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Taruna 98 on 03/10/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        idlingResource = mainActivityRule.getActivity().getCountingIdlingResource();
        Espresso.registerIdlingResources(idlingResource);// TODO: 08/10/2017 INFO its depricated. Not sure with the alternative
    }

    @Test
    public void recyclerviewDataIsLoadedTest() {

        onView(withText("Nutella Pie")).check(matches(isDisplayed()));// TODO: 08/10/2017 Question Since the data is static I asumly this is safe to do, because if data is loaded Nutella Pie will always displayed. Or there is something better to check the data is loaded to recycler view
    }


}
