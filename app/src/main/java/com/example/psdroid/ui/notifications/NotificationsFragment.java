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
    //recyclerview
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
        ref.child(thisusername).child("Notifications")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        notificationsList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            //get data
                            ModelNotification model = ds.getValue(ModelNotification.class);
                            //add to list
                            notificationsList.add(model);
                        }
                        //Calling Adapter
                        adapterNotification = new AdapterNotification(getActivity(), notificationsList);
                        notificationRv.setAdapter(adapterNotification);
                       /* MySwipeHelper swipeHelper = new MySwipeHelper(getActivity(), notificationRv, 200) {
                            @Override
                            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MySwipeHelper.MyButton> buffer) {
                                buffer.add(new MyButton(getContext(), "Deny", 30, 0, Color.parseColor("#3366ff"),
                                        new MyButtonClickListener() {
                                            @Override
                                            public void onClick(int pos) {
                                                // Toast.makeText(getContext(), "Deny Clicked", Toast.LENGTH_SHORT).show();
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setTitle("Deny");
                                                builder.setMessage("Are you sure to deny this request?");
                                                builder.setPositiveButton("Deny", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                                                        ref.child(thisusername).child("Notifications").child(timestamp).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(getContext(), "Notification Deleted....", Toast.LENGTH_SHORT).show();
                                                                Toast.makeText(getContext(), "Request Denied!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }
                                                });
                                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                });
                                                builder.create().show();
                                            }
                                        }));
                                buffer.add(new MyButton(getActivity(), "Allow", 30, 0, Color.parseColor("#3366ff"),
                                        new MyButtonClickListener() {
                                            @Override
                                            public void onClick(int pos) {
                                                Toast.makeText(getActivity(), "Allow clicked ", Toast.LENGTH_SHORT).show();
                                            }
                                        }));
                            }
                        };*/
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }
}