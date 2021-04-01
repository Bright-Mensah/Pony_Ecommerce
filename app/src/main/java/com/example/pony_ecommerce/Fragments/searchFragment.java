package com.example.pony_ecommerce.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pony_ecommerce.Adapters.searchTaskAdapter;
import com.example.pony_ecommerce.R;
import com.example.pony_ecommerce.modelClass.searchTask;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<searchTask>taskList;
    private TextView catName;
    private searchTaskAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public searchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static searchFragment newInstance(String param1, String param2) {
        searchFragment fragment = new searchFragment();
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
        View view =  inflater.inflate(R.layout.fragment_search2, container, false);

        recyclerView = view.findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        catName = view.findViewById(R.id.searchTaskTitle);
        catName.setText("click on the item to search what you want");

        taskList = new ArrayList<>();


        taskList.add(new searchTask("Female Dress",R.mipmap.cart_female_dress));
        taskList.add(new searchTask("Female Necklace",R.mipmap.cart_female_necklace));
        taskList.add(new searchTask("Computer Accessories",R.mipmap.cart_computer_accessories));
        taskList.add(new searchTask("Camera Accessories",R.mipmap.cart_camera_accessories));
        taskList.add(new searchTask("Baby Product",R.mipmap.cart_baby_product));
        taskList.add(new searchTask("Sporting goods",R.mipmap.cart_sporting_goods));
        taskList.add(new searchTask("Phones And Tablet",R.mipmap.cart_sporting_goods));
        taskList.add(new searchTask("Shoes",R.mipmap.cart_shoes));
        taskList.add(new searchTask("Speakers",R.mipmap.cart_speakers));
        taskList.add(new searchTask("Gaming",R.mipmap.cart_gaming));
        taskList.add(new searchTask("Bag",R.mipmap.cart_bag));
        taskList.add(new searchTask("Watches",R.mipmap.cart_watches));
        taskList.add(new searchTask("Television",R.mipmap.cart_television));





        adapter = new searchTaskAdapter(taskList,getContext());

        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();



        return view;
    }
}