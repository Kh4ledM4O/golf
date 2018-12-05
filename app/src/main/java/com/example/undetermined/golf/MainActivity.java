package com.example.undetermined.golf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;



import com.google.firebase.auth.FirebaseAuth;


import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 200;
    private List<AuthUI.IdpConfig> providers;
    private AuthUI mAuthUI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuthUI = AuthUI.getInstance();
        if(isUserLogged()){loginUser();}
        setContentView(R.layout.activity_main);
        findViewById(R.id.SiginInActivity).setOnClickListener(this);//btn listener
        providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.SiginInActivity:

                startActivityForResult(
                        mAuthUI.createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false,true)
                                .setAvailableProviders(providers)
                                .build(),
                            RC_SIGN_IN);
                }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                loginUser();
            }
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(MainActivity.this, "error on singing in",Toast.LENGTH_LONG).show();
            }
            return;
        }
        Toast.makeText(MainActivity.this, "nah",Toast.LENGTH_LONG).show();
    }

    private boolean isUserLogged() {
        return mAuth.getCurrentUser() != null;
    }
    private void loginUser(){
        if(isUserLogged()) {
            Intent loginToProfile = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(loginToProfile);
            finish();
        }
    }

}
