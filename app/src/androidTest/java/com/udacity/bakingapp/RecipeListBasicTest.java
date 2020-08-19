package com.udacity.bakingapp;


import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.udacity.bakingapp.activity.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions.checkNotNull;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeListBasicTest {
    private boolean mIsTablet;

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setIsTablet() {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        targetContext.getResources().getBoolean(R.bool.tablet);
        mIsTablet = targetContext.getResources().getBoolean(R.bool.tablet);
    }

    @Test
    public void nutellaPieTest() {
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
        if (mIsTablet) {
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
            onView(withId(R.id.instructions_container)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
            onView(withId(R.id.instructions_container)).check(matches(isDisplayed()));
            onView(withId(R.id.tv_ingredients)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void BrowniesTest() {
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withText("Brownies")).check(matches(isDisplayed()));
        if (mIsTablet) {
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
            onView(withId(R.id.instructions_container)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
            onView(withId(R.id.instructions_container)).check(matches(isDisplayed()));
            onView(withId(R.id.tv_ingredients)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void YellowCakeTest() {
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withText("Yellow Cake")).check(matches(isDisplayed()));
        if (mIsTablet) {
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
            onView(withId(R.id.instructions_container)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
            onView(withId(R.id.instructions_container)).check(matches(isDisplayed()));
            onView(withId(R.id.tv_ingredients)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void CheesecakeTest() {
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withText("Cheesecake")).check(matches(isDisplayed()));
        if (mIsTablet) {
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
            onView(withId(R.id.instructions_container)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
            onView(withId(R.id.instructions_container)).check(matches(isDisplayed()));
            onView(withId(R.id.tv_ingredients)).check(matches(isDisplayed()));
        }
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}
