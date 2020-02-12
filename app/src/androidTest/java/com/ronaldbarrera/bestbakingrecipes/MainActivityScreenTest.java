package com.ronaldbarrera.bestbakingrecipes;


import android.widget.TextView;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.ronaldbarrera.bestbakingrecipes.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    public static final String RECIPE_NAME = "Brownies";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecyclerViewItem_OpensRecipeActivity() {

        // recyclerview item and clicks it.
        onView(withId(R.id.recipes_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Checks that the OrderActivity opens with the correct tea name displayed
        //onView(withId(R.id.tea_name_text_view)).check(matches(withText(TEA_NAME)));
        onView(allOf(isDescendantOfA(withResourceName("action_bar")), withText(RECIPE_NAME)))
                .check(matches(isDisplayed()));

    }
}
