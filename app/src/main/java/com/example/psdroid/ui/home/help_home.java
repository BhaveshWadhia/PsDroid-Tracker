package com.example.psdroid.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

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

public class help_home extends AppCompatActivity {
    ImageSlider imageSlider;
    public Toolbar settings_toolbar;
    public String thisusername;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.home_helplayout);

        Intent intent = getIntent();
        thisusername = intent.getStringExtra("user");
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
        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);
    }
}
