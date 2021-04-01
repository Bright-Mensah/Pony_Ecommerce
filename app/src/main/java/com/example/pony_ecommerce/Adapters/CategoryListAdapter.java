package com.example.pony_ecommerce.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pony_ecommerce.PurchasingGoods.purchaseFemaleDress;
import com.example.pony_ecommerce.modelClass.CategoriesList;
import com.example.pony_ecommerce.R;

import java.util.List;

public class CategoryListAdapter  extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder>{
    private List<CategoriesList>categoriesList;
    private Context context;
    private FragmentManager fragmentManager;
    private FrameLayout frameLayout;

    public CategoryListAdapter(List<CategoriesList> categoriesList, Context context) {
        this.categoriesList = categoriesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_female_dress_items_layout,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriesList list = categoriesList.get(position);
        holder.cartName.setText(list.getCartName());
        holder.cartPrice.setText(list.getCartPrice());




        Glide.with(context).load(list.getCartImage()).placeholder(R.drawable.red_cart).into(holder.cart_Icon);
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cart_Icon;
        TextView cartName,cartPrice;

        public ViewHolder(View itemView){
            super(itemView);

            cart_Icon = itemView.findViewById(R.id.cart_femaleDress_icon);
            cartName = itemView.findViewById(R.id.cart_femaleDress_Name);
            cartPrice = itemView.findViewById(R.id.cart_femaleDress_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();

                    fragmentManager = appCompatActivity.getSupportFragmentManager();
                    frameLayout = appCompatActivity.findViewById(R.id.home_Framelayout);






                    int position = getAdapterPosition();

                    if (position !=0 || position !=RecyclerView.NO_POSITION){

                        CategoriesList list = categoriesList.get(position);


                        if (position==0){
                            setFragment(new purchaseFemaleDress());
                        }
                    }
                }
            });
        }

    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
