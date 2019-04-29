package com.example.mark6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mark6.CustomList.CustomList;
import com.example.mark6.RecyclerView.CustomRecyclerView;
import com.example.mark6.SimpleListView.SimpleList;

public class DynamicList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_list);
    }

    public void simpleList(View view){
        Intent intent = new Intent(this, SimpleList.class);
        startActivity(intent);
    }

    public void customList(View view){
        Intent intent = new Intent(this, CustomList.class);
        startActivity(intent);
    }

    public void recyclerView(View view){
        Intent intent = new Intent(this, CustomRecyclerView.class);
        startActivity(intent);
    }
}
