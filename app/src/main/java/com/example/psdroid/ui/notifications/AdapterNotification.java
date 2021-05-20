package com.example.psdroid.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psdroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.text.format.DateFormat;
//import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.HolderNotification>{


    private Context context;
    private ArrayList<ModelNotification> notificationsList;

    public AdapterNotification(Context context, ArrayList<ModelNotification> notificationsList) {
        this.context = context;
        this.notificationsList = notificationsList;
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
        ModelNotification model = notificationsList.get(position);
        String name = model.getsUsername();
        String notification = model.getNotification();
        String timestamp = model.getTimestamp();
        String senderUid = model.getsUid();
        //String pTime;
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();

//we will get the ame,email of the user of notification from his uid
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.orderByChild("user").equalTo(name)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String name =""+ds.child("name").getValue();
                            String email =""+ds.child("email").getValue();

                            //add to model

                            model.setsUsername(name);
                            model.setsEmail(email);
                            //set to views

                            holder.nameTv.setText(name);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        holder.notificationTv.setText(notification);
        holder.timeTv.setText(pTime);
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
