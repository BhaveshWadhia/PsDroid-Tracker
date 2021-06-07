package com.example.psdroid.ui.introduction;
//Import Class
import android.os.Bundle;
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
// Introductory Activity
public class IntroductoryActivity extends AppCompatActivity {
    ImageView logo,intro;
    LottieAnimationView lottieAnimationView;
    TextView appname;
    private static final int NUM_PAGES = 3;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        logo =  findViewById(R.id.logo);
        intro =  findViewById(R.id.image);
        lottieAnimationView =  findViewById(R.id.tick_lottie);
        appname = findViewById(R.id.appname);

        ViewPager viewPager = findViewById(R.id.pager);
        ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        anim = AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        viewPager.startAnimation(anim);

        intro.animate().translationY(-3000).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        appname.animate().translationY(2300).setDuration(1000).setStartDelay(4000);

    }
    private static class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{
        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new OnBoardingFragment1();
                case 1:
                    return new OnBoardingFragment2();
                case 2:
                    return new OnBoardingFragment3();
            }
            return null;
        }
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
//End of Code
}
