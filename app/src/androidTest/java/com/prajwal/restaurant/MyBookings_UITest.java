package com.prajwal.restaurant;

import android.database.Cursor;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.CursorMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.intent.Intents.*;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.*;
import static androidx.test.espresso.intent.matcher.IntentMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by Prajwal Waingankar
 * on 07-Jul-20.
 * Github: prajwalmw
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MyBookings_UITest {

/*
    @Rule
    public ActivityTestRule<Booking_Fragment_Extends> booking_fragment_extendsActivityTestRule =
            new ActivityTestRule<>(Booking_Fragment_Extends.class);
*/

    @Rule
    public IntentsTestRule<Booking_Fragment_Extends> booking_fragment_extendsIntentsTestRule =
            new IntentsTestRule<>(Booking_Fragment_Extends.class);


    @Test
    public void testmyui()
    {
        /*Espresso.onData(is(instanceOf(Cursor.class)),
                CursorMatchers.withRowString("R_NAME", is("HBHJ")));*/

       /* onData(CursorMatchers.withRowString("NAME", "hbhj"))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));*/

        onData(CursorMatchers.withRowString("NAME", "ggg"))
                .perform(click());

        intended(allOf(
                hasComponent(hasShortClassName(".Edit_Detail")),
                toPackage("com.prajwal.restaurant"),
                hasExtra("name", "2")));

        onView(withId(R.id.id_data))
                .check(ViewAssertions.matches(withText("ID : 2")));

        /*Espresso.onView(withId(R.id.call_manager))
                .perform(click());*/
    }
}
