package com.example.psdroid.ui.home;
//Import Class
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;

import java.util.ArrayList;
import java.util.Objects;
//Home fragment Help
public class Home_Help extends AppCompatActivity {
    ImageSlider imageSlider;
    public Toolbar settings_toolbar;
    public String thisusername;
    TextView help_text,help_title_text;
    //CURRENT USER DETAILS
    String fullname,username,email,phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_help_content);
        Intent intent = getIntent();
        thisusername = intent.getStringExtra("user");
        help_text = findViewById(R.id.help_text);
        help_title_text = findViewById(R.id.help_title);
        settings_toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(settings_toolbar);   //Set toolbar for the settings activity

        //Current Users Details
        SharedPreferences getaccountDetails= getSharedPreferences("ACCOUNT_SHARED_PREF", MODE_PRIVATE);
        fullname = getaccountDetails.getString("fullname","");
        username = getaccountDetails.getString("user","");
        email = getaccountDetails.getString("email","");
        phone = getaccountDetails.getString("mob","");
        //Current Users Details

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);    //Set back button on toolbar
        //Create click listener for back button
        settings_toolbar.setNavigationOnClickListener(view -> {
            Intent returnIntent = new Intent(getApplicationContext(), MainScreen.class);
            returnIntent.putExtra("user",thisusername);
            startActivity(returnIntent);
            finish();       //Close the activity
        });

        imageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.help1,null));
        images.add(new SlideModel(R.drawable.help2,null));
        images.add(new SlideModel(R.drawable.help3,null));
        images.add(new SlideModel(R.drawable.help4,null));
        images.add(new SlideModel(R.drawable.help5,null));
        images.add(new SlideModel(R.drawable.help6,null));
        images.add(new SlideModel(R.drawable.help7,null));
        images.add(new SlideModel(R.drawable.help8,null));
        imageSlider.setImageList(images, ScaleTypes.FIT);
        //Set default title & help text for first image
        String dft_title = "HOME";
        String dft_msg = "Siren\nTrack Me\nWhere are you?\nSOS\nContacts\nFake Caller";
        help_title_text.setText(dft_title);
        help_text.setText(dft_msg);
        //Change description when slider image is changed
        imageSlider.setItemChangeListener(index ->{
          switch(index){
              case 0:
                  String msg1_title = "HOME";
                  String msg1 = "Siren\nTrack Me\nWhere are you?\nSOS\nContacts\nFake Caller";
                  help_title_text.setText(msg1_title);
                  help_text.setText(msg1);
                  break;
              case 1:
                  String msg2_title = "SIREN";
                  String msg2 = "On";
                  help_title_text.setText(msg2_title);
                  help_text.setText(msg2);
                  break;
              case 2:
                  String msg3_title = "SIREN";
                  String msg3 = "Off";
                  help_title_text.setText(msg3_title);
                  help_text.setText(msg3);
                  break;
              case 3:
                  String msg4_title = "TRACK ME";
                  String msg4 = "Track your location\nShare your location with other family members";
                  help_title_text.setText(msg4_title);
                  help_text.setText(msg4);
                  break;
              case 4:
                  String msg5_title = "CONTACTS";
                  String msg5 = "Add Contacts\nDelete Contacts";
                  help_title_text.setText(msg5_title);
                  help_text.setText(msg5);
                  break;
              case 5:
                  String msg6_title = "Where Are You?";
                  String msg6 = "Select a contact from the list\nSend Request\nWait until request is accepted\nView location";
                  help_title_text.setText(msg6_title);
                  help_text.setText(msg6);
                  break;
              case 6:
                  String msg7_title = "FAKE CALLER";
                  String msg7 = "Set name\nSet timer\nSet call";
                  help_title_text.setText(msg7_title);
                  help_text.setText(msg7);
                  break;
              case 7:
                  String msg8_title = "FAKE CALL";
                  String msg8 = "Wait for call\nAccept/Reject Call";
                  help_title_text.setText(msg8_title);
                  help_text.setText(msg8);
                  break;
          }
        });

    }
    //End of Code
}
