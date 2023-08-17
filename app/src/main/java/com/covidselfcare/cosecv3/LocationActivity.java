package com.covidselfcare.cosecv3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.content.pm.PackageManager;


import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class LocationActivity extends AppCompatActivity{
    private TextView tvlatitude, tvlongitude;
    private Button blocation;

    FusedLocationProviderClient fusedLocationProviderClient;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        blocation = findViewById(R.id.button_location);
        tvlatitude = findViewById(R.id.latitude);
        tvlongitude = findViewById(R.id.longitude);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                LocationActivity.this
        );


        blocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(ActivityCompat.checkSelfPermission(LocationActivity.this
                ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(LocationActivity.this
                ,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }else {
                    ActivityCompat.requestPermissions(LocationActivity.this
                        , new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            ,Manifest.permission.ACCESS_COARSE_LOCATION}
                            ,100);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1]
                == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();
        } else {
            Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE
        );
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            // When Location service is enabled
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if(location != null){
                        tvlatitude.setText(String.valueOf(location.getLatitude()));
                        tvlongitude.setText((String.valueOf(location.getLongitude())));
                    }else {
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                tvlatitude.setText(String.valueOf(location1.getLatitude()));
                                tvlongitude.setText(String.valueOf(location1.getLongitude()));
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(null,locationCallback, Looper.myLooper());
                    }
                }
            });
        }else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

}