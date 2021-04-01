package com.example.pony_ecommerce.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

import com.example.pony_ecommerce.Activities.LoginActivity;
import com.example.pony_ecommerce.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    //  call  widget here
    private  TextView signup_txt_login,signup_txt_signUp,signUp_choose_image;
    private CircleImageView Sign_up_user_profile_picture;
    private EditText signup_et_username,signup_et_phoneNumber,
            signup_et_Email,signup_et_password,signup_et_confirmPassword;
    private ImageView sign_up_show_Password,sign_up_show_confirmation_Password;
    private Button signup_btn,signup_fb_signIn;
    private FrameLayout frameLayout;
    private ProgressBar progressBar;

    private Uri imageUrl;
    private String downloadUrl;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    private FirebaseAuth firebaseAuth;
    private StorageReference profilePicRef;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    private String userId ;



    // <!-----------------> constant permission <!----------------->
    private static final int  CAMERA_REQUEST_CODE = 100;
    private static final int  STORAGE_REQUEST_CODE = 200;

    // <!-----------------> image picks  <!----------------->
    private static final int CAMERA_IMAGE_PICK_CODE = 300;
    private static final  int GALLERY_IMAGE_PICK_CODE = 400;

    // <!----------------->permission arrays <!----------------->
    private String[] cameraPermission;
    private String[] storagePermission;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);

        frameLayout = getActivity().findViewById(R.id.signUp_frameLayout);
        signup_txt_login = view.findViewById(R.id.signup_txt_login);
        signup_txt_signUp = view.findViewById(R.id.signup_txt_signUp);
        Sign_up_user_profile_picture = view.findViewById(R.id.Sign_up_user_profile_picture);
        signUp_choose_image = view.findViewById(R.id.signUp_choose_image);
        signup_et_username = view.findViewById(R.id.signup_et_username);
        signup_et_phoneNumber = view.findViewById(R.id.signup_et_phoneNumber);
        signup_et_Email = view.findViewById(R.id.signup_et_Email);
        signup_et_password = view.findViewById(R.id.signup_et_password);
        sign_up_show_Password = view.findViewById(R.id.sign_up_show_Password);
        signup_et_confirmPassword = view.findViewById(R.id.signup_et_confirmPassword);
        sign_up_show_confirmation_Password = view.findViewById(R.id.sign_up_show_confirmation_Password);
        signup_btn = view.findViewById(R.id.signup_btn);
        signup_fb_signIn = view.findViewById(R.id.signup_fb_signIn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore  = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        progressBar = view.findViewById(R.id.progressBar);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // <!-----------------> animation for the header textViews <!----------------->
        signup_txt_login.setTranslationY(300);
        signup_txt_signUp.setTranslationY(300);


        signup_txt_login.setAlpha(0);
        signup_txt_signUp.setAlpha(0);

        signup_txt_login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400);
        signup_txt_signUp.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600);
        // <!-----------------> animation for the header textViews <!----------------->


        // initialize permission arrays
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};



        // when user clicks on the profilePicture
        Sign_up_user_profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showDialog to the user to choose
                showAlertDialog();

            }
        });
        signUp_choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // when user clicks on the the choose image
                // showDialog to the user to choose
                showAlertDialog();


            }
        });

        profilePicRef = FirebaseStorage.getInstance().getReference("User Profile Picture");


        // <!-----------------> check if all fields are not empty and let the signUp button show<!----------------->

        signup_et_username.addTextChangedListener(new TextWatcher() {
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
        signup_et_Email.addTextChangedListener(new TextWatcher() {
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
        signup_et_phoneNumber.addTextChangedListener(new TextWatcher() {
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
        signup_et_password.addTextChangedListener(new TextWatcher() {
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
        signup_et_confirmPassword.addTextChangedListener(new TextWatcher() {
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


        // <!-----------------> show password when eyes are clicked <!----------------->
        signup_et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        signup_et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

          signup_et_confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        signup_et_confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());



        sign_up_show_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signup_et_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    // show password
                    signup_et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    signup_et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    // hide password
                }
            }
        });
        sign_up_show_confirmation_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signup_et_confirmPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    // show password
                    signup_et_confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    signup_et_confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    // hide password
                }
            }
        });




        // sign up user now
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                signup_et_username.setVisibility(View.GONE);
                signup_et_phoneNumber.setVisibility(View.GONE);
                signup_et_Email.setVisibility(View.GONE);
                signup_et_password.setVisibility(View.GONE);
                signup_et_confirmPassword.setVisibility(View.GONE);
                Sign_up_user_profile_picture.setVisibility(View.INVISIBLE);
                signup_fb_signIn.setVisibility(View.INVISIBLE);
                signup_txt_signUp.setVisibility(View.INVISIBLE);
                sign_up_show_Password.setVisibility(View.INVISIBLE);
                sign_up_show_confirmation_Password.setVisibility(View.INVISIBLE);
                signup_txt_login.setVisibility(View.INVISIBLE);
                signUp_choose_image.setVisibility(View.INVISIBLE);
                checkEmailPassword();

            }
        });

    }

    private void checkEmailPassword() {
        if (signup_et_username.getText().toString().length()>=6){
            if (signup_et_phoneNumber.getText().toString().length()>=10){
                if (signup_et_Email.getText().toString().matches(emailPattern)){
                    if (signup_et_password.getText().toString().length()>=8){
                        if (signup_et_confirmPassword.getText().toString().equals(signup_et_password.getText().toString()) && signup_et_confirmPassword.length()>=8){

                            // sign the user up when all the input matches

                            uploadImage();



                        }
                        else{
                            progressBar.setVisibility(View.INVISIBLE);
                            signup_et_username.setVisibility(View.VISIBLE);
                            signup_et_phoneNumber.setVisibility(View.VISIBLE);
                            signup_et_Email.setVisibility(View.VISIBLE);
                            signup_et_password.setVisibility(View.VISIBLE);
                            signup_et_confirmPassword.setVisibility(View.VISIBLE);
                            Sign_up_user_profile_picture.setVisibility(View.VISIBLE);
                            signup_fb_signIn.setVisibility(View.VISIBLE);
                            signup_txt_signUp.setVisibility(View.VISIBLE);
                            sign_up_show_Password.setVisibility(View.VISIBLE);
                            sign_up_show_confirmation_Password.setVisibility(View.VISIBLE);
                            signup_txt_login.setVisibility(View.VISIBLE);
                            signUp_choose_image.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "make sure confirm password is equal to password and not less than 8 characters", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        progressBar.setVisibility(View.INVISIBLE);
                        signup_et_username.setVisibility(View.VISIBLE);
                        signup_et_phoneNumber.setVisibility(View.VISIBLE);
                        signup_et_Email.setVisibility(View.VISIBLE);
                        signup_et_password.setVisibility(View.VISIBLE);
                        signup_et_confirmPassword.setVisibility(View.VISIBLE);
                        Sign_up_user_profile_picture.setVisibility(View.VISIBLE);
                        signup_fb_signIn.setVisibility(View.VISIBLE);
                        signup_txt_signUp.setVisibility(View.VISIBLE);
                        sign_up_show_Password.setVisibility(View.VISIBLE);
                        sign_up_show_confirmation_Password.setVisibility(View.VISIBLE);
                        signup_txt_login.setVisibility(View.VISIBLE);
                        signUp_choose_image.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "length of password must not be less than 8 characters", Toast.LENGTH_SHORT).show();
                    }


                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    signup_et_username.setVisibility(View.VISIBLE);
                    signup_et_phoneNumber.setVisibility(View.VISIBLE);
                    signup_et_Email.setVisibility(View.VISIBLE);
                    signup_et_password.setVisibility(View.VISIBLE);
                    signup_et_confirmPassword.setVisibility(View.VISIBLE);
                    Sign_up_user_profile_picture.setVisibility(View.VISIBLE);
                    signup_fb_signIn.setVisibility(View.VISIBLE);
                    signup_txt_signUp.setVisibility(View.VISIBLE);
                    sign_up_show_Password.setVisibility(View.VISIBLE);
                    sign_up_show_confirmation_Password.setVisibility(View.VISIBLE);
                    signup_txt_login.setVisibility(View.VISIBLE);
                    signUp_choose_image.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "email must be in a form of email Pattern", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                progressBar.setVisibility(View.INVISIBLE);
                signup_et_username.setVisibility(View.VISIBLE);
                signup_et_phoneNumber.setVisibility(View.VISIBLE);
                signup_et_Email.setVisibility(View.VISIBLE);
                signup_et_password.setVisibility(View.VISIBLE);
                signup_et_confirmPassword.setVisibility(View.VISIBLE);
                Sign_up_user_profile_picture.setVisibility(View.VISIBLE);
                signup_fb_signIn.setVisibility(View.VISIBLE);
                signup_txt_signUp.setVisibility(View.VISIBLE);
                sign_up_show_Password.setVisibility(View.VISIBLE);
                sign_up_show_confirmation_Password.setVisibility(View.VISIBLE);
                signup_txt_login.setVisibility(View.VISIBLE);
                signUp_choose_image.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Phone number's length must be more or equal to 10 characters", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            signup_et_username.setVisibility(View.VISIBLE);
            signup_et_phoneNumber.setVisibility(View.VISIBLE);
            signup_et_Email.setVisibility(View.VISIBLE);
            signup_et_password.setVisibility(View.VISIBLE);
            signup_et_confirmPassword.setVisibility(View.VISIBLE);
            Sign_up_user_profile_picture.setVisibility(View.VISIBLE);
            signup_fb_signIn.setVisibility(View.VISIBLE);
            signup_txt_signUp.setVisibility(View.VISIBLE);
            sign_up_show_Password.setVisibility(View.VISIBLE);
            sign_up_show_confirmation_Password.setVisibility(View.VISIBLE);
            signup_txt_login.setVisibility(View.VISIBLE);
            signUp_choose_image.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "username length must be equal to  6 or more characters ", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadImage() {
        StorageReference filPath = profilePicRef.child(signup_et_username.getText().toString() + "  " +imageUrl.getLastPathSegment()+ ".jpg");

        UploadTask uploadTask = filPath.putFile(imageUrl);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri>uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();



                        }


                        downloadUrl = filPath.getDownloadUrl().toString();
                        return filPath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadUrl = task.getResult().toString();



                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            HashMap<String, Object> userMap = new HashMap<>();
                            userMap.put("userName", signup_et_username.getText().toString());
                            userMap.put("email", signup_et_Email.getText().toString());
                            userMap.put("phoneNumber", signup_et_phoneNumber.getText().toString());
                            userMap.put("password", signup_et_password.getText().toString());
                            userMap.put("confirmPassword", signup_et_confirmPassword.getText().toString());
                            userMap.put("profilePic","" +downloadUrl);


                            firebaseFirestore.collection("USERS").add(userMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()){

                                    }

                                }
                            });



                            reference.child("Users").child(signup_et_password.getText().toString()).updateChildren(userMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){



                                            }
                                        }
                                    });






                            firebaseAuth.createUserWithEmailAndPassword(signup_et_Email.getText().toString(),signup_et_password.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(getContext(), "congratulations, your account has been created", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getContext(),LoginActivity.class));

                                            }

                                        }
                                    });
                        }


                    }
                });

            }
        });
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(signup_et_username.getText().toString())){
            if (!TextUtils.isEmpty(signup_et_phoneNumber.getText().toString())){
                if (!TextUtils.isEmpty(signup_et_Email.getText().toString())){
                    if (!TextUtils.isEmpty(signup_et_password.getText().toString())){
                        if (!TextUtils.isEmpty(signup_et_confirmPassword.getText().toString())){
                            signup_btn.setVisibility(View.VISIBLE);


                        }
                        else{
                            signup_btn.setVisibility(View.GONE);

                        }
                    }
                    else{
                        signup_btn.setVisibility(View.GONE);

                    }
                }
                else{
                    signup_btn.setVisibility(View.GONE);

                }
            }
            else{
                signup_btn.setVisibility(View.GONE);

            }
        }
        else{
            signup_btn.setVisibility(View.GONE);

        }
    }

    private void showAlertDialog() {
        String options[] = new String[]{"Camera","Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Pick image")
        .setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // handle clicks
                if (which == 0){
                    // means camera is clicked
                    // if camera is clicked, ask for camera permission
                    if (cameraPermission()){
                        // if permission granted
                        // pick image from camera
                        pickImageFromCamera();

                    }
                    else {
                        // if permission is not granted or denied, request for camera permission
                        requestCameraPermission();
                    }
                }
                else{
                    // gallery is clicked
                    // if gallery is clicked, ask for storage permission
                    if (storagePermission()){
                        // if permission granted
                        // pick image from gallery
                        pickImageFromGallery();

                    }
                    else{
                        // if permission is not granted or denied, request for storage permission
                        requestStoragePermission();

                    }
                }
            }
        }).show();



    }

    private void pickImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GALLERY_IMAGE_PICK_CODE);

    }

    private void pickImageFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"Temp_title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"Temp_description");

        ContentResolver contentResolver = getActivity().getContentResolver();

        imageUrl = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUrl);

        startActivityForResult(cameraIntent,CAMERA_IMAGE_PICK_CODE);


    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(getActivity(),storagePermission,STORAGE_REQUEST_CODE);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(getActivity(),cameraPermission,CAMERA_REQUEST_CODE);
    }

    private boolean storagePermission() {
        boolean result = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);


        return result;

    }

    private boolean cameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        return result&& result1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        pickImageFromCamera();
                    }
                    else{
                        Toast.makeText(getContext(), "camera permissions are necessary...", Toast.LENGTH_SHORT).show();
                    }
                }

            }break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted){
                        pickImageFromGallery();
                    }
                    else{
                        Toast.makeText(getContext(), "storage permission is necessary...", Toast.LENGTH_SHORT).show();
                    }
                }

            }break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == CAMERA_IMAGE_PICK_CODE){
                imageUrl = data.getData();
                try {
                    Picasso.get().load(imageUrl).into(Sign_up_user_profile_picture);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
            else{
                if (requestCode == GALLERY_IMAGE_PICK_CODE){
                    imageUrl = data.getData();
                    try {
                        Picasso.get().load(imageUrl).into(Sign_up_user_profile_picture);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}