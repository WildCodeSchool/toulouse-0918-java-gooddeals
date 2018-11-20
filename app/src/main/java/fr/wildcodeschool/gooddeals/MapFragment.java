package fr.wildcodeschool.gooddeals;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class MapFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 17f;
    private LatLng esquirol = new LatLng(43.600346, 1.443844);

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        moveCamera(esquirol, DEFAULT_ZOOM);
        getLocationPermission();
        if (mLocationPermissionsGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }
        DealSingleton dealSingleton = DealSingleton.getInstance();
        final ArrayList<Deal> deals = dealSingleton.getDealArrayList();
        ArrayList<Deal> dealsFilter = new ArrayList<>();

        Bundle mbundle = this.getArguments();
        if (mbundle != null) {
            boolean pourManger = mbundle.getBoolean("filter_manger", true);
            boolean friandises = mbundle.getBoolean("filter_friandises", true);
            boolean bienEtre = mbundle.getBoolean("filter_bienEtre", true);
            boolean loisirs = mbundle.getBoolean("filter_loisirs", true);
            boolean aperos = mbundle.getBoolean("filter_aperos", true);
            for (Deal deal : deals) {
                switch (deal.getType()) {

                    case "Pour Manger":
                        if (pourManger) {
                            dealsFilter.add(deal);
                        }
                        break;
                    case "Apéro":
                        if (aperos) {
                            dealsFilter.add(deal);
                        }
                        break;
                    case "Friandises":
                        if (friandises) {
                            dealsFilter.add(deal);
                        }
                        break;
                    case "Bien-être":
                        if (bienEtre) {
                            dealsFilter.add(deal);
                        }
                        break;
                    case "Loisirs":
                        if (loisirs) {
                            dealsFilter.add(deal);
                        }
                        break;
                }
            }
        }

        for (Deal deal : dealsFilter) {
            int icon = R.drawable.pin;
            switch (deal.getType()) {

                case "Pour Manger":
                    icon = R.drawable.pin;

                    break;
                case "Apéro":
                    icon = R.drawable.pin_blue;
                    break;
                case "Friandises":
                    icon = R.drawable.pin_violet;
                    break;
                case "Bien-être":
                    icon = R.drawable.pin_pink;
                    break;
                case "Loisirs":
                    icon = R.drawable.pin_red;
                    break;
            }
            MarkerOptions markerOptions = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(icon));
            markerOptions.position(new LatLng(deal.getLatitude(), deal.getLongitude()));
            Marker marker = mMap.addMarker(markerOptions);
            marker.setTag(deal);
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Deal deal = (Deal) marker.getTag();
                Intent intent = new Intent(getActivity(), Popup.class);
                intent.putExtra("EXTRA_TITLE", deal.getName());
                intent.putExtra("EXTRA_DESCRIPTION", deal.getDescription());
                intent.putExtra("EXTRA_IMAGE", deal.getImage());
                intent.putExtra("EXTRA_LATITUDE", deal.getLatitude());
                intent.putExtra("EXTRA_LONGITUDE", deal.getLongitude());
                startActivity(intent);
                return false;
            }
        });
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
                                moveCamera(esquirol, DEFAULT_ZOOM);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
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
                getDeviceLocation();
            } else {
                requestPermissions(permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            requestPermissions(permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }



    @Override
    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            mMap.setMyLocationEnabled(true);
                            getDeviceLocation();
                            return;
                        }
                    }
                }
            }
        }
    }
}
