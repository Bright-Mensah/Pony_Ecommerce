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
 * Use the {@link female_Necklace#newInstance} factory method to
 * create an instance of this fragment.
 */
public class female_Necklace extends Fragment {
    private RecyclerView recyclerView;
    private List<CategoriesList> necklaceList;
    private TextView cat_Tile;
    private CategoryListAdapter adapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public female_Necklace() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Femalenecklace.
     */
    // TODO: Rename and change types and number of parameters
    public static female_Necklace newInstance(String param1, String param2) {
        female_Necklace fragment = new female_Necklace();
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
        View view = inflater.inflate(R.layout.fragment_femalenecklace, container, false);
        cat_Tile = view.findViewById(R.id.femaleNecklace_category_title);

        cat_Tile.setText("Female Necklace");

        recyclerView = view.findViewById(R.id.femaleNecklace_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        necklaceList = new ArrayList<>();

        // check if list is empty
        clearNecklaceList();

        getDataFromFirebase();

        return view;
    }

    private void getDataFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("categoryItems").child("categoryFemaleNecklace");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // check if array list is not empty then add items to the arrayList
                clearNecklaceList();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    CategoriesList list = new CategoriesList();

                    list.setCartImage(dataSnapshot.child("imageUrl").getValue().toString());
                    list.setCartName(dataSnapshot.child("name").getValue().toString());
                    list.setCartPrice(dataSnapshot.child("price").getValue().toString());

                    // set items to arrayList
                    necklaceList.add(list);
                }

                // set adapter with the recyclerView
                adapter = new CategoryListAdapter(necklaceList,getContext());
                // set adapter
                recyclerView.setAdapter(adapter);
                // notify dataSetChanged
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearNecklaceList() {
        if (necklaceList !=null){
            // arrayList contain element
            // therefore clear arrayList
            necklaceList.clear();

            // check if the adapter is empty
            if (adapter !=null){
                // adapter contains element
                // therefore notify adapter when data changes
                adapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onResume() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        // hide actionBar when user navigate to different fragment
        super.onResume();
    }

    @Override
    public void onStop() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        // show toolbar when user returns  to home fragment
        super.onStop();
    }
}