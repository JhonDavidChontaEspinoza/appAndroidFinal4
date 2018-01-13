package com.jhon.appandroidfinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainFActivity extends AppCompatActivity {

    //facebook
    private TextView nombreFBTextView, emailFBTextView, uidFBTextView;
    private ImageView photoFB;
    //Google
   /* private ImageView phoImageView2;
    private TextView nameGTextView;
    private TextView emailGTextView;
    private TextView idGTextView;

    private GoogleApiClient googleApiClient;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_f);

        //GOogle
        /*phoImageView2=(ImageView)findViewById(R.id.photoImageView);
        nameGTextView=(TextView)findViewById(R.id.nameGTextView);
        emailGTextView=(TextView)findViewById(R.id.emailGTextView);
        idGTextView=(TextView)findViewById(R.id.idGTextView);

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
                    goLoginScreenGoogle();

                }
            }
        };*/
        //Facebook
        nombreFBTextView = (TextView) findViewById(R.id.nombreFBTextView);
        emailFBTextView = (TextView) findViewById(R.id.emailFBTextView);

        uidFBTextView = (TextView) findViewById(R.id.uidFBtextView);

        photoFB = (ImageView) findViewById(R.id.photoFB);

        FirebaseUser userFacebook = FirebaseAuth.getInstance().getCurrentUser();

        if (userFacebook != null) {
            String name = userFacebook.getDisplayName();
            String email = userFacebook.getEmail();
            Uri photoUrl = userFacebook.getPhotoUrl();
            String uid = userFacebook.getUid();

            nombreFBTextView.setText(name);
            emailFBTextView.setText(email);
            uidFBTextView.setText(uid);

            Glide.with(this)
                    .load(userFacebook.getPhotoUrl())
                    .crossFade()
                    .placeholder(R.drawable.myusuerfb)
                    .thumbnail(0.5f)
                    .into(photoFB);


        } else {
            goLoginScreenFacebook();
        }

    }

    /*private void setUserData(FirebaseUser user) {
        nameGTextView.setText(user.getDisplayName());
        emailGTextView.setText(user.getEmail());
        idGTextView.setText(user.getUid());

        Glide.with(this).load(user.getPhotoUrl()).into(phoImageView2);

    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);

    }
    private void goLoginScreenGoogle() {
        Intent intent=new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }*/
    private void goLoginScreenFacebook() {
        Intent intent = new Intent(this, LoginFActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
   /* public void logOut(View view) {
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLoginScreenGoogle();
                }else
                {
                    Toast.makeText(getApplicationContext(), R.string.not_close_session,Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/

    public void logoutFacebook(View view) {
        FirebaseAuth.getInstance().signOut();//Cierra Sessión En Firebase
        LoginManager.getInstance().logOut();
        goLoginScreenFacebook();
    }
   /* public  void revoke(final View view){
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLoginScreenGoogle();

                }else
                {
                    Toast.makeText(getApplicationContext(),"No se pudo WTHA",Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/
   /* @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }*/
   /* @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuthListener!=null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }*/
}
