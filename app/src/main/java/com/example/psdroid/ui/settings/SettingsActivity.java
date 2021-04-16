package com.example.psdroid.ui.settings;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//Import Class
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;

import com.example.psdroid.MainActivity;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
//Settings Activity
public class SettingsActivity extends AppCompatActivity {
    public Toolbar settings_toolbar;
    //Create a instance of the state and replace the current fragment with the settings_activity Fragment
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        settings_toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(settings_toolbar);   //Set toolbar for the settings activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);    //Set back button on toolbar
        //Create click listener for back button
        settings_toolbar.setNavigationOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainScreen.class));
            finish();       //Close the activity
        });
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.settings, new SettingsFragment()).commit();
        }
    }
    //Set content of root file to settings_activity Fragment
    public static class SettingsFragment extends PreferenceFragmentCompat
    {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
        {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}
