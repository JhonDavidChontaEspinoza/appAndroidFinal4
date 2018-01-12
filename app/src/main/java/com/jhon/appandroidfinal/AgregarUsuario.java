package com.jhon.appandroidfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jhon.appandroidfinal.Model.Coche;
import com.jhon.appandroidfinal.Model.FirebaseReferences;
import com.jhon.appandroidfinal.Model.Usuario;

/**
 * Created by DS on 07/01/2018.
 */

public class AgregarUsuario extends AppCompatActivity implements View.OnClickListener{
    EditText txtApellido, txtNombre, txtCorreo;

    Button AgregarBtnU, ListarBtnU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);

        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtApellido = (EditText)findViewById(R.id.txtApellido);
        txtCorreo = (EditText)findViewById(R.id.txtCorreo);

        AgregarBtnU = (Button) findViewById(R.id.btnAgregarU);
        ListarBtnU = (Button)findViewById(R.id.btnListarU);
        AgregarBtnU.setOnClickListener(this);
        ListarBtnU.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnAgregarU:
                GuardarUsuario();
                break;
            case R.id.btnListarU:
                Intent I = new Intent(AgregarUsuario.this,
                        ListaUsuarioActivity.class);
                startActivity(I);
                break;

            default: break;
        }
    }
    public void GuardarUsuario()
    {
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        final DatabaseReference tutorialRef=database.getReference(FirebaseReferences.BBM_REFERENCES);
        String Nombre=txtNombre.getText().toString();
        String Apellidos=txtApellido.getText().toString();
        String Correo=txtCorreo.getText().toString();
        double Latitud = 0;
        double Longitus = 0;
        Usuario usuario=new Usuario(Nombre,Apellidos,Correo,0,0);
        tutorialRef.child(FirebaseReferences.USUARIO_REFERENCES).push().setValue(usuario);
        Toast.makeText(this,"Usuario Agregado", Toast.LENGTH_SHORT).show();
    }
}
