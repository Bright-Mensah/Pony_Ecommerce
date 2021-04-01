package com.example.pony_ecommerce.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.pony_ecommerce.R;
import com.example.pony_ecommerce.modelClass.CategoriesList;

import java.util.List;

public class gridItemsAdapter extends BaseAdapter {

    private List <CategoriesList>itemList;
    private Context context;

    public gridItemsAdapter(List<CategoriesList> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_layout, null);
        view.setElevation(0);

        ImageView dressImage = view.findViewById(R.id.grid_dressImage);
        TextView dressName = view.findViewById(R.id.grid_dressName);
        TextView dressPrice = view.findViewById(R.id.grid_dressPrice);

        CategoriesList list = itemList.get(position);
        dressName.setText(list.getCartName());
        dressPrice.setText(list.getCartPrice());
        (Glide.with(context).load(list.getCartImage())).placeholder(R.drawable.red_cart).into(dressImage);





        convertView = view;


         return convertView;
        }


}
