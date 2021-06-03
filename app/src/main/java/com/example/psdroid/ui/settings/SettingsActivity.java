package com.example.psdroid.ui.settings;
//Import Class
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
import java.util.Objects;
//Settings Activity
public class SettingsActivity extends AppCompatActivity {
    public Toolbar settings_toolbar;
    public String thisusername;
    //Create a instance of the state and replace the current fragment with the settings_activity Fragment
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Intent intent = getIntent();
        thisusername = intent.getStringExtra("user");
        settings_toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(settings_toolbar);   //Set toolbar for the settings activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);    //Set back button on toolbar
        //Create click listener for back button
        settings_toolbar.setNavigationOnClickListener(view -> {
            Intent returnIntent = new Intent(getApplicationContext(),MainScreen.class);
            returnIntent.putExtra("user",thisusername);
            startActivity(returnIntent);
            finish();       //Close the activity
        });
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.settings, new SettingsFragment()).commit();
        }
    }
    //Set content of root file to settings_activity FrameLayout
    public static class SettingsFragment extends PreferenceFragmentCompat
    {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
        {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}
