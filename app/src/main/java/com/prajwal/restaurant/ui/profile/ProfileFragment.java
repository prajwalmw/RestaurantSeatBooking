package com.prajwal.restaurant.ui.profile;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.firebase.auth.FirebaseAuth;
import com.prajwal.restaurant.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor_user;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ImageView profile;
    TextView username, email;
    View root;
    FirebaseAuth firebaseAuth;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        root = inflater.inflate(R.layout.profile_fragment, container, false);
        profile = root.findViewById(R.id.profile_image);
        username = root.findViewById(R.id.username_text);
        email = root.findViewById(R.id.email_text);
        onStart();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();

        sharedPrefs = getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        String uri_string = sharedPrefs.getString("profile_image","");
        Log.d("URI","URI: "+uri_string);
        Uri uri = Uri.parse(uri_string);
        Glide.with(getContext())
                .load(firebaseAuth.getCurrentUser().getPhotoUrl()).asBitmap().error(R.drawable.restaurant)//asbitmap after load always.
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        profile.setImageBitmap(resource);
                    }
                });

        String username1 = sharedPrefs.getString("username","Username");
        String email2 = sharedPrefs.getString("email","Email ID");

        username.setText(username1);
        email.setText(email2);
    }
}
