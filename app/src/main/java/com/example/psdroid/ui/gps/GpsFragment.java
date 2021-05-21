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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.example.psdroid.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
//GPS Fragment
public class
GpsFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap map;
    MapView mapView;
    String thisusername;
    public GpsFragment(String _user) {
        //Constructor
        thisusername = _user;
    }

    //Inflate view & Enable menus for this fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true); // Enable Menu for this fragment
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
        { return; }
        map.setMyLocationEnabled(true);
    }
}
