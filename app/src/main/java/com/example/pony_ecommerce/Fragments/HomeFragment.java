package com.example.pony_ecommerce.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;


import com.example.pony_ecommerce.ViewPager.DisplayItemsViewPagerAdapter;

import com.example.pony_ecommerce.Adapters.gridItemsAdapter;
import com.example.pony_ecommerce.Adapters.homeCategoriesAdapter;


import com.example.pony_ecommerce.Adapters.ShoeDisplayAdapter;
import com.example.pony_ecommerce.Adapters.stripAdAdapter;
import com.example.pony_ecommerce.Categories.Category_FemaleDress;
import com.example.pony_ecommerce.R;
import com.example.pony_ecommerce.modelClass.CategoriesList;
import com.example.pony_ecommerce.modelClass.DisplayItemModelClass;
import com.example.pony_ecommerce.modelClass.HomeCategoriesList;
import com.example.pony_ecommerce.modelClass.shoeDisplayModelClass;
import com.example.pony_ecommerce.modelClass.stripAdModelClass;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private RecyclerView home_categories__recyclerview;
    private List<HomeCategoriesList> homeCategoriesLists;
    private ViewPager viewPager;
    private List<DisplayItemModelClass>itemsList;
    private int currentPage = 0;
    private Timer timer;
    private DisplayItemsViewPagerAdapter adapter1;
    private DisplayItemModelClass items;
    private TabLayout tab_indicator;
    private RecyclerView shoeRecyclerView;
    private List<shoeDisplayModelClass> shoeList;
    private ShoeDisplayAdapter shoeAdapter;
    private RecyclerView stripAdRecyclerview;
    private List<stripAdModelClass>stripAdList;
    private stripAdAdapter stripAdapter;
    private gridItemsAdapter gridAdapter;
    private List<CategoriesList>gridList;
    private GridView gridView;
    private FrameLayout frameLayout;
    private RecyclerView secondStripAdRecyclerView;
     private List<stripAdModelClass>secondStripAdList;
    private stripAdAdapter secondStripAdapter;












    private final long DELAYS_MS = 500; // delay in milliseconds before task is to be executed
    private final long PERIOD_MS = 3000; // time in milliseconds between successive task  executions


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        home_categories__recyclerview = view.findViewById(R.id.home_category_recyclerview);

        home_categories__recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        homeCategoriesLists = new ArrayList<>();




        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_female_dress, "Female Dress"));
        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_female_necklace, "Female Necklace"));
        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_computer_accessories, "Computer Accessories"));
        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_camera_accessories, "Camera Accessories"));
        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_baby_product, "Baby \nProduct"));
        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_musical_instrument, "Musical Instrument"));
        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_sporting_goods, "Sporting goods"));
        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_phones_and_tablet, "Phones \n And Tablet"));
        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_shoes, "Shoes"));
        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_speakers, "Speakers"));
        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_gaming, "Gaming"));
        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_bag, "Bag"));
        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_watches, "Watches"));
        homeCategoriesLists.add(new HomeCategoriesList(R.mipmap.cart_television, "Television"));


        homeCategoriesAdapter adapter = new homeCategoriesAdapter(homeCategoriesLists, getContext());

        home_categories__recyclerview.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setClipToOutline(false);
        viewPager.setPageMargin(20);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE){

//                    if (itemsList.size() !=6){
//                        currentPage = 0;
//                    }
                    if (currentPage ==itemsList.size()){
                        currentPage = 0;
                        viewPager.setCurrentItem(currentPage++,true);

                    }



                }

            }
        };
        viewPager.addOnPageChangeListener(onPageChangeListener);

        startViewPagerSlideShow();

        // when the user touch the viewpager
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                stopViewPagerSlideShow();
                if (event.getAction() == MotionEvent.ACTION_UP){
                    startViewPagerSlideShow();
                }

                return false;
            }
        });




        tab_indicator = view.findViewById(R.id.tab_Indicator);

        itemsList = new ArrayList<>();

        clearAll();

        getDataFromFirebase();



