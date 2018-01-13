package com.jhon.appandroidfinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jhon.appandroidfinal.Model.Coche;
import com.jhon.appandroidfinal.Model.CocheAdapterL;

import java.util.ArrayList;
import java.util.List;

public class ListaCocheActivity extends AppCompatActivity {


    List<Coche> lstcoches;
    ArrayList<Coche> ArrayCoche;
    ArrayAdapter<Coche> AACoche;
    CocheAdapterL adapterL = null;
    GridView CochesGV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_coche);

        CochesGV = (GridView) findViewById(R.id.gvCoches);
        lstcoches = new ArrayList<Coche>();


        AACoche = new ArrayAdapter<Coche>(this, R.layout.layout, lstcoches);
        CochesGV.setAdapter(AACoche);

        //
        lstcoches = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        database.getReference().getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstcoches.removeAll(lstcoches);
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {
                    Coche coche = snapshot.getValue(Coche.class);
                    lstcoches.add(coche);
                }
                AACoche.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adapterL = new CocheAdapterL(this, R.layout.layout, ArrayCoche);
        CochesGV.setAdapter(adapterL);
        adapterL.notifyDataSetChanged();
    }
}
