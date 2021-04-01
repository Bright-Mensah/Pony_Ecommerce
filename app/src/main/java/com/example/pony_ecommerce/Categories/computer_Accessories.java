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
 * Use the {@link computer_Accessories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class computer_Accessories extends Fragment {
    private RecyclerView recyclerView;
    private List<CategoriesList> accessoriesList;
    private CategoryListAdapter adapter;
    private TextView cart_Title;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public computer_Accessories() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment computer_Accessories.
     */
    // TODO: Rename and change types and number of parameters
    public static computer_Accessories newInstance(String param1, String param2) {
        computer_Accessories fragment = new computer_Accessories();
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
        View view =  inflater.inflate(R.layout.fragment_computer__accessories, container, false);
        cart_Title = view.findViewById(R.id.computerAccessories_category_title);

        cart_Title.setText("Computer Accessories");

        recyclerView = view.findViewById(R.id.recyclerview_computerAccessoriesCategory);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        // arrayList
        accessoriesList = new ArrayList<>();

        // check if arrayList is empty
        clearArrayList();

        getDataFromFirebase();

        return view;
    }

    private void getDataFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("categoryItems").child("categoryComputerAccessories");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearArrayList();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    CategoriesList list  = new CategoriesList();
                    list.setCartImage(dataSnapshot.child("imageUrl").getValue().toString());
                    list.setCartName(dataSnapshot.child("name").getValue().toString());
                    list.setCartPrice(dataSnapshot.child("price").getValue().toString());

                    accessoriesList.add(list);
                }
                // set adapter with recyclerView
                adapter = new CategoryListAdapter(accessoriesList,getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearArrayList() {
        if (accessoriesList !=null){
            //contains element
            // clear arrayList
            accessoriesList.clear();

            if (adapter !=null){
                // contain element
                // therefore notify Adapter
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        // hide  toolbar when user moves from home fragment to different fragment
        super.onResume();
    }

    @Override
    public void onStop() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        // show toolbar when user returns back to home  fragment
        super.onStop();
    }
}