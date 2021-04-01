package com.example.pony_ecommerce.Categories;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pony_ecommerce.modelClass.CategoriesList;
import com.example.pony_ecommerce.Adapters.CategoryListAdapter;
import com.example.pony_ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Category_FemaleDress#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Category_FemaleDress extends Fragment {
    private TextView category_title;
    private RecyclerView recyclerView;
    private List<CategoriesList>categoriesLists;
    private  CategoryListAdapter adapter;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Category_FemaleDress() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Category_FemaleDress.
     */
    // TODO: Rename and change types and number of parameters
    public static Category_FemaleDress newInstance(String param1, String param2) {
        Category_FemaleDress fragment = new Category_FemaleDress();
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
        View view =  inflater.inflate(R.layout.fragment_category__female_dress, container, false);






        // categoryTitle
        category_title = view.findViewById(R.id.category_title);

        category_title.setText("Category: Female Dress");

        recyclerView = view.findViewById(R.id.recyclerview_category_femaleDress);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        categoriesLists = new ArrayList<>();


        clearAll();

        getDataFromFirebase();



        return view;
    }

    private void clearAll() {

        // check if the list is empty
        if (categoriesLists !=null){
            categoriesLists.clear();

            if (adapter !=null){
                adapter.notifyDataSetChanged();
            }
        }
        else{
            categoriesLists = new ArrayList<>();
        }
    }

    private void getDataFromFirebase() {
        // fetching data from database and display it on recyclerView
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("categoryItems").child("categoryFemaleDress");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    CategoriesList list = new CategoriesList();

                    list.setCartImage(dataSnapshot.child("imageUrl").getValue().toString());
                    list.setCartName(dataSnapshot.child("name").getValue().toString());
                    list.setCartPrice(dataSnapshot.child("price").getValue().toString());

                    categoriesLists.add(list);
                }
                // set adapter
                 adapter = new CategoryListAdapter(categoriesLists,getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    public void onResume() {
        (( AppCompatActivity) getActivity()).getSupportActionBar().hide();
        // hide  toolbar when user moves from home fragment to different fragment

        super.onResume();
    }

    @Override
    public void onStop() {
        (( AppCompatActivity) getActivity()).getSupportActionBar().show();
        /*show toolbar when user moves from different fragment to home fragment
        or show toolbar when the user returns to the home fragment
         */
        super.onStop();
    }
}