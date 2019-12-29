package com.prajwal.restaurant.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class RestaurantDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "restaurant.db";
    public static SQLiteDatabase database;

    public static final String R_TABLE = "Restaurant_Data";
    public static final String _id = "_id";
    public static final String R_NAME = "NAME";
    public static final String R_SEATS = "SEATS";
    public static final String R_CONTACT = "MOBILE_NO";

    public static final String CREATE_RESTAURANT_DATA = "CREATE TABLE IF NOT EXISTS "+R_TABLE+"(" +
            _id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            R_NAME + " TEXT," +
            R_SEATS + " INTEGER," +
            R_CONTACT + " INTEGER);";


    public RestaurantDatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_RESTAURANT_DATA);
        Log.i("DATA","Table has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newversion) {
        switch (oldVersion) {
            case 1:
                //upgrade logic from version 1 to 2
            case 2:
                //upgrade logic from version 2 to 3
            case 3:
                //upgrade logic from version 3 to 4
            case 4:
                //upgrade logic from version 4
            default:
                throw new IllegalStateException(
                        "onUpgrade() with unknown oldVersion " + oldVersion);
        }

    }
}
