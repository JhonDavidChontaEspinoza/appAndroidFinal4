package com.jhon.appandroidfinal;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jhon.appandroidfinal.Model.Coche;
import com.jhon.appandroidfinal.Model.FirebaseReferences;

public class AgregarCocheActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtMarca, txtDueno, txtPuertas,txtRuedas;
    //ImageView FotoIV;
    Button AgregarBtnC, ListarBtnC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_coche);
        txtMarca = (EditText)findViewById(R.id.txtMarca);
        txtDueno = (EditText)findViewById(R.id.txtDueno);
        txtPuertas = (EditText)findViewById(R.id.txtPuertas);
        txtRuedas = (EditText)findViewById(R.id.txtRuedas);
        //FotoIV = (ImageView) findViewById(R.id.ivFoto);
        AgregarBtnC = (Button) findViewById(R.id.btnAgregarC);
        ListarBtnC = (Button)findViewById(R.id.btnListarC);
        AgregarBtnC.setOnClickListener(this);
        ListarBtnC.setOnClickListener(this);


        //FotoIV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnAgregarC:
                GuardarCoche();
                break;
            case R.id.btnListarC:
                Intent I = new Intent(AgregarCocheActivity.this,
                        ListaCocheActivity.class);
                startActivity(I);
                break;
            /*case R.id.ivFoto:
                try
                {
                    ActivityCompat.requestPermissions(
                            MainActivity.this,new String[]
                                    {android.Manifest.permission.READ_EXTERNAL_STORAGE},99);
                }
                catch (Exception E){}
                break;*/
            default: break;
        }
    }
    public void GuardarCoche()
    {
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        final DatabaseReference tutorialRef=database.getReference(FirebaseReferences.BBM_REFERENCES);
        String Marca=txtMarca.getText().toString();
        String Dueno=txtDueno.getText().toString();
        String Puertas=txtPuertas.getText().toString();
        int myPuertas = Integer.parseInt(Puertas);
        String Ruedas=txtRuedas.getText().toString();
        int myRuedas=Integer.parseInt(Ruedas);
        Coche coche=new Coche(Marca,Dueno,myPuertas,myRuedas);
        tutorialRef.child(FirebaseReferences.COCHE_REFERENCES).push().setValue(coche);
        Toast.makeText(this,"Coche Agregado", Toast.LENGTH_SHORT).show();
    }
}
