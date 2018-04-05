package com.example.lewho.projectappmobile;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.GeoApiContext;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        // getString(R.string.directionsApiKey)
        return geoApiContext.setQueryRateLimit(3)
                .setApiKey("AIzaSyC1jD_TMj1d8JKZh9HvcG8CrRGNBgeelNs")
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
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
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%   " + getIntent().getDoubleExtra("lat", 0.0));
        double lat = getIntent().getDoubleExtra("lat", 0.0);
        double lng = getIntent().getDoubleExtra("lng", 0.0);
        System.out.println("************************** " + lat + "   " + lng);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lng);

        DateTime now = new DateTime();
//        DirectionsResult result = DirectionsApi.newRequest(getGeoContext()).mode(TravelMode.DRIVING).origin(origin).destination(destination).departureTime(now).await();



        mMap.addMarker(new MarkerOptions().position(sydney).title(getIntent().getStringExtra("nom")));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