//        for (int i =0; i<4;i++) {
////            itemsList.add(new DisplayItemModelClass(R.mipmap.category_shoes));
//            itemsList.add(items);
//
//
//        }



            getActivity().getWindow().setGravity(R.dimen.activity_horizontal_margin);





        // shoe recyclerView

        shoeRecyclerView = view.findViewById(R.id.shoes_display_recyclerView);
        shoeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        shoeRecyclerView.setHasFixedSize(true);

        shoeList = new ArrayList<>();

        clearAllForShoes();


        getShoeDataFromFirebase();


        
        // stripAd RecyclerView
        
        stripAdRecyclerview = view.findViewById(R.id.strip_AdRecyclerView);

        stripAdRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        stripAdRecyclerview.setHasFixedSize(true);
        stripAdRecyclerview.setItemAnimator(new DefaultItemAnimator());


        
        stripAdList = new ArrayList<>();
        
        clearStripAdAll();
        
        getStripAdDataFromFirebase();



        // display female dress in a grid layout style

        frameLayout = getActivity().findViewById(R.id.home_Framelayout);


        TextView gridTitle = view.findViewById(R.id.gridItems_layout_title);
        Button btnViewAll = view.findViewById(R.id.gridItem_ViewAll);
         gridView = view.findViewById(R.id.gridView);


         gridTitle.setText("Female Dress");

         btnViewAll.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 setFragment(new Category_FemaleDress());

             }
         });



        gridList = new ArrayList<>();


        clearGridItemsAll();

        getGridItemsDataFromFirebase();


        // second strip ad recyclerview

        secondStripAdRecyclerView = view.findViewById(R.id.second_strip_AdRecyclerView);

        secondStripAdRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        secondStripAdRecyclerView.setHasFixedSize(true);

        secondStripAdList = new ArrayList<>();

        clearSecondStripAdsAll();

        getSecondStripAdDataFromFirebase();












        return view;
        
        
        
    }


    private void getSecondStripAdDataFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("stripAd").child("obj2");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String image = snapshot.child("imageUrl").getValue().toString();
                    stripAdModelClass items = new stripAdModelClass();
                    items.setStripAdImage(image);

                    secondStripAdList.add(items);

                }
                stripAdAdapter secondStripAdapter = new stripAdAdapter(secondStripAdList,getContext());
                secondStripAdRecyclerView.setAdapter(secondStripAdapter);
                secondStripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void clearSecondStripAdsAll() {
        if (secondStripAdList !=null){
            // contains element
            // clear arrayList
            secondStripAdList.clear();

            if (secondStripAdapter !=null){
                // contains element
                // therefore notify the adapter
                secondStripAdapter.notifyDataSetChanged();
            }
        }
    }

    private void getGridItemsDataFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("categoryItems").child("categoryFemaleDress");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearGridItemsAll();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    CategoriesList list = new CategoriesList();
                    list.setCartImage(dataSnapshot.child("imageUrl").getValue().toString());
                    list.setCartName(dataSnapshot.child("name").getValue().toString());
                    list.setCartPrice(dataSnapshot.child("price").getValue().toString());

                    //set arrayList with the model class
                    gridList.add(list);
                }
                // set adapter to the gridView
                gridAdapter  = new gridItemsAdapter(gridList,getContext());
                gridView.setAdapter(gridAdapter);
                // notify adapter if there's any change
                gridAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearGridItemsAll() {
        if (gridList !=null){
            // contain something
            // clear arrayList
            gridList.clear();

            if (gridAdapter !=null){
                //contain something
                // nothing adapter when there's changes
                gridAdapter.notifyDataSetChanged();
            }
        }
    }


    private void stopViewPagerSlideShow() {
        timer.cancel();
    }

    private void startViewPagerSlideShow() {
        // after setting the viewpager and tabLayout move to the timer or runnable

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage >= itemsList.size()){
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++,true);

            }
        };
        timer = new Timer(); // This is a new Thread
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAYS_MS,PERIOD_MS);



    }

    @Override
    public void onPause() {
        timer.cancel();
        super.onPause();
    }

    @Override
    public void onResume() {

        super.onResume();
    }





    private void getStripAdDataFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("stripAd");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearStripAdAll();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    // call your model class for strip Ad
                    stripAdModelClass items = new stripAdModelClass();

                    items.setStripAdImage(dataSnapshot.child("imageUrl").getValue().toString());

                    stripAdList.add(items);

                }
                // set stripAd adapter here
                stripAdapter = new stripAdAdapter(stripAdList,getContext());
                stripAdRecyclerview.setAdapter(stripAdapter);
                stripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearStripAdAll() {
        if (stripAdList !=null){
            stripAdList.clear();

            if (stripAdapter !=null){
                stripAdapter.notifyDataSetChanged();
            }
        }
    }

    private void getShoeDataFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("shoes");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                clearAllForShoes();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    shoeDisplayModelClass items = new shoeDisplayModelClass();
                    items.setShoeImage(dataSnapshot.child("imageUrl").getValue().toString());
                    items.setShoeName(dataSnapshot.child("name").getValue().toString());
                    items.setShoePrice(dataSnapshot.child("price").getValue().toString());

                    shoeList.add(items);
                }
                 shoeAdapter = new ShoeDisplayAdapter(shoeList,getContext());
                shoeRecyclerView.setAdapter(shoeAdapter);
                shoeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearAllForShoes() {
        if (shoeList !=null){
            shoeList.clear();

            if (shoeAdapter !=null){
                shoeAdapter.notifyDataSetChanged();
            }


        }
    }

    private void getDataFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("categoryItems").child("categoryFemaleDress");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                 items = new DisplayItemModelClass();
                    items.setGetImage(dataSnapshot.child("imageUrl").getValue().toString());

                    itemsList.add(items);


                }


                    adapter1 = new DisplayItemsViewPagerAdapter(itemsList,getContext());
                viewPager.setAdapter(adapter1);



                // set tab indicator with view pager
                tab_indicator.setupWithViewPager(viewPager);









            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





    private void clearAll() {
        if (itemsList != null){
            itemsList.clear();

            if (adapter1 !=null){
                adapter1.notifyDataSetChanged();
            }
        }
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(frameLayout.getId(),fragment).addToBackStack(null);

        fragmentTransaction.commit();

    }


}