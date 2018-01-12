package com.jhon.appandroidfinal;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jhon.appandroidfinal.Model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    DatabaseReference rootRef, demoRef;
    double Lat, Log;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //rootRef = FirebaseDatabase.getInstance().getReference();
        //database reference pointing to demo node
        //demoRef = rootRef.child("BMM/usuarios");

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
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.child("BBM/usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {

                List listUsers = new ArrayList();
                for (DataSnapshot noteDataSnapshot : datasnapshot.getChildren()) {
                    //  = noteDataSnapshot.getValue(Usuario.class);
                    Usuario urs = noteDataSnapshot.getValue(Usuario.class);
                    listUsers.add(urs);
                    //Lat = urs.getLatitud();
                    //Log = urs.getLongitud();
                    LatLng latLng = new LatLng(urs.getLatitud(), urs.getLongitud());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(""));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    //mMap.animateCamera(CameraUpdateFactory.zoomTo(5), 2000, null);
                }

                /*List<Address> addressList = null;
                MarkerOptions mo = new MarkerOptions();


                Geocoder geocoder = new Geocoder(getApplicationContext());

                    for (int i = 0; i<addressList.size(); i++){
                        Address myAddres = addressList.get(i);
                        LatLng latLng = new LatLng(myAddres.getLatitude(), myAddres.getLongitude());
                        mo.position(latLng);
                        mo.title("Resulttados de Busqueda");
                        mMap.addMarker(mo);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }*/



                /*LatLng latLng = new LatLng(Lat, Log);
                mMap.addMarker(new MarkerOptions().position(latLng).title("INSTITUTO SISE"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));*/
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
                // System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        // Add a marker in Sydney and move the camera

    }
}
