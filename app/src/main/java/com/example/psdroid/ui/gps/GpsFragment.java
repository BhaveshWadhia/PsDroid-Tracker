package com.example.psdroid.ui.gps;
//Import class
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.example.psdroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//GPS Fragment
public class GpsFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap map;
    private FirebaseAuth auth;
    MapView mapView;
    String thisusername;
    String responsesenderusername,timestamp;
    public GpsFragment(String _user) {
        //Constructor
        thisusername = _user;
    }

    //Inflate view & Enable menus for this fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true); // Enable Menu for this fragment
        Bundle bundle = this.getArguments();
        responsesenderusername = bundle.getString("key");
        timestamp = bundle.getString("time");
        Toast.makeText(getContext(), "" + responsesenderusername, Toast.LENGTH_SHORT).show();
        auth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_gps, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu_1, @NonNull MenuInflater inflater_1) {
        //Inflate Menu
        menu_1.clear();
        inflater_1.inflate(R.menu.gpstracking_menu, menu_1);
        super.onCreateOptionsMenu(menu_1, inflater_1);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.mapView);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {


            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(thisusername).child("Location").child(timestamp);

        ValueEventListener listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               // Toast.makeText(getContext(), "Hii", Toast.LENGTH_SHORT).show();
                String Lat = (String)snapshot.child("lat").getValue();
                String Lon = (String)snapshot.child("lon").getValue();
                String u = (String) snapshot.child("uname").getValue();



              /*  Toast.makeText(getContext(), "" + timestamp, Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "" + u, Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "" + Lat + Lon, Toast.LENGTH_SHORT).show();*/
                LatLng location = new LatLng(Double.parseDouble(Lat), Double.parseDouble(Lon));

              //  LatLng location = new LatLng(Lat,Lon);
                map.addMarker(new MarkerOptions().position(location).title(responsesenderusername+"'s Location"));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14F));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        map.setMyLocationEnabled(true);
    }
}
