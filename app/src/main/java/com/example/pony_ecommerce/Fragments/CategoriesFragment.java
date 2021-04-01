package com.example.pony_ecommerce.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.pony_ecommerce.Adapters.fragmentCategoriesAdapter;
import com.example.pony_ecommerce.modelClass.fragmentCategoriesList;
import com.example.pony_ecommerce.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {

    private FrameLayout frameLayout;
    private RecyclerView categoriesRecyclerview;
    private List<fragmentCategoriesList> categoryList;





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
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
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        frameLayout = getActivity().findViewById(R.id.home_Framelayout);
        categoriesRecyclerview = view.findViewById(R.id.categories_recyclerview);

        categoriesRecyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));
        categoriesRecyclerview.setHasFixedSize(true);



        categoryList = new ArrayList<>();

        categoryList.add(new fragmentCategoriesList(R.mipmap.category_shoes,"Shoes"));
        categoryList.add(new fragmentCategoriesList(R.mipmap.category_female_dress,"Female Dress"));
        categoryList.add(new fragmentCategoriesList(R.mipmap.category_female_necklace,"Female Necklace"));
        categoryList.add(new fragmentCategoriesList(R.mipmap.category_phones_and_tablet,"Phones And Tablet"));
        categoryList.add(new fragmentCategoriesList(R.mipmap.categroy_computer_accessories,"Computer Accessories"));
        categoryList.add(new fragmentCategoriesList(R.mipmap.category_camera_accessories,"Camera Accessories"));
        categoryList.add(new fragmentCategoriesList(R.mipmap.category_speakers,"Speakers"));
        categoryList.add(new fragmentCategoriesList(R.mipmap.category_gaming,"Gaming"));
        categoryList.add(new fragmentCategoriesList(R.mipmap.category_bags,"Bags"));
        categoryList.add(new fragmentCategoriesList(R.mipmap.category_baby_product,"Baby Product"));
        categoryList.add(new fragmentCategoriesList(R.mipmap.category_musical_instrument,"Musical Instrument"));
        categoryList.add(new fragmentCategoriesList(R.mipmap.category_watches,"Watches"));
        categoryList.add(new fragmentCategoriesList(R.mipmap.category_sporting_goods,"Sporting goods"));
        categoryList.add(new fragmentCategoriesList(R.mipmap.category_television,"Television"));


        fragmentCategoriesAdapter adapter = new fragmentCategoriesAdapter(categoryList,getContext());

        categoriesRecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();




















        return view;

    }

    }



