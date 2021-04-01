package com.example.pony_ecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;


import com.example.pony_ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Paper.init(this);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        // these are for the firebaseAuth login with the email
        String email = Paper.book().read("email");
        String userPassword = Paper.book().read("password");


        //these are for the firebaseDatabase login with username
        String username = Paper.book().read("username");
        String password = Paper.book().read("password");




        if (email !="" && userPassword !=""){
            // if the email and the password is not empty
            // means the user already logged in to the app
            // then check if the email and password is not empty

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(userPassword)) {
                // if the email and password are not empty
                // then open the app to the home activity so that the user wont login again
                allowAccessWithEmail(email, userPassword);


                    }
                    else{
                        // check if the username and password are empty
                         if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                             // if the username and password are not empty
                             // then open the app to the home activity so that the user wont login again
                                     allowAccessWithUsername(username,password);


                    }
                         else {
                             // else if the username and password is empty
                             // then after the splash screen display move the user to the login activity
                             // fpr

                             new CountDownTimer(5000, 1000) {
                                 @Override
                                 public void onFinish() {
                                     startActivity(new Intent(SplashActivity.this, MainActivity.class));




                                 }

                                 @Override
                                 public void onTick(long millisUntilFinished) {

                                 }


                             }.start();


                         }
            }



            }
            else {
            if (username !="" && password !=""){

                new CountDownTimer(5000, 1000) {
                    @Override
                    public void onFinish() {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));




                    }

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }


                }.start();



            }

        }










    }

    private void allowAccessWithUsername(String username, String password) {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Users");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = dataSnapshot.child("userName").getValue().toString();
                    String pass = dataSnapshot.child("password").getValue().toString();

                    if (username.equals(name)){

                        if (password.equals(pass)){

                            mainIntent();
                        }
                        else{


                        }


                    }
                    else{


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void allowAccessWithEmail(String email, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mainIntent();

                        }
                        else{
                            String error = task.getException().toString();
                            Toast.makeText(SplashActivity.this, "Error: " +error, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void mainIntent() {
        startActivity(new Intent(SplashActivity.this,HomeActivity.class));
        finish();
    }


}