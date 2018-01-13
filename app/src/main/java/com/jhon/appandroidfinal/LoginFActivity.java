package com.jhon.appandroidfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class LoginFActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    //Recursos de facebook

    public static final int SIGN_IN_CODE = 777;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuthFacebook;
    private FirebaseAuth.AuthStateListener firebaseAuthListenerFacebook;
    //Recursos de GOOGLE
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    private FirebaseAuth firebaseAuthGoogle;
    private FirebaseAuth.AuthStateListener firebaseAuthListenerGoogle;


    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_f);
        //Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton = (SignInButton) findViewById(R.id.signInButton);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
                Auth.GoogleSignInApi.revokeAccess(googleApiClient);
            }
        });

        firebaseAuthGoogle = FirebaseAuth.getInstance();
        firebaseAuthListenerGoogle = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuthG) {


                FirebaseUser userGoogle = firebaseAuthGoogle.getCurrentUser();
                if (userGoogle != null) {
                    goMainScreenGoogle();
                }
            }
        };
        //facebook
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButtoon);

        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //goMainScreen();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Operación Cancelada", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Ocurrió un Error no previsto", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseAuthFacebook = FirebaseAuth.getInstance();
        firebaseAuthListenerFacebook = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuthF) {


                FirebaseUser userFacebook = firebaseAuthFacebook.getCurrentUser();
                if (userFacebook != null) {


                    goMainScreenFacebook();
                }
            }
        };

    }


    private void handleFacebookAccessToken(AccessToken accessToken) {

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuthFacebook.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error al Iniciar Sessióm xD", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void goMainScreenFacebook() {
        Intent intentFB = new Intent(this, MenuActivity.class);
        intentFB.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentFB);
    }

    private void goMainScreenGoogle() {
        Intent intentG = new Intent(this, MenuActivity.class);
        intentG.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentG);
    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuthFacebook.addAuthStateListener(firebaseAuthListenerFacebook);
        firebaseAuthGoogle.addAuthStateListener(firebaseAuthListenerGoogle);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult resultGoogle = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResultGoogle(resultGoogle);
        }

    }

    private void handleSignInResultGoogle(GoogleSignInResult resultGoogle) {
        if (resultGoogle.isSuccess()) {
            firebaseAuthWithGoogle(resultGoogle.getSignInAccount());
        } else {
            Toast.makeText(this, R.string.not_LoginActivity, Toast.LENGTH_LONG).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        firebaseAuthGoogle.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.not_firebase_auth, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthFacebook != null) {
            firebaseAuthFacebook.removeAuthStateListener(firebaseAuthListenerFacebook);
        }

        if (firebaseAuthListenerGoogle != null) {
            firebaseAuthGoogle.removeAuthStateListener(firebaseAuthListenerGoogle);
        }
    }


}
