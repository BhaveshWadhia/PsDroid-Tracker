package com.example.psdroid.ui.notifications;
//Import class
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.psdroid.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
//Notification Fragment
public class RequestFragment extends Fragment {
    //Recyclerview
    String thisusername;
    RecyclerView notificationRv;
    private FirebaseAuth auth;
    private ArrayList<ModelNotification> notificationsList;
    private AdapterNotification adapterNotification;

    public RequestFragment() {
        //Constructor
    }

    public RequestFragment(String _user) {
        thisusername = _user;
    }
    //Inflate view & Enable menus for this fragment
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);   //Enable options menu for this fragment
        setMenuVisibility(true);  //Enable visibility
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        //Initialize recyclerview
        notificationRv = view.findViewById(R.id.notificationRv);
        auth = FirebaseAuth.getInstance();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean yes = prefs.getBoolean("check_box_preference_2",false);
        if(!yes) {
            Toast.makeText(getContext(), "You may have not allowed to show the notification", Toast.LENGTH_SHORT).show();
        }
        else{
            getAllNotifications();
        }
        // getresponseNotifications();
        return view;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.notifications_help_btn)
        {
           help_message();
        }
        return super.onOptionsItemSelected(item);
    }

    //Create the Notification Toolbar Menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu_1, @NonNull MenuInflater inflater_1) {
        //Inflate Menu
        menu_1.clear();
        inflater_1.inflate(R.menu.notificaitons_menu, menu_1);
        super.onCreateOptionsMenu(menu_1, inflater_1);
    }

    private void getAllNotifications() {
        notificationsList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(thisusername).child("Notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notificationsList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //Get data
                    ModelNotification model = ds.getValue(ModelNotification.class);
                    //Add to list
                    notificationsList.add(model);
                }
                //Calling Adapter
                adapterNotification = new AdapterNotification(getActivity(), notificationsList);
                notificationRv.setAdapter(adapterNotification);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    //Help message function for ths activity
    private void help_message() {
        AlertDialog.Builder alert_builder = new AlertDialog.Builder(getContext());
        alert_builder.setTitle("Notifications");
        alert_builder.setMessage("This page will be notifying you if any of the users of app has requested to access your location.\nYou can allow or deny their request.");
        alert_builder.setCancelable(true);
        alert_builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = alert_builder.create();
        alertDialog.show();
    }
    //End of Code
}