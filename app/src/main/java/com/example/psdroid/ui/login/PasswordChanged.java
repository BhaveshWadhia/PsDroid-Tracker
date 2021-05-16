package com.example.psdroid.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.psdroid.MainScreen;
import com.example.psdroid.R;

public class PasswordChanged extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_changed);
        Button startBtn = findViewById(R.id.startButton);
        startBtn.setOnClickListener(view -> {
            Intent intent = new Intent(PasswordChanged.this, MainScreen.class);
            startActivity(intent);
            //startActivity(new Intent(getApplicationContext(), MainScreen.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_right);
            finish();
        });
    }
}