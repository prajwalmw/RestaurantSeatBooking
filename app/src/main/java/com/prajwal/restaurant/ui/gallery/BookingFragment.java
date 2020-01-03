package com.prajwal.restaurant.ui.gallery;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prajwal.restaurant.Edit_Detail;
import com.prajwal.restaurant.R;
import com.prajwal.restaurant.database.RestaurantDatabaseHelper;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class BookingFragment extends Fragment {

    private BookingViewModel bookingViewModel;
    RestaurantDatabaseHelper restaurantDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    ListView listView;
    View root;
    Cursor cursor;
    SimpleCursorAdapter simpleCursorAdapter;
    long clicked_id;
    String string_id;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor_user;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    FloatingActionButton call;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);
         root = inflater.inflate(R.layout.fragment_booking, container, false);
       /* final TextView textView = root.findViewById(R.id.text_gallery);
        bookingViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
       onStart();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        restaurantDatabaseHelper = new RestaurantDatabaseHelper(getContext());
        sqLiteDatabase = restaurantDatabaseHelper.getReadableDatabase();

        String[] projection = new String[] {RestaurantDatabaseHelper._id,RestaurantDatabaseHelper.R_NAME};
        listView = (ListView) root.findViewById(R.id.listview);

        call = root.findViewById(R.id.call_manager);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)

                {
                    final String a = "tel:"+"9769779980";
                    Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(a));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(i);
                }
            }
        });

        cursor = sqLiteDatabase.query(RestaurantDatabaseHelper.R_TABLE, projection, null, null, null, null, null);

        simpleCursorAdapter = new SimpleCursorAdapter(getContext(),R.layout.mybooking_listlayout,cursor,
                projection,new int[]{R.id.prajwal_list_id,R.id.prajwal_list_name});
        listView.setAdapter(simpleCursorAdapter);
        listView.setTextFilterEnabled(true);


        //end
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // String UserInfo = lv.getItemAtPosition(position).toString();

                clicked_id = simpleCursorAdapter.getItemId(position);
                string_id = Long.toString(clicked_id);

                Toast.makeText(getContext(),"position id : "+ clicked_id,Toast.LENGTH_LONG).show();
                Intent int_data = new Intent(getContext(), Edit_Detail.class);

                int_data.putExtra("name",string_id);
                startActivity(int_data);

            }
        });

        //delete
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                sharedPrefs = getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
                editor_user = sharedPrefs.edit();
                String email = sharedPrefs.getString("email","no email");

                Log.d("TAG","email is "+email);
                if("prajwal@intelehealth.io".equalsIgnoreCase(email))
                {
                    AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                    ad.setTitle("Delete");
                    ad.setMessage("Are you sure you want to delete ? ");

                    final int pos = position;
                    ad.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cursor.moveToPosition(pos);
                            // db.delete(c.getInt(c.getColumnIndex(Personal_medical_database._id)));
                            //c = db.getAll();
                            restaurantDatabaseHelper.delete
                                    (cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper._id)));       //hint for update.
                            cursor=restaurantDatabaseHelper.getAll();
                            simpleCursorAdapter.swapCursor(cursor);

                            listView.setAdapter(simpleCursorAdapter);
                            //cAdapter.notifyDataSetChanged();

                        }
                    });

                    ad.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    ad.show();
                }
                else
                {
                    //Do nothing
                }
                return  true;

            }
        });


        //end
    }


}