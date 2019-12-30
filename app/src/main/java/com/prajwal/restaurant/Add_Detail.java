package com.prajwal.restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.prajwal.restaurant.database.RestaurantDatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.telephony.PhoneNumberUtils;
import android.text.InputType;
import android.text.Selection;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;

import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_AVAILABLE;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_CONTACT;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_NAME;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_SEATS;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_TABLE;

public class Add_Detail extends AppCompatActivity {
    EditText name, phone, seats;
    EditText available_seats;
    String name_text, phone_text, seats_text, available_text;
    RestaurantDatabaseHelper restaurantDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Bundle bundle;
    String user_id;
    boolean add;
    long rowInserted;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor_user;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

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
        available_seats = findViewById(R.id.available_seats);

        sharedPrefs = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        editor_user = sharedPrefs.edit();
        String email = sharedPrefs.getString("email","no email");

        Log.d("TAG","email is "+email);

        if("prajwal@intelehealth.io".equalsIgnoreCase(email))
        {

        }
        else
        {

        }
        String avai = sharedPrefs.getString("available","0");
        available_seats.setText(avai);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefs = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
                editor_user = sharedPrefs.edit();
                String email = sharedPrefs.getString("email","no email");

                Log.d("TAG","email is "+email);

                if("prajwal@intelehealth.io".equalsIgnoreCase(email) && name.getText().toString().equals("") && phone.getText().toString().equals("")
                        && seats.getText().toString().equals("") && !available_seats.getText().toString().equals("")  && !sharedPrefs.getString("available","").equalsIgnoreCase(available_seats.getText().toString()))
                {
                    editor_user.putString("available",available_seats.getText().toString());
                    editor_user.apply();
                    String avai = sharedPrefs.getString("available","no available");
                    finish();
                    Log.d("AVAI","AVAI: "+avai);
                    Toast.makeText(getApplicationContext(), "Edit Successful!", Toast.LENGTH_SHORT).show();
                    //insertData();
                }

                else if("prajwal@intelehealth.io".equalsIgnoreCase(email) && !name.getText().toString().equals("") && !phone.getText().toString().equals("")
                        && !seats.getText().toString().equals("") && !available_seats.getText().toString().equals(""))
                {
                    editor_user.putString("available",available_seats.getText().toString());
                    editor_user.apply();
                    String avai = sharedPrefs.getString("available","no available");
                    finish();
                    Log.d("AVAI","AVAI: "+avai);
                    //Toast.makeText(getApplicationContext(), "Edit Successful!", Toast.LENGTH_SHORT).show();
                    insertData();
                }

                else if("prajwal@intelehealth.io".equalsIgnoreCase(email) && name.getText().toString().equals("") && phone.getText().toString().equals("")
                        && seats.getText().toString().equals("") && !available_seats.getText().toString().equals("") && sharedPrefs.getString("available","").equalsIgnoreCase(available_seats.getText().toString()))
                {
                    finish();
                    //Log.d("AVAI","AVAI: "+avai);
                    Toast.makeText(getApplicationContext(), "Welcome Home", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    insertData();
                }
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
        //PhoneNumberUtils.formatNumber(phone_text);
        seats_text = seats.getText().toString();
        available_text = available_seats.getText().toString();

        restaurantDatabaseHelper = new RestaurantDatabaseHelper(this);
        sqLiteDatabase = restaurantDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(R_NAME, name_text);
        values.put(R_CONTACT, phone_text);
        values.put(R_SEATS, seats_text);
        values.put(R_AVAILABLE, available_text);


            if (!name.getText().toString().equals("") && !phone.getText().toString().equals("")
                && !seats.getText().toString().equals("") && !available_seats.getText().toString().equals("")) {

                int val1 = Integer.parseInt(seats.getText().toString());
                int val2 = Integer.parseInt(available_seats.getText().toString());

                if (val1 <= val2 && val2 != 0) {
                    rowInserted = -1;

                    if (bundle.containsKey("new")) {
                        rowInserted = sqLiteDatabase.insert(R_TABLE, null, values);
                        Intent intent = new Intent(Add_Detail.this, Booking_Fragment_Extends.class);
                        startActivity(intent);
                        finish(); //wont come back to the add form screen.
                    }

                    if (rowInserted != -1)
                        Toast.makeText(getApplicationContext(), "Booking Confirmed", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_LONG).show();

                    if (bundle.containsKey("user_edit_id")) {
                        //setscreen(user_id);
                        Log.d("id", "PResent_id" + user_id);
                        sqLiteDatabase.update(R_TABLE, values, "_id=" + user_id, null);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Seats Full", Toast.LENGTH_LONG).show();
                }
            }
            else {
                    if (name.getText().toString().equals("")) {
                        name.setError("Name is mandatory!");
                    }
                    if (phone.getText().toString().equals("")) {
                        phone.setError("Phone is mandatory!");
                    }
                    if (seats.getText().toString().equals("")) {
                        seats.setError("Seats is mandatory!");
                    }
                    if (available_seats.getText().toString().equals("")) {
                        seats.setError("Available seats cannot be empty!");
                    }

                    Toast.makeText(getApplicationContext(), "Please Enter Data", Toast.LENGTH_LONG).show();
                    //  et.setError( "First name is required!" );
                }

    }

    private void setscreen(String str) {
        RestaurantDatabaseHelper restaurantDatabaseHelper = new RestaurantDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = restaurantDatabaseHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(R_TABLE, new String[]{R_NAME,R_CONTACT,R_SEATS,R_AVAILABLE},
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // firebaseAuthLoggedUser = new FirebaseAuthLoggedUser();
        //Log.d("TAG","email is "+firebaseAuthLoggedUser.getEmail());
        SharedPreferences sharedPrefs = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        String email = sharedPrefs.getString("email","no email");

        Log.d("TAG","email is "+email);

        if("prajwal@intelehealth.io".equalsIgnoreCase(email))
        {
            getMenuInflater().inflate(R.menu.edit_menu, menu);
        }
        else
        {

        }
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {

            /*available_seats.setFocusable(true);
            available_seats.setEnabled(true);
            available_seats.setClickable(true);
            available_seats.setFocusableInTouchMode(true);*/
            available_seats.setEnabled(true);
            name.setEnabled(false);
            phone.setEnabled(false);
            seats.setEnabled(false);
            available_seats.requestFocus();
            available_seats.setFocusableInTouchMode(true);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(available_seats, InputMethodManager.SHOW_FORCED);
            //available_seats.setSelection(available_seats.getText().length());
            available_seats.setInputType(InputType.TYPE_CLASS_NUMBER);
            Selection.setSelection(available_seats.getText(),available_seats.getText().length());


           return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
