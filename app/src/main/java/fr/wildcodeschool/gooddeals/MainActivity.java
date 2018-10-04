package fr.wildcodeschool.gooddeals;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class MainActivity extends NavbarActivity implements OnMapReadyCallback {

    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    public static ArrayList dealArrayList() {

        ArrayList deals = new ArrayList();

        deals.add(new Deal("SLD Café", "-20%", 43.5996366, 1.4438431999999466));
        deals.add(new Deal("SUBWAY", "-30%", 43.59993009999999, 1.4439228999999614));
        deals.add(new Deal("Bagelstein", "-15%", 43.5988244, 1.4442524999999478));
        deals.add(new Deal("Columbus Café & Co", "-10%", 43.59942960000001, 1.4440872999999783));
        deals.add(new Deal("BDM", "HAPPY HOUR (18h-20h) demi 3E pinte 4E", 43.5998993, 1.4435880999999426));
        deals.add(new Deal("Chocolat de Neuville", "-15%", 43.599738, 1.4444590000000517));
        deals.add(new Deal("Magnolia Café", "-10%", 43.5993325, 1.4438192999999728));
        deals.add(new Deal("Duck Me", "Une plache de canardises offerte pour l'achat d'une bouteille", 43.6021283, 1.4468752000000222));
        deals.add(new Deal("Pitaya", "-15%", 43.599064, 1.4439680000000408));
        deals.add(new Deal("Le Fénétra", "-10%", 43.6015097, 1.4428634999999304));
        deals.add(new Deal("L'inde", "-10%", 43.5983318, 1.4442956000000322));
        deals.add(new Deal("BWAMOA", "-15%", 43.6007651, 1.444766399999935));
        deals.add(new Deal("Body' Minute", "Crte Abonnée gratuite qui donne accès à des tarifs réduits", 43.6007528, 1.4447450999999774));
        deals.add(new Deal("Kreme", "-15%", 43.5990122, 1.4442180999999437));
        deals.add(new Deal("Au Péché Mignon", "-10%", 43.5986127, 1.4454370999999355));
        deals.add(new Deal("La Manufacture des Carmes", "-20%", 43.5986274, 1.4442845999999463));
        deals.add(new Deal("Santosha", "10E le plat", 43.5982421, 1.4442629000000125));
        deals.add(new Deal("JEAN-PASCAL COLLIN", "-10%", 43.5993921, 1.443273500000032));
        deals.add(new Deal("Beach Park Toulouse", "", 43.5924694, 1.3089104000000589));
        deals.add(new Deal("Karl Maison", "-10%", 43.6014334, 1.443336899999963));

        return deals;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                mMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button accept = findViewById(R.id.button);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToSearch = new Intent(MainActivity.this,
                        DealsActivity.class);
                startActivity(goToSearch);
            }
        });

        initDrawer();

        if (isServicesOK()) {
            getLocationPermission();
        }

    }

    private void getDeviceLocation() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            if (currentLocation != null) {
                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);

                            }

                        } else {
                            Toast.makeText(MainActivity.this, R.string.unableCurrentLocation, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity.this);


    }


    public boolean isServicesOK() {

        int available = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            return true;

        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance()
                    .getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, R.string.mapRequests, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }

        } else {
            ActivityCompat.requestPermissions(this, permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }


}
