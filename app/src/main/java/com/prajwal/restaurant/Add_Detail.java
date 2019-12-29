package com.prajwal.restaurant;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prajwal.restaurant.database.RestaurantDatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_CONTACT;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_NAME;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_SEATS;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_TABLE;

public class Add_Detail extends AppCompatActivity {
    EditText name, phone, seats;
    TextView available_count;
    String name_text, phone_text, seats_text;
    RestaurantDatabaseHelper restaurantDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Bundle bundle;
    String user_id;
    boolean add;
    long rowInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle = getIntent().getExtras();
        user_id = bundle.getString("user_edit_id", null);
        add = bundle.getBoolean("new");

        name = findViewById(R.id.name_edit);
        phone = findViewById(R.id.phone_edit);
        seats = findViewById(R.id.seats_edit);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                //Intent intent = new Intent(Add_Detail.this, Booking_Fragment_Extends.class);
                //startActivity(intent);
            }
        });

        if (bundle.containsKey("user_edit_id")) {
            setscreen(user_id);
        }
    }

    public void insertData() {
        name_text = name.getText().toString();
        phone_text = phone.getText().toString();
        PhoneNumberUtils.formatNumber(phone_text);
        seats_text = seats.getText().toString();

        restaurantDatabaseHelper = new RestaurantDatabaseHelper(this);
        sqLiteDatabase = restaurantDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(R_NAME, name_text);
        values.put(R_CONTACT, phone_text);
        values.put(R_SEATS, seats_text);

        if (!name.getText().toString().equals("") && !phone.getText().toString().equals("")
                && !seats.getText().toString().equals("")) {
            if (bundle.containsKey("new")) {
                rowInserted = sqLiteDatabase.insert(R_TABLE, null, values);
                Intent intent = new Intent(Add_Detail.this, Booking_Fragment_Extends.class);
                startActivity(intent);
            }

            if (rowInserted != -1)
                Toast.makeText(getApplicationContext(), "Patient Added ", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_LONG).show();

            if (bundle.containsKey("user_edit_id")) {
                //setscreen(user_id);
                Log.d("id", "PResent_id" + user_id);
                sqLiteDatabase.update(R_TABLE, values, "_id=" + user_id, null);
                finish();
            }
        } else {
            if (name.getText().toString().equals("")) {
                name.setError("Name is mandatory!");
            }
            if (phone.getText().toString().equals("")) {
                phone.setError("Phone is mandatory!");
            }
            if (seats.getText().toString().equals("")) {
                seats.setError("Seats is mandatory!");
            }

            Toast.makeText(getApplicationContext(), "Please Enter Data", Toast.LENGTH_LONG).show();
            //  et.setError( "First name is required!" );
        }
    }

    private void setscreen(String str) {
        RestaurantDatabaseHelper restaurantDatabaseHelper = new RestaurantDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = restaurantDatabaseHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(R_TABLE, new String[]{R_NAME,R_CONTACT,R_SEATS},
                RestaurantDatabaseHelper._id+"=?", new String[]{str}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                name.setText(cursor.getString(cursor.getColumnIndexOrThrow(R_NAME)));
                phone.setText(cursor.getString(cursor.getColumnIndexOrThrow(R_CONTACT)));
                seats.setText(cursor.getString(cursor.getColumnIndexOrThrow(R_SEATS)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
    }
}
