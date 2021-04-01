package com.example.pony_ecommerce.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pony_ecommerce.Activities.HomeActivity;
import com.example.pony_ecommerce.Activities.SignUpActivity;
import com.example.pony_ecommerce.R;
import com.example.pony_ecommerce.modelClass.Users;
import com.example.pony_ecommerce.staticClass.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // call the widgets here
    private TextView login_txt_login,login_txt_signUp,txt_seller_login,txt_forgot_password;
    private EditText login_et_email, login_et_password, login_et_username,login_et_username_password;
    private ImageView showPassword,showUsernamePassword;
    private Button login_btn,login_facebook_signIn,login_usernameBtn;
    private CheckBox remember_me, remember_me_username;

    private FrameLayout frameLayout;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private Switch logInWithUsername;






    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        frameLayout = getActivity().findViewById(R.id.login_frameLayout);
        login_txt_login = view.findViewById(R.id.login_txt_login);
        login_txt_signUp = view.findViewById(R.id.login_txt_signUp);
        txt_seller_login = view.findViewById(R.id.txt_seller_login);
        txt_forgot_password = view.findViewById(R.id.txt_forgot_password);
        login_et_email = view.findViewById(R.id.login_et_email);
        login_et_password = view.findViewById(R.id.login_password);
        showPassword = view.findViewById(R.id.showPassword);
        login_btn = view.findViewById(R.id.login_btn);
        login_facebook_signIn = view.findViewById(R.id.login_facebook_signIn);
        remember_me = view.findViewById(R.id.remember_me);
        logInWithUsername = view.findViewById(R.id.switchUsername);
        login_et_username = view.findViewById(R.id.login_et_username);
        login_usernameBtn = view.findViewById(R.id.login_username_btn);
        showUsernamePassword = view.findViewById(R.id.showUsernamePassword);
        login_et_username_password = view.findViewById(R.id.login_username_password);
        remember_me_username = view.findViewById(R.id.remember_me_username);



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        progressDialog = new ProgressDialog(getContext());





        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // animation for textViews
        login_txt_login.setTranslationY(300);
        login_txt_signUp.setTranslationY(300);

        login_txt_login.setAlpha(0);
        login_txt_signUp.setAlpha(0);

        login_txt_login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400);
        login_txt_signUp.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600);


        login_et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        login_et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Paper.init(getContext());


        login_et_username_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkUsernameInput();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        login_et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkUsernameInput();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        // show password instances
        login_et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        login_et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        login_et_username_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        login_et_username_password.setTransformationMethod(PasswordTransformationMethod.getInstance());



        // when the eye icon is clicked show or hide password for the user

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login_et_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    // show password

                    login_et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    // hide password
                    login_et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        showUsernamePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login_et_username_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

                    // show password
                    login_et_username_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else{
                    // hide password
                    login_et_username_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });






        // when the user clicks on the signUp textView send the user to signUp
        login_txt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SignUpActivity.class));
            }
        });

        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ForgotPasswordFragment());
            }
        });





        // login the user if his or her account exist

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    progressDialog.setTitle("Logging In");
                    progressDialog.setMessage("please wait, whiles logging in the user");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setIcon(R.drawable.splash_title);
                    progressDialog.show();
                    checkEmailAndPassword();


            }
        });







        // when the user toggle the switch button let the user sign in with username
        logInWithUsername.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    login_et_email.setVisibility(View.INVISIBLE);
                    login_et_password.setVisibility(View.INVISIBLE);
                    login_et_username.setVisibility(View.VISIBLE);
                    login_et_username_password.setVisibility(View.VISIBLE);


                    login_usernameBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String name = login_et_username.getText().toString();
                            String userPassword = login_et_username_password.getText().toString();


                            progressDialog.setTitle("Logging In");
                            progressDialog.setMessage("please wait, whiles logging in the user");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.setIcon(R.drawable.splash_title);
                            progressDialog.show();
                            signInWithUsername(name,userPassword);

                        }
                    });




                }
                else{
                    login_et_email.setVisibility(View.VISIBLE);
                    login_et_password.setVisibility(View.VISIBLE);
                    login_et_username.setVisibility(View.INVISIBLE);
                    login_et_username_password.setVisibility(View.INVISIBLE);




                }
            }
        });







    }

    private void checkUsernameInput() {
        if (!TextUtils.isEmpty(login_et_username.getText().toString())){
            if (!TextUtils.isEmpty(login_et_username_password.getText().toString())){
                login_usernameBtn.setVisibility(View.VISIBLE);
                showUsernamePassword.setVisibility(View.VISIBLE);
                remember_me_username.setVisibility(View.VISIBLE);
                remember_me.setVisibility(View.INVISIBLE);
            }
            else{
                login_usernameBtn.setVisibility(View.INVISIBLE);
                remember_me_username.setVisibility(View.INVISIBLE);
                showUsernamePassword.setVisibility(View.INVISIBLE);
                remember_me.setVisibility(View.INVISIBLE);

            }

        }
        else{
            login_usernameBtn.setVisibility(View.INVISIBLE);
            remember_me_username.setVisibility(View.INVISIBLE);
            showUsernamePassword.setVisibility(View.INVISIBLE);
            remember_me.setVisibility(View.INVISIBLE);
        }
    }


    private void checkEmailAndPassword() {

        // if the user clicks on the checkbox then when the user open the app next time
        // the app should already open for him or her without signing in
        if (remember_me.isChecked()){
            Paper.book().write("email",login_et_email.getText().toString());
            Paper.book().write("password",login_et_password.getText().toString());
        }


        if (login_et_email.getText().toString().matches(emailPattern)){
            if (login_et_password.getText().toString().length()>=8){

                firebaseAuth.signInWithEmailAndPassword(login_et_email.getText().toString(), login_et_password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    login_btn.setEnabled(false);
                                    login_btn.setTextColor(Color.BLACK);

                                    Prevalent.currentUser =login_et_password.getText().toString();
                                    Toast.makeText(getContext(), "login successful", Toast.LENGTH_SHORT).show();

                                    mainIntent();
                                } else {
                                    login_btn.setEnabled(true);
                                    progressDialog.dismiss();
                                    login_btn.setTextColor(Color.RED);
                                    String error = task.getException().toString();
                                    Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getContext(), "check password or email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
        else{
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Email must match email pattern", Toast.LENGTH_SHORT).show();
        }








    }

    private void mainIntent() {
        startActivity(new Intent(getContext(), HomeActivity.class));

    }

    private void checkInput() {
        if (!TextUtils.isEmpty(login_et_email.getText().toString())){
            if (!TextUtils.isEmpty(login_et_password.getText().toString())){
                login_btn.setEnabled(true);
                login_btn.setVisibility(View.VISIBLE);
                remember_me.setVisibility(View.VISIBLE);
                showPassword.setVisibility(View.VISIBLE);


            }
            else{
                login_btn.setEnabled(false);
                login_btn.setVisibility(View.GONE);
                remember_me.setVisibility(View.GONE);
                showPassword.setVisibility(View.GONE);
            }

        }
        else{
            login_btn.setVisibility(View.GONE);
            remember_me.setVisibility(View.GONE);
            showPassword.setVisibility(View.GONE);
        }
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private  void signInWithUsername(String username,String password) {

        if (remember_me_username.isChecked()) {
            Paper.book().write("username", username);
            Paper.book().write("password", password);
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(password).exists()){
                    Users userData = new Users();
                    userData.setName(snapshot.child("Users").child(password).child("userName").getValue().toString());
                    android.util.Log.e("username","" +userData.getName());
                    userData.setPassword(snapshot.child("Users").child(password).child("password").getValue().toString());
                    android.util.Log.e("userPassword","" +userData.getPassword());


                    if (username.equals(userData.getName())){
                        if (login_et_password.getText().toString().equals(userData.getPassword())){
                            progressDialog.dismiss();
                            mainIntent();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "password is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "username is not correct", Toast.LENGTH_SHORT).show();
                        setFragment(new LoginFragment());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





     
    }





}