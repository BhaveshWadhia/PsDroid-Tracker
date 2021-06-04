package com.example.psdroid.ui.home;
// Import Class
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
import java.util.Objects;
// Fake Caller Activity
public class FakeCallerActivity extends AppCompatActivity {
    public float setTimer;
    public String get_fakename,thisusername;
    public long get_ms;
    boolean set_timer = false;
    //CURRENT USER DETAILS
    String fullname,username,email,phone;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisusername = getIntent().getStringExtra("user");
        setContentView(R.layout.activity_fake_caller);
        Toolbar fakeCaller_toolbar = findViewById(R.id.fakecall_toolbar);  //Set toolbar for the application
        setSupportActionBar(fakeCaller_toolbar);

        //Current Users Details
        SharedPreferences getaccountDetails= getSharedPreferences("ACCOUNT_SHARED_PREF", MODE_PRIVATE);
        fullname = getaccountDetails.getString("fullname","");
        username = getaccountDetails.getString("user","");
        email = getaccountDetails.getString("email","");
        phone = getaccountDetails.getString("mob","");
        //Current Users Details

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);  //Set back button for the toolbar
        //Create click listener for back button
        fakeCaller_toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainScreen.class);
            intent.putExtra("user", thisusername);
            startActivity(intent);
            finish();      //Close the activity
        });
        //Elements of the activity
        TextView fakename = findViewById(R.id.fakename_input);
        Button caller_btn = findViewById(R.id.fakecall_btn);
        // Get details of the timer which is set
        RadioGroup timer_btn = findViewById(R.id.timer_grp);
        timer_btn.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.timer_30sec:
                    setTimer = 0.5f;
                    set_timer = true;
                    Toast.makeText(getApplicationContext(), "5 minute", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.timer_1min:
                    setTimer = 1f;
                    set_timer = true;
                    Toast.makeText(getApplicationContext(), "10 minute", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.timer_3min:
                    setTimer = 3f;
                    set_timer = true;
                    Toast.makeText(getApplicationContext(), "15 minute", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.timer_5min:
                    setTimer = 5f;
                    set_timer = true;
                    Toast.makeText(getApplicationContext(), "30 minute", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
        //Set Call button
        caller_btn.setOnClickListener(view -> {
            //When button is clicked get details of the fake call and set the call
            get_fakename = fakename.getText().toString().trim();
            if (get_fakename.equals("") || get_fakename.equals(" ")) {
                get_fakename = "Unknown";
            }
            get_ms = (long) (setTimer * 60 * Math.pow(10, 3));
            if (!set_timer) {
                //Timer is not set yet
                Toast.makeText(getApplicationContext(), "Please select a timer", Toast.LENGTH_SHORT).show();
            } else {
                new FakeCallTask().execute();
            }
        });
    }
    //Background Service for Fake Call
    public class FakeCallTask extends AsyncTask<String, Context,String>{
        @Override
        protected String doInBackground(String... strings) {
            Toast.makeText(getApplicationContext(), "Fake call is set!!\nYou will receive a call in " + setTimer + " seconds", Toast.LENGTH_SHORT).show();
            new CountDownTimer(get_ms, 1000) {
                @Override
                public void onTick(long l) {
                    System.out.println("FakeCaller background task");
                }

                @Override
                public void onFinish() {
                    //Receive fake call
                    Intent intent = new Intent(getApplicationContext(), FakeCall.class);
                    intent.putExtra("fakename_key", get_fakename);
                    startActivity(intent);
                }
            }.start();
            return null;
        }
    }
    //End of Code
}

