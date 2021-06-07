package com.example.psdroid.ui.forget_password;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.psdroid.R;
import com.example.psdroid.ui.login.LoginActivity;

// Password Changer Activity
public class PasswordChanged extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_changed);
        Button startBtn = findViewById(R.id.startButton);
        startBtn.setOnClickListener(view -> {
            Intent intent = new Intent(PasswordChanged.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
            finish();
        });
    }
//End of Code
}
