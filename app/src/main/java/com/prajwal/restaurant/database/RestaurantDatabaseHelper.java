package com.prajwal.restaurant.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class RestaurantDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Resto.db";
    public static SQLiteDatabase database;

    public static final String R_TABLE = "Restaurant_Data";
    public static final String _id = "_id";
    public static final String R_NAME = "NAME";
    public static final String R_SEATS = "SEATS";
    public static final String R_CONTACT = "MOBILE_NO";
    public static final String R_AVAILABLE = "AVAILABLE_SEATS";
    public static final String R_LOGIN_EMAIL = "LOGIN_EMAIL";

    public static final String R_REVIEW = "Restaurant_Review";
    public static final String R_USERNAME = "Restaurant_USER";
    public static final String R_MARKS = "Restaurant_MARKS";

    public static final String CREATE_RESTAURANT_DATA = "CREATE TABLE IF NOT EXISTS "+R_TABLE+"(" +
            _id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            R_NAME + " TEXT," +
            R_SEATS + " INTEGER," +
            R_AVAILABLE + " INTEGER," +
            R_LOGIN_EMAIL + " TEXT," +
            R_CONTACT + " INTEGER);";

    public static final String CREATE_RESTAURANT_REVIEW = "CREATE TABLE IF NOT EXISTS "+R_REVIEW+"(" +
            _id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            R_USERNAME + " TEXT," +
            R_MARKS + " TEXT);";


    public RestaurantDatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_RESTAURANT_DATA);
        sqLiteDatabase.execSQL(CREATE_RESTAURANT_REVIEW);
        Log.i("DATA","Table has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newversion) {
        switch (oldVersion) {
            case 1:
                //upgrade logic from version 1 to 2
            case 2:
                //upgrade logic from version 2 to 3
                //sqLiteDatabase.execSQL("ALTER TABLE R_TABLE ADD COLUMN R_AVAILABLE");
            case 3:
                //upgrade logic from version 2 to 3
            case 4:
                //upgrade logic from version 2 to 3
            case 5:
                //upgrade logic from version 2 to 3
            case 6:
                //upgrade logic from version 2 to 3case 2:
                //                //upgrade logic from version 2 to 3
            default:
                throw new IllegalStateException(
                        "onUpgrade() with unknown oldVersion " + oldVersion);
        }

    }

    public void delete(int anInt)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(R_TABLE,_id + "=?",new String[] {String.valueOf(anInt)});
        db.close();
    }

    public Cursor getAll()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+R_TABLE,null);
        System.out.println(c);
        Log.d("DATA","CURSOR: "+c);
        return c;
    }
}
