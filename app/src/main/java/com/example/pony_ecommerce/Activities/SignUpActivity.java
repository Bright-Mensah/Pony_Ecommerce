package com.example.pony_ecommerce.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.pony_ecommerce.R;
import com.example.pony_ecommerce.Fragments.SignUpFragment;

public class SignUpActivity extends AppCompatActivity {
    public    FrameLayout frameLayout;
    public     FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fragmentManager = getSupportFragmentManager();

        frameLayout = findViewById(R.id.signUp_frameLayout);

        setFragment(new SignUpFragment());
    }
    public   void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }
}