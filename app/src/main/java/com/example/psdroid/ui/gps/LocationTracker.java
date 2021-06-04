package com.example.psdroid.ui.gps;
// Import class
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
// Location Tracking Services
public class LocationTracker extends Service implements LocationListener {
    private FusedLocationProviderClient mLocationProviderClient;
    private LocationCallback locationUpdatesCallback;
    private LocationRequest locationRequest;
    private String thisusername;

    public LocationTracker(){
        // Constructor
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        System.out.println("Inside onCreate Service");
        setUpLocationRequest();
    }

    private void setUpLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String statusValue = intent.getStringExtra("status");
        thisusername = intent.getStringExtra("name");
        System.out.println("Inside  onStartService");
        Toast.makeText(this,thisusername,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,statusValue,Toast.LENGTH_SHORT).show();
        if(statusValue!=null && statusValue.equals("stop")){
            stopSelf();
        }
        else
            {
            setUpLocationUpdatesCallback();
            mLocationProviderClient.requestLocationUpdates(locationRequest, locationUpdatesCallback, null);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationProviderClient.removeLocationUpdates(locationUpdatesCallback);
    }

    private void setUpLocationUpdatesCallback() {
        locationUpdatesCallback = new LocationCallback(){
            @Override
            public void onLocationResult(@NotNull LocationResult locationResult) {
                if(locationResult!=null){
                    Location lastLocation = locationResult.getLastLocation();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference().child("users").child(thisusername).child("TrackMe");
                    Map<String, Object> data = new HashMap<>();
                    data.put("latitude", lastLocation.getLatitude());
                    data.put("longitude", lastLocation.getLongitude());
                    data.put("status", "ON");
                    ref.setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            System.out.println("Location update saved");
                        }
                    });
                }else{
                    System.out.println("Location null");
                }
            }
        };
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
    //End of Code
}

