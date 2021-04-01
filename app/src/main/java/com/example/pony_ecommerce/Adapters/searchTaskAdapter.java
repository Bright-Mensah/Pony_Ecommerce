package com.example.pony_ecommerce.Adapters;

import android.content.Context;
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
import com.example.pony_ecommerce.R;
import com.example.pony_ecommerce.modelClass.searchTask;
import com.example.pony_ecommerce.searchCategoriesFragment.searchCameraAccessoriesFragment;
import com.example.pony_ecommerce.searchCategoriesFragment.searchComputerAccessoriesFragment;
import com.example.pony_ecommerce.searchCategoriesFragment.searchFemaleDressFragment;
import com.example.pony_ecommerce.searchCategoriesFragment.searchFemaleNecklaceFragment;

import java.util.List;

public class searchTaskAdapter extends RecyclerView.Adapter<searchTaskAdapter.ViewHolder> {

    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;

    List<searchTask>taskList;
    Context context;

    public searchTaskAdapter(List<searchTask> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        searchTask task = taskList.get(position);

        holder.catName.setText(task.getCatName());
        Glide.with(context).load(task.getCatIcon()).into(holder.catImage);

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView catName;
        ImageView catImage;


        public ViewHolder(View itemView){
            super(itemView);

            catName = itemView.findViewById(R.id.searchTaskCatTitle);
            catImage = itemView.findViewById(R.id.searchTaskCatImage);


            // when the user clicks on the item that he or she wants to search from display the items

            // set on click for the search list





            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // get position of each item
                    int position = getAdapterPosition();

                    AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();

                    frameLayout = appCompatActivity.findViewById(R.id.home_Framelayout);

                    fragmentManager = appCompatActivity.getSupportFragmentManager();

                    if (position==0) {
                        setFragment(new searchFemaleDressFragment());

                    }
                    if (position == 1){
                        setFragment(new searchFemaleNecklaceFragment());
                    }
                    if (position ==2){
                        setFragment(new searchComputerAccessoriesFragment());
                    }if (position ==3){
                        setFragment(new searchCameraAccessoriesFragment());
                    }


                }
            });



        }



    }
    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment).addToBackStack(null);
        fragmentTransaction.commit();

    }
}
