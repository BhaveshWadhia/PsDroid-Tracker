package com.example.psdroid.ui.notifications;
//Import Class
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.psdroid.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
// Notification Adapter
public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.HolderNotification>{
    public Context context;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelNotification> notificationsList;
    FusedLocationProviderClient fusedLocationProviderClient;
    public AdapterNotification(){

    }
    // Constructor for Adapter
    public AdapterNotification(Context context, ArrayList<ModelNotification> notificationsList) {
        this.context = context;
        this.notificationsList = notificationsList;
        firebaseAuth = FirebaseAuth.getInstance();
    }
    //OnCreate of View
    @NonNull @Override
    public HolderNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate view row
        View view = LayoutInflater.from(context).inflate(R.layout.notifications_content,parent,false);
        return new HolderNotification(view);
    }

    // View Holder function for notifications_content.xml
    class HolderNotification extends RecyclerView.ViewHolder{
        //declare views
        TextView nameTv,notificationTv,timeTv;
        public HolderNotification(@NonNull View itemView) {
            super(itemView);
            //init views

            nameTv = itemView.findViewById(R.id.nameTv);
            notificationTv = itemView.findViewById(R.id.notificationTv);
            timeTv = itemView.findViewById(R.id.timeTv);
        }
    }

    // Data Binding Function
    @Override
    public void onBindViewHolder(@NonNull HolderNotification holder, int position) {
        // Get and Set data to views
        // Get data
        final ModelNotification model = notificationsList.get(position);
        String name = model.getUser();
        String uname = model.getUname();
        String notification = model.getNotification();
        String uid = model.getsUid();
        final String timestamp = model.getTimestamp();
        String senderUid = model.getsUid();
        //String pTime;
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.orderByChild("user").equalTo(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String not = (String) ds.child("Notifications").getValue();
                  // String res = (String) ds.child("Location").getValue();
                    model.setNotification(not);
                  //  model.setNotification(res);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        holder.nameTv.setText(name);
        holder.notificationTv.setText(notification);
        holder.timeTv.setText(pTime);
        holder.itemView.setOnLongClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Deny");
            builder.setMessage("Are you sure you want to deny the request?");
            builder.setPositiveButton("Deny", (dialogInterface, i) -> {
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("users");
                ref1.child(uname).child("Notifications").child(timestamp).removeValue().addOnSuccessListener(aVoid -> {
                            Toast.makeText(context, "Notification Deleted....", Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, "Request Denied!", Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(e -> Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show());
            });
            builder.setNegativeButton("Allow", (dialogInterface, i) ->{
               // checkLocation();
                //Check permission
                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    //When Permission is granted
                    // getLocation();
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if(location!=null){
                                try {
                                    Geocoder geocoder = new Geocoder(context,Locale.getDefault());

                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    Toast.makeText(context, ""+addresses.get(0).getLatitude(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(context, ""+addresses.get(0).getLongitude(), Toast.LENGTH_SHORT).show();
                                    Double lat = addresses.get(0).getLatitude();
                                    Double lon = addresses.get(0).getLongitude();
                                    sendrequest(auth.getUid(), "" + name, "" + uname,""+lat,""+lon, "Has allowed you request for location");//uname=sender,name=target_user
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }


                else{
                    //when permission is denied
                    ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }


               //sendrequest(auth.getUid(), "" + name, "" + uname, ""+"123", "Has allowed you request for location");//uname=sender,name=target_user


            });
            builder.create().show();
            return false;
        });
        //Click notification for actions
    }
    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

/*
    private void checkLocation() {
        //Check permission
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //When Permission is granted
            // getLocation();
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if(location!=null){
                        try {
                            Geocoder geocoder = new Geocoder(context,Locale.getDefault());

                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            Toast.makeText(context, ""+addresses.get(0).getLatitude(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, ""+addresses.get(0).getLongitude(), Toast.LENGTH_SHORT).show();
                            sendLocationtoFirebase();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }


        else{
            //when permission is denied
            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }

    }*/

    private void sendrequest(String hisUid, String uname, String username,String lat,String lon ,String notification) {  //uname=target_user,username=sender
        // Get Timestamp
        String timestamp = "" + System.currentTimeMillis();
        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("user", username);   // Requesting users name
        hashMap.put("uname", uname);     // Target Users name
        hashMap.put("timestamp", timestamp);
        hashMap.put("pUid", hisUid);
        hashMap.put("lat", lat);
        hashMap.put("lon", lon);
        hashMap.put("notification", notification);
        //hashMap.put("sUid",myUid);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(uname).child("Location").child(timestamp).setValue(hashMap)
                .addOnSuccessListener(aVoid -> {
                    //Added successfully;
                }).addOnFailureListener(e -> {
            //Failed
        });
    }
    private void sendLocationtoFirebase() {
    }

}
