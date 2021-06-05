package com.example.psdroid.ui.notifications;
//Import Class
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.psdroid.R;
import com.example.psdroid.ui.gps.GpsFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
// Notification Adapter
public class ResponseNotificationAdapter extends RecyclerView.Adapter<ResponseNotificationAdapter.HolderNotification> {
    public Context context;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelNotification> notificationsList;
    FusedLocationProviderClient fusedLocationProviderClient;
    Boolean value=false;
    public ResponseNotificationAdapter(){
    //Constructor
    }
    // Constructor for Adapter
    public ResponseNotificationAdapter(Context context, ArrayList<ModelNotification> notificationsList) {
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
                    String not = (String) ds.child("Location").getValue();
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
            builder.setTitle("Delete");
            builder.setMessage("Are you sure you want to delete this notification?");
            builder.setPositiveButton("Delete", (dialogInterface, i) -> {
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("users");
                ref1.child(uname).child("Location").child(timestamp).removeValue().addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Notification Deleted", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context, "Request Denied!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show());
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) ->{

                  // sendrequest(auth.getUid(), "" + name, "" + uname, ""+"123", "Has allowed you request for location");//uname=sender,name=target_user
            });
            builder.create().show();
            return false;
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        holder.itemView.setOnClickListener(view -> {
            Toast.makeText(context, "Redirecting to Map Fragment "+name, Toast.LENGTH_SHORT).show();
          //  sendLocation();
            Bundle bundle = new Bundle();
            bundle.putString("key",name);
            bundle.putString("time",timestamp);
           // bundle.putBoolean("true",value);
            GpsFragment fragment = new GpsFragment(uname);
            fragment.setArguments(bundle);
            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
        });
        //Click notification for actions
    }

    private void sendrequest(String hisUid, String uname, String username,String mob ,String notification) {  //uname=target_user,username=sender
        // Get Timestamp
        String timestamp = "" + System.currentTimeMillis();
        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("user", username);   // Requesting users name
        hashMap.put("uname", uname);     // Target Users name
        hashMap.put("timestamp", timestamp);
        hashMap.put("pUid", hisUid);
        hashMap.put("mobile", mob);
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
    @Override
    public int getItemCount() {
        return notificationsList.size();
    }
//End of Code
}
