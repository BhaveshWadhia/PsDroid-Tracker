package com.example.psdroid;
//Import class
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.example.psdroid.ui.gps.GpsFragment;
import com.example.psdroid.ui.home.HomeFragment;
import com.example.psdroid.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
//Main Screen Activity
    public class MainScreen extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public Toolbar app_toolbar;
    private String intent_user;
    //CURRENT USER DETAILS
    String thisusername,fullname,username,email,phone;
    //Create instance of the main screen & display fist page as Home
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

        //Current Users Details
        SharedPreferences getaccountDetails= getSharedPreferences("ACCOUNT_SHARED_PREF", MODE_PRIVATE);
        fullname = getaccountDetails.getString("fullname","");
        username = getaccountDetails.getString("user","");
        email = getaccountDetails.getString("email","");
        phone = getaccountDetails.getString("mob","");
        //Current Users Details

        setContentView(R.layout.activity_mainscreen);       // Set content of main activity as activity_mainscreen.xml
        app_toolbar = findViewById(R.id.app_toolbar);  //Set toolbar for the application
        setSupportActionBar(app_toolbar);               //Set toolbar in the layout
        app_toolbar.setTitle("Home");
        BottomNavigationView main_navbar = findViewById(R.id.nav_view);     // Set bottom navigation bar in the layout
        main_navbar.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(username)).commit();
        intent_user = username;
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
                        selectedFragment = new HomeFragment(intent_user);
                        break;
                    case R.id.navGps_btn:
                        app_toolbar.setTitle("GPS Tracking");
                        selectedFragment = new GpsFragment(intent_user);
                        break;
                    case R.id.navNotifications_btn:
                        app_toolbar.setTitle("Notifications");
                        selectedFragment = new NotificationsFragment(intent_user);
                        break;
                }
                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                return true;
            };
    //End of Code
}
