package com.example.mark6.CustomList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.mark6.R;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<DataModel> {
    int layoutResource;
    Context context;
    ArrayList<DataModel> dataModels;
    public CustomAdapter(ArrayList<DataModel> data , Context context){
        super(context, R.layout.custom_list,data);
        this.layoutResource = R.layout.custom_list;
        this.context = context;
        dataModels = data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(layoutResource,parent,false);
        }
       DataModel dataModel = dataModels.get(position);
        TextView name = listItem.findViewById(R.id.name);
        TextView age = listItem.findViewById(R.id.age);
        TextView experience = listItem.findViewById(R.id.experience);
        name.setText(dataModel.getName());
        age.setText(dataModel.getAge());
        experience.setText(dataModel.getExperience());
         return listItem;

    }
}
