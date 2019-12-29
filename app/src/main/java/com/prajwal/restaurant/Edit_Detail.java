package com.prajwal.restaurant;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.prajwal.restaurant.database.RestaurantDatabaseHelper;
import com.prajwal.restaurant.ui.gallery.BookingFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Edit_Detail extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    RestaurantDatabaseHelper restaurantDatabaseHelper;
    Cursor c1;
    BookingFragment bookingFragment = new BookingFragment();
    Intent i_edit;

    TextView txt,et1,et2,et3;
    FloatingActionButton f_edit;
    String id_s, mobile_S, seat_S;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = getIntent().getExtras();
        String d_name = bundle.getString("name");

        restaurantDatabaseHelper = new RestaurantDatabaseHelper(this);
        sqLiteDatabase = restaurantDatabaseHelper.getReadableDatabase();

        final String selection = RestaurantDatabaseHelper._id + "=?";
        String[] selectionArgs = new String[]{d_name};

        String[] projection_data = new String[]
                {
                        RestaurantDatabaseHelper.R_NAME,
                        RestaurantDatabaseHelper._id,
                        RestaurantDatabaseHelper.R_CONTACT,
                        RestaurantDatabaseHelper.R_SEATS
                };


        c1 = sqLiteDatabase.query
                (RestaurantDatabaseHelper.R_TABLE,
                        projection_data,
                        selection,
                        selectionArgs, null, null, null
                );

        txt = (TextView) findViewById(R.id.id_data);
        et1 = (TextView) findViewById(R.id.name_data);
        et2 = (TextView) findViewById(R.id.phone_data);
        et3 = (TextView) findViewById(R.id.seats_data);

        f_edit = findViewById(R.id.fab);
        f_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i_edit = new Intent(getApplicationContext(),Add_Detail.class);
                i_edit.putExtra("user_edit_id",id_s);
                Log.d("data_id","intent_id "+id_s);
                startActivity(i_edit);
            }
        });

        try {
            int column_index = c1.getColumnIndex(RestaurantDatabaseHelper._id);
            int column_name = c1.getColumnIndex(RestaurantDatabaseHelper.R_NAME);
            int column_contact = c1.getColumnIndex(RestaurantDatabaseHelper.R_CONTACT);
            int column_seats = c1.getColumnIndex(RestaurantDatabaseHelper.R_SEATS);

            while(c1.moveToNext()) {
                int current_id = c1.getInt(column_index);
                id_s = String.valueOf(current_id);
                String current_name = c1.getString(column_name);
                int current_contact = c1.getInt(column_contact);
                mobile_S = String.valueOf(current_contact);
                int current_seats = c1.getInt(column_seats);
                seat_S = String.valueOf(current_seats);

                //my changes

                txt.setText("ID : " + id_s);
                et1.setText(current_name);
                et2.setText(mobile_S);
                et3.setText(seat_S);
            }
        } catch (Exception e) {
            //txt.setText("HELLO");
            c1.close();
        }
    }
}

