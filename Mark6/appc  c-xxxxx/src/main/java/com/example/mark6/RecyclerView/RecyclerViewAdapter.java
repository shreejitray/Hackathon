package com.example.mark6.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mark6.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    List<DataModel> datalist;

    public RecyclerViewAdapter(List<DataModel> datalist){
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_relative_layout, viewGroup, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        CustomViewHolder customViewHolder = (CustomViewHolder) viewHolder;
        DataModel data=datalist.get(i);
        customViewHolder.primaryText.setText(data.getPrimaryText());
        customViewHolder.secondaryText.setText(String.valueOf(data.getSecondaryText()));
        customViewHolder.extra = 22;
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView primaryText,secondaryText;
        int extra = 11;
        public CustomViewHolder(View itemView) {
            super(itemView);
            primaryText=itemView.findViewById(R.id.primarytext);
            secondaryText=itemView.findViewById(R.id.secondarytext);
        }
    }

}
