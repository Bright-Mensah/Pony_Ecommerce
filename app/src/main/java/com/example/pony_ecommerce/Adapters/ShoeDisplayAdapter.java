package com.example.pony_ecommerce.Adapters;

import android.content.Context;
import android.graphics.Color;
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
import com.example.pony_ecommerce.Fragments.SignUpFragment;
import com.example.pony_ecommerce.modelClass.shoeDisplayModelClass;
import com.example.pony_ecommerce.R;

import java.util.List;

public class ShoeDisplayAdapter extends RecyclerView.Adapter<ShoeDisplayAdapter.ViewHolder> {
    private List<shoeDisplayModelClass> shoeList;
    private Context context;
    private FragmentManager fragmentManager;
    private FrameLayout frameLayout;

    public ShoeDisplayAdapter(List<shoeDisplayModelClass> shoeList, Context context) {
        this.shoeList = shoeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoes_display_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        shoeDisplayModelClass items = shoeList.get(position);

        holder.shoeName.setText(items.getShoeName());
        holder.shoePrice.setText(items.getShoePrice());

        Glide.with(context).load(items.getShoeImage()).placeholder(R.drawable.red_cart).into(holder.shoeImage);
        holder.shoeImage.setBackgroundColor(Color.GREEN);


    }

    @Override
    public int getItemCount() {
        return shoeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView shoeImage;
        TextView shoeName, shoePrice;

        public ViewHolder(View itemView) {
            super(itemView);
            shoeImage = itemView.findViewById(R.id.shoe_image);
            shoeName = itemView.findViewById(R.id.shoeName);
            shoePrice = itemView.findViewById(R.id.shoePrice);


            // set on clickListener for shoes

        }
    }
}
