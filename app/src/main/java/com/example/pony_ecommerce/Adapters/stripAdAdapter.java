package com.example.pony_ecommerce.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pony_ecommerce.modelClass.stripAdModelClass;
import com.example.pony_ecommerce.R;

import java.util.List;

public class stripAdAdapter extends RecyclerView.Adapter<stripAdAdapter.ViewHolder>{
    private List<stripAdModelClass> stripAdList;
    private Context context;

    public stripAdAdapter(List<stripAdModelClass> stripAdList, Context context) {
        this.stripAdList = stripAdList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        stripAdModelClass items = stripAdList.get(position);

        Glide.with(context).load(items.getStripAdImage()).placeholder(R.drawable.red_cart).into(holder.stripAdImage);

        holder.stripAdImage.setBackgroundColor(Color.BLACK);

    }

    @Override
    public int getItemCount() {
        return stripAdList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView stripAdImage;

        public ViewHolder(View itemView){
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.stripAdImage);
        }
    }

}
