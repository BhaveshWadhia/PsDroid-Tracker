package com.example.psdroid.ui.settings;
//Import Class
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.psdroid.R;
import java.util.Objects;

//Feedback Activity
public class FeedbackActivity extends AppCompatActivity {
    public Toolbar feedback_toolbar;
    //CURRENT USER DETAILS
    String fullname,username,email,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_feedback);
        feedback_toolbar = findViewById(R.id.feedback_toolbar);
        setSupportActionBar(feedback_toolbar);   //Set toolbar for the settings activity

        //Current Users Details
        SharedPreferences getaccountDetails= getSharedPreferences("ACCOUNT_SHARED_PREF", MODE_PRIVATE);
        fullname = getaccountDetails.getString("fullname","");
        username = getaccountDetails.getString("user","");
        email = getaccountDetails.getString("email","");
        phone = getaccountDetails.getString("mob","");
        //Current Users Details

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);  //Set back button on toolbar
        //Create click listener for back button
        feedback_toolbar.setNavigationOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
            finish();       //Close the activity
        });
        EditText edit1= findViewById(R.id.feedback_textbox1);
        EditText edit2= findViewById(R.id.feedback_textbox2);
        Button btn= findViewById(R.id.feedback_button);
        RatingBar simpleRatingBar = findViewById(R.id.feedback_rating); //Initiate a rating bar
        int numberOfStars = simpleRatingBar.getNumStars(); //Get total number of stars of rating bar
        btn.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"psdroidtracker@gmail.com"});   //Set email
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback of Application");            //Set Subject of email
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Name: "+edit1.getText().toString()       //Set details
                    +"\n"+"Rating: "+numberOfStars
                    +"\n"+"Description: "+ edit2.getText().toString());
            try {
                startActivity(Intent.createChooser(emailIntent,"Please select Email")); 
            }       
            catch (ActivityNotFoundException ex)
            {
                Toast.makeText(FeedbackActivity.this, "There are no Email Client", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
