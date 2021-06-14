package com.example.psdroid.ui.home;
//Import Class
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import com.example.psdroid.R;
import com.example.psdroid.ui.add_users.AddUsersActivity;
import com.example.psdroid.ui.add_users.Contacts_SharedPref;
import com.example.psdroid.ui.login.LoginActivity;
import com.example.psdroid.ui.settings.SettingsActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.hitomi.cmlibrary.CircleMenu;

import java.util.ArrayList;
//Home Fragment
public class HomeFragment extends Fragment {
    WifiManager change_wifi;
    MediaPlayer mediaPlayer;
    final int SEND_SMS_PERMISSION_CODE = 1;
    boolean gps_switch = true, wifi_switch = true;
    FusedLocationProviderClient client;
    double latitude, longitude;
    SmsManager smsManager;
    //CURRENT USER DETAILS
    String thisusername,fullname,username,email,phone;
    // Layout Elements
    ImageView centerImg;
    boolean menuOpened = false;
    //Constructor
    public HomeFragment(String _user) {
        thisusername = _user;
    }

    public HomeFragment() {
        //Constructor
    }

    //Inflate view & Enable menus for this fragment
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Current Users Details
        SharedPreferences getaccountDetails= getActivity().getSharedPreferences("ACCOUNT_SHARED_PREF", Context.MODE_PRIVATE);
        fullname = getaccountDetails.getString("fullname","");
        username = getaccountDetails.getString("user","");
        email = getaccountDetails.getString("email","");
        phone = getaccountDetails.getString("mob","");
        //Current Users Details
        setHasOptionsMenu(true);   //Enable options menu for this fragment
        setMenuVisibility(true);  //Enable visibility
        centerImg = getActivity().findViewById(R.id.center_image);
        client = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());
        change_wifi = (WifiManager) requireContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);    // **This is not working** //
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    //When view is created
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Load the circle menu
        CircleMenu home_circleMenu = requireView().findViewById(R.id.circle_menu);
        home_circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.cm_ic_start, R.drawable.cm_ic_cancel)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.cm_ic_siren)
                .addSubMenu(Color.parseColor("#30A400"), R.drawable.cm_ic_trackme) //change it
                .addSubMenu(Color.parseColor("#FAD02C"), R.drawable.cm_ic_where_are_you)
                .addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.ic_cm_sos_foreground)
                .addSubMenu(Color.parseColor("#8A39FF"), R.drawable.ic_adduser)  // contact list icon
                .addSubMenu(Color.parseColor("#FF6A00"), R.drawable.cm_ic_fakecall)
                .setOnMenuSelectedListener(index -> {
                    switch (index) {
                        case 0:
                            siren_function();
                            break;
                        case 1:
                            trakMe_function();
                            break;
                        case 2:
                            call_whereareyou_activity();
                            break;
                        case 3:
                            Toast.makeText(getContext(), "Coming Soon...", Toast.LENGTH_SHORT).show();
                            // Not implemented yet
                            break;
                        case 4:
                            call_addUser_activity();  // View/Update contacts activity
                            break;
                        case 5:
                            call_fakeCall_activity();  //Fake Caller page activity
                            break;
                    }
                });
        // Set click listener so that image can be changed
        home_circleMenu.setOnClickListener(view1 -> {
            if(menuOpened) {
                menuOpened = false;
                centerImg.setVisibility(View.VISIBLE);  //When menu is closed show the image
            }
            else {
                menuOpened = true;
                centerImg.setVisibility(View.GONE); //Hide image when menu is opened
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
    @SuppressLint("CommitPrefEdits")
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home_gps_btn) {
            if (gps_switch) {
                item.setIcon(R.drawable.ic_gps_off);
                gps_switch = false;
            } else {
                item.setIcon(R.drawable.ic_gps_on);
                gps_switch = true;
            }
        }
        if (id == R.id.home_wifi_btn) {
            if (wifi_switch) {
                item.setIcon(R.drawable.ic_wifi_off);
                change_wifi.setWifiEnabled(false);               // **This is not working** //
                wifi_switch = false;
            } else {
                item.setIcon(R.drawable.ic_wifi_on);
                wifi_switch = true;
            }
        }
        if (id == R.id.home_help_btn) {
            Intent intent = new Intent(getActivity(), HomeHelp.class);
            intent.putExtra("user", thisusername);
            startActivity(intent);
        }
        if (id == R.id.home_settings_btn) {
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            intent.putExtra("user", thisusername);
            startActivity(intent);
        }
        if (id == R.id.home_logout_btn) {
            //Display an alert before logging out
            AlertDialog.Builder alert_builder = new AlertDialog.Builder(getContext());
            alert_builder.setMessage("Are you sure you want to logout?");
            alert_builder.setCancelable(true);
            alert_builder.setPositiveButton("Exit", (dialog, which) -> logout());
            alert_builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = alert_builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    //Creating a delay function for add user activity to load
    private void call_addUser_activity() {
        new Handler().postDelayed(this::activitytoadduser, 800);// 0.8s Delay
    }

    private void activitytoadduser() {
        Intent intent = new Intent(getActivity(), AddUsersActivity.class);
        intent.putExtra("user", thisusername);
        startActivity(intent);
    }

    //Creating a delay function for add user activity to load
    private void call_fakeCall_activity() {
        new Handler().postDelayed(this::activitytofakecall, 800);// 0.8s Delay
    }

    private void activitytofakecall() {
        Intent intent = new Intent(getActivity(), FakeCallerActivity.class);
        intent.putExtra("user", thisusername);
        startActivity(intent);
    }

    //Creating a delay function for where are you activity to load
    private void call_whereareyou_activity() {
        //  Intent intent = new Intent(getActivity(),WhereAreYourActivity.class)
        new Handler().postDelayed(this::wry_activity, 800);// 0.8s Delay
    }

    // Send username of current application user to the Where Are You Activity
    private void wry_activity() {
        Intent intent = new Intent(getActivity(), WhereAreYourActivity.class);
        intent.putExtra("user", thisusername);
        startActivity(intent);
    }

    // Main Functions of the Application
    // Track Me Function
    private void trakMe_function() {
        sendSMS_Loction();  //Get GPS location & send SMS
    }
    /*
    // Send Location
    private void sendLocation() {
        //Get location of current user  send to database
        System.out.println("Inside Send Location");
        Intent intent = new Intent(getActivity().getBaseContext(),LocationTracker.class);
        intent.putExtra("user", thisusername);
        intent.putExtra("status",trackMe_status);
        getActivity().startService(intent);
    }
    */

    //Siren Function
    private void siren_function() {
        // Get user's saved settings
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String siren = prefs.getString("list_preference_1", "default");
        //If siren settings is not set by user
        if (siren.equals("default")) {
            Toast.makeText(getContext(), "Siren Sound not selected...\nPlease select it from settings!!", Toast.LENGTH_SHORT).show();
        }
        //If siren setting is set then check which one is set
        else {
            if (mediaPlayer == null) {
                if (siren.equals("police")) {
                    mediaPlayer = MediaPlayer.create(getActivity(), com.example.psdroid.R.raw.police_siren);
                }
                if (siren.equals("scream")) {
                    mediaPlayer = MediaPlayer.create(getActivity(), com.example.psdroid.R.raw.scream_siren);
                }
                assert mediaPlayer != null;
                mediaPlayer.start();
                Toast.makeText(getContext(), "Siren ON...", Toast.LENGTH_SHORT).show();
            }
            else {
                mediaPlayer.stop();
                mediaPlayer = null;
                Toast.makeText(getContext(), "Siren OFF...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Get location function
    private void sendSMS_Loction() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }
        //When permission is granted
        else{
            smsPermission();
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                client.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        smsManager = SmsManager.getDefault(); //Get the sms manager to send the SMS
                        ArrayList<String> phone_array;
                        phone_array = Contacts_SharedPref.retrieve_phoneFromList(getContext());   //Get all the contacts from the list
                        // Message to be send
                        String loc_link = "https://maps.google.com/maps?saddr="+latitude+","+longitude;
                        String message = "PsDroid Tracker wants to notify you that "+"\""+fullname+"\""+" has turned on their location tracking, their current location is ";
                        System.out.println(message);
                        if(phone_array.size()==0)
                        {
                            Toast.makeText(getActivity(), "You need to at least add one contact into the list in order to share your location", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            for (String number : phone_array) {
                                try {
                                    System.out.println("Inside TRY of SMS");
                                    System.out.println(number);
                                    System.out.println(message);
                                    smsManager.sendTextMessage(number, null, message, null, null); //Send SMS Message
                                    smsManager.sendTextMessage(number, null, loc_link, null, null); //Send SMS Lnk
                                } catch (Exception exception) {
                                    Toast.makeText(getActivity(), "SMS Service Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
            else {
                Toast.makeText(getActivity().getApplicationContext(), "GPS provider was not found, try manually turning on your gps location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Check permission for SMS
    private void smsPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Your location is being tracked and shared to all contacts", Toast.LENGTH_SHORT).show();
        }
        else
            {
            //If permission is not granted then ask for permission
            getActivity().requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 100);
        }
    }
    //Get Permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Check condition for sending SMS
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //GPS Permission is granted now try sending SMS again
            sendSMS_Loction();
        }
        else {
            Toast.makeText(getActivity(), "Please give the permission for SMS from your phone's settings", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 101 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //SMS Permission is granted now try sending SMS again
            sendSMS_Loction();
        }
        else {
            Toast.makeText(getActivity(), "Please give the permission for GPS from your phone's settings", Toast.LENGTH_SHORT).show();
        }
    }

    //Logout function
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        //Clear Account Shared Pref
        SharedPreferences acctSharedPreferences = getActivity().getSharedPreferences("ACCOUNT_SHARED_PREF",Context.MODE_PRIVATE);
        SharedPreferences.Editor acctEditor = acctSharedPreferences.edit();
        acctEditor.clear();
        acctEditor.apply();
        //Set isLoggedIn from Login Shared Pref to false
        SharedPreferences isLoggedIn = getActivity().getSharedPreferences("LOGIN_SHARED_PREF",Context.MODE_PRIVATE);
        SharedPreferences.Editor loginEditor = isLoggedIn.edit();
        loginEditor.putString("isLoggedIn","false");
        loginEditor.apply();
        //Clear contact shared pref
        Contacts_SharedPref.clearSharedPref(getActivity());
        //LOG OUT from app and return to login page
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
    //End of Code
}