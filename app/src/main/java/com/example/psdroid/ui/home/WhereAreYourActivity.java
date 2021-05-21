package com.example.psdroid.ui.home;
// Import Class
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
import com.example.psdroid.ui.add_users.Contacts_SharedPref;
import com.example.psdroid.ui.add_users.RecyclerViewAdapter;
import java.util.ArrayList;
import java.util.Objects;

public class WhereAreYourActivity extends AppCompatActivity implements WRY_RecyclerViewAdapter.clickInterface {
    Toolbar wry_toolbar;
    ArrayList<String> name_array,phone_array = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whereareyou_activity);
        setContentView(R.layout.friends_family_activity);      // Set content of main activity as family_friends_activity.xml
        wry_toolbar = findViewById(R.id.where_are_you_toolbar);  //Set toolbar for the application
        setActionBar(wry_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);  //Set back button for the toolbar

        //Create click listener for back button
        wry_toolbar.setNavigationOnClickListener(v -> {
            //Store array into shared pref when back button is clicked//Contacts_SharedPref.storeInList(getApplicationContext(),name_array);
            startActivity(new Intent(getApplicationContext(), MainScreen.class));
            finish();    //Close the activity
        });
        //Retrieve data from shared pref & if data exist load it on the Recycler view
        name_array = Contacts_SharedPref.retrieve_nameFromList(this);
        phone_array = Contacts_SharedPref.retrieve_phoneFromList(this);
        //Recycler View Adapter Calling
        recyclerViewAdapter = new RecyclerViewAdapter(name_array, phone_array, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    // When item is clicked send 'where are you' request to the paticular user
    @Override
    public void onClicked(int pos) {
        //Get the data of the user which is clicked & then send it to the firebase database through a request
        Toast.makeText(this, (String) name_array.get(pos),Toast.LENGTH_SHORT).show();
    }
}
