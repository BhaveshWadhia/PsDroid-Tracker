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
import android.provider.ContactsContract;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.Objects;

//Add Users Activity
public class AddUsersActivity extends AppCompatActivity {
    public static final int PICK_CONTACT = 1;
    //ArrayLst to store contact details
    ArrayList <String> contact_name;
    ArrayList <String> contact_number;
    ArrayList <String> contact_photo;
    public FloatingActionButton btn;
    public Toolbar addUser_toolbar;
    //Create instance of the screen & display page content as Add users activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_family_activity);      // Set content of main activity as family_friends_activity.xml
        addUser_toolbar = findViewById(R.id.addUser_toolbar);  //Set toolbar for the application
        setSupportActionBar(addUser_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);  //Set back button for the toolbar
        //Initialize array list
        contact_name = new ArrayList<>();
        contact_number = new ArrayList<>();
        contact_photo = new ArrayList<>();
        btn = findViewById(R.id.addUser_btn);
        //Create click listener for back button
        addUser_toolbar.setNavigationOnClickListener(v -> {
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
        String temp_photo = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
        contact_name.add(temp_name);
        contact_number.add(temp_number);
        contact_photo.add(temp_photo);
        cur.close();
        System.out.println(temp_name);
        System.out.println(temp_number);
        System.out.println(temp_photo);

        // Show a snack-bar when contact is added
        Snackbar.make(findViewById(R.id.family_friends_layout), "Contact Added",
                Snackbar.LENGTH_LONG).setAction("X", v -> {
                    //Do nothing
                }).setActionTextColor(getColor(R.color.theme_blue)).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    // If picking a contact works, send the contact data to contactPicked()
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == PICK_CONTACT) {
                contactPicked(data);
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
}


