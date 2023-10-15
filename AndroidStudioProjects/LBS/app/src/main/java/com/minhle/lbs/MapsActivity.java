package com.minhle.lbs;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.pm.PackageManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Locale;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    final private int REQUEST_COARSE_ACCESS = 123;
    boolean permissionGranted = false;
    LocationManager lm;
    LocationListener locationListener;
    final Marker[] temp = new Marker[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_ACCESS);
        } else {
            permissionGranted = true;
        }
        if (permissionGranted) {
            lm.removeUpdates(locationListener);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_ACCESS);
            return;
        } else {
            permissionGranted = true;
        }
        if (permissionGranted) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                Log.d("DEBUG", "Map clicked [" + point.latitude + " / " + point.longitude + "]");
                if (temp[0] != null) {
                    temp[0].remove();
                }
                temp[0] = mMap.addMarker(new MarkerOptions().position(point).title("new pin"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geoCoder.getFromLocation(point.latitude, point.longitude, 1);
                    String add = "";
                    if (addresses.size() > 0) {
                        for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++)
                            add += addresses.get(0).getAddressLine(i) + "\n";
                    }
                    Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_COARSE_ACCESS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                } else {
                    permissionGranted = false;
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location loc) {
            if (loc != null) {
                Toast.makeText(getBaseContext(), "Location changed : Lat: " + loc.getLatitude() + " Lng: " + loc.getLongitude(), Toast.LENGTH_SHORT).show();
                LatLng p = new LatLng((int) (loc.getLatitude()), (int) (loc.getLongitude()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(p));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(7));
            }
        }

        public void onProviderDisabled(String provider) {

        }

        public void onProviderEnabled(String provider) {

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }
    /*
    public void addProximityAlert() {
        //---use the LocationManager class to obtain locations data--
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //---PendingIntent to launch activity if the user is within some locations---
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.amazon.com"))}, 0);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_ACCESS);
            return;
        }else{
            System.out.println("loc is null");
        }
        if(permissionGranted){
            lm.removeUpdates(locationListener);
        }
        lm.addProximityAlert(37.422006, -122.084095, 5, -1, pendingIntent);
    }
    */

    public void onClickMapView(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void onClickSatelliteView(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void onClickCurrentLocation(View view) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_ACCESS);
            return;
        }else{
            permissionGranted = true;
        }
        if(permissionGranted) {
            mMap.setMyLocationEnabled(true);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public void onClick(View view){
        String locationName = ((EditText) findViewById(R.id.editText)).getText().toString();;
        if(!locationName.isEmpty()){
            Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
            try {
                List<Address> addresses = geoCoder.getFromLocationName(locationName, 1);
                if (addresses.size() > 0) {
                    LatLng p = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                    temp[0] = mMap.addMarker(new MarkerOptions().position(p).title("new pin"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(p));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getBaseContext(), "cannot search for nothing", Toast.LENGTH_LONG).show();
        }
    }

}