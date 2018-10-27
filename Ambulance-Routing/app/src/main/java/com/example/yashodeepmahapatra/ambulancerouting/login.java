package com.example.yashodeepmahapatra.ambulancerouting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.concurrent.TimeUnit;

//public class login extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
//    GoogleMap mMap;
//    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
//    private GoogleApiClient mGoogleApiClient;
//    public static final String TAG = login.class.getSimpleName();
//    private LocationRequest mLocationRequest;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        mLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                .setInterval(10 * 1000)
//                .setFastestInterval(1 * 1000);
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
////        setUpMapIfNeeded();
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mGoogleApiClient.isConnected()) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  this);
//            mGoogleApiClient.disconnect();
//        }
//    }
//
//    private void handleNewLocation(Location location) {
//        Log.d(TAG, location.toString());
//        double currentLatitude = location.getLatitude();
//        double currentLongitude = location.getLongitude();
//        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
//        MarkerOptions options = new MarkerOptions()
//                .position(latLng)
//                .title("You are here!");
//        mMap.addMarker(options);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//    }
//
//    @SuppressLint("MissingPermission")
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        Log.i(TAG, "Location services connected.");
//        @SuppressLint("MissingPermission") Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if (location == null) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
////            Log.v("SE",this.getClass().toString());
//        }
//        else {
////            Log.v("SE",this.getClass().toString());
//            handleNewLocation(location);
//        };
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        Log.i(TAG, "Location services suspended. Please reconnect.");
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        if (connectionResult.hasResolution()) {
//            try {
//                // Start an Activity that tries to resolve the error
//                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
//            } catch (IntentSender.SendIntentException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
//        }
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        handleNewLocation(location);
//    }
//    private void setUpMap() {
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
//    }
//}
public class login extends FragmentActivity implements LocationListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private String provider;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private String key;
    private DatabaseReference mdbref;
    private String email;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("user_loc");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

// check if enabled and if not send user to the GSP settings
// Better solution would be to display a dialog and suggesting to
// go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        mdbref = mDatabaseReference.push();
        key = mdbref.getKey();
        mdbref.setValue(new UserLocation(email, 0.0, 0.0));
        Log.v("Added to DB", "Success!!");
        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);
        locationManager.requestLocationUpdates(provider, 400, 1, this);
        Log.v("SE","location changed");

        // Initialize the location fields
//        UserLocation ul = null;
//        if (location != null) {
//            Log.v("Provider " ,provider);
//            onLocationChanged(location);
//            ul = new UserLocation(email,location.getLatitude(),location.getLongitude());
//
//        } else {
//            Log.v("XXXX:","Location not available");
//            Log.v("XXXX:","Location not available");
//            ul = new UserLocation(email,0.0,0.0);
//        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }
    @SuppressLint("MissingPermission")
    @Override
    protected void onPause() {
        super.onPause();
//        locationManager.removeUpdates(this);
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }
    @SuppressLint("MissingPermission")
    @Override
    protected void onStop() {
        super.onStop();
//        mdbref.removeValue();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mdbref.removeValue();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("XXXX:",location.toString()+"heyyyyyy");
        Double lat = (Double) (location.getLatitude());
        Double lng = (Double) (location.getLongitude());
        Log.v("XXXX:", ((Double) lng).toString());
        Log.v("XXXX:", ((Double) lat).toString());
        UserLocation ul1 = new UserLocation(email, location.getLatitude(), location.getLongitude());
        mdbref.setValue(ul1);
        LatLng me = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(me).title("I am here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me,15));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}