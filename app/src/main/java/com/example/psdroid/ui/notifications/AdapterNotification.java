package com.example.psdroid.ui.notifications;
//Import Class
import android.app.AlertDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.psdroid.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
// Notification Adapter
public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.HolderNotification>{
    public Context context;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelNotification> notificationsList;

    public AdapterNotification(Context context, ArrayList<ModelNotification> notificationsList) {
        this.context = context;
        this.notificationsList = notificationsList;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public HolderNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate view row
        View view = LayoutInflater.from(context).inflate(R.layout.notifications_content,parent,false);
        return new HolderNotification(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderNotification holder, int position) {
        //get and set data to views

        //get data
        final ModelNotification model = notificationsList.get(position);
        String name = model.getUser();
        //Toast.makeText(context, ""+name, Toast.LENGTH_SHORT).show();
        String notification = model.getNotification();
        String uid = model.getsUid();
        final String timestamp = model.getTimestamp();
        //Toast.makeText(context, ""+name, Toast.LENGTH_SHORT).show();
        String senderUid = model.getsUid();
        //String pTime;
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();
       // Toast.makeText(context, ""+name, Toast.LENGTH_SHORT).show();
        //we will get the ame,email of the user of notification from his uid
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.orderByChild("user").equalTo(name)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            //Toast.makeText(context, ""+name, Toast.LENGTH_SHORT).show();
                            //String name =""+ds.child("user").getValue();
                          //  String mobile =""+ds.child("mobile").getValue();
                                String not = ""+ds.child("Notifications").getValue();
                                model.setNotification(not);
                            //add to model
                          //  model.setUser(name);
                         //   model.setMobile(mobile);
                            //set to views
                            //holder.nameTv.setText(name);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        holder.nameTv.setText(name);
        holder.notificationTv.setText(notification);
        holder.timeTv.setText(pTime);

        holder.itemView.setOnLongClickListener(view -> {
           // Toast.makeText(context, ""+name, Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Deny");
            builder.setMessage("Are you sure you want to deny the request?");
            builder.setPositiveButton("Deny", (dialogInterface, i) -> {
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("users");
                ref1.child(name).child("Notifications").child(timestamp).removeValue()
                        .addOnSuccessListener(aVoid -> {
                         //   Toast.makeText(context, ""+name, Toast.LENGTH_SHORT).show();
                         //   Toast.makeText(context, ""+timestamp, Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, "Notification Deleted....", Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, "Request Denied!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show());
            });builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.create().show();
            return false;
        });
        //click notification for actions
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    //holder class for views of row_notification.xml
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
}
