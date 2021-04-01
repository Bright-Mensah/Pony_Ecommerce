package com.example.pony_ecommerce.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pony_ecommerce.Fragments.CategoriesFragment;
import com.example.pony_ecommerce.Fragments.HomeFragment;
import com.example.pony_ecommerce.Fragments.searchFragment;
import com.example.pony_ecommerce.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private CircleImageView userProfilePicture;
    private TextView userName,userEmail;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private StorageReference mStorageReference;
    private FrameLayout frameLayout;

    private DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


       drawer = findViewById(R.id.drawer_layout);
       drawer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(HomeActivity.this, "yo", Toast.LENGTH_SHORT).show();
           }
       });
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        navigationView.setNavigationItemSelectedListener(this);

        frameLayout = findViewById(R.id.home_Framelayout);

        setFragment(new HomeFragment());

        mStorageReference = FirebaseStorage.getInstance().getReference().child("User Profile Picture");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        View view = navigationView.getHeaderView(0);
        userProfilePicture = view.findViewById(R.id.nav_header_User_profile_picture);
        userName = view.findViewById(R.id.nav_header_User_Username);
        userEmail = view.findViewById(R.id.nav_header_User_Email);








            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference.child("Users");

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        String image = dataSnapshot.child("profilePic").getValue().toString();
                        String name = dataSnapshot.child("userName").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();

                        try {
                            Picasso.get().load(image).into(userProfilePicture);
                            userName.setText(name);
                            userEmail.setText(email);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });















        // paper no sql
        Paper.init(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);





        return true;

    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            // meaning when the user go the home page the drawer must be open the user clicks on it
            drawer.openDrawer(Gravity.LEFT);
        }
        else if (item.getItemId() == R.id.search){
            setFragment(new searchFragment());



        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_home){
            // send the user to home

            drawer.close();
            setFragment(new HomeFragment());
        }
        else if (id == R.id.nav_Categories){
            // send the user to categories
            drawer.close();
            setFragment(new CategoriesFragment());

        }
        else if (id == R.id.nav_myOrders){
            // send the user to my orders
            drawer.close();
        }
        else if (id == R.id.nav_wishList){
            // send the user to wishList
            drawer.close();
        }
        else if (id == R.id.nav_settings){
            // send the user to settings

            drawer.close();
        }

        else if (id == R.id.nav_notification){
            Toast.makeText(HomeActivity.this, "yo", Toast.LENGTH_SHORT).show();
            // send the user to notification
            drawer.close();
        }
        else if (id == R.id.nav_logout) {
            // log the user  out of the app
            drawer.close();
            Paper.book().destroy();
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();
        }

        return true;

    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(frameLayout.getId(),fragment).addToBackStack(null).setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);


        fragmentTransaction.commit();
    }




}