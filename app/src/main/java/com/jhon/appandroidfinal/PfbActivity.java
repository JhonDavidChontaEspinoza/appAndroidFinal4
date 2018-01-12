package com.jhon.appandroidfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PfbActivity extends AppCompatActivity {

    EditText mensajeET;
    TextView mensajeTV;
    Button btnModificar;

    DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
    DatabaseReference mensajeRef=ref.child("mensaje");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pfb);
        mensajeET=(EditText)findViewById(R.id.mensajeeditText);
        mensajeTV=(TextView)findViewById(R.id.mensajetextView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mensajeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue(String.class);

                mensajeTV.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void modificar(View view) {
        String mensaje=mensajeET.getText().toString();
        mensajeRef.setValue(mensaje);

        mensajeET.setText("");
    }

}
