package com.example.psdroid.ui.notifications;
//Import class
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
public class NotificationsFragment extends Fragment {
    //Recyclerview
    String thisusername;
    RecyclerView notificationRv;
    private FirebaseAuth auth;
    private ArrayList<ModelNotification> notificationsList;
    private AdapterNotification adapterNotification;

    //Constructor
    public NotificationsFragment(String _user) {
        thisusername = _user;
    }

    //Inflate view & Enable menus for this fragment
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true); //Enable options menu for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        //Initialize recyclerview
        notificationRv = view.findViewById(R.id.notificationRv);
        auth = FirebaseAuth.getInstance();
        getAllNotifications();
        return view;
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
}