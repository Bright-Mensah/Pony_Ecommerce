package com.example.pony_ecommerce.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pony_ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPasswordFragment extends Fragment {
    // call the widget here
    private ImageView red_email_logo,green_email_logo;
    private EditText forget_password_email;
    private ProgressBar forget_password_progressbar;
    private TextView tv_forget_password_go_back;
    private Button reset_password_btn;
    private FrameLayout parentFrameLayout;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    private FirebaseAuth firebaseAuth;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotPassword.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        parentFrameLayout = getActivity().findViewById(R.id.login_frameLayout);
        red_email_logo = view.findViewById(R.id.red_email_logo);
        green_email_logo = view.findViewById(R.id.green_email_logo);
        forget_password_email = view.findViewById(R.id.forget_password_email);
        forget_password_progressbar = view.findViewById(R.id.forget_password_progressbar);
        tv_forget_password_go_back = view.findViewById(R.id.tv_forget_password_go_back);
        reset_password_btn = view.findViewById(R.id.reset_password_btn);

        firebaseAuth = FirebaseAuth.getInstance();


        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        forget_password_email.addTextChangedListener(new TextWatcher() {
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

        tv_forget_password_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new LoginFragment());


            }
        });

        // allow the user  to reset the password
        reset_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                forget_password_progressbar.setVisibility(View.VISIBLE);


                if (forget_password_email.getText().toString().matches(emailPattern)){

                    firebaseAuth.sendPasswordResetEmail(forget_password_email.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        forget_password_progressbar.setVisibility(View.INVISIBLE);
                                        red_email_logo.setVisibility(View.GONE);
                                        green_email_logo.setVisibility(View.VISIBLE);
                                        Toast.makeText(getContext(), "Email sent successfully", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getContext(), "check your inbox to reset password", Toast.LENGTH_SHORT).show();

                                        new CountDownTimer(5000, 1000) {

                                            @Override
                                            public void onFinish() {
                                                setFragment(new LoginFragment());

                                            }
                                            @Override
                                            public void onTick(long millisUntilFinished) {

                                            }

                                        }.start();






                                    }
                                    else{
                                        String error = task.getException().toString();
                                        forget_password_progressbar.setVisibility(View.INVISIBLE);
                                        green_email_logo.setVisibility(View.INVISIBLE);
                                        red_email_logo.setVisibility(View.VISIBLE);
                                        Toast.makeText(getContext(), "Error: " +error, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            }
        });


    }

    private void checkInput() {
        if (!TextUtils.isEmpty(forget_password_email.getText().toString()) && forget_password_email.getText().toString().matches(emailPattern)){
            // if email EditText is not empty and email matches email pattern
            reset_password_btn.setEnabled(true);
            reset_password_btn.setTextColor(Color.rgb(255,255,255));


        }
        else{
            // either email EditText is empty or the email in the EditText does not match email pattern
            reset_password_btn.setEnabled(false);
            reset_password_btn.setTextColor(Color.argb(50,255,255,255));


        }
    }
    private void setFragment(Fragment fragment) {

        // send the user to another fragment when the widget is clicked
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slidout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment).addToBackStack(null);
        fragmentTransaction.commit();

    }
}