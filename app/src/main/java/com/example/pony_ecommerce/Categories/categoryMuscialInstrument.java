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
 * Use the {@link categoryMuscialInstrument#newInstance} factory method to
 * create an instance of this fragment.
 */
public class categoryMuscialInstrument extends Fragment {
    private RecyclerView recyclerView;
    private List<CategoriesList>musicalList;
    private CategoryListAdapter adapter;
    private TextView catTitle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public categoryMuscialInstrument() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment categoryMuscialInstrument.
     */
    // TODO: Rename and change types and number of parameters
    public static categoryMuscialInstrument newInstance(String param1, String param2) {
        categoryMuscialInstrument fragment = new categoryMuscialInstrument();
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
        View view =  inflater.inflate(R.layout.fragment_category_muscial_instrument, container, false);

        recyclerView = view.findViewById(R.id.musicalInstrument_recyclerView);
        catTitle = view.findViewById(R.id.musicalInstrument_categoryTitle);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // set category title
        catTitle.setText("Musical Instrument");

        // arrayList
        musicalList = new ArrayList<>();

        // check if arrayList is empty
        clearAllArrayList();

        // get data from firebase
        getDataFromFirebase();

        return view;
    }

    private void getDataFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("categoryItems").child("categoryMusicalInstrument");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // clear arrayList and notify adapter when data changes
                clearAllArrayList();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    // model class to assign the list to display items on the recyclerView
                    CategoriesList list = new CategoriesList();
                    list.setCartImage(dataSnapshot.child("imageUrl").getValue().toString());
                    list.setCartName(dataSnapshot.child("name").getValue().toString());
                    list.setCartPrice(dataSnapshot.child("price").getValue().toString());

                    // assign arrayList with the model class
                    musicalList.add(list);

                }
                // assign the adapter with recyclerView to display item on the recyclerView
                adapter = new CategoryListAdapter(musicalList,getContext());
                // set adapter with recyclerView
                recyclerView.setAdapter(adapter);
                // notify adapter when data changes
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearAllArrayList() {
        if (musicalList !=null){
            // contains element
            // therefore clear ArrayList
            musicalList.clear();

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
        // show toolbar when user returns to home fragment
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        super.onStop();
    }
}