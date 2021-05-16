package com.example.psdroid.ui.home;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.psdroid.R;

import static android.os.Build.VERSION_CODES.R;

public class Siren extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    public void play(){
    if (mediaPlayer==null){
        mediaPlayer = MediaPlayer.create(getApplicationContext(), com.example.psdroid.R.raw.siren);
       mediaPlayer.start();
    }

    if(mediaPlayer!=null){
        mediaPlayer.stop();
        mediaPlayer= null;
    }
    }



}



