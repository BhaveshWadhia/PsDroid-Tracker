package com.example.psdroid.ui.home;
//Import Class
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.psdroid.R;
import com.example.psdroid.ui.add_users.AddUsersActivity;
import com.example.psdroid.ui.login.LoginActivity;
import com.example.psdroid.ui.settings.SettingsActivity;
import com.hitomi.cmlibrary.CircleMenu;

import java.util.Objects;

import static androidx.core.content.ContextCompat.getSystemService;

//Home Fragment
public class HomeFragment extends Fragment {
    public WifiManager change_wifi;
    MediaPlayer mediaPlayer;
    public HomeFragment() {
        //Constructor
    }
    //Inflate view & Enable menus for this fragment
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);   //Enable options menu for this fragment
        setMenuVisibility(true);  //Enable visibility
        change_wifi = (WifiManager) requireContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);    // **This is not working** //
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    //When view is created load the menus
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CircleMenu home_circleMenu = requireView().findViewById(R.id.circle_menu);
        home_circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.cm_ic_start, R.drawable.cm_ic_cancel)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.cm_ic_siren)
                .addSubMenu(Color.parseColor("#30A400"), R.drawable.cm_ic_trackme) //change it
                .addSubMenu(Color.parseColor("#FF4B32"), R.drawable.cm_ic_where_are_you)
                .addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.ic_cm_sos_foreground)
                .addSubMenu(Color.parseColor("#8A39FF"), R.drawable.ic_adduser)  // contact list icon
                .addSubMenu(Color.parseColor("#FF6A00"), R.drawable.cm_ic_fakecall)
                .setOnMenuSelectedListener(index -> {
                    switch (index) {
                        case 0:
                            Toast.makeText(getContext(), "Siren...", Toast.LENGTH_SHORT).show();
                           // MediaPlayer mediaPlayer;
                            if(mediaPlayer==null) {
                                mediaPlayer = MediaPlayer.create(getActivity(), com.example.psdroid.R.raw.siren);
                                mediaPlayer.start();
                            }
                            else{
                                mediaPlayer.stop();
                                mediaPlayer=null;
                            }
                            // Call SIREN FUNCTION
                            break;
                        case 1:
                            Toast.makeText(getContext(), "Your location is being tracked", Toast.LENGTH_SHORT).show();
                            // Call SHARE LOCATION FUNCTION
                            break;
                        case 2:
                            Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();

                            break;
                        case 3:
                            Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
                            // SEND ALERT SIGNALS TO ALL CONTACT
                            break;
                        case 4:
                            Toast.makeText(getContext(), "4", Toast.LENGTH_SHORT).show();
                            call_addUser_activity();  // View/Update contacts page
                            break;
                        case 5:
                            Toast.makeText(getContext(), "5", Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
    }
    //Create the Home Toolbar Menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu_1, @NonNull MenuInflater inflater_1) {
        //Inflate Menu
        menu_1.clear();
        inflater_1.inflate(R.menu.home_menu, menu_1);
        super.onCreateOptionsMenu(menu_1, inflater_1);
    }
    //When any item from others menu is selected
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home_gps_btn){
                item.setIcon(R.drawable.ic_gps_off);
        }
        if (id == R.id.home_wifi_btn){
            item.setIcon(R.drawable.ic_wifi_off);
            change_wifi.setWifiEnabled(false);               // **This is not working** //
        }
        if (id == R.id.home_help_btn) {
            Toast.makeText(getContext(), "Helping...", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.home_settings_btn) {
           // Toast.makeText(getContext(), "Settings...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), SettingsActivity.class));
        }
        if (id == R.id.home_logout_btn) {
           //Display an alert before logging out
            AlertDialog.Builder alert_builder = new AlertDialog.Builder(getContext());
            alert_builder.setMessage("Are you sure you want to logout?");
            alert_builder.setCancelable(true);
            alert_builder.setPositiveButton("Exit", (dialog, which) -> {
                // Toast.makeText(getContext(), "Logging-Out Soon...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
                //LOG OUT from app and return to login page
            });
            alert_builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = alert_builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    //Creating a delay function for add user activity to load
    private void call_addUser_activity() {
        new Handler().postDelayed(() -> startActivity(new Intent(getContext(), AddUsersActivity.class)),800);// 0.8s Delay
    }

}