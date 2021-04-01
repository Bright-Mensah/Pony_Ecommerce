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
 * Use the {@link categoryWatches#newInstance} factory method to
 * create an instance of this fragment.
 */
public class categoryWatches extends Fragment {
    private RecyclerView recyclerView;
    private List<CategoriesList>watchesList;
    private CategoryListAdapter adapter;
    private TextView catTitle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public categoryWatches() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment categoryWatches.
     */
    // TODO: Rename and change types and number of parameters
    public static categoryWatches newInstance(String param1, String param2) {
        categoryWatches fragment = new categoryWatches();
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
        View view =  inflater.inflate(R.layout.fragment_category_watches, container, false);
        recyclerView = view.findViewById(R.id.watches_recyclerview_category);
        catTitle = view.findViewById(R.id.watches_category_title);

        // set layout manager for recyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // set category Title
        catTitle.setText("Watches");

        // arrayList
        watchesList = new ArrayList<>();

        // check if arrayList is empty
      clearArrayList();

      // get data from firebase
        getDataFromFirebase();

        return view;
    }

    private void getDataFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("categoryItems").child("categoryWatches");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // clear arrayList and notify adapter when data changes
                clearArrayList();

                // get position of each item in the database
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    // call the model class to assign the arrayList in order to display items on the recyclerView
                    CategoriesList list = new CategoriesList();
                    list.setCartImage(dataSnapshot.child("imageUrl").getValue().toString());
                    list.setCartName(dataSnapshot.child("name").getValue().toString());
                    list.setCartPrice(dataSnapshot.child("price").getValue().toString());

                    // assign the arrayList with the model class
                    watchesList.add(list);
                }
                // set the recyclerView witb the adapter
                adapter = new CategoryListAdapter(watchesList,getContext());

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
        if (watchesList !=null){
            // contains element
            // therefore clear ArrayList
            watchesList.clear();

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
        // hide toolbar when user navigate to fragment
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        super.onResume();
    }

    @Override
    public void onStop() {
        // show toolbar when user returns to the home fragment
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        super.onStop();
    }
}