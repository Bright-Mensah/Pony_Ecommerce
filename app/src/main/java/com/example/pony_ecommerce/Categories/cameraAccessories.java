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
 * Use the {@link cameraAccessories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cameraAccessories extends Fragment {
    private RecyclerView recyclerView;
    private List<CategoriesList> cameraList;
    private CategoryListAdapter adapter;
    private TextView categoryTitle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public cameraAccessories() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cameraAccessories.
     */
    // TODO: Rename and change types and number of parameters
    public static cameraAccessories newInstance(String param1, String param2) {
        cameraAccessories fragment = new cameraAccessories();
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
        View view =  inflater.inflate(R.layout.fragment_camera_accessories, container, false);
        categoryTitle = view.findViewById(R.id.camera_Accessories_category_title);

        recyclerView = view.findViewById(R.id.category_cameraAccessories_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // set the title for the category
        categoryTitle.setText("Camera Accessories");


        // arrayList
        cameraList = new ArrayList<>();

        // clear arrayList
        clearArrayList();

        // get items from firebase
        getDataFromFirebase();

        return view;
    }

    private void getDataFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("categoryItems").child("categoryCameraAccessories");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // clear arrayList and adapter to notify the list that new item has been inserted
                clearArrayList();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    // model class here
                    CategoriesList list = new CategoriesList();

                    // set the model class with the dataSnapShot to display the items in the firebase
                    list.setCartImage(dataSnapshot.child("imageUrl").getValue().toString());
                    list.setCartName(dataSnapshot.child("name").getValue().toString());
                    list.setCartPrice(dataSnapshot.child("price").getValue().toString());

                    // set the arrayList with the model class
                    cameraList.add(list);
                }
                // set the recyclerView with the adapter here
                adapter = new CategoryListAdapter(cameraList,getContext());
                // set the recyclerView with the adapter
                recyclerView.setAdapter(adapter);
                //notify dataSet Changed for the adapter when item changes
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearArrayList() {
        if (cameraList !=null){
            // contains element
            // therefore clear arrayList
            cameraList.clear();

            // check if adapter too is empty
            if (adapter!=null){
                // contains element
                // therefore notify dataSet changed
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
        // show toolbar when user returns to the home fragment
        super.onStop();
    }
}