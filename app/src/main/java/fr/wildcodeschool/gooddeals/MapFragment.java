package fr.wildcodeschool.gooddeals;


import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class MapFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {


    private static final String TAG = "MapFragment";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    public static ArrayList<Deal> dealArrayList() {

        ArrayList<Deal> deals = new ArrayList<>();

        deals.add(new Deal("SLD Café", "-20%", 43.5996366, 1.4438431999999466, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("SUBWAY", "-30%", 43.59993009999999, 1.4439228999999614, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("Bagelstein", "-15%", 43.5988244, 1.4442524999999478, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("Columbus Café & Co", "-10%", 43.59942960000001, 1.4440872999999783, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("BDM", "HAPPY HOUR (18h-20h) demi 3E pinte 4E", 43.5998993, 1.4435880999999426, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("Chocolat de Neuville", "-15%", 43.599738, 1.4444590000000517, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("Magnolia Café", "-10%", 43.5993325, 1.4438192999999728, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("Duck Me", "Une plache de canardises offerte pour l'achat d'une bouteille", 43.6021283, 1.4468752000000222, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("Pitaya", "-15%", 43.599064, 1.4439680000000408, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("Le Fénétra", "-10%", 43.6015097, 1.4428634999999304, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("L'inde", "-10%", 43.5983318, 1.4442956000000322, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("BWAMOA", "-15%", 43.6007651, 1.444766399999935, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("Body' Minute", "Crte Abonnée gratuite qui donne accès à des tarifs réduits", 43.6007528, 1.4447450999999774, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("Kreme", "-15%", 43.5990122, 1.4442180999999437, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("Au Péché Mignon", "-10%", 43.5986127, 1.4454370999999355, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("La Manufacture des Carmes", "-20%", 43.5986274, 1.4442845999999463, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("Santosha", "10E le plat", 43.5982421, 1.4442629000000125, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("JEAN-PASCAL COLLIN", "-10%", 43.5993921, 1.443273500000032, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("Beach Park Toulouse", "", 43.5924694, 1.3089104000000589, R.mipmap.ic_beer, R.drawable.sld_cafe));
        deals.add(new Deal("Karl Maison", "-10%", 43.6014334, 1.443336899999963, R.mipmap.ic_beer, R.drawable.sld_cafe));

        return deals;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (isServicesOK()) {
        }
        getLocationPermission();
        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                mMap.setMyLocationEnabled(true);
            }
        }
        for (Deal deal : dealArrayList()) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(deal.getIcon()))
                    .title(deal.getName());
            markerOptions.position(new LatLng(deal.getLatitude(), deal.getLongitude()));
            mMap.addMarker(markerOptions);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.activity_main, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void getDeviceLocation() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            if (mLocationPermissionsGranted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            if (currentLocation != null) {
                                moveCamera(new LatLng(43.6004273,1.4445871000000352), DEFAULT_ZOOM);

                            }

                        } else {
                            Toast.makeText(getActivity(), R.string.unableCurrentLocation, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
        }
    }


    private void moveCamera(LatLng latLng, float zoom) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }

    public void initMap() {
        SupportMapFragment mapFragment =
                (com.google.android.gms.maps.SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapFragment.this);

    }


    public boolean isServicesOK() {

        int available = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(getActivity());

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            return true;

        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance()
                    .getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getActivity(), R.string.mapRequests, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                mMap.setMyLocationEnabled(true);

            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }

        } else {
            ActivityCompat.requestPermissions(getActivity(), permissions,
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
