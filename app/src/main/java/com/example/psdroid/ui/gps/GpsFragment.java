package com.example.psdroid.ui.gps;
//Import class
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.psdroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//GPS Fragment
public class GpsFragment extends Fragment {
    public GpsFragment() {
        //Constructor
    }
    //Inflate view & Enable menus for this fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true); // Enable Menu for this fragment
        View v = inflater.inflate(R.layout.fragment_gps,container,false);
        SupportMapFragment add_MapSupport = (SupportMapFragment) getSupportMapFragment().findFragmentById(R.id.map);
          add_MapSupport.getMapAsync(googleMap -> {
              LatLng xyz = new LatLng(-34,151);  //Place at this location
              googleMap.addMarker(new MarkerOptions().position(xyz).title("Demo User"));  //Title of the marker
              googleMap.moveCamera(CameraUpdateFactory.newLatLng(xyz));
          });
        return v;
    }
    private FragmentManager getSupportMapFragment() {
        return null;
    }
    //Create GPS Tracking Toolbar menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu_1, @NonNull MenuInflater inflater_1) {
        //Inflate Menu
        menu_1.clear();
        inflater_1.inflate(R.menu.gpstracking_menu, menu_1);
        super.onCreateOptionsMenu(menu_1, inflater_1);
    }
   /*
    // When view is created
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Add support for map
        SupportMapFragment add_MapSupport = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        //Async Map
        add_MapSupport.getMapAsync(googleMap -> {
            //Create the map
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                //On Click add marker
                @Override
                public void onMapClick(LatLng latLng) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(latLng.latitude+ ":" + latLng.longitude);
                    googleMap.clear();
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                    googleMap.addMarker(markerOptions);
                }
            });
        });
        // makeMap();
    }
    */
    //Create the GPS Tracking Toolbar Menu

   /*
    private void makeMap() {
        //Initialize Map
        MapFragment map_view = new MapFragment();
        //Open & load fragment with map
    }
    */
}