package com.example.pony_ecommerce.searchCategoriesFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
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

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchFemaleDressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchFemaleDressFragment extends Fragment {
    private RecyclerView recyclerView;
    private EditText etSearch;
    private List<searchList>searchLists;
    private FirebaseRecyclerOptions<searchList>options;
    private FirebaseRecyclerAdapter<searchList,ViewHolder> adapter;
    private DatabaseReference dataRef;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public searchFemaleDressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchFemaleDressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static searchFemaleDressFragment newInstance(String param1, String param2) {
        searchFemaleDressFragment fragment = new searchFemaleDressFragment();
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
        View view =  inflater.inflate(R.layout.fragment_search_female_dress, container, false);

        recyclerView = view.findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        etSearch = view.findViewById(R.id.etSearch);

        dataRef = FirebaseDatabase.getInstance().getReference().child("categoryItems").child("categoryFemaleDress");


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

        Query query = dataRef.orderByChild("name").startAt(data).endAt(data + "\uf8ff");



        options = new FirebaseRecyclerOptions.Builder<searchList>()
                .setQuery(query,searchList.class).build();


        adapter = new FirebaseRecyclerAdapter<searchList, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int i, @NonNull searchList searchList) {

                holder.productPrice.setText(searchList.getPrice());
                holder.productName.setText(searchList.getName().substring(0,1).toUpperCase() +searchList.getName().substring(1));


                Picasso.get().load(searchList.getImageUrl()).into(holder.productImage);

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

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView productName,productPrice;


        public ViewHolder(View itemView){
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}