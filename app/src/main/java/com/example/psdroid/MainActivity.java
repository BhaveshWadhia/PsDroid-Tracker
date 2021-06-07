package com.example.psdroid;
//Import Class
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.psdroid.ui.introduction.IntroductoryActivity;
import com.example.psdroid.ui.LoginCheckerActivity;
//Main Activity
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        String FirstTime = preferences.getString("FirstTimeInstall","");
        if(FirstTime.equals("No")){
            // Application was already installed, skip the intro
            startActivity(new Intent(MainActivity.this, LoginCheckerActivity.class));
            finish();
        }
        else{
            // Application was installed first time, show the intro
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall","No");
            editor.apply();
            startActivity(new Intent(this, IntroductoryActivity.class));
            finish();
        }
    }
//End of Code
}
