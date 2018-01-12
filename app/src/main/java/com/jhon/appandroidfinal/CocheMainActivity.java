package com.jhon.appandroidfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.jhon.appandroidfinal.Model.Coche;
import com.jhon.appandroidfinal.Model.CocheAdapter;
import com.jhon.appandroidfinal.Model.FirebaseReferences;

import java.util.ArrayList;
import java.util.List;


public class CocheMainActivity extends AppCompatActivity {

    RecyclerView rv;
    List<Coche> coches;
    CocheAdapter cocheAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coche_main);

        rv=(RecyclerView)findViewById(R.id.recycler);

        rv.setLayoutManager(new LinearLayoutManager(this));

        coches=new ArrayList<>();

        FirebaseDatabase database=FirebaseDatabase.getInstance();

        cocheAdapter=new CocheAdapter(coches);

        rv.setAdapter(cocheAdapter);

        database.getReference().getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                coches.removeAll(coches);
                for (DataSnapshot snapshot:
                     dataSnapshot.getChildren()) {
                    Coche coche=snapshot.getValue(Coche.class);
                    coches.add(coche);
                }
                cocheAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
