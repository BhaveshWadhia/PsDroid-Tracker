package com.example.psdroid.ui.settings;
//Import Class
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
import com.example.psdroid.ui.register.SignupTabFragment;

import java.util.Objects;
//Accounts Activity
public class AccountsActivity extends AppCompatActivity {
    public Toolbar account_toolbar;;
    TextView textView;
    String fullname,name,email,phone;
    SignupTabFragment getDetails = new SignupTabFragment();
    //Create a instance of the state and replace the current fragment with the settings_activity Fragment
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);
        account_toolbar = findViewById(R.id.accounts_toolbar);
        setSupportActionBar(account_toolbar);   //Set toolbar for the settings activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);    //Set back button on toolbar
        fetchDetails();
        //Create click listener for back button
        account_toolbar.setNavigationOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            finish();       //Close the activity
        });
    }

    private void fetchDetails() {
        SharedPreferences sp = getSharedPreferences("ACCOUNT_SHARED_PREF",MODE_PRIVATE);
        name = sp.getString("user","");
        email = sp.getString("email","");
        phone= sp.getString("mob","");

        // Display Details
        textView=findViewById(R.id.fullname);
        textView.setText(name); //Idhar badme full name dalna abhi karn ne vo nhi kiya

        textView=findViewById(R.id.accounts_email);
        textView.setText(email);   // ye vo image ke niche wala hai

        textView=findViewById(R.id.name);
        textView.setText(name);

        textView=findViewById(R.id.email);
        textView.setText(email);

        textView=findViewById(R.id.phone);
        textView.setText(phone);
    }
}
