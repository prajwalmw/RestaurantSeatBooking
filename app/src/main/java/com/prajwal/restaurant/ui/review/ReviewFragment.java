package com.prajwal.restaurant.ui.review;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.prajwal.restaurant.MainActivity;
import com.prajwal.restaurant.R;
import com.prajwal.restaurant.database.RestaurantDatabaseHelper;
import com.prajwal.restaurant.ui.home.HomeFragment;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_AVAILABLE;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_CONTACT;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_MARKS;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_NAME;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_REVIEW;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_SEATS;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_TABLE;
import static com.prajwal.restaurant.database.RestaurantDatabaseHelper.R_USERNAME;

public class ReviewFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    RatingBar ratingBar;
    Button button;
    RestaurantDatabaseHelper restaurantDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    SimpleCursorAdapter simpleCursorAdapter;
    SharedPreferences sharedPrefs;
    long rowInserted;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.review_fragment, container, false);
       /* final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
       ratingBar = root.findViewById(R.id.ratingBar);
       button = root.findViewById(R.id.button);
       onStart();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        sharedPrefs = getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        restaurantDatabaseHelper = new RestaurantDatabaseHelper(getContext());
        sqLiteDatabase = restaurantDatabaseHelper.getWritableDatabase();

        addListenerOnButtonClick();
    }

    public void addListenerOnButtonClick(){
        //Performing action on Button Click
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                String rating=String.valueOf(ratingBar.getRating());
                Toast.makeText(getContext(), rating, Toast.LENGTH_LONG).show();

                String username1 = sharedPrefs.getString("username","Username");

                ContentValues values = new ContentValues();
                values.put(R_USERNAME, username1);
                values.put(R_MARKS, rating);

                rowInserted = sqLiteDatabase.insert(R_REVIEW, null, values);
                if (rowInserted != -1) {
                    Toast.makeText(getContext(), "Review Submitted Successfully!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getContext(), MainActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getContext(), "Something wrong", Toast.LENGTH_LONG).show();
                }

            }

        });
    }
}