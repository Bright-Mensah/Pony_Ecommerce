package com.example.pony_ecommerce.PurchasingGoods;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.pony_ecommerce.Adapters.purchaseItemAdapter;
import com.example.pony_ecommerce.R;
import com.example.pony_ecommerce.modelClass.purchaseItemModelClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link purchaseFemaleDress#newInstance} factory method to
 * create an instance of this fragment.
 */
public class purchaseFemaleDress extends Fragment {
    private RecyclerView recyclerView;
    private List<purchaseItemModelClass>itemsList;
    private purchaseItemAdapter adapter;
    private TextView quantity, productPrice,productName,txtConfirmingOrder;
    private ElegantNumberButton btnIncreaseAndDecrease;
    private Spinner spinnerLocation;
    private EditText phoneNumber;
    private Button btnPurchase;
    private static String itemPrice;
    private FragmentManager fragmentManager;
    private FrameLayout frameLayout;



    private TextView displayQuantity;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public purchaseFemaleDress() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment purchaseFemaleDress.
     */
    // TODO: Rename and change types and number of parameters
    public static purchaseFemaleDress newInstance(String param1, String param2) {
        purchaseFemaleDress fragment = new purchaseFemaleDress();
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
        View view =  inflater.inflate(R.layout.fragment_purchase_female_dress, container, false);

        recyclerView = view.findViewById(R.id.purchasingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        displayQuantity = view.findViewById(R.id.purchaseQuantity);


        fragmentManager = getActivity().getSupportFragmentManager();

        frameLayout = getActivity().findViewById(R.id.home_Framelayout);




        // clear List
        itemsList = new ArrayList<>();

        // get image from firebase and display it on the imageView
        getDataFromFirebase();


        // Instantiate widget
        quantity = view.findViewById(R.id.purchaseQuantity);
        btnIncreaseAndDecrease = view.findViewById(R.id.btnIncreaseAndDecrease);
        spinnerLocation = view.findViewById(R.id.locationSpinner);
        btnPurchase = view.findViewById(R.id.btnPurchase);
        phoneNumber = view.findViewById(R.id.purchasePhone);
        productPrice = view.findViewById(R.id.purchasePrice);
        productName = view.findViewById(R.id.purchaseName);



        // get item name and price for product price and product name in the database

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query mQuery = reference.child("categoryItems").child("categoryFemaleDress").orderByKey().limitToFirst(1);
        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    itemPrice = dataSnapshot.child("price").getValue().toString();

                    productPrice.setText("price: " +itemPrice);
                    productName.setText(name);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        // set the number of quantity to the textView
        // when the user clicks on the elegant button

        btnIncreaseAndDecrease.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                String count = btnIncreaseAndDecrease.getNumber();

                quantity.setText("Quantity = " +count);
            }
        });

        // spinner for the location
       String location =  spinnerLocation.getSelectedItem().toString();

       // phone number

        String phone = phoneNumber.getText().toString();

        // purchase button

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if phone number is not empty
                if (TextUtils.isEmpty(phoneNumber.getText().toString())){
                    // if phone is empty quickly prompt user to enter phone number
                    phoneNumber.setError("Please enter phone number");
                }else {
                    // let the user confirm the item he or she is purchasing
                    confirmItem();

                }
            }
        });






        return view;
    }

    private void confirmItem() {
        Dialog dialog = new Dialog(getContext());

        dialog.setContentView(R.layout.confirm_purchase);

        dialog.setTitle("Confirm Order");

        dialog.setCanceledOnTouchOutside(false);


                    TextView totalPrice = dialog.findViewById(R.id.confirmTotalPrice);
                    TextView location = dialog.findViewById(R.id.confirmLocation);
                    TextView phone = dialog.findViewById(R.id.confirmPhone);
                    Button confirmOrder = dialog.findViewById(R.id.btnConfirmOrder);
                    CircleImageView redOrder = dialog.findViewById(R.id.red_order_image);
                    ImageButton cancelOrder = dialog.findViewById(R.id.cancelOrder);
                    txtConfirmingOrder = dialog.findViewById(R.id.confirmationText);

                    // get the price from the database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("categoryItems").child("categoryFemaleDress").orderByKey().limitToFirst(1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    String price = dataSnapshot.child("price").getValue().toString();

                    location.setText("Location: " + spinnerLocation.getSelectedItem().toString());

                    phone.setText("phone: " +phoneNumber.getText().toString());



                    totalPrice.setText("Total Price: " +String.valueOf(Integer.parseInt(price) * Integer.parseInt(btnIncreaseAndDecrease.getNumber())));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // cancel order
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new purchaseFemaleDress());
                dialog.dismiss();

            }
        });

        // confirm order

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder.setVisibility(View.INVISIBLE);
                cancelOrder.setVisibility(View.INVISIBLE);
                totalPrice.setVisibility(View.INVISIBLE);
                phone.setVisibility(View.INVISIBLE);
                location.setVisibility(View.INVISIBLE);
                redOrder.setVisibility(View.VISIBLE);
                txtConfirmingOrder.setVisibility(View.VISIBLE);

                TranslateAnimation animation = new TranslateAnimation(0.0f,400.0f,0.0f,0.0f);
                animation.setDuration(5000);
                animation.setRepeatCount(5);
                animation.setRepeatMode(2);
                redOrder.startAnimation(animation);




            }
        });







        dialog.show();

    }

    private void getDataFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("categoryItems").child("categoryFemaleDress");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsList = new ArrayList<>();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    purchaseItemModelClass items = new purchaseItemModelClass();
                    items.setImage(dataSnapshot.child("imageUrl").getValue().toString());
                    items.setPrice(dataSnapshot.child("price").getValue().toString());
                    items.setName(dataSnapshot.child("name").getValue().toString());

                    itemsList.add(items);
                }
                adapter = new purchaseItemAdapter(itemsList,getContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    protected  void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }


}