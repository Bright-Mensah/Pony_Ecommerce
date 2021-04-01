package com.example.pony_ecommerce.Categories;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
 * Use the {@link Category_Shoes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Category_Shoes extends Fragment {
    private RecyclerView recyclerView;
    private CategoryListAdapter adapter;
    private List<CategoriesList>categoriesLists;
    private TextView categoryTitle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Category_Shoes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Shoes_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Category_Shoes newInstance(String param1, String param2) {
        Category_Shoes fragment = new Category_Shoes();
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
        View view =  inflater.inflate(R.layout.fragment_shoes_, container, false);


        categoryTitle = view.findViewById(R.id.shoe_category_title);

        categoryTitle.setText("Shoes");

        recyclerView = view.findViewById(R.id.shoes_recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        recyclerView.setHasFixedSize(true);


        adapter = new CategoryListAdapter(categoriesLists,getContext());


        categoriesLists = new ArrayList<>();


        // check if array list is empty

        clearList();

        getDataFromFirebase();




        return view;





    }

    private void getDataFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("categoryItems").child("categoryShoes");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearList();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    CategoriesList list = new CategoriesList();
                    list.setCartImage(dataSnapshot.child("imageUrl").getValue().toString());
                    list.setCartName(dataSnapshot.child("name").getValue().toString());
                    list.setCartPrice(dataSnapshot.child("price").getValue().toString());

                    categoriesLists.add(list);

                }
                // set adapter for the recyclerView
                adapter = new CategoryListAdapter(categoriesLists,getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearList() {
        // check if list is empty
        if (categoriesLists !=null){
            // means list contains element
            // therefore clear ArrayList
            categoriesLists.clear();

            // check if adapter is empty
            if (adapter !=null){
                // means contains element
                // therefore notify adapter when data changes
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        // hide toolbar when user navigate to the fragment
        super.onResume();
    }

    @Override
    public void onStop() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        // show toolbar when user returns  to the home fragment
        super.onStop();
    }
}