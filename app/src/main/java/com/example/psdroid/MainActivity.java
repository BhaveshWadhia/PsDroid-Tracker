package com.example.psdroid;
//Import Class
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.example.psdroid.ui.login.IntroductoryActivity;
import com.example.psdroid.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Main Activity
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        String FirstTime = preferences.getString("FirstTimeInstall","");
        if(FirstTime.equals("No")){
            //If application was opened for the first time
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

        }
        else{
            //Else

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall","No");
            editor.apply();
            startActivity(new Intent(this, IntroductoryActivity.class));
        }

    }
}
