package com.jhon.appandroidfinal.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jhon.appandroidfinal.Model.Usuario;
import com.jhon.appandroidfinal.R;

import java.util.ArrayList;
import java.util.List;


public class ObtenerAutosFragment extends Fragment implements OnMapReadyCallback {

    DatabaseReference rootRef, demoRef;
    double Lat, Log;
    MapView mMapView;
    View mView;
    Location location;
    double lat = 0.0;
    double log = 0.0;
    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
    private GoogleMap mMap;
    private GoogleMap mGoogleMap;
    private Marker marcador;

    public ObtenerAutosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_obtener_autos, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map);

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("BBM/usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                mMap.clear();

                List listUsers = new ArrayList();
                for (DataSnapshot noteDataSnapshot : datasnapshot.getChildren()) {
                    //  = noteDataSnapshot.getValue(Usuario.class);
                    Usuario urs = noteDataSnapshot.getValue(Usuario.class);
                    //listUsers.add(urs);
                    //Lat = urs.getLatitud();
                    //Log = urs.getLongitud();
                    LatLng latLng = new LatLng(urs.getLatitud(), urs.getLongitud());
                    float results[] = new float[10];
                    Location.distanceBetween(urs.getLatitud(), urs.getLongitud(), lat, log, results);
                    mMap.addMarker(new MarkerOptions().
                            position(latLng).
                            title(urs.getNombres()).
                            icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pointer)).snippet("Distancia " + results[0]));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    //mMap.animateCamera(CameraUpdateFactory.zoomTo(5), 2000, null);
                    //listUsers.clear();
                    //MarkerOptions markerOptions =new MarkerOptions();
                    miUbicacion();


                    //markerOptions.snippet("Distance = "+results[0]);


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }

    private void agregarMarcador(double lat, double log) {
        LatLng coordenadas = new LatLng(lat, log);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) {
            marcador.remove();
        }
        marcador = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Mi Posicion Actual"));
        mMap.animateCamera(miUbicacion);
    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            log = location.getLongitude();
            agregarMarcador(lat, log);
        }
    }

    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locListener);
    }


}
