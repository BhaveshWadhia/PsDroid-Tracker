package com.example.psdroid.ui.home;
// Import Class
import android.annotation.SuppressLint;
import android.content.Intent;
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
    public Integer setTimer;
    public String get_fakename;
    public long get_ms;
    boolean set_timer = false;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_caller);
        Toolbar fakeCaller_toolbar = findViewById(R.id.fakecall_toolbar);  //Set toolbar for the application
        setSupportActionBar(fakeCaller_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);  //Set back button for the toolbar
        //Create click listener for back button
        fakeCaller_toolbar.setNavigationOnClickListener(v -> {
                    startActivity(new Intent(getApplicationContext(), MainScreen.class));
                    finish();    //Close the activity
                });
        //Elements of the activity
        TextView  fakename=findViewById(R.id.fakename_input);
        Button caller_btn = findViewById(R.id.fakecall_btn);
        // Get details of the timer which is set
        RadioGroup timer_btn = findViewById(R.id.timer_grp) ;
        timer_btn.setOnCheckedChangeListener((radioGroup, i) -> {
                switch(i)
                {
                    case R.id.timer_5min:
                        setTimer = 5;
                        set_timer = true;
                        Toast.makeText(getApplicationContext(),"5 minute",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.timer_10min:
                        setTimer = 10;
                        set_timer = true;
                        Toast.makeText(getApplicationContext(),"10 minute",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.timer_15min:
                        setTimer = 15;
                        set_timer = true;
                        Toast.makeText(getApplicationContext(),"15 minute",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.timer_30min:
                        setTimer = 30;
                        set_timer = true;
                        Toast.makeText(getApplicationContext(),"30 minute",Toast.LENGTH_SHORT).show();
                        break;
                }
            });
        //Set Call button
        caller_btn.setOnClickListener(view -> {
            //When button is clicked get details of the fake call and set the call
           get_fakename = fakename.getText().toString().trim();
           if(get_fakename.equals("") || get_fakename.equals(" "))
           {
               get_fakename = "Unknown";
           }
           //get_ms = setTimer*60*10^3;
           get_ms = (long) (setTimer*Math.pow(10,3)); //For testing  purpose only
           if(!set_timer)
           {
               //Timer is not set yet
               Toast.makeText(getApplicationContext(),"Please select a timer",Toast.LENGTH_SHORT).show();
           }
           else {
               //Timer is set
               Toast.makeText(getApplicationContext(),"Fake call is set!!\nYou will receive a call in "+setTimer+" seconds",Toast.LENGTH_SHORT).show();
               new CountDownTimer(get_ms, 1000) {
                   @Override
                   public void onTick(long l) {
                   }

                   @Override
                   public void onFinish() {
                       //Receive fake call
                       Intent intent = new Intent(getApplicationContext(),FakeCall.class);
                       intent.putExtra("fakename_key",get_fakename);
                       startActivity(intent);
                   }
               }.start();
           }
        });
    }
}

