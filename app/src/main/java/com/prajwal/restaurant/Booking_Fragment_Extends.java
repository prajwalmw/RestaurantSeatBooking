package com.prajwal.restaurant;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.prajwal.restaurant.ui.gallery.BookingFragment;

public class Booking_Fragment_Extends extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.bar, new BookingFragment()).commit(); }
    }

}
