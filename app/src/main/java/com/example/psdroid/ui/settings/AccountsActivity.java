package com.example.psdroid.ui.settings;
//Import Class
import android.content.Intent;
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
        //Create click listener for back button
        account_toolbar.setNavigationOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            finish();       //Close the activity
        });
        // Display Details
        textView=findViewById(R.id.fullname);
        textView.setText(getDetails.txt_user);
        textView=findViewById(R.id.accounts_email);
        textView.setText(getDetails.txt_email);
        textView=findViewById(R.id.name);
        textView.setText(getDetails.txt_user);
        textView=findViewById(R.id.email);
        textView.setText(getDetails.txt_email);
        textView=findViewById(R.id.phone);
        textView.setText(getDetails.txt_mobile);
    }
}
