package com.example.psdroid.ui.login;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
import com.example.psdroid.ui.add_users.AddUsersActivity;
// Account Created Successfully Activity
public class AccountCreatedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_created);
        Button startBtn = findViewById(R.id.startButton);
        startBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainScreen.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_right);
        });
    }
}
