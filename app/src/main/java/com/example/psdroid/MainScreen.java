package com.example.psdroid;
//Import class
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.psdroid.ui.gps.GpsFragment;
import com.example.psdroid.ui.home.HomeFragment;
import com.example.psdroid.ui.login.LoginActivity;
import com.example.psdroid.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dalvik.system.PathClassLoader;

//Main Screen Activity
    public class MainScreen extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public Toolbar app_toolbar;
    private String user;

    // String _user = getIntent().getStringExtra("user");
    //Create instance of the main screen & display fist page as Home
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        String _user = getIntent().getStringExtra("user");
        setContentView(R.layout.activity_mainscreen);       // Set content of main activity as activity_mainscreen.xml
        app_toolbar = findViewById(R.id.app_toolbar);  //Set toolbar for the application
        setSupportActionBar(app_toolbar);               //Set toolbar in the layout
        app_toolbar.setTitle("Home");
        BottomNavigationView main_navbar = findViewById(R.id.nav_view);     // Set bottom navigation bar in the layout
        main_navbar.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(_user)).commit();
        user = _user;
        getPermissions();
    }

    //Bottom navigation when selected
    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                //use switch to get id
                switch (item.getItemId()) {
                    case R.id.navHome_btn:
                        app_toolbar.setTitle("Home");
                        selectedFragment = new HomeFragment(user);
                        break;
                    case R.id.navGps_btn:
                        app_toolbar.setTitle("GPS Tracking");
                        selectedFragment = new GpsFragment(user);
                        break;
                    case R.id.navNotifications_btn:
                        app_toolbar.setTitle("Notifications");
                        selectedFragment = new NotificationsFragment(user);
                        break;
                }
                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                return true;
            };



    //Getting all permissions from the user
    private void getPermissions() {
        //Get user permissions for sending SMS
        if (ContextCompat.checkSelfPermission(MainScreen.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) { } else {
            //If permission is not granted then ask for permission
            ActivityCompat.requestPermissions(MainScreen.this, new String[]{Manifest.permission.SEND_SMS}, 100);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Check condition for sending SMS
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { }
        else {
            Toast.makeText(this, "Please give the permission for SMS from your phone's settings", Toast.LENGTH_SHORT).show();
        }
    }
}
