package com.example.pony_ecommerce.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.pony_ecommerce.R;
import com.example.pony_ecommerce.modelClass.purchaseItemModelClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class purchaseItemAdapter extends RecyclerView.Adapter<purchaseItemAdapter.ViewHolder> {
    private List<purchaseItemModelClass>itemList;
    private Context context;
    private String num;

    public purchaseItemAdapter(List<purchaseItemModelClass> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_item_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        purchaseItemModelClass items = itemList.get(position);
        Glide.with(context).load(items.getImage()).into(holder.itemImage);







//        holder.btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 num = holder.btn.getNumber();
//                holder.product_quantity.setText("quantity = " +holder.btn.getNumber());
//            }
//        });
//
//        holder.location.getSelectedItem().toString();
//
//
//        holder.btnPurchase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (TextUtils.isEmpty(holder.phone.getText().toString())){
//                    holder.phone.setError("Please enter phone number");
//
//                }
//                else{
//
//                    Dialog dialog = new Dialog(v.getContext());
//
//                    dialog.setContentView(R.layout.confirm_purchase);
//
//
//
//                    TextView totalPrice = dialog.findViewById(R.id.confirmTotalPrice);
//                    TextView location = dialog.findViewById(R.id.confirmLocation);
//                    TextView phone = dialog.findViewById(R.id.confirmPhone);
//                    Button confirmOrder = dialog.findViewById(R.id.btnConfirmOrder);
//                    CircleImageView redOrder = dialog.findViewById(R.id.red_order_image);
//
//
//                    confirmOrder.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                                totalPrice.setVisibility(View.VISIBLE);
//                                location.setVisibility(View.VISIBLE);
//                                phone.setVisibility(View.VISIBLE);
//                                confirmOrder.setVisibility(View.VISIBLE);
//                                redOrder.setVisibility(View.INVISIBLE);
//
//                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//
//                                reference.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                        HashMap<String,Object>userMap = new HashMap<>();
//                                        userMap.put("total Price",totalPrice);
//
//
//                                        reference.child("OrderedItems").updateChildren(userMap);
//
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
//                            }
//
//                    });
//
//
//                    phone.setText("Phone= " +holder.phone.getText().toString());
//
//                    location.setText("Location= " +holder.location.getSelectedItem().toString());
//
//
//                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//                    Query query = reference.child("categoryItems").child("categoryFemaleDress").orderByKey().limitToFirst(1);
//
//                    query.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//
//                                String price = dataSnapshot.child("price").getValue().toString();
//                                totalPrice.setText(String.valueOf("Total Price= " +Integer.parseInt(price) * Integer.parseInt(holder.btn.getNumber())));
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//
//                    dialog.show();
//
//
//
//
//                }
//
//
//
//
//
//
//
//
//            }
//        });
//
//
//
//
//
//



    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView  productPrice,productName;






        public ViewHolder(View itemView){
            super(itemView);
            itemImage = itemView.findViewById(R.id.purchaseImage);


        }
    }

}
