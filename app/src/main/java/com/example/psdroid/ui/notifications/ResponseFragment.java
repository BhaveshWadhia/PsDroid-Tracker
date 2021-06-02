package com.example.psdroid.ui.notifications;
//Import class
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
public class ResponseFragment extends Fragment  {
    //Recyclerview
    String thisusername;
    RecyclerView notificationRv;
    private FirebaseAuth auth;
    private ArrayList<ModelNotification> notificationsList;
    private ResponseNotificationAdapter responseNotificationAdapter;

    //Constructor
    public ResponseFragment() {

    }

    public ResponseFragment(String _user) {
        thisusername = _user;
    }

    //Inflate view & Enable menus for this fragment
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);   //Enable options menu for this fragment
        setMenuVisibility(true);  //Enable visibility
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        //Initialize recyclerview
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        notificationRv = view.findViewById(R.id.notificationRv);
        auth = FirebaseAuth.getInstance();
       // getAllNotifications();
        Boolean yes = prefs.getBoolean("check_box_preference_2",false);
        if(!yes) {
            Toast.makeText(getContext(), "You may have not allowed to show the notification", Toast.LENGTH_SHORT).show();
        }
        else{
            getresponseNotifications();
        }

        return view;
    }

    //Create the Notification Toolbar Menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu_1, @NonNull MenuInflater inflater_1) {
        //Inflate Menu
        menu_1.clear();
        inflater_1.inflate(R.menu.notificaitons_menu, menu_1);
        super.onCreateOptionsMenu(menu_1, inflater_1);

    }

   private void getresponseNotifications() {
        notificationsList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(thisusername).child("Location").addValueEventListener(new ValueEventListener() {
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
                responseNotificationAdapter = new ResponseNotificationAdapter(getActivity(), notificationsList);
                notificationRv.setAdapter(responseNotificationAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}