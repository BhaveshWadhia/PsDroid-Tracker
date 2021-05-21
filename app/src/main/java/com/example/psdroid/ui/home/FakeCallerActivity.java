package com.example.psdroid.ui.home;
// Import Class
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.psdroid.MainScreen;
import com.example.psdroid.R;

import java.util.Objects;
// Fake Caller Activity
public class FakeCallerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fake_caller_activity);
        Toolbar fakeCaller_toolbar = findViewById(R.id.fakecall_toolbar);  //Set toolbar for the application
        setSupportActionBar(fakeCaller_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);  //Set back button for the toolbar
        //Create click listener for back button
        fakeCaller_toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainScreen.class));
            finish();    //Close the activity
        });
    }
}

