package com.example.psdroid.ui.add_users;
//Import Class
import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

//Add Users Activity
public class AddUsersActivity extends AppCompatActivity {
    //ContactPicker Counter
    public static final int PICK_CONTACT = 1;
    //ArrayLst to store contact details
    private ArrayList<String> name_array = new ArrayList<>();
    private ArrayList<String> phone_array = new ArrayList<>();
    //Layout Elements
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    public FloatingActionButton btn;
    public Toolbar addUser_toolbar;
    public ImageView layout_img;
    public TextView layout_txt1,layout_txt2;
    ProgressBar progressBar;

    //Create instance of the screen & display page content as Add users activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_family_activity);      // Set content of main activity as family_friends_activity.xml
        addUser_toolbar = findViewById(R.id.addUser_toolbar);  //Set toolbar for the application
        setSupportActionBar(addUser_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);  //Set back button for the toolbar
        btn = findViewById(R.id.addUsers_btn);
        progressBar = findViewById(R.id.progressBar);  //Initialize progress bar
        //Initialize the Recycler View
        recyclerView = findViewById(R.id.contacts_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Retrieve data from shared pref & if data exist load it on the Recycler view

        //Create click listener for back button
        addUser_toolbar.setNavigationOnClickListener(v -> {
            //Store array into shared pref when back button is clicked//Contacts_SharedPref.storeInList(getApplicationContext(),name_array);
            startActivity(new Intent(getApplicationContext(), MainScreen.class));
            finish();    //Close the activity
        });
        // Add contact clicked
        btn.setOnClickListener(v -> {
            Intent contacts = new Intent(Intent.ACTION_PICK);
            contacts.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(contacts, PICK_CONTACT);
        });
    }

    //Get contact details from the Phone's Contacts
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    // If picking a contact works, send the contact data to contactPicked()
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == PICK_CONTACT) {
                contactPicked(data);
                change_LayoutElements();  //Change the layout elements arrangement when a contact is added
            }
        } else{
            Toast.makeText(this, "Failed to pick contact", Toast.LENGTH_SHORT).show();
        }
    }
    // Request Contacts permission
    public void askForContactPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
            {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }
        }
    }

    //Get contacts
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void contactPicked(Intent data) {
        askForContactPermission(Manifest.permission.READ_CONTACTS, PICK_CONTACT);
        ContentResolver cr = getContentResolver();
        Uri uri = data.getData();
        Cursor cur = cr.query(uri, null, null, null);
        cur.moveToFirst();

        //Fetch details
        String temp_name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        String temp_number = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        cur.close();
        if (name_array.size() == 0) {
            //Set Progress bar to load
            progressBar.setVisibility(View.VISIBLE);
            //Delay of 1 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 1000);
        }
        if (name_array.size() == 5)
        {
            display_snackbar("No more contacts can be added");
        }
        if (name_array.size() < 5) {
            // Assigning values to the array
            name_array.add(temp_name);
            phone_array.add(temp_number);

            //Used for debugging
            for (int i = 0; i < name_array.size(); i++) {
                System.out.println(name_array.get(i));
                System.out.println(phone_array.get(i));
            }
            //Recycler View Adapter Calling
            recyclerViewAdapter = new RecyclerViewAdapter(name_array, phone_array, this);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            display_snackbar("Contact Added");  //Display SnackBar with this text

            //Check if any item is swiped to right
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    //On left swipe remove the contact from the array list
                    name_array.remove(viewHolder.getAdapterPosition());
                    phone_array.remove(viewHolder.getAdapterPosition());
                    recyclerViewAdapter.notifyDataSetChanged();
                    display_snackbar("Contact Removed");  //Display SnackBar with this text
                    if (name_array.size() == 0) {
                        //Set Progress bar to load
                        progressBar.setVisibility(View.VISIBLE);
                        //Delay of 1 seconds
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.INVISIBLE);
                                initial_LayoutElements();   //Set to initial layout when there are 0 contacts in the list
                            }
                        }, 1000);
                    }
                }
            }).attachToRecyclerView(recyclerView);
        }
    }

    //Functions
    //SnackBar Function
    private void display_snackbar(String text) {
        // Show a snack-bar when contact is added
        Snackbar.make(findViewById(R.id.family_friends_layout), text,
                Snackbar.LENGTH_LONG).setAction("X", v -> {
            //Do nothing
        }).setActionTextColor(getColor(R.color.theme_blue)).show();
    }
    //Function to change Layout Design when a contact is added
    private void change_LayoutElements() {
        //Hide elements when one contact is added
        layout_txt2 = findViewById(R.id.contact_text);
        layout_txt2.setVisibility(View.GONE);
        layout_img = findViewById(R.id.contact_img);
        layout_img.setVisibility(View.GONE);
        //Move the contact title to the top when a contact is added to the list
        layout_txt1 = findViewById(R.id.contact_title);
        layout_txt1.setTextSize(30);
    }
    //Function to change Layout Design when all contacts are removed
    private void initial_LayoutElements() {
        //Unhide elements when all contact are removed & Contact list is empty
        layout_txt2 = findViewById(R.id.contact_text);
        layout_txt2.setVisibility(View.VISIBLE);
        layout_img = findViewById(R.id.contact_img);
        layout_img.setVisibility(View.VISIBLE);
        //Move the contact title to the top when a contact is added to the list
        layout_txt1 = findViewById(R.id.contact_title);
        layout_txt1.setTextSize(24);
    }

}


