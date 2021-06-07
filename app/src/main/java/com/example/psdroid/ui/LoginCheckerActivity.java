package com.example.psdroid.ui;
//Import Class
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
import com.example.psdroid.ui.login.LoginActivity;
// Introductory Activity
public class LoginCheckerActivity extends AppCompatActivity {
    ImageView logo,intro;
    LottieAnimationView lottieAnimationView;
    TextView appname;
    String isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        logo =  findViewById(R.id.logo);
        intro =  findViewById(R.id.image);
        lottieAnimationView =  findViewById(R.id.tick_lottie);
        appname = findViewById(R.id.appname);

        intro.animate().translationY(-3000).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        appname.animate().translationY(2300).setDuration(1000).setStartDelay(4000);

        // Check if user has already logged in to the account
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_SHARED_PREF",MODE_PRIVATE);
        isLoggedIn = sharedPreferences.getString("isLoggedIn", "");
        // If user has already logged in to the application
        if (isLoggedIn.equals("true"))
        {
            // Load Login Activity
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, MainScreen.class));
                finish();
            }, 5000);
        }
        // If user is logging in first time
       else
        {
            // Load Login Activity
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                }, 5000);
        }
    }
    //End of Code
}
