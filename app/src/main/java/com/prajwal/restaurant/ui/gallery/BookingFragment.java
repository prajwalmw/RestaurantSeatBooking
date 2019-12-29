package com.prajwal.restaurant.ui.gallery;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.prajwal.restaurant.Edit_Detail;
import com.prajwal.restaurant.R;
import com.prajwal.restaurant.database.RestaurantDatabaseHelper;

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
    }
}