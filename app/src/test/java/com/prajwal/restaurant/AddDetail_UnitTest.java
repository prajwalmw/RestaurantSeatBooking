package com.prajwal.restaurant;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Created by Prajwal Waingankar
 * on 09-Jul-20.
 * Github: prajwalmw
 */

@RunWith(MockitoJUnitRunner.class)
public class AddDetail_UnitTest {
    Add_Detail add_detail;

    @Mock
    SharedPreferences sharedPreferences;

    @Mock
    SharedPreferences.Editor editor;

    @Mock
    Context context;


    @Before
    public void setup()
    {
        add_detail = new Add_Detail();
    }

    @Test
    public void validation_testing()
    {
       when(sharedPreferences.getString(anyString(), anyString())).thenReturn("prajwalwaingankar@gmail.com");
        assertTrue(sharedPreferences.getString("email", "no email").equals("prajwalwaingankar@gmail.com"));
    }
}
