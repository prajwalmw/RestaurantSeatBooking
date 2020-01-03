package com.prajwal.restaurant;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth firebaseauth;
    private FirebaseAuth.AuthStateListener statelistener;
    public static final int RC_SIGN_IN = 1;
    View headerview;
    TextView text_username, text_email;
    ImageView image_user;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor_user;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseauth = FirebaseAuth.getInstance();
        sharedPrefs = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        editor_user = sharedPrefs.edit();
        requestPermission();    //requests permisiions....

        //auth state listener
        statelistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser(); //firebaseAuth takes sign_in or sign_out params.

                if(user != null)
                {
                    text_username.setText(firebaseauth.getCurrentUser().getDisplayName());
                    //username of logged in user.
                    editor_user.putString("username", firebaseauth.getCurrentUser().getDisplayName());
                    editor_user.apply(); //apply is imp or else the changes wont be saved
                    String email = sharedPrefs.getString("username","no email");
                    Log.d("TAG","user is login"+email);

                    text_email.setText(firebaseauth.getCurrentUser().getEmail());
                    //email id of logged in user.
                    editor_user.putString("email", firebaseauth.getCurrentUser().getEmail() );
                    editor_user.apply(); //apply is imp or else the changes wont be saved
                    String emailnew = sharedPrefs.getString("email","no email");
                    Log.d("TAG","email is login"+emailnew);


                    Glide.with(getApplicationContext())
                            .load(firebaseauth.getCurrentUser().getPhotoUrl())
                            .asBitmap()
                            .error(R.drawable.restaurant)   //asbitmap after load always.
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource,
                                                            GlideAnimation<? super Bitmap> glideAnimation) {
                                    image_user.setImageBitmap(resource);
                                    //editor_user.putString("profile_image", firebaseauth.getCurrentUser().getPhotoUrl().toString());
                                   // editor_user.apply();
                                }
                            });

                    //editor_user.putString("profile_image", firebaseauth.getCurrentUser().getPhotoUrl());
                    //editor_user.apply();

                    Log.d("text","Nav : "+text_username);
                }
                else
                {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setLogo(R.drawable.restaurant)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }

            }
        };

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        headerview = navigationView.getHeaderView(0);
        text_username = (TextView) headerview.findViewById(R.id.textview_username);
        text_email = (TextView) headerview.findViewById(R.id.textView_emailid);
        image_user = (ImageView) headerview.findViewById(R.id.imageView_userimage);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_review, R.id.nav_share, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED && requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK && data != null) {
                text_username.setText(firebaseauth.getCurrentUser().getDisplayName());
                //username of logged in user.
                editor_user.putString("username", text_username.getText().toString());
                editor_user.apply(); //apply is imp or else the changes wont be saved


                text_email.setText(firebaseauth.getCurrentUser().getEmail());
                //email id of logged in user.
                editor_user.putString("email", text_email.getText().toString());
                editor_user.apply(); //apply is imp or else the changes wont be saved


               /* if(Build.VERSION.SDK_INT == Build.VERSION_CODES.M)
                {
                    Uri uri = data.getData();
                    String uri_string = uri.toString();
                    editor_user.putString("profile_image", uri_string
                    );
                    editor_user.apply();
                }
                else
                {
                    String uri = firebaseauth.getCurrentUser().getPhotoUrl().toString();
                    editor_user.putString("profile_image", uri);
                    editor_user.apply();
                }*/

                //Bitmap photo = null;

                Glide.with(getApplicationContext())
                        .load(firebaseauth.getCurrentUser().getPhotoUrl())
                        .asBitmap()
                        .error(R.drawable.restaurant)//asbitmap after load always.
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource,
                                                        GlideAnimation<? super Bitmap> glideAnimation) {
                                    //Bitmap photo = (Bitmap) data.getExtras().get(firebaseauth.getCurrentUser().getPhotoUrl().toString());
                                    image_user.setImageBitmap(resource);
                                   // editor_user.putString("profile_image", firebaseauth.getCurrentUser().getPhotoUrl().toString());
                                    //editor_user.apply();


                            }
                        });


                Log.d("text","Nav : "+text_username);


                Log.d("text","Nav : "+text_username);
                Toast.makeText(this, "You're now signed in", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_CANCELED) {
                //Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                //finish();
                moveTaskToBack(true);
                finish();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AuthUI.getInstance().signOut(this);
            Toast.makeText(this, "Visit Again", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    //Permission requests....
    private void requestPermission() {

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},
                PERMISSION_REQUEST_CODE);
//        onActivityResult(100,RESULT_OK,getIntent());
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // main logic
                } else {
                    // Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();

                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            new AlertDialog.Builder(this)
                                    .setMessage("You need to allow access permissions")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                                //onActivityResult(100,RESULT_OK,getIntent());
                                            }
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    })
                                    .create()
                                    .show();

                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseauth.addAuthStateListener(statelistener);


    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseauth.removeAuthStateListener(statelistener);
    }

}
