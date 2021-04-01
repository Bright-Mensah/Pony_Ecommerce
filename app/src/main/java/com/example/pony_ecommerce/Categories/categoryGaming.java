package com.example.pony_ecommerce.Categories;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pony_ecommerce.Adapters.CategoryListAdapter;
import com.example.pony_ecommerce.R;
import com.example.pony_ecommerce.modelClass.CategoriesList;
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
 * Use the {@link categoryGaming#newInstance} factory method to
 * create an instance of this fragment.
 */
public class categoryGaming extends Fragment {
    private RecyclerView recyclerView;
    private List<CategoriesList>gamingList;
    private CategoryListAdapter adapter;
    private TextView cartTitle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public categoryGaming() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment categoryGaming.
     */
    // TODO: Rename and change types and number of parameters
    public static categoryGaming newInstance(String param1, String param2) {
        categoryGaming fragment = new categoryGaming();
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
        View view =  inflater.inflate(R.layout.fragment_category_gaming, container, false);

        recyclerView = view.findViewById(R.id.gaming_recyclerView);
        cartTitle = view.findViewById(R.id.gaming_categoryTitle);

        // set recyclerView layout
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // set category Title
        cartTitle.setText("Gaming");

        // arrayList
        gamingList = new ArrayList<>();

        // check if arrayList is empty
        clearArrayList();

        // get data from firebase
        getDataFromFirebase();

        return view;
    }

    private void getDataFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("categoryItems").child("categoryGaming");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // clear ArrayList and notify adapter when data changes
                clearArrayList();
                // get the position of each item in the database
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    // model class
                    CategoriesList list = new CategoriesList();
                    list.setCartImage(dataSnapshot.child("imageUrl").getValue().toString());
                    list.setCartName(dataSnapshot.child("name").getValue().toString());
                    list.setCartPrice(dataSnapshot.child("price").getValue().toString());

                    gamingList.add(list);
                }
                // set adapter with recyclerView
                adapter = new CategoryListAdapter(gamingList,getContext());
                recyclerView.setAdapter(adapter);
                // notify adapter when data changes
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearArrayList() {
        if (gamingList !=null){
            // contains element
            // therefore clear arrayList
            gamingList.clear();

            // check if adapter is empty
            if (adapter !=null){
                // contains element
                // therefore notify adapter when data changes
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        // hide toolbar when user navigate to the fragment
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        super.onResume();
    }

    @Override
    public void onStop() {
        // show toolbar when user returns to the home fragment
        super.onStop();
    }
}