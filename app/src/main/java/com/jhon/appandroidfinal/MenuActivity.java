package com.jhon.appandroidfinal;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.jhon.appandroidfinal.Fragments.UbicacionFragment;

public class MenuActivity extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener{

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    //facebook
    private TextView nombreFBTextView;
    private ImageView photoFB;

    private ImageView phoImageView2;
    private TextView nameTextView;
    private GoogleApiClient googleApiClient;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        navigationView = (NavigationView) findViewById(R.id.menu);
        View header = navigationView.getHeaderView(0);
        nombreFBTextView = (TextView) header.findViewById(R.id.nametextView);
        photoFB=(ImageView)header.findViewById(R.id.photoProfile);
        phoImageView2=(ImageView)header.findViewById(R.id.photoProfile);
        nameTextView=(TextView)header.findViewById(R.id.nametextView);

        //google
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        googleApiClient =new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuthListener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    setUserData(user);
                }else
                {
                    goLogInScreenGoogle();
                }
            }
        };

        FirebaseUser userFacebook= FirebaseAuth.getInstance().getCurrentUser();

        if(userFacebook!=null){
            String name=userFacebook.getDisplayName();
            //String email=userFacebook.getEmail();
           Uri photoUrl=userFacebook.getPhotoUrl();
            //String uid=userFacebook.getUid();

           nombreFBTextView.setText(name); //IT SEEMS THAT HERE FAIL
            //emailFBTextView.setText(email);
           // uidFBTextView.setText(uid);

          Glide.with(this)
                    .load(userFacebook.getPhotoUrl())
                    .crossFade()
                    .placeholder(R.drawable.myusuerfb)
                    .thumbnail(0.5f)
                    .into(photoFB);


        }else
        {
            goLoginScreenFacebook();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.menu);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.isChecked())
                        {
                            item.setChecked(false);
                        }
                        else
                        {
                            item.setChecked(true);

                        }

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();


                        drawerLayout.closeDrawers();

                        switch (item.getItemId()){
                            case R.id.MiUbicacion:
                                UbicacionFragment ubicacionFragment = new UbicacionFragment();
                                transaction.replace(R.id.fragment, ubicacionFragment);
                                transaction.commit();
                                //setFragment(0);
                                break;
                            case R.id.BuscarUbicacion:
                                /*//setFragment(1);
                                ListarFragment listar = new ListarFragment();
                                transaction.replace(R.id.fragment, listar);
                                transaction.commit();*/
                                break;
                            case R.id.BuscarParadero:
                                /*ImagenesFragment ordenar = new ImagenesFragment();
                                transaction.replace(R.id.fragment, ordenar);
                                transaction.commit();*/
                                break;
                            //setFragment(2);
                            case R.id.Cerrar_Sesion:

                                firebaseAuth.signOut();
                                Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(@NonNull Status status) {
                                        if(status.isSuccess()){
                                            goLogInScreenGoogle();

                                        }else
                                        {
                                            Toast.makeText(getApplicationContext(),"No se pudo WTHA",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();
                                goLoginScreenFacebook();
                                firebaseAuth.removeAuthStateListener(firebaseAuthListener);
                                transaction.commit();
                                //setFragment(3);
                                break;

                        }

                        return false;
                    }
                });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        };

    }

    private void goLoginScreenFacebook() {
        Intent intent=new Intent(this,LoginFActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setUserData(FirebaseUser user) {
        nameTextView.setText(user.getDisplayName());
        Glide.with(this).load(user.getPhotoUrl()).into(phoImageView2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);

    }

    private void goLogInScreenGoogle() {
        Intent intent=new Intent(this,LoginFActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void logOut(View view) {
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLogInScreenGoogle();
                }else
                {
                    Toast.makeText(getApplicationContext(), R.string.not_close_session,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public  void revoke(final View view){
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLogInScreenGoogle();

                }else
                {
                    Toast.makeText(getApplicationContext(),"No se pudo WTHA",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuthListener!=null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

}
