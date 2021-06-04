package com.example.psdroid.ui.notifications;
//Import class
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.psdroid.R;
import com.google.android.material.tabs.TabLayout;
//Notification Fragment
public class NotificationsFragment extends Fragment {
    View myFragment;
    ViewPager viewPager;
    TabLayout tabLayout;
    String thisusername;
    //CURRENT USER DETAILS
    String fullname,username,email,phone;

    //Constructor
    public NotificationsFragment() {
    //Constructor
    }

    public NotificationsFragment(String _user) {
        thisusername = _user;
    }
    //Inflate view & Enable menus for this fragment
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment =  inflater.inflate(R.layout.fragment_notifications, container, false);
        viewPager = myFragment.findViewById(R.id.viewpager);
        tabLayout = myFragment.findViewById(R.id.tablayout);

        //Current Users Details
        SharedPreferences getaccountDetails= getActivity().getSharedPreferences("ACCOUNT_SHARED_PREF", Context.MODE_PRIVATE);
        fullname = getaccountDetails.getString("fullname","");
        username = getaccountDetails.getString("user","");
        email = getaccountDetails.getString("email","");
        phone = getaccountDetails.getString("mob","");
        //Current Users Details

        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        NotificationPagerAdapter adapter = new NotificationPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new RequestFragment(thisusername),"Requested Notifications");
        adapter.addFragment(new ResponseFragment(thisusername),"Allowed Notifications");
        viewPager.setAdapter(adapter);
    }
    //End of Code
}