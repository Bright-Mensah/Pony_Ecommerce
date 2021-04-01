package com.example.pony_ecommerce.searchCategoriesFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pony_ecommerce.R;
import com.example.pony_ecommerce.modelClass.searchList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchCameraAccessoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchCameraAccessoriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private EditText etSearch;
    private FirebaseRecyclerOptions<searchList>searchList;
    private FirebaseRecyclerAdapter<searchList,ViewHolder>adapter;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public searchCameraAccessoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchCameraAccessoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static searchCameraAccessoriesFragment newInstance(String param1, String param2) {
        searchCameraAccessoriesFragment fragment = new searchCameraAccessoriesFragment();
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
        View view =  inflater.inflate(R.layout.fragment_search_camera_accessories, container, false);

        recyclerView = view.findViewById(R.id.searchRecyclerView);
        etSearch = view.findViewById(R.id.etSearch);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        loadData("");

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() !=null){
                    loadData(s.toString());
                }
                else{
                    loadData("");
                }

            }
        });


        return view;

    }

    private void loadData(String data) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("categoryItems")
                .child("categoryCameraAccessories");

        Query query = reference.orderByChild("name").startAt(data).endAt(data +"\uf8ff");


        searchList = new FirebaseRecyclerOptions.Builder<searchList>()
                .setQuery(query, com.example.pony_ecommerce.modelClass.searchList.class).build();

        adapter = new FirebaseRecyclerAdapter<com.example.pony_ecommerce.modelClass.searchList, ViewHolder>(searchList) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull com.example.pony_ecommerce.modelClass.searchList searchList) {

                viewHolder.productName.setText(searchList.getName().substring(0,1) + searchList.getName().substring(1));
                viewHolder.productPrice.setText(searchList.getPrice());

                Picasso.get().load(searchList.getImageUrl()).placeholder(R.drawable.red_cart).into(viewHolder.productImage);

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_layout,parent,false);

                return new ViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);




    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView  productName, productPrice;

        public ViewHolder(View itemView){
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}