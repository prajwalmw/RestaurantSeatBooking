package com.prajwal.restaurant.ui.review;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.prajwal.restaurant.R;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class ReviewFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    RatingBar ratingBar;
    Button button;

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
            }

        });
    }
}