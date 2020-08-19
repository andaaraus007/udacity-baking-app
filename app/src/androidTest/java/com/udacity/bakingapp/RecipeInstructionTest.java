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
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RecipeInstructionTest {
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
    public void NutellaPieRecipeInstructionTest() {
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        if (!mIsTablet) {
            onView(withId(R.id.list_instructions))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.instruction_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Starting prep")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Prep the cookie crust.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Press the crust into baking form.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Start filling prep")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Finish filling prep")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Finishing Steps")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(not(isDisplayed())));

            onView(withId(R.id.iv_back_button)).perform(click());
            onView(withText("Finish filling prep")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void BrowniesRecipeInstructionTest() {
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        if (!mIsTablet) {
            onView(withId(R.id.list_instructions))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.instruction_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Starting prep")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Melt butter and bittersweet chocolate.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Add sugars to wet mixture.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Mix together dry ingredients.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Add eggs.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Add dry mixture to wet mixture.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Add batter to pan.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Remove pan from oven.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Cut and serve.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(not(isDisplayed())));

            onView(withId(R.id.iv_back_button)).perform(click());
            onView(withText("Remove pan from oven.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void YellowCakeRecipeInstructionTest() {
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        if (!mIsTablet) {
            onView(withId(R.id.list_instructions))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.instruction_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Starting prep")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Combine dry ingredients.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Prepare wet ingredients.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Add butter and milk to dry ingredients.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Add egg mixture to batter.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Pour batter into pans.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Begin making buttercream.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Prepare egg whites.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Beat egg whites to stiff peaks.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Add butter to egg white mixture.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Finish buttercream icing.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Frost cakes.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(not(isDisplayed())));

            onView(withId(R.id.iv_back_button)).perform(click());
            onView(withText("Finish buttercream icing.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void CheesecakeRecipeInstructionTest() {
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        if (!mIsTablet) {
            onView(withId(R.id.list_instructions))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.instruction_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Starting prep.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Prep the cookie crust.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Start water bath.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Prebake cookie crust. ")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Mix cream cheese and dry ingredients.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Add eggs.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Add heavy cream and vanilla.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Pour batter in pan.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Bake the cheesecake.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Turn off oven and leave cake in.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Remove from oven and cool at room temperature.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));

            onView(withId(R.id.iv_next_button)).perform(click());
            onView(withText("Final cooling and set.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(not(isDisplayed())));

            onView(withId(R.id.iv_back_button)).perform(click());
            onView(withText("Remove from oven and cool at room temperature.")).check(matches(isDisplayed()));
            onView(withId(R.id.video_player_container)).check(matches(not(isDisplayed())));
            onView(withId(R.id.iv_back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.iv_next_button)).check(matches(isDisplayed()));
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
