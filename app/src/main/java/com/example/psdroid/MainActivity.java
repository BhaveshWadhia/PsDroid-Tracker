package com.example.psdroid;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.psdroid.ui.login.AccountCreatedActivity;
import com.example.psdroid.ui.login.IntroductoryActivity;

//Main Activity
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, IntroductoryActivity.class));
    }
}
