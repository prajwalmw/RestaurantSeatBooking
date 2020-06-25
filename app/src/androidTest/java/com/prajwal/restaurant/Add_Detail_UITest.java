package com.prajwal.restaurant;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;

/**
 * Created by Prajwal Waingankar
 * on 25-Jun-20.
 * Github: prajwalmw
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Add_Detail_UITest {
    private String available_seats, seats, time;
    private String phone;
    private String name;

    @Rule
    public ActivityTestRule<Add_Detail> add_detailActivityTestRule =
            new ActivityTestRule<>(Add_Detail.class);

    @Before
    public void inital_setup()
    {
         available_seats = "200";
         name = "Prajwal Waingankar";
         seats = "2";
         phone = "7304154312";
         time = "25-06-2020 08:49 PM";
    }

    @Test
    public void Add_Details_TestUI()
    {

        Espresso.onView(withId(R.id.name_edit))
                .perform(replaceText(name), closeSoftKeyboard())
                .check(matches(withText(name)));

        Espresso.onView(withId(R.id.phone_edit))
                .perform(replaceText(phone), closeSoftKeyboard())
                .check(matches(withText(phone)));

        Espresso.onView(withId(R.id.seats_edit))
                .perform(replaceText(seats), closeSoftKeyboard())
                .check(matches(withText(seats)));

        Espresso.onView(withId(R.id.in_time_edit))
                .perform(replaceText(time), closeSoftKeyboard())
                .check(matches(withText(time)));

        Espresso.onView(withId(R.id.available_seats))
                .perform(replaceText(available_seats), closeSoftKeyboard())
                .check(matches(withText(available_seats)));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Espresso.onView(withId(R.id.fab))
                .perform(click());
    }

}
