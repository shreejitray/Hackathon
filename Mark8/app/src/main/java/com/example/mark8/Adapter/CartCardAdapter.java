package com.example.mark8.Adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mark8.DTO.MainContext;
import com.example.mark8.DTO.Product;
import com.example.mark8.R;
import com.squareup.picasso.Picasso;

public class CartCardAdapter extends RecyclerView.Adapter {

    MainContext mainContext;

    public CartCardAdapter(MainContext mainContext){
        this.mainContext = mainContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_list_item, viewGroup, false);
        TextView textView = itemView.findViewById(R.id.productCount);
        textView.setVisibility(View.VISIBLE);
        return new CartCardAdapter.CustomHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        CartCardAdapter.CustomHolder customViewHolder = (CartCardAdapter.CustomHolder) viewHolder;
        Product data=mainContext.getCartList().get(i);
        customViewHolder.price.setText(data.getPrice());
        customViewHolder.name.setText(data.getName());
        Picasso.get().load(data.getImageUrl()).into(customViewHolder.image);
        customViewHolder.count.setText(String.valueOf(data.getCount()));
        customViewHolder.id = data.getId();
        customViewHolder.product = data;
    }

    @Override
    public int getItemCount() {
        return mainContext.getCartList().size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder{

        TextView price;
        TextView name;
        ImageView image;
        TextView count;
        int id;
        Product product;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.productPrice);
            name = itemView.findViewById(R.id.productName);
            image = itemView.findViewById(R.id.productImage);
            count = itemView.findViewById(R.id.productCount);
        }

    }
}
