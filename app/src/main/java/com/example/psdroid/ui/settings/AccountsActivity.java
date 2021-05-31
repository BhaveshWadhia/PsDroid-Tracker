package com.example.psdroid.ui.settings;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
import java.util.Objects;
//Accounts Activity
public class AccountsActivity extends AppCompatActivity {
    public Toolbar account_toolbar;
    String st;
    TextView tv;
    //Create a instance of the state and replace the current fragment with the settings_activity Fragment
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);
        account_toolbar = findViewById(R.id.accounts_toolbar);
        setSupportActionBar(account_toolbar);   //Set toolbar for the settings activity
        tv=findViewById(R.id.fullname);
        st=getIntent().getExtras().getString("user");
        tv.setText(st);

        tv=findViewById(R.id.name);
        st=getIntent().getExtras().getString("user");
        tv.setText(st);

        tv=findViewById(R.id.email);
        st=getIntent().getExtras().getString("mail");
        tv.setText(st);

        tv=findViewById(R.id.phone);
        st=getIntent().getExtras().getString("mob");
        tv.setText(st);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);    //Set back button on toolbar
        //Create click listener for back button
        account_toolbar.setNavigationOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            finish();       //Close the activity
        });
    }
}
