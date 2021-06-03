package com.example.psdroid.ui.home;
// Import Class
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
import com.example.psdroid.ui.add_users.Contacts_SharedPref;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
//Where are you activity
public class WhereAreYourActivity extends AppCompatActivity implements WRY_RecyclerViewAdapter.clickInterface {
    Toolbar wry_toolbar;
    ArrayList<String> name_array, phone_array = new ArrayList<>();
    RecyclerView recyclerView;
    WRY_RecyclerViewAdapter recyclerViewAdapter;
    Boolean checkerBool = Boolean.TRUE;  // Currently target user is not checked
    TextView hidden_txt;
    String sender_user,target_user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sender_user = getIntent().getStringExtra("user");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whereareyou_activity);
        wry_toolbar = findViewById(R.id.where_are_you_toolbar);  //Set toolbar for the application
        hidden_txt= findViewById(R.id.nocontacts_hidden_txt);
        setSupportActionBar(wry_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);  //Set back button for the toolbar
        //Initialize the Recycler View
        recyclerView = findViewById(R.id.wry_contact_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Create click listener for back button
        wry_toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),MainScreen.class);
            intent.putExtra("user",sender_user);
            startActivity(intent);
            finish();    //Close the activity
        });

        //Retrieve data from shared pref & if data exist load it on the Recycler view
        name_array = Contacts_SharedPref.retrieve_nameFromList(this);
        phone_array = Contacts_SharedPref.retrieve_phoneFromList(this);
        //Recycler View Adapter Calling
        recyclerViewAdapter = new WRY_RecyclerViewAdapter(name_array, phone_array, this, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (name_array.size()==0)
        {
            // Do nothing display no contacts
            hidden_txt.setVisibility(View.VISIBLE);
        }
    }
    // When item is clicked send 'where are you' request to the particular user
    @Override
    public void onClicked(int pos) {
        //Get the data of the user which is clicked & then send it to the firebase database through a request
        String sendToName = name_array.get(pos);
        String sendToPhone = phone_array.get(pos);
        System.out.println("SendToPhone = "+sendToPhone);
        //Check all users phone number in the database & compare if the target user exist
        Query checkuser = FirebaseDatabase.getInstance().getReference("users").orderByChild("user");
        ValueEventListener eventListener_1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Get details of the user which is selected
                    String uname = ds.child("user").getValue(String.class);
                    String mob = ds.child("mobile").getValue(String.class);

                    System.out.println("Uname = "+uname);
                    System.out.println("Mob = "+mob);
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                    assert mob != null;
                    if (mob.equals(sendToPhone)) {
                        //Set boolean to true when phone number is matched & get username of that user
                        checkerBool =false;
                        target_user = uname;
                        System.out.println("targetusername = "+target_user);
                        //Send request to that particular user
                        sendrequest(auth.getUid(), "" + target_user, "" + sender_user, "" + mob, "Wants to access your location");
                        Toast.makeText(getApplicationContext(), "Tracking request has been made\nPlease wait until user accepts request", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                //If checkerBool value is true then user does not exist
                if (checkerBool) {
                    //Display that the user which is clicked does not have a PsDroid Account
                    Toast.makeText(getApplicationContext(), "The user doesn't exist...\nTry to invite them", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // If operation cancelled
            }
        };
        checkuser.addListenerForSingleValueEvent(eventListener_1);


    }

    //Function
    // Send Request Function
    private void sendrequest(String hisUid, String uname, String username, String mob, String notification) {
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
        ref.child(target_user).child("Notifications").child(timestamp).setValue(hashMap)
                .addOnSuccessListener(aVoid -> {
                    //Added successfully;
                }).addOnFailureListener(e -> {
            //Failed
        });
    }
    //End of Code
}