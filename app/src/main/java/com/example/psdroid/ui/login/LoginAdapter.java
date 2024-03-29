package com.example.psdroid.ui.login;
//Import Class
import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.psdroid.ui.register.SignupTabFragment;

import org.jetbrains.annotations.NotNull;
//Login Fragment Pager Adapter Class
public class LoginAdapter extends FragmentPagerAdapter {
    private final Context context;
    int totalTabs;

    public LoginAdapter(FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    public int getCount() {
        return totalTabs;
    }
    @NotNull
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new LoginTabFragment();
            case 1:
                return new SignupTabFragment();
            default:
                return null;
        }
    }
    //End of Code
}

