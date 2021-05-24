package com.example.psdroid.ui.login;
//Import Class

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.psdroid.R;
import com.example.psdroid.ui.home.FakeCallerActivity;
import com.example.psdroid.ui.login.LoginActivity;

// Introductory Activity
public class LoadingActivity extends AppCompatActivity {
    public ImageView logo, intro;
    public LottieAnimationView lottieAnimationView;
    public TextView appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);
        logo = findViewById(R.id.logo);
        intro = findViewById(R.id.image);
        lottieAnimationView = findViewById(R.id.tick_lottie);
        appname = findViewById(R.id.appname);
        intro.animate().translationY(-3000).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        appname.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        new Handler().postDelayed(() -> startActivity(new Intent(this, LoginActivity.class)), 5000);
    }
}
