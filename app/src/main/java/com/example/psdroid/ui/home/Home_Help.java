package com.example.psdroid.ui.home;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ActionTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.TouchListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
//Home fragment Help
public class Home_Help extends AppCompatActivity {
    ImageSlider imageSlider;
    public Toolbar settings_toolbar;
    public String thisusername;
    TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_help_content);
        Intent intent = getIntent();
        thisusername = intent.getStringExtra("user");
        text = findViewById(R.id.textView6);
        settings_toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(settings_toolbar);   //Set toolbar for the settings activity
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
        imageSlider.setItemChangeListener(index ->{
          switch(index){
              case 0:
                  String message = "•Siren\n" +
                          "•Track Me" +
                          "•Where are you?\n" +
                          "•SOS" +
                          "•Contacts\n" +
                          "•Fake Caller";
                  text.setText(message);
                  break;
              case 1:
                  String message1 = "•Siren:"+"ON";
                  text.setText(message1);
                  break;
              case 2:
                  String message2 = "•Siren:"+"OFF";
                  text.setText(message2);
                  break;
              case 3:
                  String message3 = "Track ME\n"+
                  "•Track your location\n"+
                  "•Share location with other members in family";
              ;
                  text.setText(message3);
                  break;
              case 4:
                  String message4 = "Family/Friends Contact\n"+
                  "•Add Contact\n"+
                  "•Delete Contact\n";
                  text.setText(message4);
                  break;
              case 5:
                  String message5 = "Where are you?\n"+
                  "•Select a contact from the list\n"+
                  "•Check target user\n"+
                  "•Send Request\n";
                  text.setText(message5);
                  break;
              case 6:
                  String message6 = "Fake Caller\n"+"•Set Name\n" +
                          "•Set Timer\n" +
                          "•Set Call\n";
                  text.setText(message6);
                  break;
              case 7:
                  String message7 = "Fake Caller\n"+"•Wait for call\n";
                  text.setText(message7);
                  break;
          }
        });

    }
    //End of Code
}
