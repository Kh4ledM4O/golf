package com.example.undetermined.golf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity
        implements View.OnClickListener{
        private static final String TAG = ProfileActivity.class.getSimpleName();
        private FirebaseAuth mAuth;
        private AuthUI mAuthUI;
        private TextView welcomeMessage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        mAuthUI = AuthUI.getInstance();

        findViewById(R.id.log_outbtn).setOnClickListener(this);//btn listener
        findViewById(R.id.deletebtn).setOnClickListener(this);//btn listener

        if(!isUserLogged()){signOut();}//check user if user in logged in or not.

        welcomeMessage = findViewById(R.id.textView);
        setProfileTitle();//edit the title with the profile name.
        displayWelcomeMessageWithProfileName();//set the welcoming message.


    }//end onCreate

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.log_outbtn:
                signOut();
                break;
            case R.id.deletebtn:
                deleteAccount();
                break;
        }

    }//end onClick

    private boolean isUserLogged() {
        return mAuth.getCurrentUser() != null;
    }//end isUserLogged.

    private void signOut(){
        mAuthUI.signOut(this)//we could add on complete listener.
              .addOnCompleteListener(new OnCompleteListener<Void>() {
                  public void onComplete(@NonNull Task<Void> task) {
                      if (task.isSuccessful()) {
                          Log.d(TAG, "USER IS LOGGED OUT");
                          Toast.makeText(ProfileActivity.this, "USER IS LOGGED OUT", Toast.LENGTH_SHORT).show();
                          Intent sendToMainView = new Intent(ProfileActivity.this, MainActivity.class);
                          startActivity(sendToMainView);
                          finish();
                      }
                  }
              })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, e.getLocalizedMessage());
                    }
                });
    }//end signOut

    private void deleteAccount(){
        mAuthUI.delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "USER IS Deleted");
                    Toast.makeText(ProfileActivity.this, "USER IS Deleted", Toast.LENGTH_SHORT).show();
                    Intent sendToMainView = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(sendToMainView);
                    finish();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, e.getLocalizedMessage());
            }
        });
    }// end deleteAccount

    public void setProfileTitle(){
        FirebaseUser user = mAuth.getCurrentUser();
        setTitle(user.getDisplayName());
    }//end setProfileTitle
    public void displayWelcomeMessageWithProfileName(){
        FirebaseUser user = mAuth.getCurrentUser();
        StringBuffer mSB = new StringBuffer();
        mSB.append("Hello \n");
        mSB.append(user.getDisplayName());
        welcomeMessage.setText(mSB);
    }


}
