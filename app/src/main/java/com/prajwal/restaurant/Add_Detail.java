package com.prajwal.restaurant;

import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_AVAILABLE;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_CONTACT;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_IN_TIME;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_LOGIN_EMAIL;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_NAME;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_SEATS;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_TABLE;

public class Add_Detail extends AppCompatActivity {
    EditText name, phone, seats, in_time;
    EditText available_seats;
    String name_text, phone_text, seats_text, available_text, in_time_text;
    RestaurantDatabaseHelper restaurantDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Bundle bundle;
    String user_id;
    boolean add;
    long rowInserted;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor_user;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    static final int TIME_PICKER_ID = 2222;
    Calendar c;
    private int year;
    private int month;
    private int day;
    int hour,minute;
    Date testDate1;


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
        in_time = findViewById(R.id.in_time_edit);

        sharedPrefs = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        editor_user = sharedPrefs.edit();
        String email = sharedPrefs.getString("email","no email");

        //calendar
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);          //Date
        day = c.get(Calendar.DAY_OF_MONTH);
        c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        //Time
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        //Time end

        in_time.setFocusable(false);
        in_time.setInputType(InputType.TYPE_NULL);
        in_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimePickerDialog dia = new TimePickerDialog
                        (Add_Detail.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute_new) {
                                hour = hourOfDay;
                                minute = minute_new;

                                year = c.get(Calendar.YEAR);
                                month = c.get(Calendar.MONTH);          //Date
                                day = c.get(Calendar.DAY_OF_MONTH);

            /*final int h_diff = c.get(Calendar.HOUR_OF_DAY);
            final int m_diff = c.get(Calendar.MINUTE);
            final Calendar today = Calendar.getInstance();*/


                                String am_pm;
                                if (hour > 12)
                                {
                                    hour = hour - 12;
                                    am_pm = "PM";
                                } else {
                                    am_pm = "AM";
                                }


                                StringBuilder dateValue_1 = new StringBuilder().append(day)
                                        .append("-").append(month + 1).append("-").append(year)
                                        .append(" ").append(hour).append(":").append(minute).append(" ").append(am_pm);

                                //Date date = new Date();
                                SimpleDateFormat sdf1234 = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                                String abs12 = dateValue_1.toString();

                                // testDate1 = null;
                                try {
                                    testDate1 = sdf1234.parse(abs12);
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }
                                //String strDateFormat = "hh:mm:ss a";

                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                                String formattedDate= dateFormat.format(testDate1);

                                in_time.setText(formattedDate);
                            }
                        }, hour, minute, false);
               dia.show();
            }
        });

        Log.d("TAG","email is "+email);

        if("nikita.narayan98@gmail.com".equalsIgnoreCase(email))
        {

        }
        else
        {

        }
        String avai = sharedPrefs.getString("available","");
        available_seats.setText(avai);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefs = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
                editor_user = sharedPrefs.edit();
                String email = sharedPrefs.getString("email","no email");

                Log.d("TAG","email is "+email);

                if("nikita.narayan98@gmail.com".equalsIgnoreCase(email) && name.getText().toString().equals("") && phone.getText().toString().equals("")&& in_time.getText().toString().equals("")
                        && seats.getText().toString().equals("") && !available_seats.getText().toString().equals("")  && !sharedPrefs.getString("available","").equalsIgnoreCase(available_seats.getText().toString()))
                {
                    editor_user.putString("available",available_seats.getText().toString());
                    editor_user.apply();
                    String avai = sharedPrefs.getString("available","");
                    finish();
                    Log.d("AVAI","AVAI: "+avai);
                    Toast.makeText(getApplicationContext(), "Edit Successful!", Toast.LENGTH_SHORT).show();
                    //insertData();
                }

                else if("nikita.narayan98@gmail.com".equalsIgnoreCase(email) && !name.getText().toString().equals("") && !phone.getText().toString().equals("") && !in_time.getText().toString().equals("")
                        && !seats.getText().toString().equals("") && !available_seats.getText().toString().equals(""))
                {
                    editor_user.putString("available",available_seats.getText().toString());
                    editor_user.apply();
                    String avai = sharedPrefs.getString("available","");
                    finish();
                    Log.d("AVAI","AVAI: "+avai);
                    //Toast.makeText(getApplicationContext(), "Edit Successful!", Toast.LENGTH_SHORT).show();
                    insertData();
                }

                else if("nikita.narayan98@gmail.com".equalsIgnoreCase(email) && name.getText().toString().equals("") && phone.getText().toString().equals("") && in_time.getText().toString().equals("")
                        && seats.getText().toString().equals("") && !available_seats.getText().toString().equals("") && sharedPrefs.getString("available","").equalsIgnoreCase(available_seats.getText().toString()))
                {
                    if (name.getText().toString().equals("")) {
                        name.setError("Name is mandatory!");
                    }
                    if (phone.getText().toString().equals("")) {
                        phone.setError("Phone is mandatory!");
                    }
                    if (seats.getText().toString().equals("")) {
                        seats.setError("Seats is mandatory!");
                    }
                    if (in_time.getText().toString().equals("")) {
                        in_time.setError("In-Time is mandatory!");
                    }
                    if (available_seats.getText().toString().equals("")) {
                        available_seats.setError("Contact Manager");
                        Toast.makeText(getApplicationContext(), "Contact Manager", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Please Enter Data", Toast.LENGTH_LONG).show();
                    }

                    //  et.setError( "First name is required!" );
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
        in_time_text = in_time.getText().toString();
        available_text = available_seats.getText().toString();
        String emailnew = sharedPrefs.getString("email","no email");

        restaurantDatabaseHelper = new RestaurantDatabaseHelper(this);
        sqLiteDatabase = restaurantDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(R_NAME, name_text);
        values.put(R_CONTACT, phone_text);
        values.put(R_SEATS, seats_text);
        values.put(R_LOGIN_EMAIL, emailnew);
        values.put(R_IN_TIME, in_time_text);
        values.put(R_AVAILABLE, available_text);
Log.d("DB","VALUES: "+values);

            if (!name.getText().toString().equals("") && !phone.getText().toString().equals("")
                    && !in_time.getText().toString().equals("")
                && !seats.getText().toString().equals("") && !available_seats.getText().toString().equals("")) {

                int val1 = Integer.parseInt(seats.getText().toString());
                int val2 = Integer.parseInt(available_seats.getText().toString());

                if (val1 <= val2 && val2 != 0 && val1 != 0) {
                    rowInserted = -1;

                    if (bundle.containsKey("new")) {
                        rowInserted = sqLiteDatabase.insert(R_TABLE, null, values);
                        Intent intent = new Intent(Add_Detail.this, Booking_Fragment_Extends.class);
                        startActivity(intent);
                        finish(); //wont come back to the add form screen.
                    }

                    if (rowInserted != -1)
                    {
                        Toast.makeText(getApplicationContext(), "Booking Confirmed", Toast.LENGTH_SHORT).show();
                        int minus = Integer.parseInt(available_text) - Integer.parseInt(seats_text);
                        available_text = String.valueOf(minus);
                        available_seats.setText(available_text);
                        editor_user.putString("available",available_seats.getText().toString());
                        editor_user.apply();
                    }
                    else
                        //Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_LONG).show();

                    if (bundle.containsKey("user_edit_id")) {
                        //setscreen(user_id);
                        Log.d("id", "PResent_id" + user_id);

                        //code to edit the available seats logic on adding less than og value
                        int i = Integer.parseInt(sharedPrefs.getString("seats_enter",""));
                        Log.d("value", "i " + i);
                        int i2 = Integer.parseInt(seats_text);
                        Log.d("value", "i2 " + i2);
                        if(i >= i2)
                        {
                            int a1 = i - i2;
                            Log.d("value", "a1 " + a1);
                            int a2 = Integer.parseInt(available_text) + a1;
                            Log.d("value", "a2 " + a2);
                            available_text = String.valueOf(a2);
                            Log.d("value", "available " + available_text);
                            editor_user.putString("available",available_text);
                            editor_user.apply();
                            sqLiteDatabase.update(R_TABLE, values, "_id=" + user_id, null);
                            finish();
                        }
                        else
                        {
                            int a1 = i2 - i;
                            Log.d("value", "a1 " + a1);
                            int a2 = Integer.parseInt(available_text) - a1;
                            Log.d("value", "a2 " + a2);
                            available_text = String.valueOf(a2);
                            Log.d("value", "available " + available_text);
                            editor_user.putString("available",available_text);
                            editor_user.apply();
                            sqLiteDatabase.update(R_TABLE, values, "_id=" + user_id, null);
                            finish();
                        }
                        sqLiteDatabase.update(R_TABLE, values, "_id=" + user_id, null);
                        finish();
                    }
                }
                else if(val1 == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter correct number of seats", Toast.LENGTH_LONG).show();
                }
                    else {
                    Toast.makeText(getApplicationContext(), "Seats Full! There are seats on waiting!", Toast.LENGTH_LONG).show();
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
                if (in_time.getText().toString().equals("")) {
                    in_time.setError("In-Time is mandatory!");
                }

                if (available_seats.getText().toString().equals("")) {
                    available_seats.setError("Contact Manager");
                    Toast.makeText(getApplicationContext(), "Contact Manager", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Data", Toast.LENGTH_LONG).show();
                }

                    //  et.setError( "First name is required!" );
                }

    }

    private void setscreen(String str) {
        RestaurantDatabaseHelper restaurantDatabaseHelper = new RestaurantDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = restaurantDatabaseHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(R_TABLE, new String[]{R_NAME,R_CONTACT,R_SEATS,
                        R_AVAILABLE,R_IN_TIME},
                RestaurantDatabaseHelper._id+"=?", new String[]{str}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                name.setText(cursor.getString(cursor.getColumnIndexOrThrow(R_NAME)));
                phone.setText(cursor.getString(cursor.getColumnIndexOrThrow(R_CONTACT)));
                seats.setText(cursor.getString(cursor.getColumnIndexOrThrow(R_SEATS)));
                in_time.setText(cursor.getString(cursor.getColumnIndexOrThrow(R_IN_TIME)));
                editor_user.putString("seats_enter", seats.getText().toString());
                editor_user.apply();
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

        if("nikita.narayan98@gmail.com".equalsIgnoreCase(email))
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
            in_time.setEnabled(false);
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
