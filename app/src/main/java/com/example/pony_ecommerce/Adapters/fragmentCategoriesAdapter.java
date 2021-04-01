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
import com.example.pony_ecommerce.modelClass.fragmentCategoriesList;
import com.example.pony_ecommerce.Categories.Category_FemaleDress;
import com.example.pony_ecommerce.R;

import java.util.List;

public class fragmentCategoriesAdapter  extends RecyclerView.Adapter<fragmentCategoriesAdapter.ViewHolder>{
    private List<fragmentCategoriesList>categoriesLists;
    private Context context;

    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;

    public fragmentCategoriesAdapter(List<fragmentCategoriesList> categoriesLists, Context context) {
        this.categoriesLists = categoriesLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_categories_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        fragmentCategoriesList list = categoriesLists.get(position);
        holder.categoryName.setText(list.getCategoryName());

        Glide.with(context).load(list.getCategoryIcon()).placeholder(R.drawable.red_cart).into(holder.categoryIcon);



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

            categoryName = itemView.findViewById(R.id.fragment_category_name);
            categoryIcon = itemView.findViewById(R.id.fragment_category_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    // since we are not in fragment or activity
                    // call the appCompactActivity to be able to call the fragment

                    AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();

                    frameLayout = appCompatActivity.findViewById(R.id.home_Framelayout);

                    fragmentManager = appCompatActivity.getSupportFragmentManager();




//                    bun.putInt("image",getAdapterPosition());
//                    fragment.setArguments(bun);
//                    if (fragment !=null){
//                        fragmentManager.beginTransaction().replace(frameLayout.getId(),fragment).commit();
//                    }











                    // get position of each item

                    int position = getAdapterPosition();

                    // check if category item exist or not
                    if (position !=0 || position !=RecyclerView.NO_POSITION){
                        // if the item exist then get the position of the item

                        fragmentCategoriesList list = categoriesLists.get(position);

                        // after getting the position send the user to the page of each category item
                        if (position == 0){
                            setFragment(new Category_Shoes());
                        }
                        else{
                            if (position ==1){

                                setFragment(new Category_FemaleDress());


                            } else if (position ==2){

                                setFragment(new female_Necklace());


                            }else if (position ==3){
                                setFragment(new categoryPhonesAndTablet());





                            }else if (position ==4){

                                setFragment(new computer_Accessories());


                            }else if (position ==5){

                                setFragment(new cameraAccessories());


                            }else if (position ==6){
                                setFragment(new categorySpeakers());




                            }else if (position ==7){
                                setFragment(new categoryGaming());




                            }else if (position ==8){
                                setFragment(new categoryBag());




                            }
                            else if (position ==9) {

                                setFragment(new categoryBabyProduct());

                            }

                         else if (position ==10) {

                                setFragment(new categoryMuscialInstrument());

                            }
                         else if (position ==11) {

                                setFragment(new categoryWatches());

                            }
                         else if (position ==12) {

                                setFragment(new categorySportingGoods());

                            }else if (position ==13) {
                             setFragment(new categoryTelevision());




                            }

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

