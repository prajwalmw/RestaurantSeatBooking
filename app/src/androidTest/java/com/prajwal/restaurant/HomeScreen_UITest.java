package com.prajwal.restaurant;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;

/**
 * Created by Prajwal Waingankar
 * on 23-Jun-20.
 * Github: prajwalmw
 */

@RunWith(AndroidJUnit4.class)
public class HomeScreen_UITest {
/*    1. ViewMatcher
    2. ViewAction
    3. ViewAssertion */

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

/*    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.CALL_PHONE",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE");*/

    @Test
    public void HomeScreen_UI_Testing()
    {
        Espresso.onView(withId(R.id.image_resto))
                .check(ViewAssertions.matches(ViewMatchers.withContentDescription("drawable/niki")));

        Espresso.onView(withId(R.id.text_description))
                .check(matches(ViewMatchers.withText("Always on time!\n\nExpect the best experience from us. \nThe app is designed to reduce your waiting time and provide you the best experience")));

        Espresso.onView(withId(R.id.button_book))
                .perform(click());
    }
}
