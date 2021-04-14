package com.example.psdroid;
//Import class
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.example.psdroid.ui.gps.GpsFragment;
import com.example.psdroid.ui.home.HomeFragment;
import com.example.psdroid.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//Main activity
    public class MainScreen extends AppCompatActivity {
      public Toolbar  app_toolbar;
        //Create instance of the main screen & display fist page as Home
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mainscreen);       // Set content of main activity as activity_main.xml
            app_toolbar = findViewById(R.id.app_toolbar);  //Set toolbar for the application
            setSupportActionBar(app_toolbar);               //Set toolbar in the layout
            BottomNavigationView main_navbar = findViewById(R.id.nav_view);     // Set bottom navigation bar in the layout
            main_navbar.setOnNavigationItemSelectedListener(navListener);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        }
        //Bottom navigation when selected
        @SuppressLint("NonConstantResourceId")
        private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
                item -> {
                    Fragment selectedFragment = null;
                    //use switch to get id
                    switch (item.getItemId()) {
                        case R.id.navHome_btn:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navGps_btn:
                            selectedFragment = new GpsFragment();
                            break;
                        case R.id.navNotifications_btn:
                            selectedFragment = new NotificationsFragment();
                            break;
                    }
                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                    return true;
                };
    }