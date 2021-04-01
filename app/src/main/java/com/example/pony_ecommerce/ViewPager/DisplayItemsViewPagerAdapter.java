package com.example.pony_ecommerce.ViewPager;

import android.content.ContentProviderResult;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.pony_ecommerce.modelClass.DisplayItemModelClass;
import com.example.pony_ecommerce.R;

import java.util.List;

public class DisplayItemsViewPagerAdapter extends PagerAdapter {
    private List<DisplayItemModelClass>itemsList;
    private Context context;

    public DisplayItemsViewPagerAdapter(List<DisplayItemModelClass> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.display_items_viewpager_layout,null);

        ImageView itemsDisplay = view.findViewById(R.id.image_display);

        DisplayItemModelClass items = itemsList.get(position);

        Glide.with(context).load(items.getGetImage()).into(itemsDisplay);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

}
