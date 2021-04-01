package com.example.pony_ecommerce.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pony_ecommerce.Categories.Category_Shoes;
import com.example.pony_ecommerce.Categories.cameraAccessories;
import com.example.pony_ecommerce.Categories.categoryBabyProduct;
import com.example.pony_ecommerce.Categories.categoryBag;
import com.example.pony_ecommerce.Categories.categoryGaming;
import com.example.pony_ecommerce.Categories.categoryMuscialInstrument;
import com.example.pony_ecommerce.Categories.categoryPhonesAndTablet;
import com.example.pony_ecommerce.Categories.categorySpeakers;
import com.example.pony_ecommerce.Categories.categorySportingGoods;
import com.example.pony_ecommerce.Categories.categoryTelevision;
import com.example.pony_ecommerce.Categories.categoryWatches;
import com.example.pony_ecommerce.Categories.computer_Accessories;
import com.example.pony_ecommerce.Categories.female_Necklace;
import com.example.pony_ecommerce.modelClass.HomeCategoriesList;
import com.example.pony_ecommerce.Categories.Category_FemaleDress;
import com.example.pony_ecommerce.R;

import java.util.List;

public class homeCategoriesAdapter extends RecyclerView.Adapter<homeCategoriesAdapter.ViewHolder> {

    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;


    private List<HomeCategoriesList> categoriesLists;
    private Context context;

    public homeCategoriesAdapter(List<HomeCategoriesList> categoriesLists, Context context) {
        this.categoriesLists = categoriesLists;
        this.context = context;
    }

    @NonNull
    @Override
    public homeCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull homeCategoriesAdapter.ViewHolder holder, int position) {
        HomeCategoriesList list = categoriesLists.get(position);
        holder.categoryName.setText(list.getCatName());
        Glide.with(context).load(list.getCatIcon()).placeholder(R.drawable.red_cart).into(holder.categoryIcon);


    }

    @Override
    public int getItemCount() {
        return categoriesLists.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView categoryName;
        ImageView categoryIcon;

        public ViewHolder(View itemView){
            super(itemView);

            categoryName = itemView.findViewById(R.id.category_name);
            categoryIcon = itemView.findViewById(R.id.category_icon);


            // category item on home screen
            // set them on click so that it can communicate with the user
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    // helps to call the fragment when you are in adapter
                    AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();






                    frameLayout = appCompatActivity.findViewById(R.id.home_Framelayout);
                    fragmentManager = appCompatActivity.getSupportFragmentManager();








                    // get  category item position
                    int position = getAdapterPosition();

                    // check if category item still exist
                    if (position != 0 || position !=RecyclerView.NO_POSITION){
                        // if items still exist or position is not equal to 0
                        // then get the position of the item
                        HomeCategoriesList list = categoriesLists.get(position);

                        // after getting the position of the item
                        // let set the position of each item on the category to its fragment

                        if (position == 0){
                            //  send the user to the category item page
                            setFragment(new Category_FemaleDress());

                        }
                        else if (position ==1){
                            setFragment(new female_Necklace());

                        }
                        else if (position ==2){
                            setFragment(new computer_Accessories());


                        }
                        else if (position ==3){
                            setFragment(new cameraAccessories());


                        }
                        else if (position ==4){
                            setFragment(new categoryBabyProduct());


                        }
                        else if (position ==5){
                            setFragment(new categoryMuscialInstrument());


                        }
                        else if (position ==6){
                            setFragment(new categorySportingGoods());


                        }
                        else if (position ==7){
                            setFragment(new categoryPhonesAndTablet());


                        }
                        else if (position ==8){
                            setFragment(new Category_Shoes());


                        }
                        else if (position ==9){
                            setFragment(new categorySpeakers());


                        }
                        else if (position ==10){
                            setFragment(new categoryGaming());


                        }
                        else if (position ==11){
                            setFragment(new categoryBag());


                        }
                        else if (position ==12){
                            setFragment(new categoryWatches());


                        }
                        else if (position ==13){
                            setFragment(new categoryTelevision());


                        }




                    }


                }





            });
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(frameLayout.getId(),fragment).addToBackStack(null).setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.commit();



    }



}
