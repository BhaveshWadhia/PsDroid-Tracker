package com.example.psdroid.ui.settings;
//Import Class
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.psdroid.R;
import java.util.Objects;
//Accounts Activity
public class AccountsActivity extends AppCompatActivity {
    Toolbar account_toolbar;
    TextView textView;
    String fullname,username,email,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);
        account_toolbar = findViewById(R.id.accounts_toolbar);
        setSupportActionBar(account_toolbar);   //Set toolbar for the settings activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);    //Set back button on toolbar
        fetchDetails();
        //Create click listener for back button
        account_toolbar.setNavigationOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            intent.putExtra("user",username);
            startActivity(intent);
            finish();       //Close the activity
        });
    }
    private void fetchDetails() {
        //Current Users Details
        SharedPreferences getaccountDetails= getSharedPreferences("ACCOUNT_SHARED_PREF",MODE_PRIVATE);
        fullname = getaccountDetails.getString("fullname","");
        username = getaccountDetails.getString("user","");
        email = getaccountDetails.getString("email","");
        phone = getaccountDetails.getString("mob","");

        // Display Details
        textView=findViewById(R.id.fullname);
        textView.setText(fullname);

        textView=findViewById(R.id.accounts_email);
        textView.setText(email);

        textView=findViewById(R.id.username);
        textView.setText(username);

        textView=findViewById(R.id.email);
        textView.setText(email);

        textView=findViewById(R.id.phone);
        textView.setText(phone);
    }
}
