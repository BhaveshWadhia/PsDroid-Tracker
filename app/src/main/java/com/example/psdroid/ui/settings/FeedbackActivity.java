package com.example.psdroid.ui.settings;
//Import Class
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.psdroid.R;
//Feedback Activity
public class FeedbackActivity extends AppCompatActivity {
    public Toolbar feedback_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_feedback);
        feedback_toolbar = findViewById(R.id.feedback_toolbar);
        setSupportActionBar(feedback_toolbar);   //Set toolbar for the settings activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Set back button on toolbar
        //Create click listener for back button
        feedback_toolbar.setNavigationOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
            finish();       //Close the activity
        });
        EditText edit1= findViewById(R.id.feedback_textbox1);
        EditText edit2= findViewById(R.id.feedback_textbox2);
        Button btn= findViewById(R.id.feedback_button);
        RatingBar simpleRatingBar = findViewById(R.id.feedback_rating); // initiate a rating bar
        int numberOfStars = simpleRatingBar.getNumStars(); // get total number of stars of rating bar
        btn.setOnClickListener(v -> {
            Intent i= new Intent(Intent.ACTION_SEND);
            Intent i2 = new Intent(Intent.ACTION_SENDTO);
            i.setType("message/html");
            i2.setData(Uri.parse("mailto:psdroid2021@gmail.com"));
            // i.putExtra(Intent.EXTRA_EMAIL, "psdroid2021@gmail.com");
            i.putExtra(Intent.EXTRA_SUBJECT,"Feedback from App");
            i.putExtra(Intent.EXTRA_TEXT,"Rating:"+numberOfStars);
            i.putExtra(Intent.EXTRA_TEXT,"Name: "+edit1.getText()+"\n Feedback: "+edit2.getText());
            i.putExtra(Intent.EXTRA_TEXT,"Description :");
            try {
                startActivity(Intent.createChooser(i,"Please select Email"));
            }
            catch (ActivityNotFoundException ex)
            {
                Toast.makeText(FeedbackActivity.this, "There are no Email Client", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
