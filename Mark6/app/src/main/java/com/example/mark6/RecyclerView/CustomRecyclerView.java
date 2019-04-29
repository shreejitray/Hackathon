package com.example.mark6.RecyclerView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mark6.CustomList.CustomAdapter;
import com.example.mark6.CustomList.OnSwipeListner;
import com.example.mark6.R;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerView extends AppCompatActivity {

    List<DataModel> dataModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        setupview();
    }

    private void setupview() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(dataModels);
        recyclerView.setAdapter(recyclerViewAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        OnSwipeListner swipeListner = new OnSwipeListner();
        recyclerView.setOnTouchListener(swipeListner);

        prepareData();
    }

    private void prepareData() {
        for(int i = 0; i<15;i++){
            DataModel dataModel = new DataModel();
            dataModel.setPrimaryText("Primary Text "+i);
            dataModel.setSecondaryText("secondary Text "+i);
            dataModels.add(dataModel);
        }
    }
}
